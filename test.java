package cp2024.solution;

import java.time.Duration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        System.out.println("The end!");
    }

}
