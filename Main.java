public class Main {
    public static void main(String[] args) {
        DataManager dataManager = new DataManager();
        dataManager.selectAllItems();
        dataManager.createTable();
        dataManager.insertData("youtube", "youtube.com", "KabanYoutuber", "YT");
    }

}
