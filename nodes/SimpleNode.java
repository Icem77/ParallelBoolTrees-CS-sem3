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
    public void takeSubresult(ThreadPoolExecutor executor, Integer depth, Boolean subResult) {
        if (subResult == true) {
            this.trues++;
        } else {
            this.falses++;
        }
    }
}
