package ru.sapteh;

import ru.sapteh.model.Users;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String[] args) throws SQLException, IOException {
        Database database = new Database();
        Connection connection = database.getConnect();

        List<Users> usersList = new ArrayList<>();

        //Достали данный из бд и вывели их на экран
        String sqlSelect = "SELECT * FROM " + Users.TABLE_NAME;
        try(PreparedStatement statement = connection.prepareStatement(sqlSelect)){
            ResultSet resultSet = statement.executeQuery();

            Users user = null;
            while(resultSet.next()){
                int id = resultSet.getInt(Users.ID_COLUMN);
                String firstName = resultSet.getString(Users.FIRST_NAME_COLUMN);
                String login = resultSet.getString(Users.LOGIN_COLUMN);
                String password = resultSet.getString(Users.PASSWORD_COLUMN);
                user = new Users(id,firstName,login,password);
                usersList.add(user);
            }
            for (Users users : usersList){
                System.out.println(users);
            }
        }

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Введите операцию -c , -u , -d");
            String crud = reader.readLine();
            //Добовление данных в БД
            if (crud.equals("-c")){
                System.out.println("Input FirstName");
                String firstName = reader.readLine();
                System.out.println("Input login");
                String login = reader.readLine();
                System.out.println("Input password");
                String password = reader.readLine();

                Users users = new Users(usersList.size() + 1,firstName,login,password);

                try {
                    String sqlInsert = String.format("INSERT INTO %s (%s,%s,%s) VALUES (?,?,?)", Users.TABLE_NAME, Users.FIRST_NAME_COLUMN, Users.LOGIN_COLUMN, Users.PASSWORD_COLUMN);

                    PreparedStatement statement = connection.prepareStatement(sqlInsert);
                    statement.setString(1, users.getFirstName());
                    statement.setString(2, users.getLogin());
                    statement.setString(3, users.getPassword());
                    int i = statement.executeUpdate();
                    if (i == 1) {
                        System.out.println("Данные добавились");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            //Удаление строчки в БД
            if (crud.equals("-d")){
                System.out.println("Введите id колонки");
                int id = Integer.parseInt(reader.readLine());

                String sqlDelete = String.format("DELETE FROM %s WHERE %s = %d",Users.TABLE_NAME,Users.ID_COLUMN,id);

                PreparedStatement statement = connection.prepareStatement(sqlDelete);
                int i = statement.executeUpdate();
                if (i == 1){
                    System.out.println("Данные удалены");
                }

            }
            //Изменение строчки в БД
            if (crud.equals("-u")){

                System.out.println("Введите id колонки");
                int id = Integer.parseInt(reader.readLine());
                System.out.println("Input FirstName");
                String firstName = reader.readLine();
                System.out.println("Input login");
                String login = reader.readLine();
                System.out.println("Input password");
                String password = reader.readLine();

                String sqlUpdate = String.format("UPDATE %s SET %s=?,%s=?,%s=? WHERE %s = %d",Users.TABLE_NAME,Users.FIRST_NAME_COLUMN,Users.LOGIN_COLUMN,Users.PASSWORD_COLUMN,Users.ID_COLUMN,id);

                PreparedStatement statement = connection.prepareStatement(sqlUpdate);
                statement.setString(1,firstName);
                statement.setString(2,login);
                statement.setString(3,password);

                for (Users users : usersList){
                    if (id == users.getId()){
                        users.setFirstName(firstName);
                        users.setLogin(login);
                        users.setPassword(password);
                    }
                }

                int i = statement.executeUpdate();
                if (i == 1 ){
                    System.out.println("Данные обновлены ");
                }
                for (Users user : usersList){
                    System.out.println(user);
                }
            }
        }
    }
}
