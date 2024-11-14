package cp2024.solution;

public abstract class Task implements Runnable {
    private Task afterTask;

    public Task(Task afterTask) {
        this.afterTask = afterTask;
    }

    protected void doAfterTask() {
        if (this.afterTask != null) {
            afterTask.run();
        }
    }
}
