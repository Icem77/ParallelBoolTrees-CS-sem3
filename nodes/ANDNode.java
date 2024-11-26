package cp2024.solution.nodes;

import java.util.concurrent.ThreadPoolExecutor;

import cp2024.solution.tasks.PushToParent;

public class ANDNode extends SimpleNode {

    public ANDNode(Node parentNode, Integer nargs) {
        super(parentNode, nargs);
    }

    @Override
    public void check(ThreadPoolExecutor executor, Integer depth) {
        if (this.trues + this.falses == this.nargs) {
            executor.submit(new PushToParent(executor, parentNode, this.trues == this.nargs, depth - 1));
        } else if (this.falses > 0) {
            executor.submit(new PushToParent(executor, parentNode, false, depth - 1));
            this.cancelWithLock(executor, depth);
        }
    }
}
