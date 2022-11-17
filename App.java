import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.awt.*;
import java.util.Scanner;

class App {
    DataManager dataManager;
    DataRow dataRow;

    public App() {
        dataManager = new DataManager();
        dataRow = new DataRow();
    }

    public void startApp() {
        displayMenu();
        int userChoice = getMenuChoice();
        if (userChoice == 1){
            addNewPassword();
        }
    }


    public int getMenuChoice() {
        Scanner scanner = new Scanner(System.in);
        boolean isDataCorrect = false;
        int userChoice = 0;
        while (!isDataCorrect) {
            try {
                System.out.print(">");
                userChoice = scanner.nextInt();
                if (userChoice > 0 && userChoice < 5) {
                    isDataCorrect = true;
                }
            } catch (Exception e) {
                System.out.println("Nieprawidłowe dane.");
                scanner.next();
            }
        }

        return userChoice;
    }

    public void displayMenu() {
        System.out.println("""
                \tMENU
                1. Add new password.
                2. Retrive password.
                3. Show all passwords.
                4. About."""
        );
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public void addNewPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nazwa aplikacji\n>");
        String app = scanner.next();

    }

}
