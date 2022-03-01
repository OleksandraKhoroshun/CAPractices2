import java.util.Iterator;

public class Test {
    public static void main(String[] args){
        ST<Integer,String> st = new ST<Integer,String>();
        st.put(10,"A");
        st.put(20,"B");
        st.put(30,"C");
        st.put(40,"D");
        st.put(50,"E");

        //st.delete(40);
        //Iterator<Integer> it1 = st.keys(-1000,1000).iterator();
        //while(it1.hasNext())
        //System.out.println(it1.next());
        //System.out.println(st.contains(40));
        //System.out.println(st.contains(10));
        //System.out.println(it1.next());
        //System.out.println(st.keys(0,20));
        //System.out.println(st.rank(22));
        //System.out.println(st.size(-1000,22));

        //for(int i: st.keys(0,100)){
          //  System.out.println(i+" "+st.get(i));
        //}

        BST<Integer,String> bst = new BST<Integer,String>();
        bst.put(10,"A");
        bst.put(20,"B");
        bst.put(30,"C");
        bst.put(40,"D");
        bst.put(50,"E");

        //bst.deleteMin();
        //Iterator<Integer> it = bst.iterator().iterator();
        System.out.println(bst.rank(19));
        //System.out.println(bst.get(10));


    }
}
