import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;

public class Client{
    private Socket socket;

    public Client(){}
    public Client(String ip,int port){
        try {
            socket = new Socket(ip,port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void clientSend(byte [] bytes,int len){
        BufferedOutputStream sendOut = null;
        try {
            sendOut = new BufferedOutputStream(socket.getOutputStream());
            sendOut.write(bytes,0,len);

            sendOut.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public int clientRecv(Integer num,byte [] bytes){
        BufferedInputStream recvIn = null;
        int redCount = -1;
        try {
            recvIn = new BufferedInputStream(socket.getInputStream());
            recvIn.skip(num);
            redCount = recvIn.read(bytes);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return redCount;
    }
    public void clientClose(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



