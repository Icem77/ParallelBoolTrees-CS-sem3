package cp2024.solution;

import cp2024.circuit.CircuitSolver;
import cp2024.circuit.CircuitValue;
import cp2024.solution.nodes.ResultNode;
import cp2024.solution.tasks.ExpandNode;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.concurrent.LinkedBlockingQueue;

import cp2024.circuit.Circuit;

public class ParallelCircuitSolver implements CircuitSolver {
    ExecutorService workers;
    LinkedList<LinkedBlockingQueue<ResultType>> resultChannels;
    private Boolean isStoped;

    public ParallelCircuitSolver() {
        this.workers = Executors.newCachedThreadPool(
                runnable -> {
                    Thread thread = new Thread(runnable);
                    thread.setDaemon(true); // Mark the thread as a daemon
                    return thread;
                });
        this.resultChannels = new LinkedList<>();
        this.isStoped = false;
    }

    @Override
    public CircuitValue solve(Circuit c) {
        LinkedBlockingQueue<ResultType> newChannel = new LinkedBlockingQueue<>();
        ParallelCircuitValue val = new ParallelCircuitValue(newChannel);
        if (this.isStoped == true) {
            newChannel.add(ResultType.INTERRUPTED);
            return val;
        } else {
            ResultNode resultNode = new ResultNode(null, newChannel);
            this.resultChannels.add(newChannel);
            workers.submit(new ExpandNode(workers, c.getRoot(), resultNode));
            return val;
        }
    }

    @Override
    public void stop() {
        if (this.isStoped == false) {
            this.isStoped = true;

            workers.shutdownNow();

            for (LinkedBlockingQueue<ResultType> channel : resultChannels) {
                channel.add(ResultType.INTERRUPTED);
            }
        }
    }
}
