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

    /**
     *
     * @param bytes 即发往客户端的字节数组
     * @param off 即字节数组的偏移量
     * @param len 即发往客户端的实际数组长度
     *
     */
    public void clientSend(byte [] bytes,int off,int len){
        BufferedOutputStream sendOut = null;
        try {
            sendOut = new BufferedOutputStream(socket.getOutputStream());
            sendOut.write(bytes,off,len);
            sendOut.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param bytes 用来存储读到的数据
     * @return 读到的数据长度，即读到的字节个数
     */
    public int clientRecv(byte [] bytes){
        BufferedInputStream recvIn = null;
        int redCount = -1;
        try {
            recvIn = new BufferedInputStream(socket.getInputStream());
            redCount = recvIn.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return redCount;
    }

    /**
     * 关闭对应到套接字
     */
    public void cliClose(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



