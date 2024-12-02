package cp2024.solution;

import cp2024.circuit.CircuitSolver;
import cp2024.circuit.CircuitValue;

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
        this.workers = Executors.newCachedThreadPool();
        this.resultChannels = new LinkedList<>();
        this.isStoped = false;
    }

    @Override
    public CircuitValue solve(Circuit c) {
        LinkedBlockingQueue<ResultType> newChannel = new LinkedBlockingQueue<>();
        ParallelCircuitValue val = new ParallelCircuitValue(newChannel);

        if (this.isStoped == true) {
            newChannel.add(ResultType.INTERRUPTED);
        } else {
            workers.submit(new StartCircuitCalculaion(workers, newChannel, resultChannels, c.getRoot()));
        }

        return val;
    }

    @Override
    public void stop() {
        if (this.isStoped == false) {
            this.isStoped = true;

            workers.shutdownNow();

            for (LinkedBlockingQueue<ResultType> channel : resultChannels) {
                channel.add(ResultType.INTERRUPTED);
            }
            resultChannels.clear();
        }
    }
}
