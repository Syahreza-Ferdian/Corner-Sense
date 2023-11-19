public class Booking {
    private User user;
    private Jadwal jadwal;
    private Station station;
    private String kodeBooking;

    public Booking(String kodeBooking) {
        this.kodeBooking = kodeBooking;
    }

    public Booking(User user, Jadwal jadwal, Station station) {
        this.user = user;
        this.jadwal = jadwal;
        this.station = station;
        this.kodeBooking = this.autoGenerateKodeBooking();
    }

    public String autoGenerateKodeBooking() {
        StringBuilder kodeBookingSb = new StringBuilder();
        String possibleChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        final int PANJANG_KODE_BOOKING = 9;

        for (int i = 1; i <= PANJANG_KODE_BOOKING; i++) {
            int index = (int) (Math.random() * possibleChar.length());
            kodeBookingSb.append(possibleChar.charAt(index));
        }

        if (!Database.daftarBookings.contains(new Booking(kodeBookingSb.toString()))) {
            return kodeBookingSb.toString();
        }

        return autoGenerateKodeBooking();
    }

    public void showBookingDetails() {
        System.out.printf("\n =======================%4s%s>> DETAIL BOOKING <<%s\n", "", AnsiColor.CYAN_BOLD, AnsiColor.RESET);
        System.out.printf("|\t\t\t|\n");
        System.out.printf("|\t\t\t|%3sKode Booking\t: %s\n", "", this.kodeBooking);
        System.out.printf("|\t\t\t|%3sStation Number\t: %d\n", "", this.station.getStationID());
        System.out.printf("|\t%s\t\t|%3sJam\t\t\t: %s - %s\n", "QR CODE", "", this.jadwal.formatJam(this.jadwal.getStart()), this.jadwal.formatJam(this.jadwal.getEnd()));
        System.out.printf("|\t\t\t|%3sHari\t\t: %s\n", "", this.jadwal.getHari());
        System.out.printf("|\t\t\t|%3sTanggal\t\t: %s\n", "", this.jadwal.formatTanggal(this.jadwal.getStart()));
        System.out.printf("|\t\t\t|\n");
        System.out.printf(" =======================%4sTerima kasih telah melakukan booking di Corner Sense :)\n", "");

        System.out.println("\nUSERINFO: Tunjukkan QR Code pada penjaga Corner Sense untuk memulai sesi bermain");
    }

    @Override
    public boolean equals(Object obj) {
        return ((Booking)obj).kodeBooking == this.kodeBooking;
    }

    // MUTATOR AND ACCESSOR
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Jadwal getJadwal() {
        return jadwal;
    }

    public void setJadwal(Jadwal jadwal) {
        this.jadwal = jadwal;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public String getKodeBooking() {
        return kodeBooking;
    }
}
