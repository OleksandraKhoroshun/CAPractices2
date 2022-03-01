
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedTransferQueue;

public class ST<Key extends Comparable<Key>, Value> {

    public class Node<Key,Value> {
        public Key key;
        public Value val;

        public Node(Key key,Value val) {
            this.key = key;
            this.val = val;
        }
    }

    private static final int init_size = 1;

    private Node<Key,Value>[] st;
    private int size=0;

    ST() {
        st = new Node[init_size];
    }

    private void resize(int capacity) {
        Node<Key,Value>[] temp = new Node[capacity];

        for (int i = 0; i < size; i++)
            temp[i] = st[i];
        st = temp;
    }

    void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("value of key is null in put()");
        if (val == null) delete(key);

        else {
            if (size >= st.length) resize(2 * size);

            st[size] = new Node<Key,Value>(key,val);
            size++;
        }
    }

    Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("value of key is null in get()");
        for (int i = 0; i < size; i++)
            if (st[i].key.equals(key)) {
                Value res = st[i].val;
                return st[i].val;
            }
        return null;
    }

    void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("value of key is null in delete()");
        for (int i = 0; i < size; i++) {
            if (key.equals(st[i].key)) {
                st[i].key = st[size-1].key;
                st[size-1] = null;
                size--;
                if (size > 0 && size == st.length/4) resize(st.length/2);
                return;
            }
        }
    }

    boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("value of key is null in contains()");
        for (int i = 0; i < size; i++)
            if (st[i].key.equals(key)) {
                return true;
            }
        return false;
    }

    boolean isEmpty(){
        return size() == 0;
    }

    int size(){
        return size;
    }

    Key min() {
        if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
        Key minimum=st[0].key;
        for (int i = 1; i < size; i++){
            if (st[i].key.compareTo(minimum)==-1) {
                minimum=st[i].key;
            }
            }
        return minimum;
    }//найменший ключ

    Key max(){
        if (isEmpty()) throw new NoSuchElementException("called max() with empty symbol table");
        Key maximum=st[0].key;
        for (int i = 1; i < size; i++){
            if (st[i].key.compareTo(maximum)==1) {
                maximum=st[i].key;
            }
        }
        return maximum;
    } //найбільший ключ

    Key floor(Key key){
        if (isEmpty()) throw new NoSuchElementException("called floor() with empty symbol table");
        int i = rank(key);
        if (i < size && key.compareTo(st[i].key) == 0)
            return st[i].key;
        if (i == 0) return null;
        else return st[i-1].key;
    } //найбільший ключ менший або рівний key

    Key ceiling(Key key){
        if (isEmpty()) throw new NoSuchElementException("called ceiling() with empty symbol table");
        int i = rank(key);
        if (i == size) return null;
        else return st[i].key;
    } //найменший ключ більший або рівний key

    int rank(Key key) {
        int res=0;
        for(int i=0;i<size;i++){
            if(st[i].key.compareTo(key)==-1)res++;
        }
      return res;
    }//кількість ключів менших за key

    Key select(int k){
        if (k < 0 || k >= size) {      // same as when the key is null
            throw new IllegalArgumentException("Value of key is less than 0 or bugger than array size");
        }
        return st[k].key;
    } //key k

    void deleteMin(){
        delete(min());
    }

    void deleteMax(){
        delete(max());
    }

    int size(Key lo, Key hi){
        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    } //кількість ключів в [lo..hi]

    Iterable<Key> keys(){
        return keys(min(), max());
    } //повертає ітератор по ключам

    Iterable<Key> keys(Key lo, Key hi){
        LinkedTransferQueue<Key> queue = new LinkedTransferQueue<Key>();
        if (lo.compareTo(hi) > 0) return queue;
        for (int i = rank(lo); i < rank(hi); i++)
                    queue.add(st[i].key);
        if (contains(hi)) queue.add(st[rank(hi)].key);

        return queue;

    }
}