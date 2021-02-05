/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Database;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import server.clientHandler;

public class Song {
    
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
    
    public byte[] get_Song(int id)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
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
            try {
                connection.close();
                preparedStatement.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public ArrayList<String> getAllSong()
    {
        ArrayList<String> songs= new ArrayList<String>();
        ArrayList<String> artist = new ArrayList<String>();
        Connection connection=null;
        PreparedStatement pr=null;
        String query = "SELECT Title,Artist FROM songs";
        try{
            connection = getConnection();
            pr = connection.prepareStatement(query);
            rs = pr.executeQuery();
            while(rs.next())
            {
                songs.add(rs.getString("Title"));
            }
            return songs;
        }catch(SQLException e){
            return songs;
        }finally {
            try {
                connection.close();
                pr.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public int getSongId(String title)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
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

    public String getSongArtist(int id)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String artist="";
        String query = "SELECT Artist FROM Songs WHERE Id=?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            rs =  preparedStatement.executeQuery();
            while (rs.next())
                artist = rs.getString("Artist");
            return artist;
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

    public String getSongAlbum(int id)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String album = "";
        String query = "SELECT Album FROM Songs WHERE Id=?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            rs= preparedStatement.executeQuery();
            while (rs.next())
                album = rs.getString("Album");
            return album;
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
    
    public boolean checkForLike(String uname,int id)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String query = "SELECT * FROM LIKED WHERE username=? AND songId=?";
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,uname);
             preparedStatement.setInt(2,id);
             rs = preparedStatement.executeQuery();
             if(rs.next())
                 return true;
             return false;
        }catch(SQLException e)
        {
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
    
     public boolean checkForDisLike(String uname,int id)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String query = "SELECT * FROM disliked WHERE username=? AND songId=?";
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,uname);
             preparedStatement.setInt(2,id);
             rs = preparedStatement.executeQuery();
             if(rs.next())
                 return true;
             return false;
        }catch(SQLException e)
        {
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
     
    public void deleteFrom(String status,String uname,int id)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String query = "DELETE FROM "+status+" WHERE username=? AND songId=?";
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,uname);
            preparedStatement.setInt(2,id);
            preparedStatement.execute();          
        }catch (Exception e){
            
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

    public boolean likeOrDislike(String uname,String status,int id)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String query;
        try{
            connection = getConnection();
            if(status.equals("liked"))
            {
                query = "INSERT INTO liked VALUES(?,?)";
                if(checkForDisLike(uname,id))
                    deleteFrom("disliked",uname,id);
                else{
                    if(checkForLike(uname,id))
                        return true;
                }
            }
            else{
                query = "INSERT INTO disliked VALUES(?,?)";
                if(checkForLike(uname,id))
                    deleteFrom("liked",uname,id);
                else{
                    if(checkForDisLike(uname,id))
                        return true;
                }
            }
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,uname);
            preparedStatement.setInt(2,id);
            preparedStatement.execute();
            return true;
        }catch (Exception e){
             e.printStackTrace();
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
    
    
    public void increaseView(int songId)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        int total=0;
        String query1 = "SELECT Views FROM Songs WHERE Id=?";
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1,songId);
            rs = preparedStatement.executeQuery();
            if(rs.next())
                total = rs.getInt("Views");
            String query = "UPDATE Songs SET Views=? WHERE Id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, total+1);
            preparedStatement.setInt(2,songId);
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
    
