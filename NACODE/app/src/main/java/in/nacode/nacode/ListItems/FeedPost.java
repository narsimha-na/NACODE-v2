package in.nacode.nacode.ListItems;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class FeedPost  {

    public String title,desc,img_url,code,links;
    public Date timestamp;

    public FeedPost(){

    }

    public FeedPost(String title, String desc, String img_url, String code, String links,Date timestamp) {
        this.title = title;
        this.desc = desc;
        this.img_url = img_url;
        this.code = code;
        this.links = links;
        this.timestamp = timestamp;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }






}
