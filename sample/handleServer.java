package sample;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;

public class handleServer
{
    private Socket socket;
    public DataInputStream dis;
    public DataOutputStream dos;
    public BufferedReader br;
    public ObjectInputStream objectInputStream;

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

    public ArrayList<Integer> getRecent(String uname)
    {
        ArrayList<Integer> recentId=null;
        ArrayList<String > recentTitle=null;

        try {
            dos.writeUTF("recent");
            dos.writeUTF(uname);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            recentId = (ArrayList<Integer>) objectInputStream.readObject();
            recentTitle = (ArrayList<String>) objectInputStream.readObject();
            Controller.recentTitle = recentTitle;
            return recentId;
        } catch (Exception e) {
            e.printStackTrace();
            return recentId;
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

    public ArrayList<Integer> getTrending()
    {
        ArrayList<Integer> trendingId=null;
        ArrayList<String> trendingTitle=null;
        try {
            dos.writeUTF("trending");
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            trendingId= (ArrayList<Integer>) objectInputStream.readObject();
            trendingTitle = (ArrayList<String>) objectInputStream.readObject();
            Controller.trendingTitle = trendingTitle;
            return trendingId;
        } catch (Exception e) {
            e.printStackTrace();
            return trendingId;
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
        try {
            dos.writeUTF("getPlaylist");
            dos.writeUTF(uname);
            dos.writeUTF(name);
            dos.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            Ids = (Set<Integer>) objectInputStream.readObject();
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
}
