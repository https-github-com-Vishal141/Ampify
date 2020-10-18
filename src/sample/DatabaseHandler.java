package sample;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;

public class DatabaseHandler {
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
        }
    }

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
        }
    }

//    public boolean insertUserDetail(String uname,String language,String genere,String artist)
//    {
//        Connection connection=null;
//         PreparedStatement preparedStatement=null;
//        String query1 = "INSERT INTO Languages VALUES(?,?)";
//        String query2 = "INSERT INTO Generes VALUES(?,?)";
//        String query3 = "INSERT INTO Artists VALUES(?,?)";
//        try{
//            connection = getConnection();
//            preparedStatement = connection.prepareStatement(query1);
//            preparedStatement.setString(1,uname);
//            preparedStatement.setString(2,language);
//            preparedStatement.execute();
//            preparedStatement = connection.prepareStatement(query2);
//            preparedStatement.setString(1,uname);
//            preparedStatement.setString(2,genere);
//            preparedStatement.execute();
//            preparedStatement = connection.prepareStatement(query3);
//            preparedStatement.setString(1,uname);
//            preparedStatement.setString(2,artist);
//            preparedStatement.execute();
//            return true;
//        }catch (Exception e){
//            return false;
//        }
//        finally {
//            try {
//                connection.close();
//                preparedStatement.close();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }

    public boolean likeOrDislike(String uname,String status,int id)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
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
            try {
                connection.close();
                preparedStatement.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean addToHistory(String uname,int id,String time,String date,String hour)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String query = "SELECT COUNT(*) FROM History";
        String query1 = "INSERT INTO History VALUES(?,?,?,?,?,?)";
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            int count = rs.getInt("COUNT(*)");
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

    public void addToSearchHistory(String uname,String searchedItem)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String query = "INSERT INTO searchedHistory VALUES(?,?)";
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,uname);
            preparedStatement.setString(2,searchedItem);
            preparedStatement.execute();
        }catch (SQLException e){

        }finally {
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
        String query = "UPDATE Songs SET Views=Views+1 WHERE Id = ?";
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,songId);
            preparedStatement.execute();
        }catch (SQLException e){

        }finally {
            try {
                connection.close();
                preparedStatement.close();
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

    public ArrayList<Integer> getRecent(String uname)
    {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ArrayList<Integer> Ids = new ArrayList<Integer>();
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
        Set<Integer> Ids = new HashSet<Integer>();
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
            String query2 = "SELECT songId FROM Songs WHERE Artist=? OR Album=?";
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
                    Ids.add(rs.getInt("songId"));
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

        Set<Integer> Ids = new HashSet<Integer>();
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

//    public void close()
//    {
//        try {
//            connection.close();
//            preparedStatement.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

}

