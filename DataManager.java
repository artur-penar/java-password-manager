import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class DataManager {
    static Connection connection = null;

    DataManager() {
        createTable();
    }

    private static void openConnection() {
        String url = "jdbc:sqlite:/home/artur/java-projects/PasswordManager/db/data-base.db";
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connection established.");
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public static void closeConnection() {
        try {
            if (connection != null) {

                System.out.println("Connection closed.");
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    public static void createTable() {
        String createTablelQuerry = "CREATE TABLE IF NOT EXISTS passwords (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	app text ,\n"
                + "	login text,\n"
                + " password text, \n"
                + " email text,\n"
                + " url text,\n"
                + " spec text"
                + ");";

        openConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTablelQuerry);
            System.out.println("Table created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        closeConnection();
    }


    public void displayAllItems() {
        String sqlQuery = "SELECT id, app, url, login, password from passwords";
        openConnection();
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sqlQuery)) {

            while (result.next()) {
                System.out.println(result.getInt("id") + "\t" +
                        result.getString("app") + "\t\t" +
                        result.getString("url") + "\t\t" +
                        result.getString("login") + "\t\t" +
                        result.getString("password"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        closeConnection();
    }


    public void insertData(DataRow dataRow) {
        String insertDataQuery = "INSERT INTO passwords(app, login, password, email, url, spec) VALUES(?,?,?,?,?,?)";
        openConnection();
        try (PreparedStatement prepStatement = connection.prepareStatement(insertDataQuery)) {
            prepStatement.setString(1, dataRow.getApp());
            prepStatement.setString(2, dataRow.getLogin());
            prepStatement.setString(3, dataRow.getPassword());
            prepStatement.setString(4, dataRow.getEmail());
            prepStatement.setString(5, dataRow.getUrl());
            prepStatement.setString(6, dataRow.getSpec());
            prepStatement.executeUpdate();
            System.out.println("Data inserted.");
            dataRow.displayRow();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        closeConnection();
    }
    
    public void deleteData(int id){
        String deleteSql = "DELETE FROM passwords WHERE id = ?";
        openConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSql)){
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Succesfully removed.");
    } catch (SQLException e){
          System.out.println(e.getMessage());

    }
  }


    public DataRow getById(int id) {
        DataRow dataRow = new DataRow();
        String sqlSelectQuery = "SELECT * FROM passwords WHERE id = ?";
        openConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(sqlSelectQuery)) {
            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();

            dataRow.setID(result.getInt("id"));
            dataRow.setApp(result.getString("app"));
            dataRow.setEmail(result.getString("email"));
            dataRow.setLogin(result.getString("login"));
            dataRow.setPassword(result.getString("password"));
            dataRow.setUrl(result.getString("url"));
            dataRow.setSpec(result.getString("spec"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        closeConnection();
        return dataRow;
    }

    
    public void displayAvailableAppNames(){
        ArrayList<String> appNames = getAllAppNames();
        if (!appNames.isEmpty()){
            int appCounter = 1;
            for(String name : appNames){
                System.out.println(appCounter + ": " + name);
                appCounter++;
      }
        } else System.out.println("Is no application."); 
   
  }

    private ArrayList<String> getAllAppNames() {
        String allAppDisplayQuery = "SELECT app from passwords";
        ArrayList<String> apps = new ArrayList<String>(); 
        String appName = null;
        openConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(allAppDisplayQuery)) {
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                appName = result.getString("app");
                if (!apps.contains(appName)){
                    apps.add(appName);
        }
            }
        } catch (SQLException e) {
            System.out.println(e);

        }

        closeConnection();
        return apps;
    }


    public ArrayList<DataRow> getListByApp(String appName) {
        ArrayList<DataRow> dataRowsList = new ArrayList<DataRow>();
        String sqlSelectQuerry = "SELECT * FROM passwords WHERE app = ?";
        openConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(sqlSelectQuerry)) {
            pstmt.setString(1, appName);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                dataRowsList.add(new DataRow(
                        result.getInt("id"),
                        result.getString("app"),
                        result.getString("email"),
                        result.getString("login"),
                        result.getString("password"),
                        result.getString("url"),
                        result.getString("spec")
                ));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (dataRowsList.isEmpty()) {
            System.out.println("Record '" + appName + "' does not exists.");
        }
        closeConnection();
        return dataRowsList;
    }
}



    




  



