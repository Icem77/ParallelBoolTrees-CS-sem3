package cp2024.solution.nodes;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class ResultNode extends Node {
    private LinkedBlockingQueue<Boolean> channel;

    public ResultNode(Node parentNode) {
        super(parentNode);
    }

    @Override
    public void takeSubresult(ThreadPoolExecutor executor, Integer depth, Boolean subResult) {
        channel.add(subResult);
    }

    @Override
    public void check(ThreadPoolExecutor executor, Integer depth) {
    }

}
