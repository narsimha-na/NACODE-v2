package in.nacode.nacode.ListItems;

import android.view.View;

public class PlaygroundListPost{

    private String name;
    private String lang;
    private String time;

    public PlaygroundListPost(String name, String lang, String time) {
        this.name = name;
        this.lang = lang;
        this.time = time;
    }

    public PlaygroundListPost() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
