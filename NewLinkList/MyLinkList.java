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
    private static final int START_POS = 0;
    //根据pos返回偏移后的节点引用
    private ListNode getListNode(int pos){
        //若输入位置不合法将直接返回节点为空
        if(pos < START_POS || pos > listSize){
            return null;
        }
        //若输入位置节点合法则返回节点必不为空
        ListNode node = head;
        for(int i = 0; i < pos; i++) {
            node =node.next;
        }
        return node;
    }
    //查找num并返回其引用，若有重复返回第一个节点的引用
    private ListNode selectLinkNode(int num){
        //链表为空直接return
        if(isEmpty()){
            return null;
        }
        ListNode p = head.next;
        while(null != p && p.num != num){
            p=p.next;
        }
        //找到则返回引用否则返回空
        return p;
    }
    public MyLinkList(){
        head = new ListNode();
    }
    //判断链表是否为空
    public boolean isEmpty(){
        if(0 != listSize) {
            return false;
        }
        else {
            return true;
        }
    }
    //在满足条件的合法的位置插入节点
    public boolean insertListNode(int num,int pos){
        ListNode node = new ListNode(num);
       //获取偏移节点
        ListNode p = getListNode(pos);
        if(null == p){
            return false;
        }
        //执行插入操作
        node.next = p.next;
        p.next = node;
        listSize += 1;
        return true;
    }
    //以下标的形式删除节点
    public boolean deletePosListNode(int pos){
        ListNode p = getListNode(pos);
        if(null == p){
            //输入pos有误
            return false;
        }
        //删除
        p.next = p.next.next;
        listSize -= 1;
        return true;
    }
    //以num的形式删除节点
    public boolean deleteNumListNode(int num){
        boolean res = true;
        ListNode p = selectLinkNode(num);
        if(null == p){
            //未找到该节点或是链表为空
            res = false;
        }
        p.next = p.next.next;
        listSize -= 1;
        return res;
    }
    //查找num是否存在与链表中
    public boolean isNumLinkList(int num){
        boolean res = false;
        if(null!= selectLinkNode(num)){
            res = true;
        }
        return res;
    }
    //链表头插
    public void push_front(int num){
        insertListNode(num,0);
        return;
    }
    //链表头删
    public void pop_front(){
        if(!isEmpty()){
            if(!deletePosListNode(0)){
                throw new DeletLinkListException("删除异常");
            }
        }
        return;
    }
    // 链表拷贝
    public void copyLinkList(MyLinkList list){
        //如果地址相同则无需拷贝直接return
        if(list.head == head){
            return;
        }
        ListNode p = head.next;
        while(null != p) {
            list.insertListNode(p.num,list.listSize);
            p = p.next;
        }
        return;
    }
    //展示数据
    public void showLinkList(){
        if(null == head.next) {
            System.out.println("链表为空");
            return;
        }
        ListNode p = head.next;
        while(null != p){
            System.out.println(p.num);
            p = p.next;
        }
    }
    //清空链表
    public void cleanLinkList(){
        ListNode p = head;
        while(null != p.next){
            pop_front();
        }
    }
    // 获取当前链表的长度
    public int getListSize(){
        return listSize;
    }
}
