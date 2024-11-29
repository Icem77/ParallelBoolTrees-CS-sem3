package cp2024.solution.nodes;

import java.util.concurrent.ExecutorService;

import cp2024.solution.tasks.PushToParent;

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
            if (A == true && B != null) {
                executor.submit(new PushToParent(executor, parentNode, B));
                if (C == null) {
                    this.cancelWithLock(executor);
                }
            } else if (A == false && C != null) {
                executor.submit(new PushToParent(executor, parentNode, C));
                if (B == null) {
                    this.cancelWithLock(executor);
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
