
//构建一个Hashtable类

public class Hashtable {



    //定义一个节点类，里面定义了每一个节点所需要的数据

    public class Node {

        Node next;//指向下一节点

        Object key;//键值

        Object data;//数据域



        //节点的构造函数

        public Node(Object key,Object data) {

            this.key=key;

            this.data=data;

        }

    }



    public Node[] Headlist=new Node[1];//申请一个定长数组

    public int size=0;//记录当前hash表的元素个数

    public float peakValue=1.7f;//定义一个峰值，如果当前hash存储的元素个数超过这个峰值就进行rehash



    //主函数入口

    public static void main(String[] args) {

        //定义一定数量的键值对

        String[] key= {"a","b","c","d","e","f","g","i"};

        String[] data= {"1","2","3","4","5","6","7","8"};



        //初始化哈希表

        Hashtable table=new Hashtable();

        for(int i=0;i<key.length;i++) {

            //将每一个键值对一一加到构造好的哈希表中

            table.put(key[i], data[i]);

            System.out.println("展示当前的hash表");

            //展示每一次添加数据之后的哈希表构成

            table.show();

        }

        for(int i=0;i<key.length;i++) {

            //根据键值从哈希表中获取相应的数据

            String data1=(String)table.get(key[i]);

            System.out.print(data1+" ");

        }

    }



    //往哈希表中添加一个键值对

    public void put(Object key,Object data) {

        //判断当前的哈希表容量是否已经达到峰值，如果达到峰值，就hash表的重构

        if((size*1.0)/Headlist.length>peakValue) rehash();

        //调用hash函数获取键值对应的hashcode，从而定位到相应的头结点

        int index=hash(key,Headlist.length);

        //把当前的节点封装成Node节点类

        Node node=new Node(key,data);

        //加入哈希表

        input(node,Headlist,index);

        size++;

    }



    //设计一个添加函数,实现代码的复用

    private void input(Node node,Node[] list,int index) {

        //如果头结点位置为空，则把当前节点赋值给头结点

        if(list[index]==null) {

            list[index]=node;

        }else {

            //否则，遍历该链表，并判断该键值是否已经存在于哈希表中，如果没有就将其加到链表尾部

            Node temp=list[index];

            //判断表头元素的键值是否和我们即将输入的键值一样

            if(temp.key==node.key) {

                System.out.println(temp.key+"--该键值已存在！");

            }else {

                while(temp.next!=null) {

                    temp=temp.next;

                    if(temp.key==node.key) {

                        System.out.println(temp.key+"--该键值已存在！");

                        break;

                    }

                }

                temp.next=node;

            }

        }

    }





    //hash函数计算出键值对应的hashcode，也就是头结点的位置

    private Integer hash(Object key,int lenth) {

        Integer index=null;

        if(key!=null) {

            //进来的可能是一个字符串，而不是数字

            //先转化为字符数组

            char[] charlist=key.toString().toCharArray();

            int number=0;

            //依次计算出每个字符对应的ASCII码

            for(int i=0;i<charlist.length;i++) {

                number+=charlist[i];

            }

            //对哈希表的数组长度取余，得到头结点的位置

            index=Math.abs(number%lenth);

        }

        return index;

    }



    //rehash函数对当前的hash表进行扩展，重新定位当前表中的所有

    public void rehash() {

        //每次扩展都把当前的哈希表增大一倍

        Node[] newnode=new Node[Headlist.length*2];

        //遍历原来的哈希表，依次把每个数据重新添加到新的哈希表中

        for(int i=0;i<Headlist.length;i++) {

            if(Headlist[i]!=null) {

                //先把每个列表的头结点重新hash进去

                int headposition=hash(Headlist[i].key,newnode.length);

                //这个地方一定要用new重新构建一个新的节点来保存原来哈希表中节点的键值对。

                Node rehashheadnode=new Node(Headlist[i].key,Headlist[i].data);

                //设置它的下一个节点为空，这条代码不写也可以，这里为了强调它的重要性，特意将其写了出来

                //这条代码的作用就是去除原来哈希表中各个节点的关联关系

                rehashheadnode.next=null;

                input(rehashheadnode,newnode,headposition);

                Node temp=Headlist[i];

                while(temp.next!=null) {

                    temp=temp.next;

                    //定义一个Node类型的数据来储存需要rehash的数据

                    Node rehashnextnode=new Node(temp.key,temp.data);

                    rehashnextnode.next=null;

                    int nextposition=hash(temp.key,newnode.length);

                    input(rehashnextnode,newnode,nextposition);

                }

            }

        }

        //重新设置节点数组的引用

        Headlist=newnode;

    }



    //显示当前的hash表

    public void show() {

        for(int i=0;i<Headlist.length;i++) {

            if(Headlist[i]!=null) {

                System.out.print(Headlist[i].key+":"+Headlist[i].data+"-->");

                Node temp=Headlist[i];

                while(temp.next!=null) {

                    temp=temp.next;

                    System.out.print(temp.key+":"+temp.data+"-->");

                }

                System.out.println();

            }

        }

    }



    //获取键值相对应的数据

    public Object get(Object key) {

        //先获取key对应的hashcode

        int index=hash(key,Headlist.length);

        Node temp=Headlist[index];

        //先判断相应的头结点是否为空

        if(temp==null) return null;

        else {

            //判断节点中的key和待查找的key是否相同

            if(temp.key==key) return temp.data;

            else {

                while(temp.next!=null) {

                    temp=temp.next;

                    if(temp.key==key) return temp.data;

                }

            }

        }

        return null;

    }



    //返回当前hash表的大小

    public int length(){

        return size;

    }

}

/*

 *巨坑，原来的节点的下一个节点需要重新设置为null

 */
