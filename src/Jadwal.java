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

    public void showJadwal() {
        int count = 0;
        System.out.printf("\n%4s%3s%16s%9s%13s%8s%12s%7s\n", "No", "|", "Hari, Tgl", "|", "Waktu", "|", "Status", "|");
        System.out.println("------------------------------------------------------------------------");
        // System.out.printf("%4d%3s%21s%4s%16s%5s%14s%5s\n", 1, "|", String.format("%s, %02d-%02d-%d", "Rabu", 5, 11, 2023), "|", String.format("%02d:%02d - %02d:%02d", 9, 0, 10, 0), "|", "Not Available", "|");
        for(Jadwal jdwl : Database.daftarJadwal) {
            System.out.printf("%4d%3s%21s%4s%16s%5s%14s%5s\n",
                ++count, "|",
                String.format("%s, %02d-%02d-%d", jdwl.getHari(), jdwl.start.get(Calendar.DAY_OF_MONTH), jdwl.start.get(Calendar.MONTH) + 1, jdwl.start.get(Calendar.YEAR)), "|",
                String.format("%02d:%02d - %02d:%02d", jdwl.start.get(Calendar.HOUR), jdwl.start.get(Calendar.MINUTE), jdwl.end.get(Calendar.HOUR), jdwl.end.get(Calendar.MINUTE)), "|", 
                jdwl.isAvailable() ? "Available" : "Not Available", "|"    
            );
        }
    }
}
