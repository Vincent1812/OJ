package graph.lca.leetcode.q236;

import utils.TreeNode;

import java.util.*;

public class Solution {
    Map<Integer, Integer> depth;
    Map<Integer, List<Integer>> st;

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || p == null || q == null) {
            return null;
        }
        if (p == q) {
            return p;
        }
        if(p == root || q == root){
            return root;
        }
        depth = new HashMap<>();
        st = new HashMap<>();
        depth.put(root.val, 1);
        st.put(root.val, new ArrayList<>());
        if (root.left != null) {
            dfs(root, root.left);
        }
        if (root.right != null) {
            dfs(root, root.right);
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

    void dfs(TreeNode pn, TreeNode cn) {
        depth.put(cn.val, depth.get(pn.val) + 1);
        List<Integer> stLine = new ArrayList<>();
        stLine.add(pn.val);
        for (int i = 1; i < 14; ++i) {
            int p = stLine.get(i - 1);
            if (st.get(p) == null || i > st.get(p).size()) {
                break;
            }
            stLine.add(st.get(p).get(i - 1));
        }
        st.put(cn.val, stLine);
        if (cn.left != null) {
            dfs(cn, cn.left);
        }
        if (cn.right != null) {
            dfs(cn, cn.right);
        }
    }

    int lca(int u, int v) {
        if (depth.get(u) < depth.get(v)) {
            int tmp = u;
            u = v;
            v = tmp;
        }
        for (int i = st.get(u).size() - 1; i >= 0; --i) {
            if (depth.get(st.get(u).get(i)) >= depth.get(v)) {
                u = st.get(u).get(i);
            }
        }
        if (u == v) {
            return u;
        }
        for (int i = st.get(u).size() - 1; i >= 0; --i) {
            if (st.get(u).get(i) != st.get(v).get(i)) {
                u = st.get(u).get(i);
                v = st.get(v).get(i);
            }
        }
        return st.get(u).get(0);
    }
}
