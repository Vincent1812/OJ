package graph.lca.leetcode.q1644;

import utils.TreeNode;

import java.util.*;

public class Solution {
    Map<Integer, List<Integer>> st;
    Map<Integer, Integer> depth;

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || p == null || q == null) {
            return null;
        }
        if (p == q) {
            return p;
        }
        depth = new HashMap<>();
        depth.put(root.val, 1);
        st = new HashMap<>();
        st.put(root.val, new ArrayList<>());
        if (root.left != null) {
            dfs(root, root.left);
        }
        if (root.right != null) {
            dfs(root, root.right);
        }
        if (depth.get(p.val) == null || depth.get(q.val) == null) {
            return null;
        }
        if (p == root || q == root) {
            return root;
        }
        int value = lca(p.val, q.val);
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode pop = stack.pop();
            if (pop.val == value) {
                return pop;
            }
            if (pop.right != null) {
                stack.push(pop.right);
            }
            if (pop.left != null) {
                stack.push(pop.left);
            }
        }
        return null;
    }

    private int lca(int u, int v) {
        if (depth.get(u) < depth.get(v)) {
            int tmp = u;
            u = v;
            v = tmp;
        }
        for (int i = st.get(u).size() - 1; i >= 0; i--) {
            if (depth.get(st.get(u).get(i)) >= depth.get(v)) {
                u = st.get(u).get(i);
                i = st.get(u).size() - 1;
            }
        }
        if (u == v) {
            return u;
        }
        for (int i = st.get(u).size() - 1; i >= 0; i--) {
            if (st.get(u).get(i).intValue() != st.get(v).get(i).intValue()) {
                u = st.get(u).get(i);
                v = st.get(v).get(i);
                i = st.get(u).size() - 1;
            }
        }
        return st.get(u).get(0);
    }

    void dfs(TreeNode pnode, TreeNode cnode) {
        depth.put(cnode.val, depth.get(pnode.val) + 1);
        List<Integer> stLine = new ArrayList<>();
        stLine.add(pnode.val);
        for (int i = 1; i < 14; ++i) {
            int p = stLine.get(i - 1);
            if (st.get(p) == null || st.get(p).size() < i) {
                break;
            }
            stLine.add(st.get(p).get(i - 1));
        }
        st.put(cnode.val, stLine);
        if (cnode.left != null) {
            dfs(cnode, cnode.left);
        }
        if (cnode.right != null) {
            dfs(cnode, cnode.right);
        }
    }

}
