import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Programmers {
    public static void main(String[] args) {
        Solution solution = new Solution();
//        int[] answer = solution.solution(1);
//        for(int i=0; i<answer.length; i++){
//            System.out.println();
//        }
        System.out.println(Arrays.toString(solution.solution(4)));
//        System.out.println(solution.plus(5));
    }
}

class Solution {
    public int[] solution(int n) {
        int size = 0, flag = 1;
        for(int i=1; i<=n; i++){
            size += i;
        }
        int[] answer = new int[size];
        int idx=0, j=1;

        // n번, n-1번 n-2번 ... 1번 반복문을 돌며 삼각달팽이를 그림
        for(int i=n; i>0; i--){
            for(int k=0; k<i; k++){
                // 왼쪽 대각선으로 내려감
                if(flag == 1){
                    answer[idx] = j++;
                    if(k+1 !=i)
                        idx=idx+plus(idx);
                    else {
                        flag = 2;
                        idx++;
                    }
                }
                // 오른쪽으로 감
                else if(flag == 2){
                    answer[idx] = j++;
                    if(k+1 !=i)
                        idx++;
                    else{
                        flag=3;
                        idx -= plus(idx);
                    }
                }
                // 왼쪽 대각선으로 올라감
                else if(flag == 3){
                    answer[idx]=j++;
                    if(k+1 !=i)
                        idx -= plus(idx);
                    else {
                        flag = 1;
                        idx=idx+plus(idx);
                    }
                }
            }
        }

        return answer;
    }

    // 인덱스가 num일때 더하거나 빼주는 수를 리턴
    public static int plus(int num){
        int sum=0, i;
        for(i=1; i<=num; i++){
            if(sum+i>num)
                return i;
            else
                sum +=i;
        }
        return i;
    }
}

class Solution03 {
    final static int HASH_SIZE = 1000;
    final static int HASH_NUM = 17;
    final static int HASH_LEN = 400;
    // 진짜 데이터를 넣어놓음
    // data[key]의 데이터가 있는 배열의 수 = length[key]
    static String[][] s_data = new String[HASH_SIZE][HASH_LEN];
    // int [key] = 현재 키값의 문자열 갯수를 알려줌
    static int[] length = new int[HASH_SIZE];
    // 실제 데이터의 개수
    static int[][] data = new int[HASH_SIZE][HASH_LEN];


    public String solution(String[] participant, String[] completion) {
        String answer = "";
        int key;

        for(int i=0;i<completion.length; i++){
            key = getKey(completion[i]);
            hash(key, completion[i]);
        }

        for(int i=0; i<participant.length; i++){
            key = getKey(participant[i]);

            if(search(key, participant[i])==-1){
                return participant[i];
            }
        }

        return answer;
    }

    // hash table에 넣을 key를 얻어옴
    public static int getKey(String str){
        int key = 0;

        for(int i=0; i<str.length(); i++){
            key += str.charAt(i)*HASH_NUM;
        }

        return key%HASH_SIZE;
    }

    // key와 str을 받아서 해시테이블에 저장함
    public static void hash(int key, String str){
        if(length[key]>0){
            for(int j=0; j<length[key]; j++){
                if(str.equals(s_data[key][j])){
                    data[key][j]++;
                    return;
                }
            }
        }

        data[key][length[key]]++;
        s_data[key][length[key]++] = str;
    }

    // key와 str을 받아서 존재하는 문자열이라면 data를 1줄여주고
    // 존재하지 않는 문자열이거나 data의 개수가 0개라면 -1 반환
    public static int search(int key, String str){
        for(int i=0; i<length[key]; i++){
            if(s_data[key][i].equals(str) && data[key][i]!=0){
                data[key][i]--;
                return 1;
            }
        }

        return -1;
    }
}

