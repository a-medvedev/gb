package gbook;

import java.sql.Date;

public class Record {
    private long date;
    private int id;
    private String user, message, strDate;

    public void setStrDate(){
        strDate = new Date(date).toString();
    }

    public String getStrDate(){
        return strDate;
    }

    public void setDate(long d){
        date = d;
        setStrDate();
    }

    public long getDate(){
        return date;
    }

    public void setId(int i){
        id = i;
    }

    public long getId(){
        return id;
    }

    public void setUser(String u){
        user = u;
    }

    public String getUser(){
        return user;
    }

    public void setMessage(String mess){
        message = mess;
    }

    public String getMessage(){
        return message;
    }
}
