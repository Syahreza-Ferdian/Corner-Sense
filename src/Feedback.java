import java.time.LocalDateTime;

public class Feedback {
    private String feedbackMsg;
    private User requestedBy;
    private LocalDateTime requestedOn;
    private Admin respondedBy;
    private String respondMsg;

    public Feedback(User requestedBy, String feedbackMsg) {
        this.requestedBy = requestedBy;
        this.feedbackMsg = feedbackMsg;
        this.requestedOn = LocalDateTime.now();
    }

    public String getFeedbackMsg() {
        return feedbackMsg;
    }

    public User getRequestedBy() {
        return requestedBy;
    }

    public LocalDateTime getrequestedOn() {
        return requestedOn;
    }

    public void setRespondMsg(String respondMsg) {
        this.respondMsg = respondMsg;
    }

    public String getRespondMsg() {
        return respondMsg;
    }

    public void setRespondedBy(Admin respondedBy) {
        this.respondedBy = respondedBy;
    }

    public Admin getRespondedBy() {
        return respondedBy;
    }

    public boolean isResponded() {
        return !(respondMsg == null);
    }
}
