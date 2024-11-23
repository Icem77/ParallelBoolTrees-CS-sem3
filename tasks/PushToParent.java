package cp2024.solution.tasks;

import java.util.concurrent.ThreadPoolExecutor;

import cp2024.solution.nodes.Node;

public class PushToParent extends PrioritezedTask {
    private Node parentNode;
    private Boolean valueToPush;

    public PushToParent(ThreadPoolExecutor executor, Node parentNode, Boolean valueToPush, Integer depth) {
        super(executor, depth);
        this.parentNode = parentNode;
        this.valueToPush = valueToPush;
    }

    @Override
    public void run() {
        this.parentNode.takeSubresult(executor, depth, valueToPush);
    }
}
