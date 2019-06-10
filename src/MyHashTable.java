public class MyHashTable {

    // 设置一个定长数组，存放头节点
    public Node[] headList = new Node[1];
    // 记录当前hash表的元素个数
    public int size=0;
    // 当hsah存储的元素个数超过这个值时就会refresh
    public float peakValue=1.7f;

    /**
     * 主函数的入口
     * @param args
     */
    public static void main(String[] args){

        // 定义键值对
        String[] key={"a","s","d","f","g","h","j","k","l"};
        String[] data={"1","2","3","4","5","6","7","8","9"};

        // 新建hashTable
        MyHashTable myHashTable=new MyHashTable();

        // 循环构建该hashTable
        for(int i=0;i<key.length;i++){
            myHashTable.put(key[i],data[i]);
            System.out.println("展示当前的hash表");
            myHashTable.show();
        }

        // 根据key值获取data
        for(int i=0;i<key.length;i++){
            String data0=(String)myHashTable.get(key[i]);
            System.out.print(data0+" ");
        }
    }

    /**
     * 计算hash的值,该算法可以替换
     * @param key
     * @param length
     * @return
     */
    private Integer hash(Object key,int length){
       // 最后返回的索引值
        Integer index=null;
        if(key!=null){
            // 将key值转换位字符数组
            char[] charList = key.toString().toCharArray();
            // 存储所有字符的ASCII码
            int number=0;
            for(int i=0;i<charList.length;i++){
                number=number+charList[i];
            }
            // 得到头节点的位置
            index=Math.abs(number%length);
        }
        return index;
    }

    /**
     * 当headList大小不够时，扩容
     */
    public void reHash(){
        Node[] newNode = new Node[headList.length*2];

        // 遍历旧的headList
        for(int i=0;i<headList.length;i++){

            // 先处理头节点
            if(headList[i]!=null){

                // 计算新得到的键值
                int newHeadIndex = hash(headList[i].key,newNode.length);
                // 声明一个新结点
                Node newHeadNode = new Node(headList[i].key,headList[i].data);
                // 使该结点没有其他链接
                newHeadNode.next=null;
                // 将该结点插入新的headList中
                input(newHeadNode,newNode,newHeadIndex);

                // 遍历的指针结点
                Node tmp = headList[i];
                // 该链表有结点时
                while(tmp.next!=null){
                    tmp=tmp.next;
                    // 计算新得的键值
                    int newNextIndex = hash(tmp.key,newNode.length);
                    // 对其作与HeadNode相同的操作
                    // 声明一个新结点
                    Node newNextNode = new Node(tmp.key,tmp.data);
                    newNextNode.next=null;
                    input(newNextNode,newNode,newNextIndex);
                }
            }
        }
        headList=newNode;
    }

    /**
     * 向hashCode中添加键值对
     * @param key
     * @param data
     */
    public void put(Object key,Object data){

        // 判断是否要扩容
        if((size*1.0)/headList.length>peakValue){
            reHash();
        }

        // 计算hash值
        int index = hash(key,headList.length);

        // 将数据封装成对象
        Node node = new Node(key,data);

        // 加入Hash表中
        input(node,headList,index);
        size++;
    }

    /**
     * 添加函数
     * @param node
     * @param list
     * @param index
     */
    public void input(Node node,Node[] list,int index){
        // 若根据计算出来的位置，在list中为空值
        if(list[index]==null){
            list[index]=node;
        }
        else{
            Node tmp = list[index];
            // 判断键值是否存在
            if(tmp.key==node.key){
                System.out.println(tmp.key+"该键值已经存在!");
            }
            else {
                while(tmp.next!=null){
                    tmp=tmp.next;
                    if(tmp.key==node.key){
                        System.out.println(tmp.key+"该键值意境存在!");
                        break;
                    }
                }
                tmp.next=node;
            }
        }
    }

    /**
     * 返回headList的大小
     * @return
     */
    public int length(){
        return size;
    }

    /**
     * 显示当前HashTable
     */
    public void show(){
        // 遍历该headList
        for(int i=0;i<headList.length;i++){
            if(headList[i]!=null){
                System.out.println(headList[i].key+":"+headList[i].data+"-->");
                // 遍历该head的next
                Node tmp = headList[i];
                while(tmp.next!=null){
                    tmp=tmp.next;
                    System.out.print(tmp.key+":"+tmp.data+"-->");
                }
                System.out.println();
            }
        }
    }

    /**
     * 根据键值获取到data
     * @param key
     * @return
     */
    public Object get(Object key){

        // 将键值转换为对应的hash值
        int index = hash(key,headList.length);
        Node tmp = headList[index];
        if(tmp==null){
            System.out.println("找不到该data");
            return null;
        }
        else{
            if(key==tmp.key){
                return tmp.data;
            }
            else {
                while (tmp.next != null) {
                    tmp = tmp.next;
                    if (key == tmp.key) {
                        return tmp.data;
                    }
                }
                System.out.println("找不到该data");
                return null;
            }
        }
    }
}

/**
 * 散列表中的结点类
 */
class Node{

    // 构造链表的形式
    Node next;

    // 键值
    Object key;

    // 储存的数据
    Object data;

    /**
     * 构造方法
     * @param key
     * @param data
     */
    public Node(Object key,Object data){
        this.key=key;
        this.data=data;
    }
}
