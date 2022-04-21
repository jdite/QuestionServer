import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class QuestionThread implements Runnable{
    private final Socket s;
    private BlockingQueue<String[]> deq;
    private int rounds = 300;

    public QuestionThread(Socket s, BlockingQueue deq) {
        this.s = s;
        this.deq = deq;
    }
    public void run() {
        try (this.s;
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
            String [] qa = deq.take();
            out.println(qa[0]);
            String answer;
            int r = 0, x = 0;
            while ((answer = in.readLine()) != null) {
                if(answer.equalsIgnoreCase(qa[1])) {
                    out.println("Korrekt!");
                    r++;
                }
                else
                    out.println("Falsch! Richtig wäre: "+qa[1]);
                System.out.println(x+" rounds: "+rounds);
                x++;
                if (x >= rounds) {
                    out.println("Du hast "+r+" von "+rounds+" Fragen richtig beantwortet.");
                    break;
                }
                qa = deq.take();
                out.println(qa[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
