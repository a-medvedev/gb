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

        String query = "jdbc:h2:mem:posts", login = "gb_admin", password = "test";
        try {
            //Class.forName("org.h2.Driver");
            link = ds.getConnection();
            link.createStatement().executeUpdate(
                    "CREATE TABLE `posts`" +
                            "(`id` INT NOT NULL AUTO_INCREMENT," +
                            "`user` TEXT," +
                            "`post` TEXT," +
                            "`date` FLOAT," +
                            "PRIMARY KEY  (`id`))"
            );
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
                addStatement.setLong(2, new Date().getTime());
                addStatement.setString(1, name);
                addStatement.setString(3, message);
                addStatement.execute();
            } catch (SQLException e) {
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
                r.id = rs.getInt("id");
                r.date = rs.getLong("date");
                r.message = rs.getString("post");
                r.user = rs.getString("user");
                records.add(r);
            }
        } catch (SQLException e) {
            //System.out.println("Произошла ошибка чтения сообщений.");
        }


        request.setAttribute("text1", text);
        request.setAttribute("records", records);
        request.getRequestDispatcher("/test.jsp").forward(request, response);
    }
}
