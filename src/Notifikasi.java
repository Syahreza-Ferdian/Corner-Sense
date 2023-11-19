public class Notifikasi {
    private User user;

    private static boolean isFeedbackNotificationShown = false;


    public Notifikasi(User user) {
        this.user = user;
        if (!isFeedbackNotificationShown) {
            this.onFeedbackResponded();
        }
    }

    public void onFeedbackResponded() {
        Feedback userFeedback = null;
        for (Feedback fdb : Database.daftarFeedbacks) {
            if (fdb.getRequestedBy().getUsername().equals(this.user.getUsername())) {
                userFeedback = fdb;
                break;
            }
        }

        if (userFeedback != null && userFeedback.isResponded()) {
            System.out.printf("\n%sSYSTEM NOTIFICATION:%s Admin %s merespon ke feedback Anda dengan respon \'%s\'\n", 
                AnsiColor.RED, 
                AnsiColor.RESET,
                userFeedback.getRespondedBy().getUsername(),
                userFeedback.getRespondMsg()
            );
            isFeedbackNotificationShown = true;
        }
    }
}
