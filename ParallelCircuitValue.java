package cp2024.solution;

import cp2024.circuit.CircuitValue;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class ParallelCircuitValue implements CircuitValue {
    private LinkedBlockingQueue<CircuitResult> channel;
    private CircuitResult result;
    private Boolean isBroken;

    public ParallelCircuitValue(LinkedBlockingQueue<CircuitResult> channel) {
        this.channel = channel;
        this.result = null;
        this.isBroken = false;
    }

    @Override
    public boolean getValue() throws InterruptedException {
        if (this.result == null) {
            this.result = channel.take();
        }

        switch (this.result) {
            case FALSE:
                return false;
            case TRUE:
                return true;
            default:
                throw new InterruptedException();
        }
    }
}
