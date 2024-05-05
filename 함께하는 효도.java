import java.io.*;
import java.util.*;
import java.awt.Point;
// 전달되어야 하는 것 : 흐른 시간, 수확여부, 

public class Main {
    static final int MAX_SEC = 4;
    static int n,m;
    static int[][] field;
    static ArrayList<Point> friends = new ArrayList<>();
    static boolean[][] visited;
    static ArrayList<ArrayList<Route>> routeGroup = new ArrayList<>();
    static ArrayList<Point> visitedPoints = new ArrayList<>();
    static int result = Integer.MIN_VALUE;

    static int[][] directions = {{-1,0},{1,0},{0,1},{0,-1}};

    // 한 사람의 경로 Route : Point 리스트
    // 경로 조합  : List<Route>
    // 모든 가능한 경로 조합 : List<List<Route>>

    static class Route {
        Point[] points;

        public Route (ArrayList<Point> points) {
            this.points = points.toArray(new Point[4]);
        }
    }

    // 친구별 모든 가능한 루트 찾기
    static void findAllRouteCombination() {
        for (int i=0; i<m; i++) {
            routeGroup.add(new ArrayList<Route>());
            // 한 사람에 대해 가능한 모든 경로 찾기
            findRoutesForOne(i);
        }
    }

    // 한 사람에 대해 가능한 모든 경로 찾기
    static void findRoutesForOne(int idx) {
        // 처음 시작하는 칸 수확 및 방문 처리
        Point start = friends.get(idx);
        visitedPoints.clear();
        visitedPoints.add(start);
        dfs(visitedPoints, idx, start.x, start.y);
    }

    static void dfs(ArrayList<Point> visitedPoints, int idx, int x, int y) {
        if (visitedPoints.size() == MAX_SEC) {
            // 친구가 방문할 수 있는 루트 저장
            routeGroup.get(idx).add(new Route(visitedPoints));
            return;
        }


        for (int[] d: directions) {
            int nx = d[0] + x;
            int ny = d[1] + y;
            Point np = new Point(nx, ny);

            // 영역밖 좌표
            if (nx < 0 || nx >= n || ny < 0 || ny >= n) {
                continue;
            }
            // 이미 방문한 좌표
            if (isVisited(visitedPoints, np)) {
                continue;
            }
            visitedPoints.add(np);
            dfs(visitedPoints,idx, nx, ny);
            // 백트래킹
            visitedPoints.remove(visitedPoints.size()-1);
        }
    }

    static boolean isVisited(ArrayList<Point> visitedPoints, Point p) {
        for (Point v: visitedPoints) {
            if (p.x == v.x && p.y == v.y) {
                return true;
            }
        }
        return false;
    }

    static void findOptimalRoute(ArrayList<Route> routeList, int idx) {
        // m개의 친구들 루트가 모든 결정된 경우
        if (idx == m) {
            result = Math.max(result, findTotalYield(routeList));
            return;
     
        }

        for(Route route: routeGroup.get(idx)) {
            routeList.add(route);
            findOptimalRoute(routeList, idx+1);
            routeList.remove(routeList.size()-1);
        }
        
    }

    static int findTotalYield(ArrayList<Route> routeList) {
        // 방문 여부 초기화
        for (int i=0; i<n; i++) {
            Arrays.fill(visited[i], false);
        }
        int total  = 0;
        
        for (Route r: routeList) {
            for (Point p: r.points) {
                if (!visited[p.x][p.y]) {
                    total += field[p.x][p.y];
                    visited[p.x][p.y] = true;
                }
            }
        }

        return total;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        field = new int[n][n];
        visited = new boolean[n][n];

        // 열매 수확량 정보
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                field[i][j] = sc.nextInt();
            }
        }

        // 친구들 시작 좌표 정보
        for (int i=0; i<m; i++) {
            int x = sc.nextInt()-1;
            int y = sc.nextInt()-1;
            friends.add(new Point(x,y));
        }
        
        // 친구별 모든 가능한 루트 찾기
        findAllRouteCombination();

        // 가장 수확량이 많은 조합 찾기 (백트래킹)
        ArrayList<Route> routeList = new ArrayList<>();
        findOptimalRoute(routeList, 0);
        
        System.out.println(result);
    }
}
