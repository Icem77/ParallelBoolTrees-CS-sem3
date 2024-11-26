package cp2024.solution.nodes;

import java.util.concurrent.ThreadPoolExecutor;

public class IFSubNode extends Node {
    private IFSubNodeType type;

    public IFSubNode(Node parentNode, IFSubNodeType type) {
        super(parentNode);
        this.type = type;
    }

    @Override
    public void takeSubresult(ThreadPoolExecutor executor, Integer depth, Boolean subResult) {
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

        parentNode.check(executor, depth);
    }

    @Override
    public void check(ThreadPoolExecutor executor, Integer depth) {
        this.parentNode.check(executor, depth);
    }
}
