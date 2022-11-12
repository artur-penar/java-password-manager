

public class DataRow
{
  int id;
  String app;
  String email;
  String login;
  String password;
  String url;

  public DataRow(int id, String app, String email, String login, String password, String url) {
    this.id = id;
    this.app = app;
    this.email = email;
    this.login = login;
    this.password = password;
    this.url = url;
  }
  public void displayRow(){
    System.out.println(id + app + email + login + password + url);
  }
}
