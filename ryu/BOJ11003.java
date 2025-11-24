import java.io.*;
import java.util.*;

public class BOJ11003 {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        class Node {
            int value;
            int index;

            public Node(int value, int index){
                this.value = value;
                this.index = index;
            }
        }
        Deque<Node> d = new LinkedList<>();

        for(int i = 0; i < N ; i++){
           int now = Integer.parseInt(st.nextToken());

           while(!d.isEmpty() && d.getLast().value > now){
               d.removeLast();
           }
           d.addLast(new Node(now, i));

           if(d.getFirst().index <= i-L){
               d.removeFirst();
           }

           bw.write(d.getFirst().value + " ");

           bw.flush();
           bw.close();
        }
        br.close();
    }
}
