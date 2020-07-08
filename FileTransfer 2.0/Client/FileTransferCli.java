import java.util.Scanner;
import java.io.File;

public class FileTransferCli {
    private Client cli;
    private int buffSzie = 1024;
    private static final int OFFERSIZE = 1;
    private static final char CLIENTCLOSE = 0;
    private static final char SUCCESSCODE = 1;
    private static final char FILEREPT = 2;
    private static final char DEFAFILE = 3;
    byte[] sendInfo;
    byte[] recvInfo;

    public FileTransferCli(){}

    public FileTransferCli(String ip,int port){
        cli = new Client(ip,port);
    }

    public void fileTranfer(){
        while (true){
            System.out.println("连接成功，请输出待传的文件：");
            Scanner sc = new Scanner(System.in);
            String fileName = sc.next();
            if(fileName.equals("exit")){
                cliClose();
                return;
            }
            //向服务器发送文件名
            sendInfo = ProcessData.byteMerger((byte)SUCCESSCODE,ProcessData.strToByte(fileName));
            System.out.println(sendInfo.length);
            System.out.println(fileName);
            cli.clientSend(sendInfo,0,sendInfo.length);
            //接受服务器端信息
            recvInfo = new byte[buffSzie];
            int count = cli.clientRecv(recvInfo);
            if(recvInfo[0]== FILEREPT){
                System.out.println("服务器已存在该文件，请重新上传：");
                continue;
            }else {
                buffSzie = ProcessData.byteToLong(recvInfo, OFFERSIZE, count - OFFERSIZE).intValue();
            }
            //向服务器发送文件大小
            File file = new File(fileName);
            FileOperation fop = new FileOperation(file);
            long fileSize = fop.getFileSize();
            sendInfo = ProcessData.byteMerger((byte)SUCCESSCODE,ProcessData.longToByte(fileSize)) ;
            cli.clientSend(sendInfo,0,sendInfo.length);
            //接收服务器的回复，判断是否为断点续传
            recvInfo = new byte[buffSzie];
            count = cli.clientRecv(recvInfo);
            long fileOfferSize = 0;
            if(recvInfo[0] == DEFAFILE){
                fileOfferSize = ProcessData.byteToLong(recvInfo,OFFERSIZE,count-OFFERSIZE);
            }
            long skipNum = fileOfferSize;
            int whilCount = 0;
            //开始读取并发送数据
            while (true){
                sendInfo = new byte[buffSzie];
                recvInfo = new byte[buffSzie];
                int reCount = fop.fileReadStreamData(sendInfo,skipNum);
                if(-1 == reCount){
                    //System.out.println(fop.getFileSize());
                    System.out.println("文件传输结束！");
                    cli.clientRecv(recvInfo);
                    break;
                }
                skipNum += reCount;
                cli.clientSend(sendInfo,0,reCount);
                whilCount++;
                cli.clientRecv(recvInfo);
            }
        }
    }
    private void cliClose()
    {
        sendInfo = new byte[1];
        sendInfo[0] = (byte) CLIENTCLOSE;
        cli.clientSend(sendInfo,0,sendInfo.length);
        cli.cliClose();
    }
}
