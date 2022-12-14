import java.awt.List;
import java.util.ArrayList;

public class DataRow {
    private int id;
    private String app;
    private String email;
    private String login;
    private String password;
    private String url;
    private String spec;

    public DataRow() {
        this.id = -1;
        this.app = "";
        this.email = "";
        this.login = "";
        this.password = "";
        this.url = "";
        this.spec = "";
    }


    public DataRow(int id, String app, String email, String login, String password, String url, String spec) {
        this.id = id;
        this.app = app;
        this.email = email;
        this.login = login;
        this.password = password;
        this.url = url;
        this.spec = spec;
    }
    
    public void displayLikeTab(){
        String dT = "\t\t";
        System.out.println("id \t\t app \t\t email \t\t login \t\t password \t\t url");
        System.out.println(id + dT + app + dT + email + dT + login + dT + password + dT + url );
  }

    public void displayRow() {
        if (id == 0) {
            System.out.println("Record does not exist.");
        }else if (id == -1){
       System.out.println(
                            " app: " + app +
                            "\t email: " + email +
                            "\t login: " + login +
                            "\t password: " + password +
                            "\t url: " + url
      );      
    } else {
            System.out.println(
                    "id:" + id +
                            "\t app:" + app +
                            "\t email:" + email +
                            "\t login:" + login +
                            "\t password:" + password +
                            "\t url:" + url);
        }
    }

    // Setters
    public void setID(int id) {
        this.id = id;
    }


    public void setApp(String app) {
        this.app = app;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void setSpec(String spec) {
        this.spec = spec;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getApp() {
        return app;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getSpec() {
        return spec;
    }

    public String[] getDecryptSpecList() {
        return spec.split(" ");
    }


}


