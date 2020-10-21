package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;


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
    public ArrayList<String> Playlists;
    public ArrayList[] history;
    
    public File file = new File("song");
    public File[] files;
    
    public clientHandler(Socket s)
    {
        try {
            files = file.listFiles();
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
                   case "song":
                       Id = dis.readUTF();
                       id = Integer.parseInt(Id);
                       File file = files[id];
                       objectOutputStream.writeObject(file);
                       objectOutputStream.flush();
                       break;
                   case "recent":
                       uname = dis.readUTF();
                       recent = getRecent(uname);
                       objectOutputStream.writeObject(recent);
                       for(int songId:recent){
                           recentTitle.add(getSongTitle(songId));
                       }
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
                       objectOutputStream.writeObject(recomOnTime);
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
                       if(likeOrDislike(status,uname,id))
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
               }

           }catch (Exception e){
               e.printStackTrace();
           }
    }
}

