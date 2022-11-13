

public class DataRow
{
  private int id;
  private String app;
  private String email;
  private String login;
  private String password;
  private String url;

  public DataRow(){
    this.id = 0;
    this.app = "";
    this.email = "";
    this.login = "";
    this.password = "";
    this.url = "";
  }

  public DataRow(int id, String app, String email, String login, String password, String url) {
    this.id = id;
    this.app = app;
    this.email = email;
    this.login = login;
    this.password = password;
    this.url = url;
  }
  public void displayRow(){
    if (id == 0){
      System.out.println("Nie ma takiego rekordu.");
    }else{
    System.out.println(id + app + email + login + password + url);
    }
  }

  // Setters 
  public void setID(int id){
    this.id = id;
  }
  
  public void setApp(String app){
    this.app = app;
  }

  public void setEmail(String email){
    this.email = email;
  }

  public void setLogin(String login){
    this.login = login;
  }

  public void setPassword(String password){
    this.password = password;
  }

  public void setUrl(String url){
    this.url = url;
  }


  // Getters 
  public int getId(){
    return id;
  }

  public String getApp(){
    return app;
  }

  public String getEmail(){
    return email;
  }

  public String getLogin(){
    return login;
  }

  public String getPassword(){
    return password;
  }

  public String getUrl(){
    return url;
  }
}


