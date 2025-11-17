import java.util.*;
import java.io.*;


public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(br.readLine());
       Deque<Node> md = new LinkedList<>();
       for(int i = -; i < N ; i++){
        int now = Integer.parseInt(st.nextToken());
        while(!md.isEmpty() && md.getLast().vale > now){
            md.removeLast();
        }
        md.addLast(new Node(now,i));
        if(md.getFirst().index <= i-L){
            md.removeFirst();
        }
        bw.write(md.getFirst().value+" ");

       }
       bw.flush();
       bw.close();
    }

    static class Node{
        public int value;
        public int index;

        Node(int value, int index){
            this.value = value;
            this.index = index;
        }
    }
}