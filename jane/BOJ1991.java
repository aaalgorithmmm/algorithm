import java.util.*;
 
class TreeNode { 
    char val;
    TreeNode left;
    TreeNode right;
    public TreeNode(char val) {
        this.val = val;
    }
}
 
class BinaryTree { 
    public TreeNode root;
 
    public void addNode(char val, char leftVal, char rightVal) {
        if (root == null) {
            root = new TreeNode(val);
        }
        TreeNode cur = findNode(val, root);
        if (leftVal != '.') {
            cur.left = new TreeNode(leftVal);
        }
        if (rightVal != '.') {
            cur.right = new TreeNode(rightVal);
        }
    }
    public TreeNode findNode(char val, TreeNode node) { 
        if (node == null) {
            return null;
        }
        if (node.val == val) {
            return node;
        }
        TreeNode left = findNode(val, node.left);
        if (left != null) {
            return left;
        }
        TreeNode right = findNode(val, node.right);
        if (right != null) {
            return right;
        }
        return null;
    }
    
//전위
    public void preorderTraversal(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.val);
        preorderTraversal(node.left);
        preorderTraversal(node.right);
    }
//중위
    public void inorderTraversal(TreeNode node) {
        if (node == null) {
            return;
        }
        inorderTraversal(node.left);
        System.out.print(node.val);
        inorderTraversal(node.right);
    }
//후위 
    public void postorderTraversal(TreeNode node) {
        if (node == null) {
            return;
        }
        postorderTraversal(node.left);
        postorderTraversal(node.right);
        System.out.print(node.val);
    }
}
 
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        BinaryTree tree = new BinaryTree();
        for (int i = 0; i < n; i++) {
            String[] input = sc.nextLine().split(" ");
            tree.addNode(input[0].charAt(0), input[1].charAt(0), input[2].charAt(0));
        }
        tree.preorderTraversal(tree.root);
        System.out.println();
        tree.inorderTraversal(tree.root);
        System.out.println();
        tree.postorderTraversal(tree.root);
        System.out.println();
    }
}
