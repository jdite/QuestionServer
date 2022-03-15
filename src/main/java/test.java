import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class test {
    private static BlockingDeque<String[]> deq = new LinkedBlockingDeque();
    public static void main(String [] args) {
        Random gen = new Random();
        String [] qa = new String [2];
        int a, b, c;
        while(true) {
            qa[0]  = "test";
            a = gen.nextInt(1, 100);
            b = gen.nextInt(1, 100);
            switch (gen.nextInt(1, 4)) {
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
                deq.putFirst(qa);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
