package cp2024.solution;

import cp2024.circuit.NodeType;

import java.util.concurrent.Future;
import java.util.LinkedList;

public abstract class Node {
    protected Integer trues;
    protected Integer falses;
    protected NodeType type;
    protected Node parentNode;
    protected LinkedList<Node> subNodes; // do cancelowania zlecen na dzieci
    protected Future<?> calledAs; // do cancelowania zlecenia na siebie

    public Node(NodeType type, Node parentNode) {
        this.type = type;
        this.parentNode = parentNode;
        this.trues = 0;
        this.falses = 0;
    }

    public void takeSubresult(CircuitResult subResult) {
        switch (subResult) {
            case TRUE:
                trues++;
                break;
            case FALSE:
                falses++;
                break;
            default:
                // do nothing
                break;
        }
    }

    public void attachSubNode(Node node) {
        this.subNodes.add(node);
    }

}
