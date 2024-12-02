package cp2024.solution;

import java.util.concurrent.ExecutorService;

public class NOTNode extends Node {

    public NOTNode(Node parentNode) {
        super(parentNode);
    }

    @Override
    public void takeSubresult(ExecutorService executor, Boolean subResult) {
        executor.submit(new PushToParent(executor, parentNode, !subResult));
    }

    @Override
    public void check(ExecutorService executor) {
    }
}
