package cp2024.solution;

import cp2024.circuit.CircuitSolver;
import cp2024.circuit.CircuitValue;
import cp2024.solution.nodes.ResultNode;
import cp2024.solution.tasks.ExpandNode;
import cp2024.solution.tasks.PrioritezedTask;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cp2024.circuit.Circuit;

public class ParallelCircuitSolver implements CircuitSolver {
    ThreadPoolExecutor workers;
    LinkedList<LinkedBlockingQueue<ResultType>> resultChannels;
    private Boolean isStoped;

    // TODO pozwalaj na współbiezną obsługę wielu próśb
    // TODO mozliwie współbieznie obliczaj obwody

    public ParallelCircuitSolver() {
        this.workers = new ThreadPoolExecutor(
                8, 8, 0, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());
        this.resultChannels = new LinkedList<>();
        this.isStoped = false;
    }

    @Override
    public CircuitValue solve(Circuit c) {
        LinkedBlockingQueue<ResultType> newChannel = new LinkedBlockingQueue<>();
        if (this.isStoped) {
            newChannel.add(ResultType.INTERRUPTED);
            ParallelCircuitValue val = new ParallelCircuitValue(newChannel);

            return val;
        } else {
            ResultNode resultNode = new ResultNode(null, newChannel);
            this.resultChannels.add(newChannel);
            workers.submit(new ExpandNode(workers, c.getRoot(), resultNode, 0));
        }

        return new BrokenCircuitValue();
    }

    @Override
    public void stop() {
        if (!this.isStoped) {
            this.isStoped = true;

            workers.shutdownNow();

            for (LinkedBlockingQueue<ResultType> channel : resultChannels) {
                channel.add(ResultType.INTERRUPTED);
            }
        }
    }
}
