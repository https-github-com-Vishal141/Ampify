package server;

import java.util.*;

import server.Database.*;

public class DatabaseHandler {
    
    private LoginAndRegister loginRegister;
    private Song song;
    private Playlist playlist;
    private Group group;
    private History history;
    
    public DatabaseHandler()
    {
        loginRegister = new LoginAndRegister();
        song = new Song();
        playlist = new Playlist();
        group = new Group();
        history = new History();
    }
    
    
    //user login
    public boolean login(String uname,String pass)
    {
        return loginRegister.login(uname, pass);
    }

    //check is user login or not
    public boolean checkLogin(String username){
         return loginRegister.checkLogin(username);
    }
    
    //user log out
    public void logout(String username){
        loginRegister.logout(username);
    }
    
    public boolean checkEmail(String email)
    {
        return loginRegister.checkEmail(email);
    }

    public boolean register(String uname,String pass,String email)
    {
        return loginRegister.register(uname, pass, email);
    }

    public void insertIntolanguage(String uname,String language)
    {
        loginRegister.insertIntolanguage(uname, language);
    }

    public void insertIntoGeneres(String uname,String genere)
    {
        loginRegister.insertIntoGeneres(uname, genere);
    }

    public void insertIntoArtist(String uname,String artist)
    {
        loginRegister.insertIntoArtist(uname, artist);
    }
    
    public boolean createPlaylist(String uname,String name)
    {
        return playlist.checkPlaylist(uname, name);
    }
    
    public boolean addToPlaylist(String user,String name,int id)
    {
        return playlist.addToPlaylist(user, name, id);
    }
    
    public Set<Integer> getPlaylist(String user,String name)
    {
        return playlist.getPlaylist(user, name);
    }
    
    public ArrayList<String> getPlaylists(String uname)
    {
        return playlist.getPlaylists(uname);
    }
    
    public boolean checkForLike(String uname,int id)
    {
        return song.checkForLike(uname, id);
    }
    
     public boolean checkForDisLike(String uname,int id)
    {
        return song.checkForDisLike(uname, id);
    }
     
    public void deleteFrom(String status,String uname,int id)
    {
        song.deleteFrom(status, uname, id);
    }

    public boolean likeOrDislike(String uname,String status,int id)
    {
        return song.likeOrDislike(uname, status, id);
    }

    public boolean addToHistory(String uname,int id,String time,String date,String hour)
    {
        return history.addToHistory(uname, id, time, date, hour);
    }
    
    public ArrayList[] getHistory(String user)
    {
        return history.getHistory(user);
    }

    public byte[] get_Song(int id)
    {
        return song.get_Song(id);
    }
    
    public ArrayList<String> getAllSong()
    {
        return song.getAllSong();
    }

    public int getSongId(String title)
    {
       return song.getSongId(title);
    }

    public String getSongTitle(int id)
    {
        return song.getSongTitle(id);
    }

    public String getSongArtist(int id)
    {
        return song.getSongArtist(id);
    }

    public String getSongAlbum(int id)
    {
        return song.getSongAlbum(id);
    }

    public void addToSearchHistory(String uname,String searchedItem)
    {
        history.addToSearchHistory(uname, searchedItem);
    }
    
    public ArrayList<String> getserchHistory(String uname){
        return history.getserchHistory(uname);
    }
    
    public Set<Integer> getSearchResult(String item)
    {
        return history.getSearchResult(item);
    }

    public void increaseView(int songId)
    {
        song.increaseView(songId);
    }

    public ArrayList<Integer> getTrending()
    {
        return song.getTrending();
    }

    public Set<Integer> getRecent(String uname)
    {
        return song.getRecent(uname);
    }

    public Set<Integer> getRecommendedOnTime(String hour,String uname)
    {
        return song.getRecommendedOnTime(hour, uname);
    }

    public Set<Integer> getRecommendedOnLikes()
    {
        return song.getRecommendedOnLikes();
    }
    
    public boolean checkUser(String uname)
    {
        return group.checkUser(uname);
    }
    
    public boolean checkPlaylist(String uname,String name)
    {
        return playlist.checkPlaylist(uname, name);
    }
    
    public void sharePlaylist(String uname1,String pname,String uname2)
    {
        playlist.sharePlaylist(uname1, pname, uname2);
    }
    
    public boolean createGroup(String admin,String name)
    {
        return group.checkGroup(name);
    }
    
    public Set<String> getGroups(String uname)
    {
        return group.getGroups(uname);
    }
    
    public void addUser(String gName,String uname)
    {
        group.addUser(gName, uname);
    }
    
    public Set<String> getUsers(String name)
    {
        return group.getUsers(name);
    }
    
    public boolean checkGroup(String name)
    {
        return group.checkGroup(name);
    }
    
    public Set<String> getLiked(String uname)
    {
        return song.getLiked(uname);
    }
    
    public void getDetail()
    {
        song.getDetail();
    }
    
    public void deleteSong(String uname,String pName,int id)
    {
        playlist.deleteSong(uname, pName, id);
    }
    
    public void deletePlaylist(String uname,String pName)
    {
        playlist.deletePlaylist(uname, pName);
    }
    
    public boolean checkForAdmin(String uname,String gName)
    {
        return group.checkForAdmin(uname, gName);
    }
    
    public void deleteMember(String uname,String gName)
    {
        group.deleteMember(uname, gName);
    }
}