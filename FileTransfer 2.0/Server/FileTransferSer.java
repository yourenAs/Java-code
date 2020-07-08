import java.io.File;

public class FileTransferSer {
    private Server ser;
    private int recvBuffSize;
    private static final int OFFERSIZE = 1;
    private static final char CLIENTCLOSE = 0;
    private static final char SUCCESSCODE = 1;
    private static final char FILEREPT = 2;
    private static final char DEFAFILE = 3;
    byte[] sendInfo;
    byte[] recvInfo;

    public FileTransferSer(){}

    public void fileTransferSerStart(int port){
        //构建文件传输服务
        ser = new Server(port);
        while(true){
            //监听套接字并建立连接
            ser.serAccept();
            recvBuffSize =ser.serGetReceiveBufferSize();
            System.out.println("客户端连接成功，开始传输文件！");
            //文件传输
            fileTransfer();
        }
    }

    private void fileTransfer(){
        while(true){
            //获取待传文件名
            recvInfo = new byte[recvBuffSize];
            int count = ser.serverRecv(recvInfo);
            //客户端放弃重发文件
            if(ser.isClose(recvInfo[0])){
                ser.serverClose();
                System.out.println("客户端已关闭");
                return;
            }

            String fileName = ProcessData.byteToString(recvInfo,OFFERSIZE,count-OFFERSIZE);
            System.out.println(fileName);
            //判断是否存在同名文件
            File file = new File(fileName);
            FileOperation fop = new FileOperation(file);
            //存在同名文件告知对端，重新发送.否则告知客户端可进行下一步
            if(fop.fileExists()){
                serSendSig(FILEREPT);
                continue;
            }else{
                sendInfo = ProcessData.byteMerger((byte) SUCCESSCODE,ProcessData.longToByte((long)recvBuffSize));
                ser.serverSend(sendInfo,0,sendInfo.length);
            }
            //获取文件大小
            recvInfo = new byte[recvBuffSize];
            count = ser.serverRecv(recvInfo);
            long fileSize = ProcessData.byteToLong(recvInfo,OFFERSIZE,count-OFFERSIZE);
            long offerSize = 0;
            //判断是否需要断点续传，即检测是否存在不完整文件
            String defaultFileName = fileName + ".swp";
            File defaultFile = new File(defaultFileName);
            FileOperation dfop = new FileOperation(defaultFile);
            if(dfop.fileExists()){

                offerSize = dfop.getFileSize();
                //回复客户端应从文件偏移的字节
                sendInfo = ProcessData.byteMerger((byte)DEFAFILE,ProcessData.longToByte(offerSize));
                ser.serverSend(sendInfo,0,sendInfo.length);
            }else{
                //回复客户端正常操作
                serSendSig(SUCCESSCODE);
            }
            int whilCount = 0;
            //开始读写
            long fileCount = offerSize;
            while(true){
                if(fileCount == fileSize){
                    System.out.println("文件传输完毕");
                    defaultFile.renameTo(file);
                    break;
                }
                recvInfo = new byte[recvBuffSize];
                count = ser.serverRecv(recvInfo);
                dfop.fileWriteStreamData(recvInfo,0,count);
                serSendSig(SUCCESSCODE);
                fileCount += count;
            }

        }


    }

    private void serSendSig(char sig){
        sendInfo = new byte[1];
        sendInfo[0] = (byte) sig;
        ser.serverSend(sendInfo,0,sendInfo.length);
    }
}
