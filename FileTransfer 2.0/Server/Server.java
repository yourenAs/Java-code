import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


/**
 * 服务对象：
 * 1.可初始化套接字，并监听套接字，若有客户端访问即进行三次连接。
 * 2.可发送和接收数据.
 * 3.在客户端断开连接后可检测并清理连接套接字并继续监听，等待客户端请求。
 *
 *传输协议：
 * 0.定义读取到的buffer第一个为状态位
 * 1.若客户端回复客户端请求断开连接，则第一位为'0'对应的ASCII
 * 2.若服务器正常回复客户端，则第一位为'1'对应的ASCII
 * 3.若服务器回复客户端已有重名文件，则第一位为'2'对应的ASCII
 * 抽象出变量及方法：
 *
 */

public class Server{
    private ServerSocket socket;
    private Socket s;

    public Server(){}
    //构建一个服务器对象
    public Server(int port){
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serAccept(){
        try {
            s = socket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    /**
     *
     * @param bytes 即发往客户端的字节数组
     * @param off 即字节数组的偏移量
     * @param len 即发往客户端的实际数组长度
     *
     */
    public void serverSend(byte [] bytes,int off,int len){
        BufferedOutputStream sendOut = null;
        try {
            sendOut = new BufferedOutputStream(s.getOutputStream());
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
    public int serverRecv(byte [] bytes){
        BufferedInputStream recvIn = null;
        int redCount = -1;
        try {
            recvIn = new BufferedInputStream(s.getInputStream());
            redCount = recvIn.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return redCount;
    }

    /**
     *
     * @param sig 即约定到协议
     * @return 若为真即客户端关闭，若为假即正常
     */
    public boolean isClose(byte sig){
        boolean res = false;
        if(sig == '0'){
            res = true;
        }
        return res;
    }

    public int serGetReceiveBufferSize(){
        int res = 0;
        try {
            res = s.getReceiveBufferSize();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return res;
    }
    /**
     * 关闭对应到套接字
     */
    public void serverClose(){
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



