import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.concurrent.*;

public class MyServer {
    public static void main(String [] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        BlockingQueue deq = new ArrayBlockingQueue<String[]>(10, true);
        CountDownLatch latch = new CountDownLatch(2);
        executor.execute(new EquationGenerator(deq, latch));
        executor.execute(new RiddleGenerator(deq, latch));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Object s:deq)
            System.out.println(Arrays.toString((String[]) s));
        deq.clear();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\n");
        for (Object s:deq)
            System.out.println(Arrays.toString((String[]) s));
        try(ServerSocket ss = new ServerSocket(Integer.parseInt(args[0]))) {
            System.out.println("Server started on port "+args[0]);
            while (true)
                executor.execute(new QuestionThread(ss.accept(), deq));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
