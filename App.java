import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Scanner;

class App {
    CipherMachine cipherMachine;
    DataManager dataManager;
    DataRow dataRow;
    Scanner scanner = new Scanner(System.in);


    public App() throws Exception {
        dataManager = new DataManager();
        dataRow = new DataRow();
        cipherMachine = new CipherMachine();
    }


    public int getMenuChoice() {
        boolean isDataCorrect = false;
        int userChoice = 0;
        while (!isDataCorrect) {
            try {
                System.out.print(">");
                userChoice = scanner.nextInt();
                if (userChoice >= 0 && userChoice < 6) {
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
                4. Delete password.
                5. About.
                0. Exit."""
        );
    }

    public static boolean isValidEmail(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }


    public String getEmail() {
        boolean isValidEmail = false;

        String emailAddress = null;
        while (!isValidEmail) {
            emailAddress = scanner.next();
            if (isValidEmail(emailAddress)) {
                isValidEmail = true;
            } else {
                System.out.println("Niepoprawny email.");
                System.out.print(">");
            }

        }
        return emailAddress;
    }

    public DataRow getDataToDataRow() throws Exception {
        System.out.print("Nazwa aplikacji:\n>");
        String app = scanner.next();

        System.out.print("Login:\n>");
        String login = scanner.next();

        System.out.print("Hasło:\n>");
        String password = scanner.next();
        String encryptedPassword = cipherMachine.encrypt(password);
        String key = cipherMachine.getKey();
        String iv = cipherMachine.getIv();
        String spec = key + " " + iv;

        System.out.print("Email:\n>");
        String email = getEmail();

        System.out.print("Url:\n>");
        String url = scanner.next();

        dataRow.setApp(app);
        dataRow.setLogin(login);
        dataRow.setPassword(encryptedPassword);
        dataRow.setEmail(email);
        dataRow.setUrl(url);
        dataRow.setSpec(spec);

        return dataRow;
    }

    public void addNewPassword() throws Exception {
        dataManager.insertData(getDataToDataRow());

    }

    public ArrayList<DataRow> getPasswordByAppName(String appName) throws Exception {
        ArrayList<DataRow> encryptedDataRows = dataManager.getListByApp(appName);
        ArrayList<DataRow> decryptedDataRows = new ArrayList<>();
        for (DataRow rowToDecrypt : encryptedDataRows) {
            String[] decryptSpecList = rowToDecrypt.getDecryptSpecList();
            String secretKey = decryptSpecList[0];
            String iv = decryptSpecList[1];
            String decryptedPassword = cipherMachine.decrypt(rowToDecrypt.getPassword(), secretKey, iv);
            rowToDecrypt.setPassword(decryptedPassword);
            decryptedDataRows.add(rowToDecrypt);
        }
        return decryptedDataRows;
    }

    public void retrievePassword() throws Exception {
        boolean isFinish = false;
        while (!isFinish) {
            System.out.print("Type the application name: (-da if you want display all aviable apps / q if you want quit)\n>");
            String appName = scanner.next();
            if (appName.equals("-da")) {
                dataManager.displayAvailableAppNames();
            } else if (appName.equals("q")) {
                isFinish = true;
            } else {
                ArrayList<DataRow> passwords = getPasswordByAppName(appName);
                for (DataRow row : passwords) {
                    row.displayRow();
                }
            }
        }

    }

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  

    public void showAllPasswords() {
        dataManager.displayAllItems();
    }

    public void about() {
        System.out.println("Developed by Kaban Developers.");
    }

    public void deletePassword() {
        boolean deleteIsFinish = false;
        
        while(!deleteIsFinish){
            showAllPasswords();
            System.out.print("Type id you want to remove. \n>");
            String id = scanner.next();
            try{ 
                DataRow rowToRemove = dataManager.getById(Integer.parseInt(id));
                rowToRemove.displayRow();
            } catch (Exception e){
                System.out.println("Id must be integer.");
                System.out.println(e);
      }
        }
  }


    public void startApp() throws Exception {
        boolean isFinish = false;

        while (!isFinish) {
            clearScreen();
            displayMenu();
            int userChoice = getMenuChoice();
            if (userChoice == 1) {
                addNewPassword();
            } else if (userChoice == 2) {
                clearScreen();
                retrievePassword();
            } else if (userChoice == 3) {
                showAllPasswords();
            } else if (userChoice == 4) {
                deletePassword();
            } else if (userChoice == 5) {
                about();
            } else if (userChoice == 0) {
                isFinish = true;
                System.out.println("Bye bye.");
            }
        }
    }


}
