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

    static class SleepTask implements Runnable {

        public SleepTask() {
            super();
        }

        @Override
        public void run() {
            try {
                System.out.println("STARTING SUB TASK");
                Thread.sleep(20000000);

            } catch (InterruptedException e) {
                System.out.println("Interrupted while sleeping in subtask");
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

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 4, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        pool.submit(new SleepTask());
        System.out.println("The end!");
    }

}
