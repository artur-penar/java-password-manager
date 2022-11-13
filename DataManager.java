import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.StatementEvent;
import javax.swing.plaf.nimbus.State;


public class DataManager {
/**Data managment class**/

    static Connection connection = null;
  
  DataManager(){
  }

  public static void openConnection(){
    String url = "jdbc:sqlite:/home/artur/java-projects/PasswordManager/db/data-base.db";
    try {
      connection = DriverManager.getConnection(url);
      System.out.println("Connection established.");
    } 
    catch(SQLException e) {
      System.out.println(e);
    }

  }
  public static void closeConnection(){
    try{
      if (connection != null) {

        System.out.println("Connection closed."); 
        connection.close();
    }
  } catch (SQLException e){
      System.out.println(e);
    }
  }


 

  public static void createTable(){
    String createTablelQuerry = "CREATE TABLE IF NOT EXISTS warehouses (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	app text ,\n"
                + "	login text,\n"
                + " email text,\n"
                + " password text, \n"
                + " url text"
                + ");"; 
                                
    openConnection();
    try(Statement statemenet = connection.createStatement()){
      statemenet.execute(createTablelQuerry);
      System.out.println("Tabela została utworzona.");
    } catch (SQLException e){
      System.out.println(e.getMessage());
    }
    closeConnection();
  }


   public void selectAllItems(){
      String sqlQuerry = "SELECT id, app, url, login, password from warehouses"; 
      openConnection();
      try(Statement statement = connection.createStatement();
          ResultSet result = statement.executeQuery(sqlQuerry)){

        while (result.next()){
          System.out.println(result.getInt("id") + "\t" + 
                             result.getString("app") + "\t" + 
                             result.getString("url") + "\t" + 
                             result.getString("login") + "\t" + 
                             result.getString("password"));
        }

      } catch (SQLException e){
        System.out.println(e.getMessage());
      }
      closeConnection();
    }


    public static void insertData(String app, String url, String login, String password){
      String insertDataQuerry = "INSERT INTO warehouses(app, url, login, password) VALUES(?,?,?,?)";
      openConnection();
        try(PreparedStatement prepStatement = connection.prepareStatement(insertDataQuerry)){
            prepStatement.setString(1, app);
            prepStatement.setString(2, url);
            prepStatement.setString(3, login);
            prepStatement.setString(4, password);
            prepStatement.executeUpdate();
            System.out.println("Wprowadzono dane.");
    } catch (SQLException e){
      System.out.println(e.getMessage());
    }
    closeConnection();
  } 


  public static DataRow getByApp(String appName){
    DataRow dataRow = new DataRow(); 
    String sqlSelectQuerry = "SELECT * FROM warehouses WHERE app = ?";
    openConnection();
    
    try (PreparedStatement pstmt = connection.prepareStatement(sqlSelectQuerry)){
      pstmt.setString(1, appName);
      ResultSet result = pstmt.executeQuery();
      dataRow.setID(result.getInt("id"));
      dataRow.setApp(result.getString("app"));
      dataRow.setEmail(result.getString("email"));
      dataRow.setLogin(result.getString("login"));
      dataRow.setPassword(result.getString("password"));
      dataRow.setUrl(result.getString("url"));
    } catch (SQLException e ){
      System.out.println(e.getMessage());
    }
    closeConnection();
    return dataRow;
   }

  }



    




  



