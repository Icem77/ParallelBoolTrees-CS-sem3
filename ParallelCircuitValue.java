package cp2024.solution;

import java.util.concurrent.LinkedBlockingQueue;

import cp2024.circuit.CircuitValue;

public class ParallelCircuitValue implements CircuitValue {
    private LinkedBlockingQueue<ResultType> channel;
    private ResultType result;

    public ParallelCircuitValue(LinkedBlockingQueue<ResultType> channel) {
        this.channel = channel;
        this.result = null;
    }

    @Override
    public boolean getValue() throws InterruptedException {
        synchronized (this) {
            if (result == null) {
                this.result = channel.take();
            }

            switch (this.result) {
                case TRUE:
                    return true;
                case FALSE:
                    return false;
                default:
                    throw new InterruptedException();
            }
        }
    }
}
