import java.util.ArrayList;

public class Queue {
    ArrayList<TreeNode> queue;
    int length;

    public Queue() {
        queue = new ArrayList<>();
        length = 0;
    }

    public void enqueue(TreeNode node) {
        if (node != null) {
            queue.add(node);
            length++;
        }
    }

    public TreeNode dequeue() {
        TreeNode node = queue.get(0);
        queue.remove(0);
        length--;
        return node;
    }

    public TreeNode getHead() {
        return queue.get(0);
    }

    public int getLength() {
        return length;
    }

    public boolean isEmpty() {
        return length == 0;
    }

    public static void main(String[] args) {
        ArrayList<Integer> x = new ArrayList<>();
        x.add(1);
        x.add(2);
        x.add(3);
        x.add(4);
        System.out.println(x.get(0));
        x.remove(0);
        System.out.println(x.get(0));


    }

}
