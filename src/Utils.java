import java.util.InputMismatchException;
import java.util.Scanner;

public class Utils {

    public static int askChoice() {
        int choice = -1;
        Scanner sc = new Scanner(System.in);
        do  {
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Ceci n'est pas un nombre !");
                sc.nextLine();
                choice = -1;
            }
        } while(choice == -1);

        return choice;
    }

    public static void wait(int msToWait) {
        try {
            Thread.sleep(msToWait);
        } catch (InterruptedException e) {
            // Handle the exception if the sleep is interrupted
            System.err.println("Wait interrupted");
        }
    }


}
