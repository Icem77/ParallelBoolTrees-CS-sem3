package cp2024.solution;

import java.util.concurrent.ExecutorService;

public class ANDNode extends SimpleNode {

    public ANDNode(Node parentNode, Integer nargs) {
        super(parentNode, nargs);
    }

    @Override
    public void check(ExecutorService executor) {
        if (trues + falses == nargs) {
            executor.submit(new PushToParent(executor, parentNode, trues == nargs));
        } else if (falses > 0) {
            executor.submit(new PushToParent(executor, parentNode, false));
            cancelWithLock(executor);
        }
    }
}
