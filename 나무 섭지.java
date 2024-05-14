// D까지 유령이 남우보다 빨리 도착하면 막을 수 있음 -> No
// 아니면 Yes

import java.io.*;
import java.util.*;

class Point {
    int x;
    int y;
    int time;

    Point(int x, int y, int time) {
        this.x = x;
        this.y = y;
        this.time = time;
    }
}

public class Main {
    static int n, m;
    static char[][] board;
    static int[][] direction = {{-1,0},{1,0},{0,1},{0,-1}};
    static boolean[][] visited;
    static ArrayList<int[]> ghosts = new ArrayList<>();
    static int destX = 0, destY = 0;

    static int escapeByNamwoo(int x, int y) {
        Deque<Point> q = new ArrayDeque<>();
        q.offer(new Point(x,y,0));
        visited[x][y] = true;

        while (!q.isEmpty()) {
            Point p = q.poll();
            if (board[p.x][p.y] == 'D') {
                return p.time;
            }

            for (int[] d : direction) {
                int nx = d[0] + p.x;
                int ny = d[1] + p.y;

                // 못 움직이는 조건1: 범위 밖
                if (nx<0 || nx>=n || ny<0 || ny>=m) {
                    continue;
                }

                // 못 움직이는 조건2: 벽이 있는 경우
                if (board[nx][ny] == '#') {
                    continue;
                }

                if (!visited[nx][ny]) {
                    q.offer(new Point(nx, ny, p.time +1));
                    visited[nx][ny] = true;
                }
            }
            
        }
        // 탈출할 수 없는 경우
        return -1;
    }

    // 목적지로부터 시작점이 가장 가까운 유령 좌표 찾기
    static int[] findClosestGhostFromDest() {
        // 유령 리스트를 목적지 가까운 순 정렬
        Collections.sort(ghosts, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                int distA = Math.abs(a[0]-destX) + Math.abs(a[1]-destY);
                int distB = Math.abs(b[0]-destX) + Math.abs(b[1]-destY);
                return Integer.compare(distA, distB);
            }
        });

        int[] closestGhost = ghosts.get(0);
        return closestGhost;
    }
    static int escapeByGhost(int x, int y) {
        Deque<Point> q = new ArrayDeque<>();
        q.offer(new Point(x,y,0));
        visited[x][y] = true;

        while (!q.isEmpty()) {
            Point p = q.poll();
            if (board[p.x][p.y] == 'D') {
                return p.time;
            }

            for (int[] d : direction) {
                int nx = d[0] + p.x;
                int ny = d[1] + p.y;

                // 못 움직이는 조건1: 범위 밖
                if (nx<0 || nx>=n || ny<0 || ny>=m) {
                    continue;
                }


                if (!visited[nx][ny]) {
                    q.offer(new Point(nx, ny, p.time +1));
                    visited[nx][ny] = true;
                }
            }
            
        }
        return 0;
        
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        int namwooX = 0, namwooY = 0;

        board = new char[n][m];

        for (int i=0; i<n; i++) {
            String input = br.readLine();
            for(int j=0; j<m; j++) {
                board[i][j] = input.charAt(j);
                if (board[i][j] == 'G') {
                    ghosts.add(new int[]{i, j});
                }
                if (board[i][j] == 'N') {
                    namwooX = i;
                    namwooY = j;
                }
                if (board[i][j] == 'D') {
                    destX = i;
                    destY = j;
                }
            }
        }

        visited = new boolean[n][m];
        int timeNamwoo = escapeByNamwoo(namwooX, namwooY);
        int[] closestGhost = findClosestGhostFromDest();
        for (int i=0; i<n; i++) {
            Arrays.fill(visited[i], false);
        }
        int timeGhost = escapeByGhost(closestGhost[0], closestGhost[1]);

        if (timeNamwoo == -1 || timeNamwoo > timeGhost) {
            System.out.println("No");
        } else {
            System.out.println("Yes");
        }
        
    }
}
