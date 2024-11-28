package cp2024.solution.nodes;

import java.util.concurrent.ExecutorService;

public class IFSubNode extends Node {
    private IFSubNodeType type;

    public IFSubNode(Node parentNode, IFSubNodeType type) {
        super(parentNode);
        this.type = type;
    }

    @Override
    public void takeSubresult(ExecutorService executor, Boolean subResult) {
        switch (this.type) {
            case A:
                ((IFNode) parentNode).takeA(subResult);
                break;
            case B:
                ((IFNode) parentNode).takeB(subResult);
                break;
            case C:
                ((IFNode) parentNode).takeC(subResult);
                break;
        }

        parentNode.check(executor);
    }

    @Override
    public void check(ExecutorService executor) {
        this.parentNode.check(executor);
    }
}
