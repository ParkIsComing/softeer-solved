import java.io.*;
import java.util.*;

public class Main {
    static char getIndirectIndexing(String s, String t) {
        s = s.toUpperCase();
        t = t.toUpperCase();
        int idx = -1;
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) == 'X') {
                idx = i;
                break;
            }
        }
        return t.charAt(idx);
    }
    
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        
        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            char resultChar = getIndirectIndexing(st.nextToken(), st.nextToken());
            sb.append(resultChar);
        }

        System.out.println(sb);
    }
}
