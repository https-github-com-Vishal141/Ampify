package sample;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DatabaseHandler {
    private String url="jdbc:mysql://localhost/ampify?characterEncoding=utf-8";
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet rs;

//    public DatabaseHandler(){
//        connection = getConnection();
//    }

    public Connection getConnection()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,"root","141252");
            return connection;
        }
        catch (Exception E)
        {
           // E.printStackTrace();
            return null;
        }
    }

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

    public boolean insertIntoUsers(String uname,String pass,String email)
    {
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
            close();
        }
    }

    public boolean insertUserDetail(String uname,String language,String genere,String artist)
    {
        String query1 = "INSERT INTO Languages VALUES(?,?)";
        String query2 = "INSERT INTO Generes VALUES(?,?)";
        String query3 = "INSERT INTO Artists VALUES(?,?)";
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setString(1,uname);
            preparedStatement.setString(2,language);
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1,uname);
            preparedStatement.setString(2,genere);
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement(query3);
            preparedStatement.setString(1,uname);
            preparedStatement.setString(2,artist);
            preparedStatement.execute();
            return true;
        }catch (Exception e){
            return false;
        }
        finally {
            close();
        }
    }

    public boolean likeOrDislike(String uname,String status,int id)
    {
        String query = "INSERT INTO ? VALUES(?,?)";
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,status);
            preparedStatement.setString(2,uname);
            preparedStatement.setInt(3,id);
            preparedStatement.execute();
            return true;
        }catch (Exception e){
            return false;
        }
        finally {
            close();
        }
    }

    public boolean addToHistory(String uname,int id,String time)
    {
        String query = "SELECT COUNT(*) FROM History";
        String query1 = "INSERT INTO History VALUES(?,?,?,?)";
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            int count = rs.getInt("COUNT(*)");
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1,count+1);
            preparedStatement.setString(2,uname);
            preparedStatement.setInt(3,id);
            preparedStatement.setString(4,time);
            preparedStatement.execute();
            return true;
        }catch (Exception e){
            return false;
        }
        finally {
            close();
        }
    }

    public byte[] get_Song(int id)
    {
        String query = "SELECT song FROM Songs WHERE Id=?";
        try{
            connection = getConnection();
            Blob blob = null;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                blob = rs.getBlob("File");
            }
            byte song[] = blob.getBytes(1,(int)blob.length());
            return song;
        }catch (Exception e){
            return null;
        }
        finally {
            close();
        }
    }

    public int getSongId(String title)
    {
        String query = "SELECT Id FROM Songs WHERE Title=?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,title);
            return preparedStatement.executeQuery().getInt("Id");
        }catch (SQLException e){
            return 0;
        }
        finally {
            close();
        }
    }

    public String getSongTitle(int id)
    {
        String query = "SELECT Title FROM Songs WHERE Id=?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            return preparedStatement.executeQuery().getString("Title");
        }catch (SQLException e){
            return null;
        }
        finally {
            close();
        }
    }

    public String getSongArtist(int id)
    {
        String query = "SELECT Artist FROM Songs WHERE Id=?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            return preparedStatement.executeQuery().getString("Artist");
        }catch (SQLException e){
            return null;
        }
        finally {
            close();
        }
    }

    public String getSongAlbum(int id)
    {
        String query = "SELECT Album FROM Songs WHERE Id=?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            return preparedStatement.executeQuery().getString("Album");
        }catch (SQLException e){
            return null;
        }
        finally {
            close();
        }
    }

    public void addToSearchHistory(String uname,String searchedItem)
    {
        String query = "INSERT INTO searchedHistory VALUES(?,?)";
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,uname);
            preparedStatement.setString(2,searchedItem);
            preparedStatement.execute();
        }catch (SQLException e){

        }finally {
            close();
        }
    }

    public void close()
    {
        try {
            connection.close();
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

