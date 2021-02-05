/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Database;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author hp
 */
public class LoginAndRegister {
    private String url="jdbc:mysql://localhost/ampify?characterEncoding=utf-8";
    private ResultSet rs;
    
    //Connect to database.
    public Connection getConnection()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,"root","141252");
            return connection;
        }
        catch (Exception E)
        {
            E.printStackTrace();
            return null;
        }
    }
    
    //encrypt the password.
    public String getMd5(String input)
    {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1,messageDigest);
            String hashText = no.toString(16);
            while (hashText.length()<32)
            {
                hashText = "0"+hashText;
            }
            return hashText;
        }catch (NoSuchAlgorithmException e){
            return "";
        }
    }

    //Login user in 
    public boolean login(String uname,String pass)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String password = getMd5(pass);
        String query = "SELECT username FROM users WHERE username=? AND password=?";
        String query_ = "UPDATE users SET isLogin=1 WHERE username=?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,uname);
            preparedStatement.setString(2,password);
            rs = preparedStatement.executeQuery();
            if (rs.next())
            {
                preparedStatement = connection.prepareStatement(query_);
                preparedStatement.setString(1,uname);
                preparedStatement.execute();
                return true;
            }
            return false;
        }catch (SQLException e){
            return false;
        }finally {
           try {
               connection.close();
               preparedStatement.close();
           }catch (SQLException e){

           }
        }
    }

    //check is user login or not
    public boolean checkLogin(String username){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String query_ = "SELECT isLogin FROM users WHERE username=?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query_);
            preparedStatement.setString(1,username);
            rs = preparedStatement.executeQuery();
            if (rs.next())
            {
                return true;
            }
            return false;
        }catch (SQLException e){
            return false;
        }finally {
           try {
               connection.close();
               preparedStatement.close();
           }catch (SQLException e){

           }
        }
    }
    
    //user log out
    public void logout(String username){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String query_ = "UPDATE users SET isLogin=0 WHERE username=?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query_);
            preparedStatement.setString(1,username);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
           try {
               connection.close();
               preparedStatement.close();
           }catch (SQLException e){
               e.printStackTrace();
           }
        }
    }
    
    //check if email already exist or not.
    public boolean checkEmail(String email)
    {
        Connection connection=null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT username FROM users WHERE email=?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            rs = preparedStatement.executeQuery();
            if (rs.next())
                return true;
            return false;
        }catch (SQLException e){
            return true;
        }finally {
            try {
                connection.close();
                preparedStatement.close();
            }catch (SQLException e){

            }
        }
    }

    //create a new account.
    public boolean register(String uname,String pass,String email)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String password = getMd5(pass);
        String query = "INSERT INTO Users VALUES (?,?,?)";
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,uname);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,email);
            preparedStatement.execute();
            return true;
        }catch (Exception e){
            return false;
        }
        finally {
            try {
                connection.close();
                preparedStatement.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    //insert languages selected by users.
    public void insertIntolanguage(String uname,String language)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String query1 = "INSERT INTO Languages VALUES(?,?)";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setString(1,uname);
            preparedStatement.setString(2,language);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                connection.close();
                preparedStatement.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //insert generes selected by users.
    public void insertIntoGeneres(String uname,String genere)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String query2 = "INSERT INTO Generes VALUES(?,?)";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1,uname);
            preparedStatement.setString(2,genere);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                connection.close();
                preparedStatement.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void insertIntoArtist(String uname,String artist)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String query3 = "INSERT INTO Artists VALUES(?,?)";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query3);
            preparedStatement.setString(1,uname);
            preparedStatement.setString(2,artist);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
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
