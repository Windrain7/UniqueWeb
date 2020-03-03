import javax.swing.*;

public class Sort {

    public static void main(String[] args) {
        int[] array = {3,4,1,2,7,3,23,5};
//        bubble(array);
//        insert(array);
//        merge(array,0,array.length-1);
//        quick(array,0,array.length-1);
        heap(array);
        for (int i : array) {
            System.out.printf("%d ",i);
        }
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void bubble(int[] array) {
        for (int i=1; i<array.length; i++ ) {
            for (int j=0; j<array.length-i; j++ ) {
                if (array[j] > array[j+1]) {
                    swap(array, j,j+1);
                }
            }
        }
    }

    public static void insert(int[] array) {
        for (int i=1; i<array.length; i++) {
            int temp = array[i];
            int j;
            for (j=i-1; j >=0 ; j--) {
                if(array[j] > temp) {
                    array[j+1] = array[j];
                } else {
                    array[j+1] = temp;
                    break;
                }
            }
            array[0] = (j >=0 )?array[0]:temp;
        }
    }

    public static void merge(int[] array, int start, int end) {
        if (end > start){
            int middle = (start + end) / 2;
            merge(array,start,middle);
            merge(array,middle+1,end);
            mergeTwo(array,start,middle,end);
        }
    }

    public static void mergeTwo(int[] array, int start, int middle, int end) {
        int[] temp = new int[end-start+1];
        int i = start, j = middle+1, k = 0;
        while (i <= middle && j <= end) {
            if (array[i] < array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        if (i > middle) {
            i = j;
        }
        while (k <= end-start) {
            temp[k++] = array[i++];
        }
        for (i=start, k =0; i <= end;) {
            array[i++] = temp[k++];
        }
    }

    public static void quick(int[] array, int start, int end) {
        if (start+1 == end || start == end){
            if (array[start] > array[end]) {
                swap(array, start, end);
            }
        } else {
            int select = array[start], i = start+1, j;
            for (j = end; j > i; j--) {
                if (array[j] < select) {
                    for (; i < j && array[i] < select; i++);
                    swap(array, i, j);
                }
            }
            swap(array,i,start);
            quick(array,start,i-1);
            quick(array,i+1,end);
        }
    }

    public static void heap(int[] array) {
        buildHeap(array,array.length);
        for (int i = array.length-1; i > 0; i--) {
            swap(array,0, i);
            buildHeap(array,i);
        }
    }

    public static void buildHeap(int[] array,int length) {
        if (length > 1) {
            for (int i = (length-2)/2; i >= 0; i --) {
                int left = 2*i + 1;
                int right = 2*i + 2;
                int max = right;
                if (max >= length || array[left] > array[right]) {
                    max = left;
                }
                if (array[i] < array[max]) {
                    swap(array, i ,max);
                }
            }
        }
    }
}
