package cp2024.solution.tasks;

import java.util.concurrent.ExecutorService;

import cp2024.circuit.CircuitNode;
import cp2024.circuit.LeafNode;
import cp2024.circuit.NodeType;
import cp2024.solution.nodes.*;
import cp2024.circuit.ThresholdNode;

public class ExpandNode extends PrioritezedTask {
    private Node parentNode;
    private CircuitNode nodeToExpand;

    public ExpandNode(ExecutorService executor, CircuitNode nodeToExpand, Node parentNode) {
        super(executor);
        this.nodeToExpand = nodeToExpand;
        this.parentNode = parentNode;
    }

    @Override
    public void run() {
        if (nodeToExpand.getType() == NodeType.LEAF) {
            try {
                SqueezeLeaf squeezeLeafTask = new SqueezeLeaf(executor, (LeafNode) nodeToExpand, parentNode);
                squeezeLeafTask.run();
            } catch (Exception e) {
                // TODO
            }
        } else if (nodeToExpand.getType() == NodeType.IF) {
            CircuitNode[] args = {};
            try {
                args = nodeToExpand.getArgs();
            } catch (InterruptedException e) {
                // TODO
            }

            IFNode BigIf = new IFNode(this.parentNode);
            IFSubNode A = new IFSubNode(BigIf, IFSubNodeType.A);
            IFSubNode B = new IFSubNode(BigIf, IFSubNodeType.B);
            IFSubNode C = new IFSubNode(BigIf, IFSubNodeType.C);
            BigIf.attachSubNode(A);
            BigIf.attachSubNode(B);
            BigIf.attachSubNode(C);
            executor.submit(new ExpandNode(executor, args[0], A));
            executor.submit(new ExpandNode(executor, args[1], B));
            executor.submit(new ExpandNode(executor, args[2], C));
            parentNode.attachSubNode(BigIf);
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
                    break;
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
                    break;
            }

            parentNode.attachSubNode(newNode);

            for (CircuitNode arg : args) {
                newNode.attachAssignedTask(executor.submit(new ExpandNode(executor, arg, newNode)));
            }
        }
    }
}
