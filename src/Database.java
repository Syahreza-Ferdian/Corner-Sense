import java.util.*;

//this class is dedicated to represent the database by directly feed the data into program using LinkedList

public class Database {
    public static LinkedList<User> daftarUsers = new LinkedList<>();

    public static LinkedList<Jadwal> daftarJadwal = new LinkedList<>(){
        @Override
        public boolean add(Jadwal e) {
            // Memastikan tidak ada jadwal yang terduplikasi
            if (daftarJadwal.contains(e)) {
                return false;
            }

            super.add(e);

            //custom sort -> mengurutkan berdasarkan tgl
            Collections.sort(daftarJadwal, (jadwal1, jadwal2) -> jadwal1.getStart().compareTo(jadwal2.getStart()));

            return true;
        };
    };
    
    public static LinkedList<Station> daftarStation = new LinkedList<>();
    
    public static LinkedList<Booking> daftarBookings = new LinkedList<>();

    public static LinkedList<Feedback> daftarFeedbacks = new LinkedList<>();

    public Database() {
        initializeUserData();
        initializeStationData();
        initializeJadwalData();
    }


    // METHODS
    public void initializeUserData() {
        //future dummy user data goes here
        Database.daftarUsers.add(new User("Ferdian", "123"));
        Database.daftarUsers.add(new User("abc", "123"));
        
        // ADMIN
        Database.daftarUsers.add(new Admin("Syahreza", "123"));
    }

    public void initializeJadwalData() {
        Database.daftarJadwal.add(new Jadwal(18, 11, 2023, 8, 0, 9, 0) {{
            setStationOccupiedBy(1, daftarUsers.get(0));
            setStationOccupiedBy(2, daftarUsers.get(1));
        }});
        Database.daftarJadwal.add(new Jadwal(18, 11, 2023, 9, 0, 10, 0));
        Database.daftarJadwal.add(new Jadwal(18, 11, 2023, 10, 0, 11, 0));
        Database.daftarJadwal.add(new Jadwal(15, 11, 2023, 10, 0, 11, 0));
        Database.daftarJadwal.add(new Jadwal(16, 11, 2023, 10, 0, 11, 0));
        Database.daftarJadwal.add(new Jadwal(16, 11, 2023, 20, 0, 21, 0));
    }

    public void initializeStationData() {
        Database.daftarStation.add(new Station(1, "PC 1"));
        Database.daftarStation.add(new Station(2, "XBOX"));
        Database.daftarStation.add(new Station(3, "PS5"));
        Database.daftarStation.add(new Station(4, "PC 2"));
        Database.daftarStation.add(new Station(5, "PC 3"));
    }

    // public Linked
}
