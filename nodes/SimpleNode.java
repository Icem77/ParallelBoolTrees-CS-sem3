package cp2024.solution.nodes;

import java.util.concurrent.ThreadPoolExecutor;

public abstract class SimpleNode extends Node {
    protected Integer trues;
    protected Integer falses;
    protected Integer nargs;

    public SimpleNode(Node parentNode, Integer nargs) {
        super(parentNode);
        this.nargs = nargs;
        this.trues = 0;
        this.falses = 0;
    }

    @Override
    public synchronized void takeSubresult(ThreadPoolExecutor executor, Integer depth, Boolean subResult) {
        this.lock.lock();

        if (this.isCanceled == false) {
            if (subResult == true) {
                this.trues++;
            } else {
                this.falses++;
            }
            this.check(executor, depth);
        } else {
            this.lock.unlock();
        }
    }
}
