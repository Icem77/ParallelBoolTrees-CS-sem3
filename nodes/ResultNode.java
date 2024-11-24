package cp2024.solution.nodes;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import cp2024.solution.ResultType;

public class ResultNode extends Node {
    private LinkedBlockingQueue<ResultType> channel;

    public ResultNode(Node parentNode, LinkedBlockingQueue<ResultType> channel) {
        super(parentNode);
        this.channel = channel;
    }

    @Override
    public void takeSubresult(ThreadPoolExecutor executor, Integer depth, Boolean subResult) {
        if (subResult == null) {
            this.channel.add(null);
        } else if (subResult == true) {
            this.channel.add(ResultType.TRUE);
        } else {
            this.channel.add(ResultType.FALSE);
        }
    }

    @Override
    public void check(ThreadPoolExecutor executor, Integer depth) {
    }

}
