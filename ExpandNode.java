package cp2024.solution;

import java.util.concurrent.ExecutorService;

import cp2024.circuit.CircuitNode;
import cp2024.circuit.LeafNode;
import cp2024.circuit.NodeType;
import cp2024.circuit.ThresholdNode;

public class ExpandNode extends TaskWExecutor {
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
            SqueezeLeaf squeezeLeafTask = new SqueezeLeaf(executor, (LeafNode) nodeToExpand, parentNode);
            squeezeLeafTask.run();
        } else {
            CircuitNode[] args = {};

            try {
                args = nodeToExpand.getArgs();
            } catch (InterruptedException e) {
                return;
            }

            if (nodeToExpand.getType() == NodeType.IF) {
                IFNode BigIf = new IFNode(this.parentNode);
                IFSubNode A = new IFSubNode(BigIf, IFSubNodeType.A);
                IFSubNode B = new IFSubNode(BigIf, IFSubNodeType.B);
                IFSubNode C = new IFSubNode(BigIf, IFSubNodeType.C);
                BigIf.attachSubNode(A);
                BigIf.attachSubNode(B);
                BigIf.attachSubNode(C);
                parentNode.attachSubNode(BigIf);

                synchronized (BigIf) {
                    A.attachAssignedTask(executor.submit(new ExpandNode(executor, args[0], A)));
                    B.attachAssignedTask(executor.submit(new ExpandNode(executor, args[1], B)));
                    C.attachAssignedTask(executor.submit(new ExpandNode(executor, args[2], C)));
                }
            } else {
                Node newNode;
                switch (nodeToExpand.getType()) {
                    case AND:
                        newNode = new ANDNode(parentNode, args.length);
                        break;
                    case OR:
                        newNode = new ORNode(parentNode, args.length);
                        break;
                    case LT:
                        Integer thresholdLT = ((ThresholdNode) nodeToExpand).getThreshold();
                        if (thresholdLT <= 0) {
                            executor.submit(new PushToParent(executor, parentNode, false));
                            return;
                        } else if (thresholdLT > args.length) {
                            executor.submit(new PushToParent(executor, parentNode, true));
                            return;
                        } else {
                            newNode = new LTNode(parentNode, args.length, thresholdLT);
                            break;
                        }
                    case GT:
                        Integer thresholdGT = ((ThresholdNode) nodeToExpand).getThreshold();
                        if (thresholdGT >= args.length) {
                            executor.submit(new PushToParent(executor, parentNode, false));
                            return;
                        } else if (thresholdGT < 0) {
                            executor.submit(new PushToParent(executor, parentNode, true));
                            return;
                        } else {
                            newNode = new GTNode(parentNode, args.length, thresholdGT);
                            break;
                        }
                    case NOT:
                        newNode = new NOTNode(parentNode);
                        break;
                    default:
                        newNode = null;
                        break;
                }

                synchronized (parentNode) {
                    if (parentNode.isCanceled() == false && !Thread.interrupted()) {
                        parentNode.attachSubNode(newNode);
                    } else {
                        return;
                    }
                }

                synchronized (newNode) {
                    for (CircuitNode arg : args) {
                        if (!Thread.interrupted()) {
                            newNode.attachAssignedTask(executor.submit(new ExpandNode(executor, arg, newNode)));
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }
}
