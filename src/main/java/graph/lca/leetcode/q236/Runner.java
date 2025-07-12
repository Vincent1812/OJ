package graph.lca.leetcode.q236;

import utils.TreeInputUtil;
import utils.TreeNode;

public class Runner {
    public static void main(String[] args) {
        Solution sl = new Solution();
        TreeNode root = TreeInputUtil.buildTree(new Integer[]{1,2,3,null,4});
        sl.lowestCommonAncestor(root, TreeInputUtil.dfsGet(root, 4), TreeInputUtil.dfsGet(root, 1));
    }
}
