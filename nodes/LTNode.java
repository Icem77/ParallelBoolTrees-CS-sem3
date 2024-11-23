package cp2024.solution.nodes;

import java.util.concurrent.ThreadPoolExecutor;

import cp2024.solution.tasks.PushToParent;

public class LTNode extends ThresholdNode {

    public LTNode(Node parentNode, Integer nargs, Integer threshold) {
        super(parentNode, nargs, threshold);
    }

    @Override
    public void check(ThreadPoolExecutor executor, Integer depth) {
        if (this.trues >= this.threshold) {
            executor.submit(new PushToParent(executor, parentNode, false, depth - 1));
            this.cancelWithLock(executor, depth);
        } else if (this.falses > this.nargs - this.threshold) {
            executor.submit(new PushToParent(executor, parentNode, true, depth - 1));
            this.cancelWithLock(executor, depth);
        } else {
            this.lock.unlock();
        }
    }
}
