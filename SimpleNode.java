package cp2024.solution;

import cp2024.circuit.NodeType;

public class SimpleNode extends Node {

    public SimpleNode(NodeType type, Node parentNode) {
        super(type, parentNode);
        if (type != NodeType.OR && type != NodeType.AND) {
            throw new RuntimeException("Simple node should be of type AND, OR, ");
        }
    }
}
