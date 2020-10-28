package sample;

import sample.CustomPlaylist.AddSongController;
import sample.CustomPlaylist.CustomPlaylistController;
import sample.Group.group;
import sample.Search.SearchResultController;

import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class handleServer
{
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private BufferedReader br;
    private ObjectInputStream objectInputStream;

    public handleServer()
    {
        try{
            socket = new Socket("localhost",4870);
            System.out.println("Server Connected");
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

//    public void stop()
//    {
//        try {
//            dos.writeUTF("stop");
//            dos.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public boolean login(String uname,String pass)
    {
        try {
            dos.writeUTF("login");
            dos.writeUTF(uname);
            dos.writeUTF(pass);
            dos.flush();
            System.out.println(br.readLine());
            if (br.readLine().toString().equals("true"))
               return true;
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public String  register(String uname,String pass,String em)
    {
        try {
            dos.writeUTF("register");
            dos.writeUTF(uname);
            dos.writeUTF(pass);
            dos.writeUTF(em);
            dos.flush();
            br.readLine();
            return br.readLine();
        }catch (IOException e){
            e.printStackTrace();
            return "";
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getRecent(String uname)
    {
        ArrayList<Integer> recentId=null;
        ArrayList<String > recentTitle=null;

        try {
            dos.writeUTF("recent");
            dos.writeUTF(uname);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            recentId = (ArrayList<Integer>) objectInputStream.readObject();
            recentTitle = (ArrayList<String>) objectInputStream.readObject();
           // Controller.recentTitle = recentTitle;
            return recentTitle;
        } catch (Exception e) {
            e.printStackTrace();
            return recentTitle;
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getTrending()
    {
        ArrayList<Integer> trendingId=null;
        ArrayList<String> trendingTitle=null;
        try {
            dos.writeUTF("trending");
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            trendingId= (ArrayList<Integer>) objectInputStream.readObject();
            trendingTitle = (ArrayList<String>) objectInputStream.readObject();
           // Controller.trendingTitle = trendingTitle;
            return trendingTitle;
        } catch (Exception e) {
            e.printStackTrace();
            return trendingTitle;
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void getRecommended(String uname,String time)
    {
        Set<Integer> recomOnTimeId= null;
        ArrayList<String> recomTimeTitle = null;
        Set<Integer> recomOnLikeId= null;
        ArrayList<String > recomLikeTitle = null;
         try {
            dos.writeUTF("recommended");
            dos.writeUTF(uname);
            dos.writeUTF(time);
            dos.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            recomOnTimeId = (Set<Integer>) objectInputStream.readObject();
            recomTimeTitle = (ArrayList<String>) objectInputStream.readObject();
            recomOnLikeId = (Set<Integer>) objectInputStream.readObject();
            recomLikeTitle = (ArrayList<String>) objectInputStream.readObject();
           // Controller.recommendedId1 = recomOnTimeId;
            Controller.reTimeTitle = recomTimeTitle;
           // Controller.recommendedId2 = recomOnLikeId;
            Controller.reLikeTitle = recomLikeTitle;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
             try {
                 socket.close();
                 dis.close();
                 dos.close();
                 br.close();
                 objectInputStream.close();
             }catch (IOException e){
                 e.printStackTrace();
             }
         }
    }

    public boolean createPlaylist(String uname,String name)
    {
        try {
            dos.writeUTF("createPlaylist");
            dos.writeUTF(uname);
            dos.writeUTF(name);
            dos.flush();
            br.readLine();
            if (br.readLine().equals("created"))
                return true;
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public boolean addToPlaylist(String uname,String name,int id)
    {
        try {
            dos.writeUTF("addToPlaylist");
            dos.writeUTF(uname);
            dos.writeUTF(name);
            dos.writeUTF(""+id);
            dos.flush();
            br.readLine();
            if (br.readLine().equals("added"))
                return true;
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getPlaylists(String uname)
    {
        ArrayList<String> names=null;
        try {
            dos.writeUTF("getPlaylists");
            dos.writeUTF(uname);
            dos.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            names = (ArrayList<String>) objectInputStream.readObject();
            return names;
        } catch (Exception e) {
            e.printStackTrace();
            return names;
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public Set<Integer> getPlaylist(String uname,String name)
    {
        Set<Integer> Ids=null ;
        ArrayList<String> titles=null;
        try {
            dos.writeUTF("getPlaylist");
            dos.writeUTF(uname);
            dos.writeUTF(name);
            dos.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            Ids = (Set<Integer>) objectInputStream.readObject();
            titles = (ArrayList<String>) objectInputStream.readObject();
            CustomPlaylistController.Songs.addAll(titles);
            return Ids;
        } catch (Exception e) {
            e.printStackTrace();
            return Ids;
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList[] getHistory(String uname)
    {
        ArrayList[] history=null;
        try {
            dos.writeUTF("history");
            dos.writeUTF(uname);
            dos.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            history = (ArrayList[]) objectInputStream.readObject();
            return history;
        } catch (Exception e) {
            e.printStackTrace();
            return history;
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public boolean LikeOrDisLike(String status,String uname,String id)
    {
        try {
            dos.writeUTF("LikeOrDisLike");
            dos.writeUTF(status);
            dos.writeUTF(uname);
            dos.writeUTF(id);
            dos.flush();
            br.readLine();
            if (br.readLine().equals("done"))
                return true;
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getAllSong()
    {
        ArrayList<String> songs=null;
        try {
            dos.writeUTF("AllSong");
            dos.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            songs = (ArrayList<String>) objectInputStream.readObject();
            return songs;
        } catch (Exception e) {
            e.printStackTrace();
            return songs;
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> searchHistory(String uname)
    {
        ArrayList<String> searchHistory=null;
        try {
            dos.writeUTF("searchHistory");
            dos.writeUTF(uname);
            dos.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            searchHistory = (ArrayList<String>) objectInputStream.readObject();
            return searchHistory;
        } catch (Exception e) {
            e.printStackTrace();
            return searchHistory;
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getResult(String item)
    {
        ArrayList<String> result = null;
        Set<Integer> ids = null;
        try {
            dos.writeUTF("searchResult");
            dos.writeUTF(item);
            dos.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            ids = (Set<Integer>) objectInputStream.readObject();
            result = (ArrayList<String >) objectInputStream.readObject();
            SearchResultController.Ids = ids;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getResult1(String item)
    {
        ArrayList<String> result = null;
        Set<Integer> ids = null;
        try {
            dos.writeUTF("searchResult");
            dos.writeUTF(item);
            dos.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            ids = (Set<Integer>) objectInputStream.readObject();
            result = (ArrayList<String >) objectInputStream.readObject();
            AddSongController.Ids = ids;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void addToSearchHistory(String user,String item)
    {
        try {
            dos.writeUTF("addToSearchHistory");
            dos.writeUTF(user);
            dos.writeUTF(item);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                //objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public URL getSong(int id)
    {
        //File file=null;
        URL uri=null;
        try {
            dos.writeUTF("getSong");
            dos.writeUTF(id+"");
            dos.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
           // file = (File) objectInputStream.readObject();
            uri = (URL) objectInputStream.readObject();
            return uri;
        }catch (Exception e){
            e.printStackTrace();
            return uri;
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public File getSrt(int id)
    {
        File file=null;
       // URI uri=null;
        try {
            dos.writeUTF("getSrt");
            dos.writeUTF(id+"");
            dos.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            file = (File) objectInputStream.readObject();
            //uri = (URI) objectInputStream.readObject();
            return file;
        }catch (Exception e){
            e.printStackTrace();
            return file;
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void addToHistory(int id)
    {
         String date = LocalDate.now()+"";
         String time = LocalTime.now()+"";
         String hour = LocalTime.now().getHour()+"";
        try {
            dos.writeUTF("addToHistory");
            dos.writeUTF(Controller.Username);
            dos.writeUTF(id+"");
            dos.writeUTF(date);
            dos.writeUTF(time);
            dos.writeUTF(hour);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
              //  objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void increaseView(int id)
    {
        try {
            dos.writeUTF("increaseView");
            dos.writeUTF(id+"");
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                //  objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public String  sharePlaylist(String pname,String uname)
    {
        try {
            dos.writeUTF("share");
            dos.writeUTF(Controller.Username);
            dos.writeUTF(pname);
            dos.writeUTF(uname);
            dos.flush();
            System.out.println(br.readLine());
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                //  objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public void insertDetail(String uname,String detail,String type)
    {
        try {
            dos.writeUTF("insertDetail");
            dos.writeUTF(type);
            dos.writeUTF(uname);
            dos.writeUTF(detail);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                //  objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public String createGroup(String admin,String name)
    {
        String result=null;
        try {
            dos.writeUTF("createGroup");
            dos.writeUTF(admin);
            dos.writeUTF(name);
            dos.flush();
            br.readLine();
            result = br.readLine();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                //  objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public Set<String> getGroups(String uname)
    {
        Set<String> groups=null;
        try {
            dos.writeUTF("getGroups");
            dos.writeUTF(uname);
            dos.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            groups = (Set<String>) objectInputStream.readObject();
            return groups;
        } catch (Exception e) {
            e.printStackTrace();
            return groups;
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void getGroupDetails(String gName)
    {
        Set<String> songs;
        Set<String> members ;
        try {
            dos.writeUTF("groupDetails");
            dos.writeUTF(gName);
            dos.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            songs = (Set<String>) objectInputStream.readObject();
            members = (Set<String>) objectInputStream.readObject();
            group.songs.addAll(songs);
            group.members.addAll(members);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                //  objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public String  addUser(String gName,String uname)
    {
        String result=null;
        try {
            dos.writeUTF("addUser");
            dos.writeUTF(gName);
            dos.writeUTF(uname);
            dos.flush();
            br.readLine();
            result = br.readLine();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }finally {
            try {
                socket.close();
                dis.close();
                dos.close();
                br.close();
                //  objectInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }



//    public void close()
//    {
//        try {
//            socket.close();
//            dis.close();
//            dos.close();
//            br.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
