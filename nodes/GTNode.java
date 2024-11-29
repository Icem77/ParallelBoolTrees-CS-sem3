package cp2024.solution.nodes;

import java.util.concurrent.ExecutorService;

import cp2024.solution.tasks.PushToParent;

public class GTNode extends ThresholdNode {

    public GTNode(Node parentNode, Integer nargs, Integer threshold) {
        super(parentNode, nargs, threshold);
    }

    @Override
    public void check(ExecutorService executor) {
        if (this.trues > this.threshold) {
            executor.submit(new PushToParent(executor, parentNode, true));
            this.cancelWithLock(executor);
        } else if (this.nargs - this.falses < this.threshold + 1) {
            executor.submit(new PushToParent(executor, parentNode, false));
            this.cancelWithLock(executor);
        } else if (this.trues + this.falses == this.nargs) {
            executor.submit(new PushToParent(executor, parentNode, this.trues > this.threshold));
        }
    }
}
