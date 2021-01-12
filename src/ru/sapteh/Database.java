package ru.sapteh;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public final String Driver = "com.mysql.cj.jdbc.Driver";
    public final String URL = "jdbc:mysql://localhost:3306/users?serverTimezone = UTC";
    public final String Login = "root";
    public final String Password = "1234";


    public Connection getConnect(){
        try {
            Class.forName(Driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL,Login,Password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
    public void closeConnection(Connection connection){
        if (connection == null){
            return;
        }else {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


}
