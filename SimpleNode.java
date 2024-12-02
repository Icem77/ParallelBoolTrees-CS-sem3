package cp2024.solution;

import java.util.concurrent.ExecutorService;

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
    public void takeSubresult(ExecutorService executor, Boolean subResult) {
        if (subResult == true) {
            trues++;
        } else {
            falses++;
        }
    }
}
