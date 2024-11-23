package cp2024.solution;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class test {

    static class SleepTask extends RecursiveTask<Integer> {

        public SleepTask() {
            super();
        }

        @Override
        public Integer compute() {
            try {
                System.out.println("STARTING SUB TASK");
                Thread.sleep(20000000);
                return 0;
            } catch (InterruptedException e) {
                System.out.println("Interrupted while sleeping in subtask");
                return 0;
            }
        }
    }

    static class NotListeningTask extends RecursiveTask<Integer> {
        @Override
        public Integer compute() {
            System.out.println("I am not listening!");
            while (true) {
                if (Thread.interrupted()) {
                    System.out.println("Got interrupted!");
                    ;
                }
            }
        }
    }

    private static class ForkingTask extends RecursiveTask<Integer> {

        @Override
        public Integer compute() {
            System.out.println("Task started!");
            SleepTask sleepTasknew = new SleepTask();
            NotListeningTask notListeningTask = new NotListeningTask();
            System.out.println("About to fork!");
            notListeningTask.fork();
            sleepTasknew.compute();
            return 0;
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();

        Future<?> future = pool.submit(new ForkingTask());

        try {
            System.out.println("Give em some time");
            Thread.sleep(10000);
            System.out.println("Woke up");
        } catch (InterruptedException e) {
        }

        future.cancel(true);
        pool.shutdown();
    }

}
