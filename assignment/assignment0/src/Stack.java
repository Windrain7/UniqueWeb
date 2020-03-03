import java.util.Scanner;

public class Stack {
    private Node top;
    private Node bottom;
    private int length;

    public static void main(String[] args) {
        Stack stack = new Stack();
        for (int i=0; i<3; i++) {
            Scanner scanner = new Scanner(System.in);
            int data = scanner.nextInt();
            Node node = new Node(data);
            stack.push(node);
        }
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            System.out.println(node.data);
        }

    }

    public void push(Node node) {
        if (length == 0) {
            bottom = node;
        }
        node.pre = top;
        top = node;
        length += 1;
    }

    public Node pop() {
        Node node = top;
        top = top.pre;
        length -=1;
        return node;
    }

    public Boolean isEmpty() {
        return length==0;
    }

    public int getLength() {
        return length;
    }

    static class Node {
        int data;
        Node pre;

        Node(int data) {
            this.data = data;
        }
        Node() {}
    }
}
