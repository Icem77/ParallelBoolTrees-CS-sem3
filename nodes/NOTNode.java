package cp2024.solution.nodes;

import java.util.concurrent.ThreadPoolExecutor;

import cp2024.solution.tasks.PushToParent;

public class NOTNode extends Node {

    public NOTNode(Node parentNode) {
        super(parentNode);
    }

    @Override
    public void takeSubresult(ThreadPoolExecutor executor, Integer depth, Boolean subResult) {
        executor.submit(new PushToParent(executor, parentNode, !subResult, depth - 1));
    }

    @Override
    public void check(ThreadPoolExecutor executor, Integer depth) {
    }
}
