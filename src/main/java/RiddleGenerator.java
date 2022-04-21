import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Deque;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class RiddleGenerator implements Runnable {
    private BlockingQueue<String[]> deq;
    private CountDownLatch latch;
    public RiddleGenerator(BlockingQueue<String[]> deq, CountDownLatch latch) {
        this.deq = deq;
        this.latch = latch;
    }
    @Override
    public void run() {
        Random gen = new Random();
        try (RandomAccessFile raf = new RandomAccessFile(Thread.currentThread().getContextClassLoader().getResource("riddles.csv").getFile(), "r")){
            while(true) {
                raf.seek(gen.nextLong(0, raf.length() - 2));
                raf.readLine();
                deq.put(raf.readLine().substring(1).split("\","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
