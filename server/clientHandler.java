package server;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Calendar;
import java.util.Collections;



public class clientHandler implements Runnable {
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
    public static Set<String> languages;
    public static Set<String> artists;
    public static Set<String> generes;
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
            
            //starting Http Server
            
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
           try {
              // ps.println("hello\n");
               DatabaseHandler db = new DatabaseHandler();
               String work = dis.readUTF();
               String uname= "";
               
              if(!work.equals("register") && !work.equals("login")){
                  uname = dis.readUTF();
                  if(!db.checkLogin(uname))
                      return;
              }
               
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
                       if (db.login(uname,pass))
                       {                              
                          ps.println("true");
                       }
                       else
                           ps.println("failed");
                       ps.flush();
                       break;
                   case "register":
                       uname = dis.readUTF();
                       pass = dis.readUTF();
                       email = dis.readUTF();
                       ps.println("checking");
                       if (db.checkEmail(email))
                           ps.println("emailExist");
                       else
                       {
                           if (db.register(uname,pass,email))
                           {                            
                               ps.println("Complete");
                           }
                           else
                               ps.println("userExist");
                       }
                       ps.flush();
                       break;
                   case "getSong":
                       Id = dis.readUTF();
                       id = Integer.parseInt(Id);
                       URL url = new URL("http://localhost:8500/ampify/song"+id+".mp3");
                       objectOutputStream.writeObject(url);
                       objectOutputStream.flush();
                       break;
                   case "getSrt":
                       Id = dis.readUTF();
                       id = Integer.parseInt(Id);
                       File file = srtfiles[id-1];
                       objectOutputStream.writeObject(file);
                       objectOutputStream.flush();
                       break;
                   case "recent":
                       uname = dis.readUTF();
                       recomOnTime = db.getRecent(uname);
                       objectOutputStream.writeObject(recent);
                       for(int songId:recomOnTime){
                           recentTitle.add(db.getSongTitle(songId));
                       }
                      // System.out.println(recomOnTime);
                       //System.out.println(recentTitle);
                       objectOutputStream.writeObject(recentTitle);
                       objectOutputStream.flush();
                       break;
                   case "trending":
                       trending = db.getTrending();
                       objectOutputStream.writeObject(trending);
                       for(int songId:trending){
                           trendingTitle.add(db.getSongTitle(songId));
                       }
                       objectOutputStream.writeObject(trendingTitle);
                       objectOutputStream.flush();
                       break;
                   case "recommended":
                       uname = dis.readUTF();
                       String time = dis.readUTF();
                       recomOnTime = db.getRecommendedOnTime(time,uname);
                       recomOnLike = db.getRecommendedOnLikes();
                       for(int songId:recomOnTime){
                           trendingTitle.add(db.getSongTitle(songId));
                       }
                       for(int songId:recomOnLike){
                           recentTitle.add(db.getSongTitle(songId));
                       }
                       objectOutputStream.writeObject(recomOnTime);
                       objectOutputStream.writeObject(trendingTitle);
                       objectOutputStream.writeObject(recomOnLike);
                       objectOutputStream.writeObject(recentTitle);
                       objectOutputStream.flush();
                       break;
                   case "history":                      
                       uname = dis.readUTF();
                       history = db.getHistory(uname);
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
                       db.addToHistory(uname,id,time,date,hour);
                       break;
                   case "createPlaylist":
                       uname = dis.readUTF();
                       name = dis.readUTF();
                       ps.println("creating");
                       if(db.createPlaylist(uname,name)){
                           ps.println("created");
                       }else{
                           ps.println("error");
                       }
                       ps.flush();
                       break;
                   case "addToPlaylist":
                       uname = dis.readUTF();
                       name = dis.readUTF();
                       Id = dis.readUTF();
                       id = Integer.parseInt(Id);
                       ps.println("adding");
                       if(db.addToPlaylist(uname,name,id))
                           ps.println("added");
                       else
                           ps.println("error");
                       ps.flush();
                       break;
                   case "getPlaylist":
                       uname  = dis.readUTF();
                       name = dis.readUTF();
                       recomOnTime = db.getPlaylist(uname,name);
                       for(int i:recomOnTime)
                           recentTitle.add(db.getSongTitle(i));
                       objectOutputStream.writeObject(recomOnTime);
                       objectOutputStream.writeObject(recentTitle);
                       objectOutputStream.flush();
                       break;
                   case "getPlaylists":
                       uname = dis.readUTF();
                       Playlists = db.getPlaylists(uname);
                       objectOutputStream.writeObject(Playlists);
                       objectOutputStream.flush();
                       break;
                   case "LikeOrDisLike":
                       String status = dis.readUTF();
                       uname = dis.readUTF();
                       Id = dis.readUTF();
                       id = Integer.parseInt(Id);
                       ps.println("doing");
                       if(db.likeOrDislike(uname,status,id))
                           ps.println("done");
                       else
                           ps.println("error");
                       ps.flush();
                       break;
                   case "AllSong":
                       Playlists = db.getAllSong();
                       objectOutputStream.writeObject(Playlists);
                       objectOutputStream.flush();
                       break;
                   case "searchHistory":
                       uname = dis.readUTF();
                       Playlists = db.getserchHistory(uname);
                       objectOutputStream.writeObject(Playlists);
                       objectOutputStream.flush();
                       break;
                   case "searchResult":
                       String item = dis.readUTF();
                       recomOnLike = db.getSearchResult(item);
                       for(int i:recomOnLike)
                       {
                           String title = db.getSongTitle(i);
                           String artist = db.getSongArtist(i);
                           recentTitle.add(title+"\t"+"\t"+artist);
                       }
                       objectOutputStream.writeObject(recomOnLike);
                       objectOutputStream.writeObject(recentTitle);
                       objectOutputStream.flush();
                       break;
                   case "addToSearchHistory":                 
                       uname = dis.readUTF();
                       item = dis.readUTF();
                       db.addToSearchHistory(uname,item);
                       break;   
                   case "increaseView":
                       Id = dis.readUTF();
                       id = Integer.parseInt(Id);
                       db.increaseView(id);
                       break;
                   case "share":
                       uname = dis.readUTF();
                       String pname = dis.readUTF();
                       String uname2 = dis.readUTF();
                       ps.println("doing");
                       if(db.checkPlaylist(uname2,pname))
                       {
                           ps.println("playlist exist");
                           break;
                       }
                       if(db.checkUser(uname2))
                       {
                           db.sharePlaylist(uname,pname,uname2);
                           ps.println("shared");
                       }
                       else
                           ps.println("user not exist");
                       ps.flush();
                       break;
                   case "LanguageDetails":
                       uname = dis.readUTF();
                       String detail = dis.readUTF();
                       db.insertIntolanguage(uname,detail);
                       break;
                   case "ArtistDetails":
                       uname = dis.readUTF();
                       detail = dis.readUTF();
                       db.insertIntoArtist(uname,detail);
                       break;
                   case "GeneresDetails":
                       uname = dis.readUTF();
                       detail = dis.readUTF();
                       db.insertIntoGeneres(uname,detail);
                       break;
                   case "createGroup":
                       uname = dis.readUTF();
                       name = dis.readUTF();
                       ps.println("creating");
                       if(db.createGroup(uname,name))
                           ps.println("true");
                       else
                           ps.println("group exist");
                       ps.flush();
                       break;
                   case "getGroups":
                       uname = dis.readUTF();
                       groups = db.getGroups(uname);
                       objectOutputStream.writeObject(groups);
                       objectOutputStream.flush();
                       break;
                   case "groupDetails":
                       name = dis.readUTF();
                       groups = db.getUsers(name);
                       trending = db.getTrending();
                       for(int songId:trending){
                           Title.add(db.getSongTitle(songId));
                       }
                       objectOutputStream.writeObject(Title);
                       objectOutputStream.writeObject(groups);
                       objectOutputStream.flush();
                       break;
                   case "addUser":
                       name = dis.readUTF();
                       uname = dis.readUTF();
                       ps.println("adding");
                       if(db.checkUser(uname))
                       {
                           db.addUser(name,uname);
                           ps.println("done");
                       }
                       else{
                           ps.println("user do not exist");
                       }
                       ps.flush();   
                       break;
                   case "liked":
                       uname = dis.readUTF();
                       groups = db.getLiked(uname);
                       objectOutputStream.writeObject(groups);
                       objectOutputStream.flush();
                       break;
                   case "getDetails":
                       db.getDetail();
                       objectOutputStream.writeObject(languages);
                       objectOutputStream.writeObject(artists);
                       objectOutputStream.writeObject(generes);
                       objectOutputStream.flush();
                       break;
                   case "DeleteSong":
                       uname = dis.readUTF();
                       String pName = dis.readUTF();
                       Id = dis.readUTF();
                       id = Integer.parseInt(Id);
                       db.deleteSong(uname, pName, id);
                       break;
                   case "DeletePlaylist":
                       uname = dis.readUTF();
                       pName = dis.readUTF();
                       db.deletePlaylist(uname, pName);
                       break;
                   case "DeleteMember":
                       String admin = dis.readUTF();
                       uname = dis.readUTF();
                       pName = dis.readUTF();
                       if(db.checkForAdmin(admin, pName))
                       {
                          db.deleteMember(uname, pName);
                          ps.println("true");
                       }
                       else
                       {
                           ps.println("Not Admin");
                       }
                       ps.flush();
                       break;
                   case "DeleteGroup":
                       uname = dis.readUTF();
                       pName = dis.readUTF();
                       db.deleteMember(uname, pName);
                       break;
               }
              

           }catch (Exception e){
               e.printStackTrace();
           }finally{
               try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                ps.close();
                objectOutputStream.close();
                //  objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
           }      
    }
    
}

