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
        String encrpytedPassword = cipherMachine.encrypt(password);
        String key = cipherMachine.getKey();
        String iv = cipherMachine.getIv();
        String spec = key + " " + iv;

        System.out.print("Email:\n>");
        String email = getEmail();
        System.out.print("Url:\n>");
        String url = scanner.next();

        dataRow.setApp(app);
        dataRow.setLogin(login);
        dataRow.setPassword(encrpytedPassword);
        dataRow.setEmail(email);
        dataRow.setUrl(url);
        dataRow.setSpec(spec);
        dataRow.displayRow();

        return dataRow;
    }

    public void addNewRecord() throws Exception {
        dataManager.insertData(getDataToDataRow());

    }

    public ArrayList<DataRow> getPasswordByApp() throws Exception {
        System.out.print("Podaj nazwę aplikacji: \n>");
        String appName = scanner.next();
        ArrayList<DataRow> encryptedDataRows = dataManager.getListByApp(appName);
        ArrayList<DataRow> decryptedDataRows = new ArrayList<DataRow>();
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

    public void startApp() throws Exception {
        displayMenu();
        int userChoice = getMenuChoice();
        if (userChoice == 1) {
            addNewRecord();
        } else if (userChoice == 2) {
            for (DataRow dataRow : getPasswordByApp()) {
                dataRow.displayRow();
            }
        }
        ;

    }


}
