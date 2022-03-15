import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyClient {
    public static void main(String [] args) {
        try (
                Socket echoSocket = new Socket(args[0], Integer.parseInt(args[1]));        // 1st statement
                PrintWriter out =                                            // 2nd statement
                        new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in =                                          // 3rd statement
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn =                                       // 4th statement
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {
            String userInput, answer;
            if((userInput = in.readLine()) != null)
                System.out.println(userInput);
            while((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println(in.readLine());
                answer = in.readLine();
                System.out.println(answer);
                if(answer.contains("Fragen richtig beantwortet"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
