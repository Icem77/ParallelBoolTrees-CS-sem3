package cp2024.solution;

import java.util.concurrent.ExecutorService;

public class ORNode extends SimpleNode {

    public ORNode(Node parentNode, Integer nargs) {
        super(parentNode, nargs);
    }

    @Override
    public void check(ExecutorService executor) {
        if (trues > 0) {
            executor.submit(new PushToParent(executor, parentNode, true));
            this.cancelWithLock(executor);
        } else if (trues + falses == nargs) {
            executor.submit(new PushToParent(executor, parentNode, trues > 0));
        }
    }
}
