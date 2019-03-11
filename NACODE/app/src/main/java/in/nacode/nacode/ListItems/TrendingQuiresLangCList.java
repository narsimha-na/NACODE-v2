package in.nacode.nacode.ListItems;

public class TrendingQuiresLangCList {

   private String title;
    private String time;
    private int likes;



    public TrendingQuiresLangCList(String title, String time, int likes) {
        this.title = title;
        this.time = time;
        this.likes = likes;
    }

    public TrendingQuiresLangCList() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }





}
