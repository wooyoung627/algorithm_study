import java.util.*;

class Main01 {
    static List<LinkedList<Integer>> linkedLists = new ArrayList<>();
    static int v, e, first;
    static boolean[] visit;
    static String output = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a,b;
        v = scanner.nextInt();
        e = scanner.nextInt();
        first = scanner.nextInt();

        visit = new boolean[v];


        for(int i=0; i<v; i++){
            linkedLists.add(new LinkedList<>());
        }

        for(int i=0; i<e; i++){
            a = scanner.nextInt();
            b = scanner.nextInt();

            linkedLists.get(a-1).add(b);
            linkedLists.get(b-1).add(a);
        }

        for (LinkedList<Integer> linkedList : linkedLists) {
            Collections.sort(linkedList);
        }

        DFS(first);
        System.out.println(output);

        output = "";
        visit = new boolean[v];

        BFS(first);

        System.out.println(output);
    }

    // DFS
    public static void DFS(int first) {
        LinkedList<Integer> linkedList = linkedLists.get(first-1);

        visit[first - 1] = true;
        output += (""+first+" ");

        for(int u:linkedList){
            if(!visit[u-1]){
                visit[u-1]=true;
                DFS(u);
            }
        }
    }

    // BFS
    public static void BFS(int first){
        Queue<Integer> queue = new ArrayDeque<>();
        int search;

        output += (""+first+" ");
        visit[first-1]=true;
        queue.add(first);

        while (queue.size()!=0){
            search = queue.remove();
            LinkedList<Integer> linkedList = linkedLists.get(search-1);

            for(int u : linkedList){
                if(!visit[u-1]){
                    queue.add(u);
                    visit[u-1]=true;
                    output=output.concat(""+u+" ");
                }
            }
        }
    }
}

class Main02 {
    static int N,M;
    static int[][] count;
    static boolean[][] visit, graph;
    static LinkedList<int[]> linkedList = new LinkedList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String num;
        N = scanner.nextInt();
        M = scanner.nextInt();

        graph = new boolean[N][M];
        count = new int[N][M];
        visit = new boolean[N][M];

        for(int i=0; i<N; i++){
            num = scanner.next();
            for(int j=0; j<M; j++){
                graph[i][j]= Integer.parseInt(num.substring(j, j + 1)) == 1;
            }
        }
        BFS();
        System.out.println(count[N-1][M-1]+1);
    }

    public static void BFS(){
        Queue<int[]> queue = new ArrayDeque<>();
        int[] curr;
        int move;

        queue.add(new int[]{0,0});
        visit[0][0] = true;

        while(!queue.isEmpty()){
            curr = queue.poll();
            move = count[curr[0]][curr[1]] + 1;
            getNode(curr);

            for(int[] arr : linkedList){
                count[arr[0]][arr[1]] = move;
                visit[arr[0]][arr[1]] = true;
                queue.add(arr);
            }
        }

    }

    public static void getNode(int[] arr){
        int a = arr[0];
        int b = arr[1];
        linkedList.clear();

        if(a-1>=0 && !visit[a-1][b] && graph[a-1][b]){
            linkedList.add(new int[]{a - 1, b});
        }
        if(b-1>=0 && !visit[a][b-1] && graph[a][b-1]){
            linkedList.add(new int[]{a, b-1});
        }
        if((a+1)<N && !visit[a+1][b] && graph[a+1][b]){
            linkedList.add(new int[]{a + 1, b});
        }
        if((b+1)<M && !visit[a][b+1] && graph[a][b+1]){
            linkedList.add(new int[]{a, b+1});
        }
    }

}

class Main03 {
    static int subin, sister;
    static boolean[] visit;
    static Queue<int[]> queue = new ArrayDeque<>();
    static int min = 100000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        subin = scanner.nextInt();
        sister = scanner.nextInt();
        visit = new boolean[sister*2];

        // 수빈과 동생이 같이 있는 경우
        if(subin==sister)
            System.out.println(0);
            // 수빈이 더 높은 숫자에 위치한 경우
        else if(subin>sister){
            System.out.println(subin-sister);
        }
        // 동생이 더 높은 숫자에 위치한 경우
        else{
            play();
            System.out.println(min);
        }
    }

    public static void play(){
        int[] arr;
        queue.add(new int[]{0, subin});

        while (!queue.isEmpty()){
            arr = queue.poll();
            visit[arr[1]] = true;
            if(arr[1]==sister && min>arr[0]){
                min = arr[0];
                return;
            }

            if(arr[1]-1>0 && !visit[arr[1]-1]) {
                queue.add(new int[]{arr[0] + 1, arr[1] - 1});
                visit[arr[1]-1]=true;
            }
            if(arr[1]<sister && !visit[arr[1]*2]){
                queue.add(new int[]{arr[0] + 1, arr[1]*2});
                visit[arr[1]*2]=true;
            }
            if(arr[1]+1<sister*2 && !visit[arr[1]+1]){
                queue.add(new int[]{arr[0] + 1, arr[1]+1});
                visit[arr[1]+1]=true;
            }
        }
    }
}


class Main04 {
    static boolean[][] visit, graph;
    static int M,N,K,T, num;
    static int[] arr;

    public static void main(String[] args) {
        int a,b;
        Scanner scanner = new Scanner(System.in);
        LinkedList<int[]> list;
        T = scanner.nextInt();

        for(int i=0; i<T; i++){
            num = 0;
            M = scanner.nextInt();
            N = scanner.nextInt();
            K = scanner.nextInt();
            graph = new boolean[N][M];
            list = new LinkedList<>();

            for(int j=0; j<K; j++){
                b = scanner.nextInt();
                a = scanner.nextInt();
                graph[a][b] = true;
                list.add(new int[]{a,b});
            }

            visit = new boolean[N][M];
            for (int[] ints : list) {
                arr = ints;
                if (!visit[arr[0]][arr[1]]) {
                    near(arr[0], arr[1]);
                    num++;
                }
            }
            System.out.println(num);
        }
    }

