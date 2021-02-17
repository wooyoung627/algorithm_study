import java.util.*;

public class Main {
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