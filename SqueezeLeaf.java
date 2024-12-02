package cp2024.solution;

import java.util.concurrent.ExecutorService;

import cp2024.circuit.LeafNode;

public class SqueezeLeaf extends TaskWExecutor {
    private Node parentNode;
    private LeafNode leafToSqueeze;

    public SqueezeLeaf(ExecutorService executor, LeafNode leafToSqueeze, Node parentNode) {
        super(executor);
        this.parentNode = parentNode;
        this.leafToSqueeze = leafToSqueeze;
    }

    @Override
    public void run() {
        try {
            boolean squeezedValue = leafToSqueeze.getValue();
            executor.submit(new PushToParent(executor, parentNode, squeezedValue));
        } catch (InterruptedException e) {
        }
    }
}
