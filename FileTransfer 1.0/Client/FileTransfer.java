import java.io.File;
import java.lang.Long;

public class FileTransfer {
    private static final int BUFFERSIZE = 2048*2048;
    private static final int SUCCESSCODE = -1;

    public static void main(String[] args) {
        Client cli = new Client("127.0.0.1", 10086);
        byte [] recvInfo = new byte[BUFFERSIZE];
        //首先传递待发送文件的大小
        File file = new File("《码出高效：Java开发手册》.pdf");
        //判断文件是否存在
        if(!file.exists()){
            return;
        }
        //文件存在,发送文件大小
        long l = file.length();
        byte[] fileInfo = Long.toString(file.length()).getBytes();
        cli.clientSend(fileInfo,fileInfo.length);
        //收到服务端的确认回复
        cli.clientRecv(0,recvInfo);
        if(recvInfo[0] != SUCCESSCODE){
            cli.clientClose();
            return;
        }
        //发送待传文件名
        String filename = file.getName();
        byte [] fileName = filename.getBytes();
        cli.clientSend(fileName,fileName.length);
        //收到服务端的确认回复

        int renum = cli.clientRecv(0,recvInfo);
        Long fileOffSizse = new Long(0);
        if(recvInfo[0] != SUCCESSCODE){
            fileOffSizse = Long.valueOf(new String(recvInfo,0,renum));
        }
        //开始向服务端发送文件，边读边写
        FileOperation op = new FileOperation(file);
        int reCount = 0;
        while(true){
            byte [] sendInfo = new byte[BUFFERSIZE];
            reCount = op.fileReadStreamData(fileOffSizse,sendInfo);
            if(-1 == reCount){
                cli.clientClose();
                break;
            }
            fileOffSizse += reCount;
            cli.clientSend(sendInfo,reCount);
            //接受服务端回复
            cli.clientRecv(0,recvInfo);
        }

    }
}