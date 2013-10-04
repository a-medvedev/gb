package gbook;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet (urlPatterns = "/book")
public class Book extends HttpServlet{

    @Resource(name = "jdbc/testDS")
    private DataSource ds;

    private Connection link;
    private PreparedStatement addStatement;
    private PreparedStatement listStatement;

    @Override
    public void init(){
        try {
            //Class.forName("org.h2.Driver");
            link = ds.getConnection();
            DatabaseMetaData dbm = link.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "posts", null);
            if (!tables.next()) {
                link.createStatement().executeUpdate(
                        "CREATE TABLE `posts`" +
                                "(`id` INT NOT NULL AUTO_INCREMENT," +
                                "`user` TEXT," +
                                "`post` TEXT," +
                                "`date` FLOAT," +
                                "PRIMARY KEY  (`id`))"
                );
            }
            addStatement = link.prepareStatement("INSERT INTO posts (user, date, post) VALUES (?, ?, ?)");
            listStatement = link.prepareStatement("SELECT * FROM posts");

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){

        String name = (String) request.getParameter("name");
        String message = (String) request.getParameter("message");

        if (!name.isEmpty() && !message.isEmpty()){
            try{
                addStatement.setLong(2, new java.util.Date().getTime());
                addStatement.setString(1, name);
                addStatement.setString(3, message);
                addStatement.execute();
                response.sendRedirect("http://localhost:8080/gb/book");
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else {
            try {
                response.sendRedirect("http://localhost:8080/gb/book");
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String text = "HELLO!";
        //Запрет кеширования
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control","no-store");
        String text = null;
        text = link.toString();

        List<Record> records = new ArrayList<Record>();
        try {
            ResultSet rs = listStatement.executeQuery();
            while(rs.next()){
                Record r = new Record();
                r.setId(rs.getInt("id"));
                r.setDate(rs.getLong("date"));
                r.setMessage(rs.getString("post"));
                r.setUser(rs.getString("user"));
                records.add(r);
            }
        } catch (SQLException e) {
            System.err.println("Произошла ошибка чтения сообщений. Err");
            System.out.println("Произошла ошибка чтения сообщений. Out");
        }  catch (NullPointerException e){

        }


        request.setAttribute("text1", text);
        request.setAttribute("records", records);
        request.getRequestDispatcher("/book.jsp").forward(request, response);
    }
}
