public class ProcessData {
    /**
     *
     * @param str
     * @return
     *      将string类型转化为byte类型
     */
    static public byte [] strToByte(String str){
        byte[] bytes = str.getBytes();
        return  bytes;
    }

    /**
     *
     * @param bytes
     * @return
     *      将byte类型转化为string类型
     */
    static public String byteToString(byte[] bytes,int off,int count){
        String str = null;
        str = new String(bytes,off,count);
        return str;
    }

    /**
     *
     * @param num 待从long转为byte[]的数据
     * @return 返回一个byte类型的数据
     */
    static public byte [] longToByte(Long num){
        byte[] bytes = num.toString().getBytes();
        return bytes;
    }

    /**
     *
     * @param bytes 待转为long类型待byte数组
     * @param off 待转数组的起点
     * @param len 转化的长度
     * @return
     */
    static public Long byteToLong (byte[] bytes, int off, int len){
        Long num = Long.valueOf(new String(bytes,off,len));
        return num;
    }
    /**
     *
     * @param bt 一个字节数据
     * @param bt2 一个byte数组数据
     * @return 将bt与bt2进行拼接然后返回
     *      主要用于将信号与数据拼接到一块发送给对端
     */
    static public byte[] byteMerger(byte bt, byte[] bt2){
        byte[] bt1 = new byte[1];
        bt1[0] = bt;
        byte[] bt3 = new byte[bt1.length+bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }
}
