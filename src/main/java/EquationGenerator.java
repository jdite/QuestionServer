import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class EquationGenerator implements Runnable {
    private BlockingQueue<String[]> deq;
    private CountDownLatch latch;
    public EquationGenerator(BlockingQueue<String[]> deq, CountDownLatch latch) {
        this.deq = deq;
        this.latch = latch;
    }
    @Override
    public void run() {
        Random gen = new Random();
        String [] qa = new String [2];
        int a, b, c;
        latch.countDown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(true) {
            a = gen.nextInt(1, 51);
            if((c = gen.nextInt(1, 51)) < a)
                b = c;
            else {
                b = a;
                a = c;
            }
            switch (gen.nextInt(1, 5)) {
                case 1:
                    c = a + b;
                    qa[0] = a+" + "+b+" = ?";
                    qa[1] = ""+c;
                    break;
                case 2:
                    c = a - b;
                    qa[0] = a+" - "+b+" = ?";
                    qa[1] = ""+c;
                    break;
                case 3:
                    c = a * b;
                    qa[0] = a+" * "+b+" = ?";
                    qa[1] = ""+c;
                    break;
                case 4:
                    c = a / b;
                    qa[0] = a+" / "+b+" = ?";
                    qa[1] = ""+c;
                    break;
            }
            try {
                deq.put(qa.clone());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
