import java.io.File;

public class FileTransfer {
    private static final int BUFFERSIZE = 2048*2048;
    private static final int SUCCESSCODE = -1;

    public static void main(String [] args){
        Server ser = new Server(10086);
        //首先接收待传文件的大小
        byte [] fileInfo = new byte[BUFFERSIZE];
        byte [] recvInfo = new byte[BUFFERSIZE];
        byte [] sendInfo = new byte[1];
        sendInfo[0] = SUCCESSCODE;
        //获取文件大小
        Integer offsize = 0;
        int count = ser.serverRecv(offsize,fileInfo);
        Long fileSizse = Long.valueOf(new String(fileInfo,0,count));
        //回复确认
        ser.serverSend(sendInfo,sendInfo.length);
        //获取文件名
        count = ser.serverRecv(0,recvInfo);
        String filename = new String(recvInfo,0,count);
        //判断是否存在同名文件
        File file = new File(filename);
        if(file.exists()){
            ser.serverClose();
            System.out.println("存在同名文件");
            return;
        }
        //判断是否是不完整文件
        File refile = new File(filename+".swp");
        if(refile.exists()){
            System.out.println("111111");
            long fileOffSize = refile.length();
            System.out.println(fileOffSize);
            byte[] fileOffSizeArr = Long.toString(fileOffSize).getBytes();
            ser.serverSend(fileOffSizeArr,fileOffSizeArr.length);
        }else{
            //回复确认
            ser.serverSend(sendInfo,sendInfo.length);
        }
        //接受并写入文件
        FileOperation op = new FileOperation(refile);
        int reNum = 0;
        int pos = 0;
        while(true){
            reNum = ser.serverRecv(0, recvInfo);
            if (-1 == reNum) {
                ser.serverClose();
                if (fileSizse == refile.length()) {
                    refile.renameTo(file);
                }
                return;
            }
            op.fileWriteStreamData(recvInfo, pos, reNum);
            ser.serverSend(sendInfo, sendInfo.length);

        }


    }
}
