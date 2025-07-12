package graph.lca.leetcode.q1644;

import utils.TreeInputUtil;
import utils.TreeNode;

public class Runner {
    public static void main(String[] args) {
        Solution sl = new Solution();
        TreeNode root = TreeInputUtil.buildTree(new Integer[]{4611, 12514, null, 4550, null, 8071, null, 14954, null, null, 17415, 624, null, null, 1908, 876, null, 4775, null, 10189, null, 8136, null, 12424});
        TreeNode resp = sl.lowestCommonAncestor(root, TreeInputUtil.dfsGetIfNull(root, 4611), TreeInputUtil.dfsGetIfNull(root, 10604));
        System.out.println(resp == null ? null : resp.val);
    }
}
