package ru.sapteh.model;

import java.util.Objects;

//POJO
public class Users {
    public static final String TABLE_NAME = "user";
    public static final String ID_COLUMN = "id";
    public static final String FIRST_NAME_COLUMN = "first_name";
    public static final String LOGIN_COLUMN = "login";
    public static final String PASSWORD_COLUMN = "pasword";
    private int id;
    private String firstName;
    private String login;
    private String password;

    public Users(){}

    public Users(int id,String firstName,String login,String password){
        this.id = id;
        this.firstName = firstName;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return String.format("%d %s %s %s",getId(),getFirstName(),getLogin(),getPassword());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return id == users.id &&
                Objects.equals(firstName, users.firstName) &&
                Objects.equals(login, users.login) &&
                Objects.equals(password, users.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, login, password);
    }
}
