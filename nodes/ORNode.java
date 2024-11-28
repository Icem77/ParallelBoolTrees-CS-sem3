package cp2024.solution.nodes;

import java.util.concurrent.ExecutorService;

import cp2024.solution.tasks.PushToParent;

public class ORNode extends SimpleNode {

    public ORNode(Node parentNode, Integer nargs) {
        super(parentNode, nargs);
    }

    @Override
    public void check(ExecutorService executor) {
        if (this.trues > 0) {
            executor.submit(new PushToParent(executor, parentNode, true));
            this.cancelWithLock(executor);
        } else if (this.trues + this.falses == this.nargs) {
            executor.submit(new PushToParent(executor, parentNode, this.trues > 0));
        }
    }
}
