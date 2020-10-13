package sample.Login;

import sample.DatabaseHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginDb extends DatabaseHandler {
    protected Connection connection;
    protected PreparedStatement preparedStatement;
    protected ResultSet rs;

    public loginDb(){
        connection = getConnection();
    }

    public boolean login(String uname,String pass)
    {
        String password = getMd5(pass);
        String query = "SELECT * FROM Users WHERE username=? and password = ?";
        try {
            preparedStatement= connection.prepareStatement(query);
            preparedStatement.setString(1,uname);
            preparedStatement.setString(2,password);
            rs = preparedStatement.executeQuery();
            if (rs.next())
                return true;
            return false;
        }catch (SQLException E){
            return false;
        }
    }

}
