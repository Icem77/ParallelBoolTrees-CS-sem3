package cp2024.solution.tasks;

import java.util.concurrent.ThreadPoolExecutor;

import cp2024.circuit.CircuitNode;
import cp2024.circuit.LeafNode;
import cp2024.circuit.NodeType;
import cp2024.solution.nodes.*;
import cp2024.circuit.ThresholdNode;

public class ExpandNode extends PrioritezedTask {
    private Node parentNode;
    private CircuitNode nodeToExpand;

    public ExpandNode(ThreadPoolExecutor executor, CircuitNode nodeToExpand, Integer depth) {
        super(executor, depth);
        this.nodeToExpand = nodeToExpand;
    }

    @Override
    public void run() {
        if (nodeToExpand.getType() == NodeType.LEAF) {
            executor.submit(new SqueezeLeaf(executor, (LeafNode) nodeToExpand, parentNode, depth));
        } else {
            CircuitNode[] args = {};
            try {
                args = nodeToExpand.getArgs();
            } catch (InterruptedException e) {
                // TODO
            }
            Node newNode;
            switch (nodeToExpand.getType()) {
                case AND:
                    newNode = new ANDNode(parentNode, args.length);
                case OR:
                    newNode = new ORNode(parentNode, args.length);
                    break;
                case LT:
                    newNode = new LTNode(parentNode, args.length, ((ThresholdNode) nodeToExpand).getThreshold());
                    break;
                case GT:
                    newNode = new GTNode(parentNode, args.length, ((ThresholdNode) nodeToExpand).getThreshold());
                    break;
                case NOT:
                    newNode = new NOTNode(parentNode);
                    break;
                default:
                    newNode = null;
            }

            parentNode.attachSubNode(newNode);

            for (CircuitNode arg : args) {
                executor.submit(new ExpandNode(executor, arg, depth + 1));
            }
        }
    }
}
