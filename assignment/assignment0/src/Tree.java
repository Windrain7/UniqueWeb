public class Tree {
    public static void preTravel(TreeNode root) {
        if (root != null) {
            System.out.println(root.getData());
            preTravel(root.getLeft());
            preTravel(root.getRight());
        }
    }

    public static void inTravel(TreeNode root) {
        if (root != null) {
            inTravel(root.getLeft());
            System.out.println(root.getData());
            inTravel(root.getRight());
        }
    }

    public static void posTravel(TreeNode root) {
        if (root != null) {
            posTravel(root.getLeft());
            posTravel(root.getRight());
            System.out.println(root.getData());
        }
    }

    public static void preTravel1(TreeNode root) {
        Stack stack = new Stack();
        TreeNode p = root;
        while (!stack.isEmpty() || p != null) {
            if (p != null) {
                stack.push(p);
                System.out.println(p.getData());
                p = p.getLeft();
            } else {
                p = stack.pop().getRight();
            }
        }
    }

    public static void inTravel1(TreeNode root) {
        Stack stack = new Stack();
        TreeNode p = root;
        while (!stack.isEmpty() || p != null) {
            if (p != null) {
                stack.push(p);
                p = p.getLeft();
            } else {
                p = stack.pop();
                System.out.println(p.getData());
                p = p.getRight();
            }
        }
    }

    public static void posTravel1(TreeNode root) {
        Stack stack = new Stack();
        TreeNode p = root;
        TreeNode popp;
        while (!stack.isEmpty() || p != null) {
            if (p != null) {
                stack.push(p);
                p = p.getLeft();
            } else {
                popp = stack.pop();
                System.out.println(popp.getData());
                if (!stack.isEmpty() && stack.getTop().getLeft() == popp) {
                    p = stack.getTop().getRight();
                }
            }
        }
    }

    public static void breathTravel(TreeNode root) {
        Queue queue = new Queue();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.dequeue();
            System.out.println(node.getData());
            queue.enqueue(node.left);
            queue.enqueue(node.right);
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode node = new TreeNode(2);
        root.setLeft(node);
        node = new TreeNode(3);
        root.setRight(node);
        node = new TreeNode(4);
        root.getLeft().setLeft(node);
        node = new TreeNode(5);
        root.getLeft().setRight(node);
        node = new TreeNode(6);
        root.getRight().setLeft(node);
        node = new TreeNode(7);
        root.getRight().setRight(node);
        node = new TreeNode(8);
        root.getLeft().getRight().setLeft(node);
        node = new TreeNode(9);
        root.getLeft().getRight().setRight(node);
//        Tree.preTravel(root);
//        System.out.println("");
//        Tree.preTravel1(root);
//        System.out.println("");
//        Tree.inTravel(root);
//        System.out.println("");
//        Tree.inTravel1(root);
//        System.out.println("");
//        Tree.posTravel(root);
//        System.out.println("");
//        Tree.posTravel1(root);
//        System.out.println("");
//        Tree.breathTravel(root);
    }

}