    // 인접한 배추를 탐방하며 visit = true 로 설정
    public static void near(int a, int b){
        if(a-1>=0 && !visit[a-1][b] && graph[a-1][b]){
            visit[a-1][b] = true;
            near(a-1, b);
        }
        if(a+1<N && !visit[a+1][b] && graph[a+1][b]){
            visit[a+1][b] = true;
            near(a+1, b);
        }
        if(b-1>=0 && !visit[a][b-1] && graph[a][b-1]){
            visit[a][b-1] = true;
            near(a, b-1);
        }
        if(b+1<M && !visit[a][b+1] && graph[a][b+1]){
            visit[a][b+1] = true;
            near(a, b+1);
        }
    }
}

class Main05 {
    static int N, M, count = 0;
    static boolean[] visit;
    static ArrayList<LinkedList<Integer>> linkedList = new ArrayList<>();

    public static void main(String[] args) {
        int a,b;
        Scanner scanner = new Scanner(System.in);
        N = scanner.nextInt();
        M = scanner.nextInt();
        visit = new boolean[N];

        for(int i=0; i<N; i++){
            linkedList.add(new LinkedList<>());
        }

        for(int i=0; i<M; i++){
            a = scanner.nextInt();
            b = scanner.nextInt();
            linkedList.get(a-1).add(b-1);
            linkedList.get(b-1).add(a-1);
        }

        for(int i=0; i<N; i++){
            if(!visit[i]){
                count++;
                check(i);
            }
        }

        System.out.println(count);
    }

    public static void check(int num){
        LinkedList<Integer> arr = linkedList.get(num);
        for(Integer i : arr){
            if(!visit[i]){
                visit[i] = true;
                check(i);
            }
        }
    }
}

class Main06 {
    static LinkedList<Integer> house = new LinkedList<>();
    static boolean[][] map, visit;
    static int N, count=0;

    public static void main(String[] args) {
        String str;

        Scanner scanner = new Scanner(System.in);
        N = scanner.nextInt();
        map = new boolean[N][N];
        visit = new boolean[N][N];


        for(int i=0; i<N; i++){
            str = scanner.next();
            for(int j=0; j<N; j++)
                map[i][j] = str.charAt(j) == '1';
        }

        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                count=0;
                if(!visit[i][j] && map[i][j]){
                    visit[i][j] = true;
                    check(i, j);
                    house.add(count);
                }
            }
        }
        Collections.sort(house);

        System.out.println(house.size());
        for(int i=0; i<house.size(); i++)
            System.out.println(house.get(i));
    }

    public static void check(int a, int b){
        count++;

        if(a>0 && !visit[a-1][b] && map[a-1][b]){
            visit[a-1][b] = true;
            check(a-1, b);
        }
        if(b>0 && !visit[a][b-1] && map[a][b-1]){
            visit[a][b-1] = true;
            check(a, b-1);
        }
        if(a+1<N && !visit[a+1][b] && map[a+1][b]){
            visit[a+1][b] = true;
            check(a+1, b);
        }
        if(b+1<N && !visit[a][b+1] && map[a][b+1]){
            visit[a][b+1] = true;
            check(a, b+1);
        }
    }
}

class Main07 {
    static int k;
    static int[] list;
    static String result = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while((k=scanner.nextInt())!=0){
            list = new int[k];

            for(int i=0; i<k; i++){
                list[i] = scanner.nextInt();
            }

            for(int i=0; i<k; i++){
                dfs(i, ""+list[i]+" ", 1);
            }
            result = result.concat("\n");
        }
        System.out.println(result);
    }

    public static void dfs(int i, String str, int count){
        if(count==6){
            result += str + "\n";
        }
        else{
            for(int j=i+1; j<k; j++){
                dfs(j, str+list[j]+" ", count+1);
            }
        }
    }
}

class Main08 {
    static int N, K, M;
    static LinkedList<int[]> program = new LinkedList<>();
    static int[] virusCode;
    static boolean flag = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        N = scanner.nextInt();
        K = scanner.nextInt();
        virusCode = new int[K];

        for(int i=0; i<N; i++){
            M = scanner.nextInt();
            program.add(new int[M]);

            for(int j=0; j<M; j++)
                program.get(i)[j] = scanner.nextInt();
        }

        for(int i=0; i<program.get(0).length-K +1; i++){
            if(flag)
                break;
            dfs(getCode(i, 0), 1);
        }

        if(flag)
            System.out.println("YES");
        else
            System.out.println("NO");
    }

    public static boolean check(int[] arr, int idx){
        int[] copy = new int[K];
        int[] reverse = new int[K];

        for(int i=0; i<program.get(idx).length-K + 1; i++){
            System.arraycopy(program.get(idx), i, copy, 0, K);

            for(int j=0; j<copy.length; j++){
                reverse[K-j-1] = copy[j];
            }

            if(Arrays.equals(copy, arr) || Arrays.equals(reverse, arr)) {
                return true;
            }
        }
        return false;
    }

    public static int[] getCode(int a, int idx){
        int[] arr = new int[K];

        if (K >= 0)
            System.arraycopy(program.get(idx), a, arr, 0, K);

        return arr;
    }

    public static void dfs(int[] arr, int idx){
        if(idx==N){
            flag = true;
        }
        else if(check(arr, idx)){
            dfs(arr, idx+1);
        }
    }
}