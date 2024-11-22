package cp2024.solution;

import cp2024.circuit.CircuitSolver;
import cp2024.circuit.CircuitValue;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Future;

import cp2024.circuit.Circuit;

public class ParallelCircuitSolver implements CircuitSolver {
    ThreadPoolExecutor workers;
    PriorityBlockingQueue<Runnable> tasks;
    LinkedList<Future<?>> ogoingCircuitCalculations;
    LinkedList<LinkedBlockingQueue<Boolean>> correspondingChannels;
    private Boolean isStoped;

    // TODO pozwalaj na współbiezną obsługę wielu próśb
    // TODO mozliwie współbieznie obliczaj obwody

    public ParallelCircuitSolver() {
        this.tasks = new PriorityBlockingQueue<>();
        this.workers = new ThreadPoolExecutor(
            8, 8, 0, TimeUnit.SECONDS, tasks
        );
        this.isStoped = false;
    }

    @Override
    public CircuitValue solve(Circuit c) {
        if (this.isStoped) {
            return new BrokenCircuitValue();
        }

        // Future<?> newCircuitTask = initNewCircuit(AfterTask);

        return new BrokenCircuitValue();
    }

    @Override
    public void stop() {
        if (!this.isStoped) {
            this.isStoped = true;

            for (Future<?> calculation : ogoingCircuitCalculations) {
                calculation.cancel(true);
                correspondingChannels.removeFirst();
            }

            workers.shutdown();
        }
    }
}