    public Set<String> getLiked(String uname)
    {
        ResultSet rs2;
        Set<String> likes = new LinkedHashSet<String>();
        Connection connection=null;
        PreparedStatement ps = null;
        String query = "SELECT songId FROM liked WHERE username=?";
        try{
            connection = getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1,uname);
            rs2 = ps.executeQuery();
            while(rs2.next())
            {
                int id = rs2.getInt("songId");
                likes.add(getSongTitle(id));
            }
            return likes;
        }catch(SQLException e)
        {
            e.printStackTrace();
           return likes;
        }finally {
               try {
                   connection.close();
                   ps.close();
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
    }
    
    public ArrayList<Integer> getTrending()
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        int i;
        ArrayList<Integer> Ids = new ArrayList<Integer>();
        String query = "SELECT Id FROM Songs ORDER BY Views DESC";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            int j=1;
            while (rs.next())
            {
                i = rs.getInt("Id");
                Ids.add(i);
                if (j==10)
                    break;
                j++;
            }
            return Ids;
        }catch (SQLException e){
            //Ids.add(5);
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

    public Set<Integer> getRecent(String uname)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        Set<Integer> Ids = new LinkedHashSet<Integer>();
        String query = "SELECT songId FROM History WHERE username=? ORDER BY serial_no DESC";
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,uname);
            rs = preparedStatement.executeQuery();
            while (rs.next())
                Ids.add(rs.getInt("songId"));
            return Ids;
        }catch (SQLException e){
            e.printStackTrace();
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

    public Set<Integer> getRecommendedOnTime(String hour,String uname)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        Set<Integer> Ids = new LinkedHashSet<Integer>();
        ArrayList<Integer> ids = new ArrayList<Integer>();
        String query = "SELECT songId FROM history WHERE hour<? AND hour>? AND username=?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,Integer.parseInt(hour)+1+"");
            preparedStatement.setString(2,Integer.parseInt(hour)-1+"");
            preparedStatement.setString(3,uname);
            rs = preparedStatement.executeQuery();
            while (rs.next())
                ids.add(rs.getInt("songId"));
            String artist,album;
            String query2 = "SELECT Id FROM Songs WHERE Artist=? OR Album=?";
            preparedStatement = connection.prepareStatement(query2);
            int i=1;
            for (int id:ids)
            {
                artist = getSongArtist(id);
                album = getSongAlbum(id);
                preparedStatement.setString(1,artist);
                preparedStatement.setString(2,album);
                rs = preparedStatement.executeQuery();
                while (rs.next())
                    Ids.add(rs.getInt("Id"));
                if (i==5)
                    break;
                i++;
            }
            return Ids;
        }catch (SQLException e){
            e.printStackTrace();
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

    public ArrayList<Integer> sort(ArrayList<Integer> ids,ArrayList<Integer> likes)
    {
        List<Integer> copyList= new ArrayList(ids);
        ArrayList<Integer> sortedIds = new ArrayList(copyList);
        Collections.sort(sortedIds,(left,right)->likes.get(copyList.indexOf(left))-likes.get(copyList.indexOf(right)));
        return sortedIds;
    }

    public Set<Integer> getRecommendedOnLikes()
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;

        Set<Integer> Ids = new LinkedHashSet<Integer>();
        ArrayList<Integer> likes = new ArrayList<Integer>();
        ArrayList<Integer> id = new ArrayList<Integer>();
        String query = "SELECT COUNT(*) FROM Liked WHERE songId=?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            for(int i=1;i<=20;i++)
            {
                preparedStatement.setInt(1,i);
                rs = preparedStatement.executeQuery();
                //likes.add(rs.getInt("COUNT(*)"));
                while (rs.next())
                    likes.add(rs.getInt("COUNT(*)"));
                id.add(i);
            }
            id = sort(id,likes);
            Collections.reverse(id);
            String query2 = "SELECT Id FROM Songs WHERE Artist=? OR Album=?";
            String artist,album;
            preparedStatement = connection.prepareStatement(query2);
            for (int ids:id)
            {
                artist = getSongArtist(ids);
                album = getSongAlbum(ids);
                preparedStatement.setString(1,artist);
                preparedStatement.setString(2,album);
                rs = preparedStatement.executeQuery();
                int i=1;
                while (rs.next())
                {
                    Ids.add(rs.getInt("Id"));
                    if (i==5)
                        break;
                    i++;
                }
            }
            return Ids;
        }catch (SQLException e){
            e.printStackTrace();
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
    
    public void getDetail()
    {
        Set<String> languages = new LinkedHashSet<String>();
        Set<String> artists =new LinkedHashSet<String>();
        Set<String> generes=new LinkedHashSet<String>();
        Connection connection = null;
        PreparedStatement ps = null;
        String query = "SELECT language,Artist,generes FROM songs";
        try{
            connection = getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next())
            {
                languages.add(rs.getString("language"));
                artists.add(rs.getString("Artist"));
                generes.add(rs.getString("generes"));
            }
            clientHandler.languages = languages;
            clientHandler.artists = artists;
            clientHandler.generes = generes;
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
    
}
