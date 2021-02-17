import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HashTable {
    static final int HASH_SIZE = 1000;
    static final int HASH_LEN = 5;
    static final int HASH_VAL = 17;

    static int[][] data = new int[HASH_SIZE][HASH_LEN];
    static int[] length = new int[HASH_SIZE];
    static String[][] s_data = new String[HASH_SIZE][HASH_LEN];

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bf.readLine());
        String str;
        int key, idx;

        for(int i=0; i<n; i++){
            str = bf.readLine();
            key = getHashKey(str);
            idx = checking(key, str);

            if(idx==-1)
                System.out.println("OK");
            else
                System.out.println(str+idx);
        }
    }

    public static int getHashKey(String str){
        int key = 0;

        for(int i=0; i<str.length(); i++){
            key = (key*HASH_VAL) + str.charAt(i);
        }
        if(key<0) key = -key;

        return key%HASH_SIZE;
    }

    public static int checking(int key, String str){
        int len = length[key];

        if(len>0){
            for(int i=0; i<len; i++){
                if(str.equals(s_data[key][i])){
                    data[key][i]++;
                    return data[key][i];
                }
            }
        }
        s_data[key][length[key]++] = str;
        return -1;
    }
}
