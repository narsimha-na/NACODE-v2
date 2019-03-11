package in.nacode.nacode.ListItems;

public class BookmarkCNotesPost {

    private  String notesName;
    private String notesDate;
    private String notesLang;

    public BookmarkCNotesPost(String notesLang) {
        this.notesLang = notesLang;
    }



    public BookmarkCNotesPost(String notesName, String notesDate) {
        this.notesName = notesName;
        this.notesDate = notesDate;
    }

    public BookmarkCNotesPost() {
    }



    public String getNotesLang() {
        return notesLang;
    }

    public void setNotesLang(String notesLang) {
        this.notesLang = notesLang;
    }
    public String getNotesName() {
        return notesName;
    }

    public void setNotesName(String notesName) {
        this.notesName = notesName;
    }

    public String getNotesDate() {
        return notesDate;
    }

    public void setNotesDate(String notesDate) {
        this.notesDate = notesDate;
    }



}
