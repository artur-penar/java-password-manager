public class Main {
    public static void main(String[] args) {
        DataManager dataManager = new DataManager();
        dataManager.selectAllItems();
        dataManager.createTable();
        dataManager.insertData("youtube", "youtube.com", "KabanYoutuber", "YT");
        DataRow dataRow = new DataRow(1, "google", "artur@gmail.com", "kaban", "developer", "gmail.com");
        dataRow.displayRow(); 
  }

}
