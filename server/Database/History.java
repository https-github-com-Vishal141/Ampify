/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class History {
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
    
    public boolean addToHistory(String uname,int id,String time,String date,String hour)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        int count=0;
        String query = "SELECT COUNT(*) FROM History";
        String query1 = "INSERT INTO History VALUES(?,?,?,?,?,?)";
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while(rs.next())
                 count = rs.getInt("COUNT(*)");
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1,count+1);
            preparedStatement.setString(2,uname);
            preparedStatement.setInt(3,id);
            preparedStatement.setString(4,date);
            preparedStatement.setString(5,time);
            preparedStatement.setString(6,hour);
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
    
    public ArrayList[] getHistory(String user)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ArrayList[] history = new ArrayList[4];
        ArrayList<Integer> Ids = new ArrayList<Integer>();
        ArrayList<String> title = new ArrayList<String>();
        ArrayList<String> date = new ArrayList<String>();
        ArrayList<String> time = new ArrayList<String>();
        String query = "SELECT songId,date,time FROM history WHERE username=? ORDER BY Serial_no DESC";
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                Ids.add(rs.getInt("songId"));
                date.add(rs.getString("date"));
                time.add(rs.getString("time"));
            }
            for(int id:Ids)
                title.add(getSongTitle(id));
            history[0]=Ids;
            history[1]=title;
            history[2]=date;
            history[3]=time;
            return history;
        }catch(SQLException e){
            return history;
        }finally {
            try {
                connection.close();
                preparedStatement.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public String getSongTitle(int id)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String title="";
        String query = "SELECT Title FROM Songs WHERE Id=?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            rs = preparedStatement.executeQuery();
            while (rs.next())
                title = rs.getString("Title");
            return title;

        }catch (SQLException e){
            return null;
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
    
    public void addToSearchHistory(String uname,String searchedItem)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String query = "INSERT INTO searchhistory VALUES(?,?)";
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,uname);
            preparedStatement.setString(2,searchedItem);
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
    
    public ArrayList<String> getserchHistory(String uname){
        ArrayList<String> searched = new ArrayList<String>();
        Connection connection = null;
        PreparedStatement pr = null;
        String query = "SELECT searchedItem FROM searchhistory WHERE username=?";
        try{
            connection = getConnection();
            pr = connection.prepareStatement(query);
            pr.setString(1, uname);
            rs = pr.executeQuery();
            while(rs.next())
                searched.add(rs.getString("searchedItem"));
            return searched;
        }catch(SQLException e){
            e.printStackTrace();
            return searched;
        }finally {
            try {
                connection.close();
                pr.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public Set<Integer> getSearchResult(String item)
    {
        Set<Integer> id = new HashSet<Integer>();
        Connection connection=null;
        PreparedStatement pr = null;
        String query  = "SELECT Id FROM songs WHERE Title=? OR Artist=? OR Album=?";
        try{
            connection= getConnection();
            pr = connection.prepareStatement(query);
            pr.setString(1, item);
            pr.setString(2, item);
            pr.setString(3, item);
            rs = pr.executeQuery();
            while(rs.next())
                id.add(rs.getInt("Id"));
            return id;
        }catch(SQLException e){
            return id;
        }finally {
            try {
                connection.close();
                pr.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
