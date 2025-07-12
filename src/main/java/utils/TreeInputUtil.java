package utils;

import java.util.LinkedList;
import java.util.Queue;

public class TreeInputUtil {
    public static TreeNode buildTree(Integer[] arr) {
        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean left = true;
        for (int i = 1; i < arr.length; ++i) {
            if (left) {
                if (arr[i] != null) {
                    queue.peek().left = new TreeNode(arr[i]);
                    queue.offer(queue.peek().left);
                }
            } else {
                if (arr[i] != null) {
                    queue.peek().right = new TreeNode(arr[i]);
                    queue.offer(queue.poll().right);
                } else {
                    queue.poll();
                }
            }
            left = !left;
        }
        return root;
    }

    public static TreeNode dfsGetIfNull(TreeNode root, int val) {
        TreeNode resp = dfsGet(root, val);
        if (resp == null) {
            return new TreeNode(val);
        }
        return resp;
    }

    public static TreeNode dfsGet(TreeNode root, int val) {
        if (root.val == val) {
            return root;
        }
        if (root.left != null) {
            TreeNode left = dfsGet(root.left, val);
            if (left != null) {
                return left;
            }
        }
        if (root.right != null) {
            TreeNode right = dfsGet(root.right, val);
            if (right != null) {
                return right;
            }
        }
        return null;
    }
}
