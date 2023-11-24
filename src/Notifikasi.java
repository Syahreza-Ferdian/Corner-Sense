import java.util.Calendar;

public class Notifikasi {
    private User user;

    private static boolean isFeedbackNotificationShown = false;
    private boolean isBookingNotificationShown = false;


    public Notifikasi(User user) {
        this.user = user;
        if (!isFeedbackNotificationShown) {
            this.onFeedbackResponded();
        }
        if (!isBookingNotificationShown) {
            this.bookingNotification();
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

    public void bookingNotification() {
        Calendar sekarang = Calendar.getInstance();
        Calendar calBooking = null;
        Booking userBooking = null;

        for (Booking bkg : Database.daftarBookings) {
            if (bkg.getUser().getUsername().equals(this.user.getUsername())) {
                calBooking = bkg.getJadwal().getStart();
                userBooking = bkg;
                break;
            }
        }

        if (calBooking != null && userBooking != null) {
            if (calBooking.get(Calendar.MINUTE) - sekarang.get(Calendar.MINUTE) == 15) {
                System.out.printf("\n%sSYSTEM NOTIFICATION:%s Anda memiliki booking aktif yang jadwalnya akan dimulai 15 menit lagi.\n", AnsiColor.RED, AnsiColor.RESET);
                System.out.printf("%sDETAIL INFORMASI BOOKING:%s Station number %d, Scheduled at %s\n",
                    AnsiColor.RED,
                    AnsiColor.RESET,
                    userBooking.getStation().getStationID(),
                    calBooking.toString()   
                );
                isBookingNotificationShown = true;
            }
        }
    }
}
