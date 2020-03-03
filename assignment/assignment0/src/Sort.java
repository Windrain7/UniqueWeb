import javax.swing.*;

public class Sort {

    public static void main(String[] args) {
        int[] array = {3,4,1,2,7,3,23,5};
//        bubble(array);
//        insert(array);
        merge(array,0,array.length-1);
        for (int i : array) {
            System.out.printf("%d ",i);
        }
    }

    public static void bubble(int[] array) {
        int temp;

        for (int i=1; i<array.length; i++ ) {
            for (int j=0; j<array.length-i; j++ ) {
                if (array[j] > array[j+1]) {
                    temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
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
            array[j+1] = (j >=0 )?array[j+1]:temp;
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
}
