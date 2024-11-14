package cp2024.solution;

import cp2024.circuit.CircuitValue;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class ParallelCircuitValue implements CircuitValue {
    private Future<Boolean> channel;

    public ParallelCircuitValue(Future<Boolean> channel) {
        this.channel = channel;
    }

    @Override
    public boolean getValue() throws InterruptedException {
        try {
            Boolean result = this.channel.get();
            return result;
        } catch (ExecutionException e) {
            if (e.getCause() instanceof CancellationException) {
                System.out.println("Task got canceled at some point!");
                throw new InterruptedException();
            } else {
                throw new RuntimeException("UNPREDICTED FAILURE OF TASK!");
            }
        }
    }
}
