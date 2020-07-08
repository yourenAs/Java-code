import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    private ServerSocket socket;
    private Socket s;

    public Server(){}
    public Server(int port){
        try {
            socket = new ServerSocket(port);
            s = socket.accept();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void serverSend(byte [] bytes,int len){
        BufferedOutputStream sendOut = null;
        try {
            sendOut = new BufferedOutputStream(s.getOutputStream());
            sendOut.write(bytes,0,len);

            sendOut.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public int serverRecv(Integer num,byte [] bytes){
        BufferedInputStream recvIn = null;
        int redCount = -1;
        try {
            recvIn = new BufferedInputStream(s.getInputStream());
            recvIn.skip(num);
            redCount = recvIn.read(bytes);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return redCount;
    }
    public void serverClose(){
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean serverIsConnect(){
       return  s.isConnected();
    }

}



