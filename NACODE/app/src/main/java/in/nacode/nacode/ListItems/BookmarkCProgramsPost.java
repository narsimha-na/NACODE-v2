package in.nacode.nacode.ListItems;


public class BookmarkCProgramsPost {

    private String pName;
    private String pLang;
    private String pDate;

    public BookmarkCProgramsPost(String pName, String pLang, String pDate) {
        this.pName = pName;
        this.pLang = pLang;
        this.pDate = pDate;
    }

    public BookmarkCProgramsPost(){

    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpLang() {
        return pLang;
    }

    public void setpLang(String pLang) {
        this.pLang = pLang;
    }

    public String getpDate() {
        return pDate;
    }

    public void setpDate(String pDate) {
        this.pDate = pDate;
    }


}
