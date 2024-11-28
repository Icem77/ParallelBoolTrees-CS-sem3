package cp2024.solution.nodes;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

import cp2024.solution.tasks.CancelDown;

public abstract class Node {
    public ReentrantLock lock;
    protected Node parentNode;
    protected LinkedList<Node> subNodes; // do cancelowania zlecen na liczenie dzieci
    protected LinkedList<Future<?>> assignedTasks;
    protected Boolean isCanceled;

    public Node(Node parentNode) {
        this.lock = new ReentrantLock(true);
        this.isCanceled = false;
        this.parentNode = parentNode;
        this.subNodes = new LinkedList<>();
        this.assignedTasks = new LinkedList<>();
    }

    public abstract void takeSubresult(ExecutorService executor, Boolean subResult);

    public abstract void check(ExecutorService executor);

    public void cancel(ExecutorService executor) {
        if (this.isCanceled == false) {
            this.markAsCanceled();

            for (Future<?> assignedTask : assignedTasks) {
                assignedTask.cancel(true);
            }

            for (Node subNode : subNodes) {
                executor.submit(new CancelDown(executor, subNode));
            }
        }
    }

    public void cancelWithLock(ExecutorService executor) {
        this.markAsCanceled();

        for (Future<?> assignedTask : assignedTasks) {
            assignedTask.cancel(true);
        }

        for (Node subNode : subNodes) {
            executor.submit(new CancelDown(executor, subNode));
        }
    }

    public void markAsCanceled() {
        this.isCanceled = true;
    }

    public void attachSubNode(Node subNode) {
        synchronized (this.subNodes) {
            this.subNodes.add(subNode);
        }
    }

    public void attachAssignedTask(Future<?> task) {
        synchronized (this.assignedTasks) {
            assignedTasks.add(task);
        }
    }

    public Boolean isCanceled() {
        return this.isCanceled;
    }
}
