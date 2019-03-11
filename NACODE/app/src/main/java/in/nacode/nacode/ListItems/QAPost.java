package in.nacode.nacode.ListItems;

public class QAPost {

    public String qTitle;
    public String qDesc;
    public String qCode;
    public String qImageUrl;
    public String qUserDetails;
    public String qTopic;

    public String qTime;

    public  QAPost(){

    }

    public QAPost(String qTitle, String qDesc, String qCode, String qImageUrl, String qUserDetails,String qTopic, String qTime) {
        this.qTitle = qTitle;
        this.qDesc = qDesc;
        this.qCode = qCode;
        this.qImageUrl = qImageUrl;
        this.qTopic = qTopic;
        this.qUserDetails = qUserDetails;
        this.qTime = qTime;
    }

    public String getqTitle() {
        return qTitle;
    }

    public String getqDesc() {
        return qDesc;
    }

    public String getqCode() {
        return qCode;
    }

    public String getqImageUrl() {
        return qImageUrl;
    }

    public String getqUserDetails() {
        return qUserDetails;
    }

    public String getqTime() {
        return qTime;
    }

    public void setqTitle(String qTitle) {
        this.qTitle = qTitle;
    }

    public void setqDesc(String qDesc) {
        this.qDesc = qDesc;
    }

    public void setqCode(String qCode) {
        this.qCode = qCode;
    }

    public void setqImageUrl(String qImageUrl) {
        this.qImageUrl = qImageUrl;
    }

    public void setqUserDetails(String qUserDetails) {
        this.qUserDetails = qUserDetails;
    }

    public void setqTime(String qTime) {
        this.qTime = qTime;
    }

    public String getqTopic() {
        return qTopic;
    }

    public void setqTopic(String qTopic) {
        this.qTopic = qTopic;
    }
}
