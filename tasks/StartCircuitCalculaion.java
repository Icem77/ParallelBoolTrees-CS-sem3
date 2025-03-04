package cp2024.solution.tasks;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import cp2024.circuit.CircuitNode;
import cp2024.solution.nodes.ResultNode;
import cp2024.solution.nodes.ResultType;

public class StartCircuitCalculaion extends TaskWExecutor {
    private LinkedBlockingQueue<ResultType> channel;
    private LinkedList<LinkedBlockingQueue<ResultType>> allChanells;
    private CircuitNode rootNode;

    public StartCircuitCalculaion(ExecutorService executor, LinkedBlockingQueue<ResultType> channel,
            LinkedList<LinkedBlockingQueue<ResultType>> allChanells, CircuitNode rootNode) {
        super(executor);
        this.rootNode = rootNode;
        this.channel = channel;
        this.allChanells = allChanells;
    }

    @Override
    public void run() {
        synchronized (allChanells) {
            if (Thread.interrupted()) {
                channel.add(ResultType.INTERRUPTED);
                return;
            } else {
                allChanells.add(channel);
            }
        }

        ResultNode resultNode = new ResultNode(null, channel, allChanells);

        executor.submit(new ExpandNode(executor, rootNode, resultNode));
    }
}