class Solution02 {
    public int solution(String[] lines) {
        int answer = 0;
        // int[idx][0일시 시작시간, 1일시 종료시간][0:시, 1:분, 2:초]
        float[][][] time = new float[lines.length][2][3];
        int temp=0;
        float during=0;
        float[] min = {99, 99, 99};
        float[] curr ;
        LinkedList<float[]> linkedList = new LinkedList<>();

        for(int i=0; i<lines.length; i++){
            // 종료시간
            time[i][1][0] = Float.parseFloat(lines[i].substring(11, 13));
            time[i][1][1] = Float.parseFloat(lines[i].substring(14, 16));
            time[i][1][2] = Float.parseFloat(lines[i].substring(17, 23));

            // 걸린시간
            for(int j=24; j<lines[i].length(); j++){
                if(lines[i].charAt(j)=='s'){
                    during = round(Float.parseFloat(lines[i].substring(24, j))-0.001f);
                    break;
                }
            }

            // 시작시간은 종료시간에서 during만큼 뺀 시간
            time[i][0] = minus(time[i][1], during);

            // 제일 빠른 시작 시간 찾아서 min에 저장
            if(min[0]>=time[i][0][0] && min[1]>time[i][0][1] && min[2]>(int)time[i][0][2]){
                min[0] = time[i][0][0];
                min[1] = time[i][0][1];
                min[2] = time[i][0][2];
            }
        }

        // 시작 시간은 최고 늦게 끝나는 시간에서 0.001초 더한 시간
        curr = minus(time[lines.length-1][1], -0.001f);

        // 배열의 시작은 가장 뒤에서 부터
        int start=lines.length-1;

        // 현재 시간과 최소 시간이 같아질때까지 반복
        while(!compare(min, curr).equals("equal")){

            for(int i=start;i>=0; i--){
                // 만약 현재의 1초의 시작보다 트래픽의 종료시간이 더 크거나 같다면 연결리스트에 트래픽의 시작시간 추가하고 temp 1늘리기
                // temp는 현재 1초동안 실행중인 트래픽의 수
                // 그리고 i번째 트래픽은 확인했으니 start를 1 줄여주기 (시간 초과 때문)
                if(!compare(time[i][1], minus(curr, 0.999f)).equals("small")){
                    linkedList.add(time[i][0]);
                    temp++;
                    start--;
                }
                // 아니라면 반복문 나오기 (i번째부터 0번째 트래픽까지는 현재의 1초보다 더 전의 시간)
                else break;
            }

            // 연결리스트를 돌면서 시작시간이 현재의 1초보다 더 늦다면 지나간 트래픽이므로 연결리스트에서 빼주고 temp 줄이기
            for(int i=0; i<linkedList.size(); i++){
                if(compare(linkedList.get(i), curr).equals("big")){
                    linkedList.remove(i);
                    temp--;
                }
            }

            // 만약 현재의 트래픽의 수가 answer보다 많다면 최대이므로 answer = temp 로
            if(temp>answer)
                answer=temp;
            // 시간을 0.001초씩 줄여가며 확인
            curr = minus(curr, 0.001f);
        }

        return answer;
    }

    // 두 배열을 비교해서 앞 배열이 더 늦은시간이면 big, 아니면 small 같으면 equal
    public static String compare(float[] a, float[] b){
        if(a[0]<b[0])
            return "small";
        else if(a[0]>b[0])
            return "big";
        else if(a[1]<b[1])
            return "small";
        else if(a[1]>b[1])
            return "big";
        else if(a[2]<b[2])
            return "small";
        else if(a[2]>b[2])
            return "big";

        return "equal";
    }

    // times는 times[0] 시, times[1] 분, times[2] 초
    // times에서 t초 만큼 뺀 배열 리턴
    public static float[] minus(float[] times, float t){
        float[] result = new float[3];

        if(times[2]-t < 0){
            if(times[1]-1<0){
                result[0] = round(times[0]-1);
                result[1] = round(times[1]+59);
            }
            else{
                result[0] = times[0];
                result[1] = round(times[1] - 1);
            }
            result[2] = round(times[2]-t + 60);
        }else{
            result[0] = times[0];
            result[1] = times[1];
            result[2] = round(times[2]-t);
        }

        return result;

    }

    // 숫자를 소수점 3의자리까지 반올림
    public static float round(float num){
        return Math.round((num)*1000)/1000.0f;
    }
}

class Solution01 {
    public String solution(String new_id) {
        String answer = "";
        char word;
        new_id = new_id.toLowerCase();

        for(int i=0; i<new_id.length(); i++){
            word = new_id.charAt(i);
            if(!((word>='a' && word<='z')||(word>='0'&&word<='9') || word=='-' || word=='_'||word=='.')){
                if(i+1>=new_id.length())
                    new_id = new_id.substring(0,i);
                else
                    new_id = new_id.substring(0, i) + new_id.substring(i+1);
                i--;
            }
        }

        for(int i=1; i<new_id.length(); i++){
            if(new_id.charAt(i-1) == '.' && new_id.charAt(i)=='.'){
                if(i+1>=new_id.length())
                    new_id = new_id.substring(0,i);
                else
                    new_id = new_id.substring(0, i) + new_id.substring(i+1);
                i--;
            }
        }

        while (new_id.charAt(0)=='.' || new_id.charAt(new_id.length()-1)=='.'){
            if(new_id.length()==1){
                new_id="";
                break;
            }
            if(new_id.charAt(0)=='.'){
                new_id = new_id.substring(1);
            }
            else if(new_id.charAt(new_id.length()-1)=='.'){
                new_id = new_id.substring(0, new_id.length()-1);
            }
        }

        if(new_id.length()==0){
            new_id = "a";
        }

        if(new_id.length()>=16){
            new_id = new_id.substring(0, 15);
        }

        while (new_id.charAt(new_id.length()-1)=='.'){
            new_id = new_id.substring(0, new_id.length()-1);
        }

        if(new_id.length()<=2){
            while (new_id.length()!=3){
                new_id += new_id.charAt(new_id.length()-1);
            }
        }

        answer = new_id;

        return answer;
    }
}