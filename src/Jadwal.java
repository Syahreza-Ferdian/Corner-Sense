import java.util.Calendar;

public class Jadwal {
    private Calendar start = Calendar.getInstance();
    private Calendar end = Calendar.getInstance();

    private boolean isAvailable = true;
    private User occupiedBy;

    public Jadwal(int hari, int bulan, int tahun, int jamMulai, int menitMulai, int jamSelesai, int menitSelesai) {
        start.set(tahun, bulan - 1, hari, jamMulai, menitMulai, 00);
        end.set(tahun, bulan - 1, hari, jamSelesai, menitSelesai, 00);
    }

    public Jadwal() {
        // super();
    }

    public void setOccupiedBy(User occupiedBy) {
        this.occupiedBy = occupiedBy;
    }

    public String getHari() {
        String[] namaHari = {"Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jum'at", "Sabtu"};
        return namaHari[start.get(Calendar.DAY_OF_WEEK) - 1];
    }

    public User getOccupiedBy() {
        return occupiedBy;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public Calendar getEnd() {
        return end;
    }

    public Calendar getStart() {
        return start;
    }

    public int getTanggal(Calendar c) {
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public int getBulan(Calendar c) {
        return c.get(Calendar.MONTH) + 1;
    }

    public int getTahun(Calendar c) {
        return c.get(Calendar.YEAR);
    }

    public int getJam(Calendar c) {
        return c.get(Calendar.HOUR);
    }

    public int getMinute(Calendar c) {
        return c.get(Calendar.MINUTE);
    }

    public String getAMPM(Calendar c) {
        return c.get(Calendar.AM_PM) == 1 ? "PM" : "AM";
    }

    public void showJadwal() {
        int count = 0;
        System.out.printf("\n%4s%3s%16s%9s%16s%12s%12s%7s\n", "No", "|", "Hari, Tgl", "|", "Waktu", "|", "Status", "|");
        System.out.println("-------------------------------------------------------------------------------");
        // System.out.printf("%4d%3s%21s%4s%16s%5s%14s%5s\n", 1, "|", String.format("%s, %02d-%02d-%d", "Rabu", 5, 11, 2023), "|", String.format("%02d:%02d - %02d:%02d", 9, 0, 10, 0), "|", "Not Available", "|");
        
        for(Jadwal jdwl : Database.daftarJadwal) {
            Calendar startDate = jdwl.start;
            Calendar endDate = jdwl.end;

            System.out.printf("%4d%3s%21s%4s%23s%5s%14s%5s\n",
                ++count, "|",
                String.format("%s, %02d-%02d-%d", jdwl.getHari(), jdwl.getTanggal(startDate), jdwl.getBulan(startDate), jdwl.getTahun(startDate)), "|",
                String.format("%02d:%02d %s - %02d:%02d %s", jdwl.getJam(startDate), jdwl.getMinute(startDate), jdwl.getAMPM(startDate), jdwl.getJam(endDate), jdwl.getMinute(endDate), jdwl.getAMPM(endDate)), "|", 
                jdwl.isAvailable() ? "Available" : "Not Available", "|"    
            );
        }
    }
}
