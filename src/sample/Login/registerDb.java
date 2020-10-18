package sample.Login;

import sample.DatabaseHandler;

import java.sql.*;

public class registerDb extends DatabaseHandler {
    private ResultSet rs;

    public boolean CheckForExist(String email)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String query = "SELECT email FROM users WHERE email=?";
        String uname;
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            rs = preparedStatement.executeQuery();
            if (rs.next()){
                return true;
            }
            return false;
        }catch (SQLException E){
            return true;
        }finally {
            try {
                connection.close();
                preparedStatement.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean registerUser(String Username,String Password,String Email)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String pass = getMd5(Password);
        String query = "INSERT INTO users VALUES(?,?,?)";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,Username);
            preparedStatement.setString(2,pass);
            preparedStatement.setString(3,Email);
            preparedStatement.execute();
            return true;
        }catch (SQLException e){
            return false;
        }finally {
            try {
                connection.close();
                preparedStatement.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
