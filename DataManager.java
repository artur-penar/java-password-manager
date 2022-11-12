import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
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
                + "	id integer PRIMARY KEY,\n"
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
      String sqlQuerry = "SELECT id, app, url, login, password from passwords";
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
    
}


    




  


