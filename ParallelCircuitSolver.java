package cp2024.solution;

import cp2024.circuit.CircuitSolver;
import cp2024.circuit.CircuitValue;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Future;

import cp2024.circuit.Circuit;

public class ParallelCircuitSolver implements CircuitSolver {
    ExecutorService workers;
    LinkedList<Future<?>> ogoingCircuitCalculations;
    LinkedList<LinkedBlockingQueue<Boolean>> correspondingChannels;
    private Boolean isStoped;

    // TODO pozwalaj na współbiezną obsługę wielu próśb
    // TODO mozliwie współbieznie obliczaj obwody

    public ParallelCircuitSolver() {
        this.workers = Executors.newFixedThreadPool(8);
        this.ogoingCircuitCalculations = new LinkedList<>();
        this.correspondingChannels = new LinkedList<>();
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
