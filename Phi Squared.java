import java.io.*;
import java.util.*;

// 하루에 한번 흡수
// 인접한 미생물 중 자신보다 작거나 같은거 흡수

class Node {
    long size;
    int idx;

    Node(long size, int idx) {
        this.size = size;
        this.idx = idx;
    }

    @Override
    public String toString() {
        return "size : " + size + ",idx: " + idx;
    }
}

public class Main {
    static int n;
    static Deque<Node> micro = new ArrayDeque<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i=1; i<=n; i++) {
            long cur = Long.parseLong(st.nextToken());
            micro.offer(new Node(cur,i));
        }


        while(micro.size() > 1){
            Deque<Node> result = new ArrayDeque<>();
            while(!micro.isEmpty()) {
                Node curNode = micro.poll();

                long tmp = 0;
                // result is not empty and the last one is absorbable
                if(!result.isEmpty() && result.peekLast().size <= curNode.size) {
                    Node leftNode = result.pollLast();
                    tmp += leftNode.size;
                }

                // next node in micro is not null and is absorbable
                if (!micro.isEmpty() && micro.peek().size <= curNode.size) {
                    Node rightNode = micro.poll();
                    tmp += rightNode.size;
                }
 
                result.addLast(new Node(curNode.size + tmp, curNode.idx));

            }
            // System.out.println(result);
            micro = result;
        }
        

        Node lastNode = micro.poll();
        System.out.println(lastNode.size);
        System.out.println(lastNode.idx);
    }
}
