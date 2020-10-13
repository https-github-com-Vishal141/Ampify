package sample.Login;

import sample.DatabaseHandler;

import java.sql.*;

public class registerDb extends DatabaseHandler {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet rs;

    public registerDb(){
        connection = getConnection();
    }

    public boolean CheckForExist(String email)
    {
        String query = "SELECT email FROM users WHERE email=?";
        String uname;
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            rs = preparedStatement.executeQuery();
            if (rs.next()){
                return true;
            }
            return false;
        }catch (SQLException E){
            return true;
        }
    }

    public boolean registerUser(String Username,String Password,String Email)
    {
        String pass = getMd5(Password);
        String query = "INSERT INTO users VALUES(?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,Username);
            preparedStatement.setString(2,pass);
            preparedStatement.setString(3,Email);
            preparedStatement.execute();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

}
