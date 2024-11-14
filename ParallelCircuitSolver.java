package cp2024.solution;

import cp2024.circuit.CircuitSolver;
import cp2024.circuit.CircuitValue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import cp2024.circuit.Circuit;

public class ParallelCircuitSolver implements CircuitSolver {
    // TODO pozwalaj na współbiezną obsługę wielu próśb
    // TODO mozliwie współbieznie obliczaj obwody

    public ParallelCircuitSolver() {
        this.workers = ExecutorService.newFixedThreadPoolO(8)
    }

    @Override
    public CircuitValue solve(Circuit c) {
        // TODO niezwłocznie zwróć spejcalny obiekt reprezentujący wartość obwodu

       return new ParallelCircuitValue(new LinkedBlockingQueue<Boolean>())
    }

    @Override
    public void stop() {
        // TODO zaprzestań przyjmowania zleceń
        // TODO niezwłocznie zakończ wszystkie trwające obliczenia tego solvera

        /* FIX ME */
        throw new RuntimeException("Not implemented.");
    }
}
