package cp2024.solution;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

public class ResultNode extends Node {
    private LinkedBlockingQueue<ResultType> channel;
    private LinkedList<LinkedBlockingQueue<ResultType>> allChannels;

    public ResultNode(Node parentNode, LinkedBlockingQueue<ResultType> channel,
            LinkedList<LinkedBlockingQueue<ResultType>> allChannels) {
        super(parentNode);
        this.channel = channel;
        this.allChannels = allChannels;
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

        int index = -1;
        synchronized (allChannels) {
            index = allChannels.indexOf(channel);
            if (index != -1) {
                allChannels.remove(index);
            }
        }
    }

    @Override
    public void check(ExecutorService executor) {
    }

}
