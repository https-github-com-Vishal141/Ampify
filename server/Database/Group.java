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
import java.util.LinkedHashSet;
import java.util.Set;

public class Group {
    
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
    
    public boolean createGroup(String admin,String name)
    {
        Connection connection=null;
        PreparedStatement ps=null;
        String query = "INSERT INTO groupsTable(admin,name,username) VALUES(?,?,?)";
         try
           {
               if(checkGroup(name))
                   return false;
               connection = getConnection();
               ps = connection.prepareStatement(query);
               ps.setString(1, admin);
               ps.setString(2, name);
               ps.setString(3, admin);
               ps.execute();
               return true;
           }catch(SQLException e){
               e.printStackTrace();
               return false;
           }finally {
               try {
                   if(!checkGroup(name))
                   {
                       connection.close();
                       ps.close();
                   }
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
    }
    
    public Set<String> getGroups(String uname)
    {
        Set<String> groups = new LinkedHashSet<String>();
        Connection connection=null;
        PreparedStatement ps = null;
        String query = "SELECT name FROM groupsTable WHERE username=?";
        try{
            connection = getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1,uname);
            rs = ps.executeQuery();
            while(rs.next())
                groups.add(rs.getString("name"));
            return groups;
        }catch(SQLException e)
        {
            e.printStackTrace();
            return groups;
        }finally {
               try {
                   connection.close();
                   ps.close();
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
    }
    
    public boolean checkUser(String uname)
    {
        Connection connection=null;
        PreparedStatement ps = null;
        String query = "SELECT username FROM users WHERE username=?";
        try{
            connection = getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1,uname);
            rs = ps.executeQuery();
            if(rs.next())
                return true;
            return false;
        }catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }finally {
            try {
                connection.close();
                ps.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void addUser(String gName,String uname)
    {
        Connection connection = null;
        PreparedStatement ps = null;
        String query="INSERT INTO groupsTable(name,username) VALUES(?,?)";
        try{
            connection = getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1,gName);
            ps.setString(2,uname);
            ps.execute();
        }catch(SQLException e)
        {
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
    
    public Set<String> getUsers(String name)
    {
        Set<String> users = new LinkedHashSet<String>();
        Connection connection = null;
        PreparedStatement ps = null;
        String query = "SELECT username FROM groupsTable WHERE name=?";
        try{
            connection = getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1,name);
            rs = ps.executeQuery();
            while(rs.next())
                users.add(rs.getString("username"));
            return users;
        }catch(SQLException e)
        {
            e.printStackTrace();
            return users;
        }finally {
               try {
                   connection.close();
                   ps.close();
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
    }
    
    public boolean checkGroup(String name)
    {
        Connection connection=null;
        PreparedStatement ps = null;
        String query = "SELECT admin FROM groupsTable WHERE name=?";
        try{
            connection = getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1,name);
            rs = ps.executeQuery();
            if(rs.next())
                return true;
            return false;
        }catch(SQLException e)
        {
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
    
    public boolean checkForAdmin(String uname,String gName)
    {
        Connection connection=null;
        PreparedStatement ps = null;
        String query="SELECT admin,name FROM groupsTable WHERE admin=? AND name=?";
        try{
            connection = getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1,uname);
            ps.setString(2,gName);
            rs = ps.executeQuery();
            if(rs.next())
                return true;
            return false;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }finally {
               try {
                   connection.close();
                   ps.close();
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
    }
    
    public void deleteMember(String uname,String gName)
    {
        Connection connection = null;
        PreparedStatement ps = null;
        String query= "DELETE FROM groupsTable WHERE name=? AND username=?";
        try{
            connection = getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1,gName);
            ps.setString(2,uname);
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
