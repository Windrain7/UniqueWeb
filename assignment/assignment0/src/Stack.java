import java.util.Scanner;

public class Stack {
    private Node top;
    private Node bottom;
    private int length;

    public void push(TreeNode treeNode) {
        Node node = new Node(treeNode);
        if (length == 0) {
            bottom = node;
        }
        node.pre = top;
        top = node;
        length += 1;
    }

    public TreeNode pop() {
        Node node = top;
        top = top.pre;
        length -=1;
        return node.treeNode;
    }

    public Boolean isEmpty() {
        return length==0;
    }

    public TreeNode getTop() {
        return top.treeNode;
    }

    public int getLength() {
        return length;
    }

    static class Node {
        TreeNode treeNode;
        Node pre;

        Node(TreeNode treeNode) {
            this.treeNode = treeNode;
        }
        Node() {}
    }
}
