package cp2024.solution;

import cp2024.circuit.CircuitValue;

import java.util.concurrent.LinkedBlockingQueue;

public class ParallelCircuitValue implements CircuitValue {
    private LinkedBlockingQueue<Boolean> channel;
    private Boolean result;
    private Boolean isBroken;

    public ParallelCircuitValue(LinkedBlockingQueue<Boolean> channel) {
        this.channel = channel;
        this.result = null;
        this.isBroken = false;
    }

    @Override
    public boolean getValue() throws InterruptedException {
        if (this.result == null) {
            this.result = channel.take();
        }

        if (this.isBroken) {
            throw new InterruptedException();
        }

        return result;
    }
}
