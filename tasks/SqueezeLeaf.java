package cp2024.solution.tasks;

import java.util.concurrent.ThreadPoolExecutor;

import cp2024.circuit.LeafNode;
import cp2024.solution.nodes.Node;

public class SqueezeLeaf extends PrioritezedTask {
    private Node parentNode;
    private LeafNode leafToSqueeze;

    public SqueezeLeaf(ThreadPoolExecutor executor, LeafNode leafToSqueeze, Node parentNode, Integer depth) {
        super(executor, depth);
        this.parentNode = parentNode;
        this.leafToSqueeze = leafToSqueeze;
    }

    @Override
    public void run() {
        try {
            boolean squeezedValue = this.leafToSqueeze.getValue();
            this.executor.submit(new PushToParent(executor, parentNode, squeezedValue, depth - 1));
        } catch (InterruptedException e) {
        }
    }
}
