/**
 * 目的：熟悉Java语法
 * 实现一个单链表
 *  1.实现链表的头插、头删；
 *  2.实现链表的任意位置的插入、删除；
 *  3.实现链表拷贝；
 *  4.修改链表的某个节点；
 *  5.查找链表是否存在某个数据；
 *  6.清空链表；
 *
 *
 */
public class MyLinkList {
    private  int listSize;
    private ListNode head;

    public MyLinkList(){};
    //初始化链表
    public void initListNode(){
        if(null != head) {
            return;
        }
        head = new ListNode();
        if(null == head){
            throw new GetHeapMemoryException("获取内存构建对象失败！");
        }
        return ;
    }
    //判断链表是否初始化
    public void isInit(){
        if(null == head) {
            throw new InitLinkListException("链表未初始化！");
        }
        return ;
    }
    //判断链表是否为空
    public boolean isEmpty(){
        if(null != head.next) {
            return false;
        }
        else {
            return true;
        }
    }
    //根据pos返回偏移后的节点引用
    private ListNode getListNode(int pos)throws IllegalPosException{
        if(pos < 0 || pos > listSize){
            throw new IllegalPosException("输入的pos不合法！");
        }
        ListNode node = head;
        for(int i = 0; i < pos; i++) {
            node =node.next;
        }
        return node;
    }
    //在满足条件的合法的位置插入节点
    public void insertListNode(int num,int pos)throws IllegalPosException{
//        if(pos < 0 && pos >= listSize)
//            throw new IllegalPosException("输入的pos不合法");
        isInit();
        ListNode node = new ListNode(num);
        if(null == node) {
            throw new GetHeapMemoryException("获取内存构建对象失败");
        }
       //获取偏移节点
        ListNode p = getListNode(pos);
        //执行插入操作
        node.next = p.next;
        p.next = node;
        listSize += 1;
        return ;
    }
    //查找num并返回其引用，若有重复返回第一个节点的引用
    private ListNode selectLinkNode(int num){
        if(isEmpty()){
            return null;
        }
        ListNode p = head.next;
        while(p.num!=num){
            p=p.next;
        }
        return p;
    }
    //以下标的形式删除节点
    public void deletePosListNode(int pos)throws IllegalPosException{
        isInit();
        ListNode p = getListNode(pos);
        //删除
        p.next = p.next.next;
        listSize -= 1;
        return;
    }
    //以num的形式删除节点
    public boolean deleteNumListNode(int num){
        boolean res = true;
        isInit();
        ListNode p = selectLinkNode(num);
        if(null == p){
            res = false;
        }
        p.next = p.next.next;
        listSize -= 1;
        return res;
    }
    //查找num是否存在与链表中
    public boolean isNumLinkList(int num){
        boolean res = false;
        isInit();
        if(null!= selectLinkNode(num)){
            res = true;
        }
        return res;
    }
    //链表头插
    public void push_front(int num)throws IllegalPosException{
        insertListNode(num,0);
        return;
    }
    //链表头删
    public void pop_front()throws IllegalPosException{
        if(!isEmpty()){
            deletePosListNode(0);
        }
        return;
    }
    // 链表拷贝
    public void copyLinkList(MyLinkList list){
        if(null == list.head) {
            list.initListNode();
        }
        ListNode p = head.next;
        while(null != p) {
            try{
                list.insertListNode(p.num,list.listSize);
            }catch (IllegalPosException e){
                System.out.println(e.getMessage());
            }
            p = p.next;
        }
        return ;
    }
    //展示数据
    public void showLinkList(){
        ListNode p = head.next;
        if(null == head.next) {
            System.out.println("链表为空");
            return;
        }
        while(null != p){
            System.out.println(p.num);
            p = p.next;
        }
    }
    //清空链表
    public void cleanLinkList(){
        ListNode p = head;
        while(null != p.next){
            try{
                pop_front();
            }catch (IllegalPosException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    // 获取当前链表的长度
    public int getListSize(){
        return listSize;
    }
}
