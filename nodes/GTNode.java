package cp2024.solution.nodes;

import java.util.concurrent.ExecutorService;

import cp2024.solution.tasks.PushToParent;

public class GTNode extends ThresholdNode {

    public GTNode(Node parentNode, Integer nargs, Integer threshold) {
        super(parentNode, nargs, threshold);
    }

    @Override
    public void check(ExecutorService executor) {
        if (trues > threshold) {
            executor.submit(new PushToParent(executor, parentNode, true));
            cancelWithLock(executor);
        } else if (nargs - falses < threshold + 1) {
            executor.submit(new PushToParent(executor, parentNode, false));
            cancelWithLock(executor);
        } else if (trues + falses == nargs) {
            executor.submit(new PushToParent(executor, parentNode, trues > threshold));
        }
    }
}
