import java.util.Arrays;

public class Study {
    public static void main(String[] args) {
//        int[] arr={5,4,3,2,1};
        int[] arr = {54,687,1,5,6,78,64};
        Study study = new Study();
        int n = study.binarySearch(arr, 54);
        System.out.println("arr : " + Arrays.toString(arr));
        System.out.println("54's index : " + n);
    }

    void bubbleSort(int[] arr){
        int temp;
        for(int i=0; i<arr.length-1; i++){
            for(int j=0; j<arr.length-1-i; j++){
                if(arr[j]>arr[j+1]){
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    void selectionSort(int[] arr){
        int min, temp;
        for(int i=0; i<arr.length-1; i++){
            min=i;
            for(int j=i+1; j<arr.length; j++){
                if(arr[min]>arr[j])
                    min=j;
            }
            temp=arr[i];
            arr[i]=arr[min];
            arr[min]=temp;
        }
        System.out.println(Arrays.toString(arr));
    }

    void insertionSort(int[] arr){
        int num, i, j;
        for(i=1; i<arr.length; i++){
            num = arr[i];
            for(j=i-1; j>=0; j--){
                if(arr[j]>num){
                    arr[j+1] = arr[j];
                }
                else break;
            }
            arr[j+1]=num;
        }
        System.out.println(Arrays.toString(arr));
    }

    void quickSort(int[] arr, int left, int right){
//        // 최악의 경우, 개선 방법
//        int mid = (left+right)/2;
//        swap(arr, left, mid);

        if(left>=right) return;
        int pivot = partition(arr, left, right);

        quickSort(arr, left, pivot-1);
        quickSort(arr, pivot+1, right);

    }

    int partition(int[] arr, int left, int right){
        int i=left, j=right;

        int pivot = arr[left];

        while(i<j){
            while(pivot<arr[j])
                j--;
            while(pivot>=arr[i] && i<j)
                i++;
            swap(arr, i, j);
        }
        arr[left] = arr[i];
        arr[i] = pivot;

        return i;
    }

    void swap(int[] arr, int i, int j){
        int temp;
        temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    void mergeSort(int[] array, int left, int right){
        int mid = (left+right)/2;

        if(left<right){
            mergeSort(array, left, mid);
            mergeSort(array, mid+1, right);
            merge(array, left, right, mid);
        }
    }

    void merge(int[] array, int left, int right, int mid){
        int[] arrLeft = Arrays.copyOfRange(array, left, mid+1);
        int[] arrRight = Arrays.copyOfRange(array, mid+1, right+1);

        int j=0, i=0, k=left;
        int leftLength = arrLeft.length, rightLength = arrRight.length;

        while(j<leftLength && i<rightLength){
            if(arrLeft[j]<arrRight[i]){
                array[k]=arrLeft[j++];
            }
            else{
                array[k]=arrRight[i++];
            }
            k++;
        }
        while(j<leftLength){
            array[k++] = arrLeft[j++];
        }
        while(i<rightLength){
            array[k++] = arrRight[i++];
        }
    }

    void heapSort(int[] array){
        int n = array.length;

        // max heap 초기화
        for(int i=n/2-1; i>=0; i--){
            heapify(array, n, i);
        }

        // extract 연산
        for (int i = n-1; i>0; i--) {
            swap(array, 0, i);
            heapify(array, i, 0); // 2
        }
    }

    void heapify(int[] array, int n, int i){
        int p = i;
        int l = i*2 + 1;
        int r = i*2 + 2;

        //왼쪽 자식노드
        if (l < n && array[p] < array[l]) {
            p = l;
        }
        //오른쪽 자식노드
        if (r < n && array[p] < array[r]) {
            p = r;
        }

        //부모노드 < 자식노드
        if(i != p) {
            swap(array, p, i);
            heapify(array, n, p);
        }
    }

    void countSort(int[] arr, int n, int exp){
        int buffer[] = new int[n];
        int i, count[] = new int[10];

        // exp의 자릿수에 해당하는 count 증가
        for(i=0; i<n; i++){
            count[(arr[i]/exp)%10]++;
        }
        System.out.println("exp " + exp + " (count1): " + Arrays.toString(count));
        // 누적합 구하기
        for(i=1; i<10; i++){
            count[i] += count[i-1];
        }
        System.out.println("exp " + exp + " (count2): " + Arrays.toString(count));
        // 일반적인 Counting sort 과정
        for(i=n-1; i>=0; i--){
            buffer[count[(arr[i]/exp)%10]-1]=arr[i];
            count[(arr[i]/exp)%10]--;
        }
        System.out.println("exp " + exp + " (count3): " + Arrays.toString(count));
        System.out.println("exp " + exp + " (buffer): " + Arrays.toString(buffer));
        for(i=0; i<n; i++){
            arr[i]=buffer[i];
        }
    }

    void radixSort(int arr[], int n){
        // 최댓값 자리만큼 돌기
        int m=getMax(arr);
        System.out.println("arr : " + Arrays.toString(arr));
        // 최댓값을 나눴을 때, 0이 나오면 모든 숫자가 exp의 아래
        for(int exp=1; m/exp>0; exp *= 10){
            countSort(arr, n, exp);
            System.out.println("exp " + exp + " : " + Arrays.toString(arr));
        }
    }

    int getMax(int arr[]){
        int max=0;
        for(int i=0; i<arr.length; i++){
            if(arr[i]>max){
                max=arr[i];
            }
        }

        return max;
    }

    void countingSort(int[] arr){
        int sorted_arr[] = new int[arr.length];

        // arr 배열 안의 최대값 크기의 배열을 만들어줘야함
        int count[] = new int[getMax(arr)+1];

        for(int i=0; i<arr.length; i++){
            count[(arr[i])]++;
        }

        for(int i=1; i<count.length; i++){
            count[i] += count[i-1];
        }

        for(int i=arr.length-1; i>=0; i--){
            System.out.println(Arrays.toString(sorted_arr));
            sorted_arr[count[arr[i]]-1] = arr[i];
            count[arr[i]]--;
        }

        for(int i=0; i<arr.length; i++){
            arr[i] = sorted_arr[i];
        }
    }

    // 배열 arr에서 n을 찾자
    int binarySearch(int[] arr, int n){
        // 정렬
        quickSort(arr, 0, arr.length-1);

        int left =0, right=arr.length-1, mid = 0;

        while(left<=right){
            mid = (left+right)/2;

            if(arr[mid]<n){
                right = mid-1;
            }
            else if(arr[mid]>n){
                left = mid+1;
            }
            else{
                break;
            }
        }

        return mid;
    }
}
