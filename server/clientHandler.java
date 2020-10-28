package server;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Calendar;



public class clientHandler extends DatabaseHandler implements Runnable {
    private Socket socket;
    public OutputStream os;
    public InputStream is;
    public BufferedReader br ;
    public DataOutputStream dos;
    public DataInputStream dis;
    public PrintStream ps;
    public ObjectOutputStream objectOutputStream;

    public ArrayList<Integer> recent;
    public ArrayList<String> recentTitle=new ArrayList<String>();
    public ArrayList<Integer> trending;
    public ArrayList<String> trendingTitle=new ArrayList<String>();
    public Set<Integer> recomOnLike;
    public Set<Integer> recomOnTime;
    public Set<String> groups;
    public Set<String> Title = new LinkedHashSet<>();
    public ArrayList<String> Playlists;
    public ArrayList[] history;
    
    //public File file = new File("\\song");
    File[] files = new File("E:/nbProjects/Server/src/server/song").listFiles();
    File[] srtfiles = new File("E:/nbProjects/Server/src/server/srt").listFiles();
    
    public clientHandler(Socket s)
    {
        try {
           // files = file.listFiles();
            this.socket = s;
            this.is = socket.getInputStream();
            this.os = socket.getOutputStream();
            br = new BufferedReader(new InputStreamReader(is));
            dos = new DataOutputStream(os);
            dis = new DataInputStream(is);
            ps = new PrintStream(socket.getOutputStream());
            objectOutputStream = new ObjectOutputStream(os);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
           try {
              // ps.println("hello\n");
               String work = dis.readUTF();
               String uname="";
               String pass="";
               String email="";
               String name = "";
               String Id;
               int id;
               switch (work)
               {
                   case "login":
                       uname = dis.readUTF();
                       pass = dis.readUTF();
                       ps.println("checking");
                       if (login(uname,pass))
                       {                              
                          ps.println("true");
                       }
                       else
                           ps.println("failed");
                       break;
                   case "register":
                       uname = dis.readUTF();
                       pass = dis.readUTF();
                       email = dis.readUTF();
                       ps.println("checking");
                       if (checkEmail(email))
                           ps.println("emailExist");
                       else
                       {
                           if (register(uname,pass,email))
                           {                            
                               ps.println("Complete");
                           }
                           else
                               ps.println("userExist");
                       }
                       break;
                   case "getSong":
                       Id = dis.readUTF();
                       id = Integer.parseInt(Id);
                       File file = files[id-1];
                      // URI uri = file.toURI();
                       URL url = file.toURL();
                       objectOutputStream.writeObject(url);
                       objectOutputStream.flush();
                       break;
                   case "getSrt":
                       Id = dis.readUTF();
                       id = Integer.parseInt(Id);
                       file = srtfiles[id-1];
                       objectOutputStream.writeObject(file);
                       objectOutputStream.flush();
                       break;
                   case "recent":
                       uname = dis.readUTF();
                       recomOnTime = getRecent(uname);
                       objectOutputStream.writeObject(recent);
                       for(int songId:recomOnTime){
                           recentTitle.add(getSongTitle(songId));
                       }
                      // System.out.println(recomOnTime);
                       //System.out.println(recentTitle);
                       objectOutputStream.writeObject(recentTitle);
                       objectOutputStream.flush();
                       break;
                   case "trending":
                       trending = getTrending();
                       objectOutputStream.writeObject(trending);
                       for(int songId:trending){
                           trendingTitle.add(getSongTitle(songId));
                       }
                       objectOutputStream.writeObject(trendingTitle);
                       objectOutputStream.flush();
                       break;
                   case "recommended":
                       uname = dis.readUTF();
                       String time = dis.readUTF();
                       recomOnTime = getRecommendedOnTime(time,uname);
                       recomOnLike = getRecommendedOnLikes();
                       for(int songId:recomOnTime){
                           trendingTitle.add(getSongTitle(songId));
                       }
                       for(int songId:recomOnLike){
                           recentTitle.add(getSongTitle(songId));
                       }
                       objectOutputStream.writeObject(recomOnTime);
                       objectOutputStream.writeObject(trendingTitle);
                       objectOutputStream.writeObject(recomOnLike);
                       objectOutputStream.writeObject(recentTitle);
                       objectOutputStream.flush();
                       break;
                   case "history":                      
                       uname = dis.readUTF();
                       history = getHistory(uname);
                       objectOutputStream.writeObject(history);
                       objectOutputStream.flush();
                       break;
                   case "addToHistory":
                       uname = dis.readUTF();
                       Id = dis.readUTF();
                       String date = dis.readUTF();
                       time = dis.readUTF();
                       String hour = dis.readUTF();
                       id = Integer.parseInt(Id);
                       addToHistory(uname,id,time,date,hour);
                       break;
                   case "createPlaylist":
                       uname = dis.readUTF();
                       name = dis.readUTF();
                       ps.println("creating");
                       if(createPlaylist(uname,name)){
                           ps.println("created");
                       }else{
                           ps.println("error");
                       }
                       break;
                   case "addToPlaylist":
                       uname = dis.readUTF();
                       name = dis.readUTF();
                       Id = dis.readUTF();
                       id = Integer.parseInt(Id);
                       ps.println("adding");
                       if(addToPlaylist(uname,name,id))
                           ps.println("added");
                       else
                           ps.println("error");
                       break;
                   case "getPlaylist":
                       uname  = dis.readUTF();
                       name = dis.readUTF();
                       recomOnTime = getPlaylist(uname,name);
                       for(int i:recomOnTime)
                           recentTitle.add(getSongTitle(i));
                       objectOutputStream.writeObject(recomOnTime);
                       objectOutputStream.writeObject(recentTitle);
                       objectOutputStream.flush();
                       break;
                   case "getPlaylists":
                       uname = dis.readUTF();
                       Playlists = getPlaylists(uname);
                       objectOutputStream.writeObject(Playlists);
                       objectOutputStream.flush();
                       break;
                   case "LikeOrDisLike":
                       String status = dis.readUTF();
                       uname = dis.readUTF();
                       Id = dis.readUTF();
                       id = Integer.parseInt(Id);
                       ps.println("doing");
                       if(likeOrDislike(uname,status,id))
                           ps.println("done");
                       else
                           ps.println("error");
                       break;
                   case "AllSong":
                       Playlists = getAllSong();
                       objectOutputStream.writeObject(Playlists);
                       objectOutputStream.flush();
                       break;
                   case "searchHistory":
                       uname = dis.readUTF();
                       Playlists = getserchHistory(uname);
                       objectOutputStream.writeObject(Playlists);
                       objectOutputStream.flush();
                       break;
                   case "searchResult":
                       String item = dis.readUTF();
                       recomOnLike = getSearchResult(item);
                       for(int i:recomOnLike)
                       {
                           String title = getSongTitle(i);
                           String artist = getSongArtist(i);
                           recentTitle.add(title+"\t"+"\t"+artist);
                       }
                       objectOutputStream.writeObject(recomOnLike);
                       objectOutputStream.writeObject(recentTitle);
                       objectOutputStream.flush();
                       break;
                   case "addToSearchHistory":                 
                       uname = dis.readUTF();
                       item = dis.readUTF();
                       addToSearchHistory(uname,item);
                       break;   
                   case "increaseView":
                       Id = dis.readUTF();
                       id = Integer.parseInt(Id);
                       increaseView(id);
                       break;
                   case "share":
                       uname = dis.readUTF();
                       String pname = dis.readUTF();
                       String uname2 = dis.readUTF();
                       ps.println("doing");
                       if(checkPlaylist(uname2,pname))
                       {
                           ps.println("playlist exist");
                           break;
                       }
                       if(checkUser(uname2))
                       {
                           sharePlaylist(uname,pname,uname2);
                           ps.println("shared");
                       }
                       else
                           ps.println("user not exist");
                       break;
                   case "insertDetail":
                       //System.out.println("hello");
                       String type = dis.readUTF();
                       uname = dis.readUTF();
                       String detail = dis.readUTF();
                       //System.out.println(type);
                       if(type.equals("language"))
                           insertIntolanguage(uname,detail);
                       else
                       {
                           if(type.equals("artist"))
                               insertIntoArtist(uname,detail);
                           else
                               insertIntoGeneres(uname,detail);
                       }
                       break;
                   case "createGroup":
                       uname = dis.readUTF();
                       name = dis.readUTF();
                       ps.println("creating");
                       if(createGroup(uname,name))
                           ps.println("true");
                       else
                           ps.println("group exist");
                       break;
                   case "getGroups":
                       uname = dis.readUTF();
                       groups = getGroups(uname);
                       objectOutputStream.writeObject(groups);
                       objectOutputStream.flush();
                       break;
                   case "groupDetails":
                       name = dis.readUTF();
                       groups = getUsers(name);
                       trending = getTrending();
                       for(int songId:trending){
                           Title.add(getSongTitle(songId));
                       }
                       objectOutputStream.writeObject(Title);
                       objectOutputStream.writeObject(groups);
                       objectOutputStream.flush();
                       break;
                   case "addUser":
                       name = dis.readUTF();
                       uname = dis.readUTF();
                       ps.println("adding");
                       if(checkUser(uname))
                       {
                           addUser(name,uname);
                           ps.println("done");
                       }
                       else{
                           ps.println("user do not exist");
                       }
                           
                       break;
               }
              

           }catch (Exception e){
               e.printStackTrace();
           }       
    }
}

