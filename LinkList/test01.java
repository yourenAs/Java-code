import java.util.Scanner;

public class  test01{
    public static void main(String[] args) {
        MyLinkList list = new MyLinkList();
        list.initListNode();
//        int i = 0;
//        while(true){
//            if(i == 10) break;
//           try{
//               list.insertListNode(i+1,i);
//           }catch (IllegalPosException e){
//               System.out.println(e.getMessage());
//           }
//           ++i;
//       }

        try{
               list.insertListNode(3,0);
        }catch (IllegalPosException e){
               System.out.println(e.getMessage());
        }

        System.out.println(list.isNumLinkList(3));

//        try{
//            System.out.println("2");
//            list.insertListNode(2,1);
//        }catch (IllegalPosException e){
//            System.out.println(e.getMessage());
//        }
//        list.showLinkList();
//        MyLinkList list2 = new MyLinkList();
//        list.copyLinkList(list2);
//        list2.showLinkList();
//        list2.cleanLinkList();
//        list2.showLinkList();


    }
}