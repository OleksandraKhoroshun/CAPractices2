import java.util.ArrayList;

public class SearchDictionary {
    public int size;

    private Node first;
    private int words;

    public SearchDictionary() {
        size=1;
        first = new Node(' ');
    }

    public void addWord(String word) {
        words++;
        put(first, word);
    }

    public String delWord(String word) {
        if (words == 0) throw new NullPointerException("Dictionary empty");
        if(!hasWord(word)) throw new NullPointerException("No such word");
        del(first, word);
        words--;
        return word;
    }

    public boolean hasWord(String word) {
        return search(first, word);
    }

    public Iterable<String> query(String query) {
        ArrayList<String> result = new ArrayList<>();
        query(first, query, "", result);
        return result;
    }

    private void query(Node n, String query, String word, ArrayList<String> result) {
        //if (query.isEmpty()) throw new NullPointerException("Query is empty");
        char temp='\n';
        if (!query.isEmpty()) temp = query.charAt(0);
        int t = 0;

        if (!query.equals("*") && !query.isEmpty()) {
            boolean f = false;
            while (n.arr[t] != null ) {
                if (n.arr[t].ch == temp) {
                    f = true;
                    break;
                }
                t++;
                if(t>=n.arr.length) break;
            }
            //if (query.substring(1, query.length()).equals("\n")) {/*result.add(word);*/ return;}
            if (f) query(n.arr[t], query.substring(1, query.length()), word + temp, result);
        }
        else if (query.isEmpty()){
            while (t < n.arr.length && n.arr[t] != null) {
                if (n.arr[t].ch == '\n') result.add(word);
                //else query(n.arr[t], query, word + n.arr[t].ch, result);
                t++;
            }
        }
        else {
            while (t < n.arr.length && n.arr[t] != null) {
                if (n.arr[t].ch == '\n') result.add(word);
                else query(n.arr[t], query, word + n.arr[t].ch, result);
                t++;
            }
        }
        return;
    }

    public int countWords() {
        return words;
    }


    private class Node {
        char ch;
        Node[] arr = new Node[size];

        public Node(char ch) {
            this.ch = ch;
        }
    }


    private void put(Node n, String s) {
        char temp;

        if (s.isEmpty()) temp = '\n';
        else temp = s.charAt(0);

        int t = 0;
        while (n.arr[t] != null) {
            if (n.arr[t].ch == temp) break;
            t++;
            if (t >= n.arr.length) resize(n,n.arr.length * 2);
        }

        if (n.arr[t] == null) n.arr[t] = new Node(temp);

        if (temp == '\n') {
            return;
        }

        put(n.arr[t], s.substring(1, s.length()));
        return;
    }

    private Node del(Node n, String s) {
        char temp;

        if (s.isEmpty()) temp = '\n';
        else temp = s.charAt(0);

        int t = 0;
        while (n.arr[t] != null) {
            if (n.arr[t].ch == temp) break;
            t++;
        }

        if (temp != '\n') n.arr[t] = del(n.arr[t], s.substring(1, s.length()));
        else n.arr[t] = null;

        if (n.arr[t] == null) {
            if((t+1)<n.arr.length)
            if (n.arr[t + 1] != null) {
                while (n.arr[t + 1] != null) {if((t+1)<n.arr.length) break;n.arr[t] = n.arr[++t];}
                n.arr[t] = null;
                return n;
            }

            if (t != 0 && n.arr[t - 1] != null) return n;
            return null;
        }
        return n;
    }

    private boolean search(Node n, String s) {
        char temp;

        if (s.isEmpty()) temp = '\n';
        else temp = s.charAt(0);

        boolean result = false;
        boolean f = false;
        int t = 0;
        if(t>=n.arr.length) return false;
        while (n.arr[t] != null) {
            if (n.arr[t].ch == temp) {
                f = true;
                break;
            }
            t++;
            if(t>=n.arr.length) return false;
        }

        if (f) {
            if (temp == '\n') result = true;
            else result = search(n.arr[t], s.substring(1, s.length()));
        }

        return result;
    }

    private void resize(Node n,int size) {
        Node[] temp = new Node[size];

        for (int i = 0; i < n.arr.length; i++) {
            temp[i] = n.arr[i];
        }
        n.arr = temp;
    }

}