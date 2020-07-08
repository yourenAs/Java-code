import java.io.*;

public class FileOperation {
    //构建一个file类型
    private File file;

    public FileOperation(){}
    //参数为file的构造函数
    public FileOperation(File file){
        this.file = file;
    }
    /**
     * 文件读取方法，每次将读到bytes.len个字节
     * 参数：一个byte数组
     */
    public int fileReadStreamData(Long num,byte [] bytes){
        FileInputStream fis = null;
        int readCount = -1;
        try {
            fis = new FileInputStream(file);
            fis.skip(num);
            readCount = fis.read(bytes);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            //确保出现异常后文件描述符也可以被关闭
            if(null != fis){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return readCount;
    }
    /**
     * 文件的写方法,每次将bytes数组中的所有信息写到硬盘上
     * 参数:一个bytes数组
     */
    public void fileWriteStreamData(byte [] bytes,int offSize){
        FileOutputStream fos = null;
        try {
             fos = new FileOutputStream(file,true);
             fos.write(bytes,0,offSize);

             fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != fos){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
