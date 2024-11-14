package cp2024.solution;

import cp2024.circuit.CircuitValue;

import java.util.concurrent.LinkedBlockingQueue;

public class BrokenCircuitValue implements CircuitValue {

    @Override
    public boolean getValue() throws InterruptedException {
        throw new InterruptedException();
    }

}
