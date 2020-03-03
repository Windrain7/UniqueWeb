import java.lang.reflect.Array;
import java.util.Scanner;

public class Heap {
    private int[] heap;
    private int size;

    Heap() {
        heap = new int[100];
        size = 0;
    }

    public Boolean isEmpty() {
        return size==0;
    }

    public int get(int index) {
        if (index < size){
            return heap[index];
        } else {
            System.out.println("下标越界！");
            return -1;
        }
    }

    public int getSize() {
        return size;
    }

    public void add(int data) {
        if (size == heap.length) {
            int[] heapex = new int[heap.length+100];
            System.arraycopy(heap, 0, heapex, 0, heap.length);
            heap = heapex;
        }
        if (size == 0) {
                heap[0] = data;
                size += 1;
        } else {
            heap[size++] = data;
            upAdjust(size-1);
        }
    }

    public void delete(int index) {
        if (index < size) {
            heap[index] = heap[--size];
            downAdjust(index);
        } else {
            System.out.println("下标越界！");
        }

    }

    private void upAdjust(int index) {
        int i, father;
        for (i = index; i > 0;) {
            father = (i - 1)/2;
            if (heap[i] > heap[father]) {
                swap(i,father);
                i = father;
            } else {
                break;
            }
        }
    }

    private void downAdjust(int index) {
        for (int i = index, left = 2*i+1, right = 2*i+2; left < size; left = 2*i+1, right = 2*i+2) {
            int max = right;
            if (max >= size || heap[left] > heap[right]) {
                max = left;
            }
            if (heap[i] < heap[max]) {
                swap(i, max);
                i = max;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public static void main(String[] args) {
        Heap heap = new Heap();
        for (int i = 0; i < 9; i++) {//测试数据：[2,4,1,27,3,23,5,6,3]
            Scanner scanner = new Scanner(System.in);
            int data = scanner.nextInt();
            heap.add(data);
        }
        for (int i = 0;i < heap.size; i++) {
            System.out.println(heap.heap[i]);
        }
        System.out.println("删除"+heap.get(1));
        heap.delete(1);
        for (int i = 0;i < heap.size; i++) {
            System.out.println(heap.heap[i]);
        }
    }
}
