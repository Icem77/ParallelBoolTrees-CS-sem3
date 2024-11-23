package cp2024.solution.nodes;

import java.util.LinkedList;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

import cp2024.solution.tasks.CancelDown;

import java.util.concurrent.ThreadPoolExecutor;

public abstract class Node {
    protected ReentrantLock lock;
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

    public abstract void takeSubresult(ThreadPoolExecutor executor, Integer depth, Boolean subResult);

    public abstract void check(ThreadPoolExecutor executor, Integer depth);

    public void cancel(ThreadPoolExecutor executor, Integer depth) {
        this.lock.lock();
        if (this.isCanceled == false) {
            this.markAsCanceled();
            this.lock.unlock();

            for (Future<?> assignedTask : assignedTasks) {
                assignedTask.cancel(true);
            }

            for (Node subNode : subNodes) {
                executor.submit(new CancelDown(executor, subNode, depth + 1));
            }
        } else {
            this.lock.unlock();
        }
    }

    public void cancelWithLock(ThreadPoolExecutor executor, Integer depth) {
        this.markAsCanceled();
        this.lock.unlock();

        for (Future<?> assignedTask : assignedTasks) {
            assignedTask.cancel(true);
        }

        for (Node subNode : subNodes) {
            executor.submit(new CancelDown(executor, subNode, depth + 1));
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
}
