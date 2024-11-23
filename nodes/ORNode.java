package cp2024.solution.nodes;

import java.util.concurrent.ThreadPoolExecutor;

import cp2024.solution.tasks.PushToParent;

public class ORNode extends SimpleNode {

    public ORNode(Node parentNode, Integer nargs) {
        super(parentNode, nargs);
    }

    @Override
    public void check(ThreadPoolExecutor executor, Integer depth) {
        if (this.trues > 0) {
            executor.submit(new PushToParent(executor, parentNode, true, depth - 1));
            this.cancelWithLock(executor, depth);
        } else if (this.trues + this.falses == this.nargs) {
            executor.submit(new PushToParent(executor, parentNode, this.trues > 0, depth - 1));
            this.cancelWithLock(executor, depth);
        } else {
            this.lock.unlock();
        }
    }
}
