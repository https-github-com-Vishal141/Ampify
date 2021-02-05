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

/**
 *
 * @author hp
 */
public class Playlist {
    
    private String url="jdbc:mysql://localhost/ampify?characterEncoding=utf-8";
    private ResultSet rs;


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
    
    
    public boolean createPlaylist(String uname,String name)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query1 = "INSERT INTO playlists VALUES(?,?)";
        String query2 = "CREATE TABLE "+uname+"_"+name+"(Id int primary key)";
        try{
            if(checkPlaylist(uname,name))
                return false;
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setString(1,uname);
            preparedStatement.setString(2,name);
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.execute();
            return true;
        }catch(SQLException e){
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
    
    public boolean addToPlaylist(String user,String name,int id)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null; 
        String query = "INSERT "+user+"_"+name+" VALUES (?)";
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
            return true;
        }catch(SQLException e){
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
    
    public Set<Integer> getPlaylist(String user,String name)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null; 
        Set<Integer> Ids = new HashSet<Integer>();
        String query = "SELECT * FROM "+user+"_"+name;
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while(rs.next())
                Ids.add(rs.getInt("Id"));
            return Ids;
        }catch(SQLException e){
            return Ids;
        }finally {
            try {
                connection.close();
                preparedStatement.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public ArrayList<String> getPlaylists(String uname)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ArrayList<String> names = new ArrayList<String>();
        String query = "SELECT name FROM playlists WHERE username=?";
         try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);     
            preparedStatement.setString(1,uname);
            rs = preparedStatement.executeQuery();
            while(rs.next())
                names.add(rs.getString("name"));
            return names;
        }catch (Exception e){
            return names;
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
    
    
    public boolean checkPlaylist(String uname,String name)
    {
        Connection connection=null;
        PreparedStatement ps=null;
        String query = "SELECT * FROM playlists WHERE username=? AND name=?";
        try{
            connection=getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1,uname);
            ps.setString(2,name);
            rs = ps.executeQuery();
            if(rs.next())
                return true;
            return false;
        }catch(SQLException e){
            e.printStackTrace();
            return true;
        }finally {
            try {
                connection.close();
                ps.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void sharePlaylist(String uname1,String pname,String uname2)
    {
        Connection connection=null;
        PreparedStatement ps=null;
        String query = "INSERT INTO "+uname2+"_"+pname+" SELECT * FROM "+uname1+"_"+pname;
        if(createPlaylist(uname2,pname))
        {
            try
           {
               connection = getConnection();
               ps = connection.prepareStatement(query);
               ps.execute();
           }catch(SQLException e){
               e.printStackTrace();
           }finally {
               try {
                   connection.close();
                   ps.close();
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
        }
    }
    
    public void deleteSong(String uname,String pName,int id)
    {
        Connection connection= null;
        PreparedStatement ps = null;
        String query = "DELETE FROM "+uname+"_"+pName+" WHERE Id=?" ;
        try
        {
            connection = getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
               try {
                   connection.close();
                   ps.close();
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
    }
    
    public void deletePlaylist(String uname,String pName)
    {
        Connection connection = null;
        PreparedStatement ps = null;
        String query1 = "DELETE FROM Playlists WHERE username=? AND name=?";
        String query2 = "DROP TABLE "+uname+"_"+pName;
        try
        {
            connection = getConnection();
            ps = connection.prepareStatement(query1);
            ps.setString(1,uname);
            ps.setString(2,pName);
            ps.execute();
            ps = connection.prepareStatement(query2);
            ps.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
               try {
                   connection.close();
                   ps.close();
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
    }
    
}
