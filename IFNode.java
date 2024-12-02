package cp2024.solution;

import java.util.concurrent.ExecutorService;

public class IFNode extends Node {
    private Boolean A;
    private Boolean B;
    private Boolean C;

    public IFNode(Node parentNode) {
        super(parentNode);
    }

    @Override
    public void check(ExecutorService executor) {
        if (A != null) {
            if (A == true) {
                if (B != null) {
                    executor.submit(new PushToParent(executor, parentNode, B));
                    this.cancelWithLock(executor);
                } else if (C == null) {
                    executor.submit(new CancelDown(executor, subNodes.get(2)));
                }
            } else {
                if (C != null) {
                    executor.submit(new PushToParent(executor, parentNode, C));
                    this.cancelWithLock(executor);
                } else if (B == null) {
                    executor.submit(new CancelDown(executor, subNodes.get(1)));
                }
            }
        } else {
            if (B != null && C != null && B == C) {
                executor.submit(new PushToParent(executor, parentNode, B));
                this.cancelWithLock(executor);
            }
        }
    }

    @Override
    public void takeSubresult(ExecutorService executor, Boolean subResult) {
    }

    public void takeA(Boolean subResult) {
        this.A = subResult;
    }

    public void takeB(Boolean subResult) {
        this.B = subResult;
    }

    public void takeC(Boolean subResult) {
        this.C = subResult;
    }

}
