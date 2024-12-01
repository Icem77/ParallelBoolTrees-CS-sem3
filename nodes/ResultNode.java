package cp2024.solution.nodes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import cp2024.solution.ResultType;

public class ResultNode extends Node {
    private LinkedBlockingQueue<ResultType> channel;

    public ResultNode(Node parentNode, LinkedBlockingQueue<ResultType> channel) {
        super(parentNode);
        this.channel = channel;
    }

    @Override
    public void takeSubresult(ExecutorService executor, Boolean subResult) {
        if (subResult == null) {
            channel.add(null);
        } else if (subResult == true) {
            channel.add(ResultType.TRUE);
        } else {
            channel.add(ResultType.FALSE);
        }
    }

    @Override
    public void check(ExecutorService executor) {
    }

}
