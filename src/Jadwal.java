import java.util.Calendar;
import java.util.LinkedList;
import java.text.SimpleDateFormat;

public class Jadwal {
    private Calendar start = Calendar.getInstance();
    private Calendar end = Calendar.getInstance();

    private boolean isAvailable = true;
    private User occupiedBy;

    private SimpleDateFormat formatJam = new SimpleDateFormat("hh:mm a");
    private SimpleDateFormat formatTanggal = new SimpleDateFormat("dd-MM-yyyy");

    @SuppressWarnings("unchecked")
    private LinkedList<Station> stations = (LinkedList<Station>) Database.daftarStation.clone();

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

    public int getTanggalStart() {
        return this.start.get(Calendar.DAY_OF_MONTH);
    }

    public int getTanggalEnd() {
        return this.end.get(Calendar.DAY_OF_MONTH);
    }

    public int getBulanStart() {
        return this.start.get(Calendar.MONTH) + 1;
    }

    public int getBulanEnd() {
        return this.end.get(Calendar.MONTH) + 1;
    }

    public int getTahunStart() {
        return this.start.get(Calendar.YEAR);
    }

    public int getTahunEnd() {
        return this.end.get(Calendar.YEAR);
    }

    public int getJamStart() {
        return this.start.get(Calendar.HOUR);
    }

    public int getJamEnd() {
        return this.end.get(Calendar.HOUR);
    }

    public int getMinuteStart() {
        return this.start.get(Calendar.MINUTE);
    }

    public int getMinuteEnd() {
        return this.end.get(Calendar.MINUTE);
    }

    public String formatJam(Calendar c) {
        return formatJam.format(c.getTime());
    } 

    public String formatTanggal(Calendar c) {
        return formatTanggal.format(c.getTime());
    }

    public void setStationOccupiedBy(int stationNumber, User user) {
        int stationIndexOnList = stations.indexOf(new Station(stationNumber, ""));
        stations.get(stationIndexOnList).setUsedBy(user);
    }

    public void showJadwal() {
        int count = 0;
        System.out.printf("\n%4s%3s%16s%9s%16s%12s\n", "No", "|", "Hari, Tgl", "|", "Waktu", "|");
        System.out.println("------------------------------------------------------------");
        // System.out.printf("%4d%3s%21s%4s%16s%5s%14s%5s\n", 1, "|", String.format("%s, %02d-%02d-%d", "Rabu", 5, 11, 2023), "|", String.format("%02d:%02d - %02d:%02d", 9, 0, 10, 0), "|", "Not Available", "|");
        
        for(Jadwal jdwl : Database.daftarJadwal) {
            Calendar startDate = jdwl.start;
            Calendar endDate = jdwl.end;

            System.out.printf("%4d%3s%21s%4s%23s%5s\n",
                ++count, "|",
                String.format("%s, %s", jdwl.getHari(), jdwl.formatTanggal(startDate)), "|",
                String.format("%s - %s", jdwl.formatJam(startDate), jdwl.formatJam(endDate)), "|"    
            );
        }
    }

    public void showStations() {
        System.out.printf("\nDaftar station pada %s\n", this.formatTanggal(this.start));
        for (Station sts : stations) {
            System.out.printf("Station Number: %d -> %s (%s)\n", sts.getStationID(), sts.getStationName(), sts.isOccupied() ? "Not Available" : "Available");
        }
    }

    @Override
    public boolean equals(Object obj) {
        // CASTING
        Jadwal param = ((Jadwal)obj);

        if (param.getTanggalStart() == this.getTanggalStart() && param.getBulanStart() == this.getBulanStart()) {
            if (param.getJamStart() == this.getJamStart() && param.getJamEnd() == this.getJamEnd()) {
                if (param.getMinuteStart() == this.getMinuteStart() && param.getMinuteEnd() == this.getMinuteEnd()) {
                    return true;
                }
            }
        }
        return false;
    }

    // public static void main(String[] args) {
    //     Database db = new Database();
    //     Jadwal jdw = new Jadwal();
    //     // jdw.showStations();
    //     jdw.showJadwal();
    //     // jdw.setStationOccupiedBy(1, new User("Syahreza"));
    // }
}
