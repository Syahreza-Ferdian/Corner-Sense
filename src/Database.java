import java.util.*;

//this class is dedicated to represent the database by directly feed the data into program using LinkedList

public class Database {
    public static LinkedList<User> daftarUsers = new LinkedList<>();

    public static LinkedList<Jadwal> daftarJadwal = new LinkedList<>(){
        @Override
        public boolean add(Jadwal e) {
            super.add(e);

            //custom sort -> mengurutkan berdasarkan tgl
            Collections.sort(daftarJadwal, (jadwal1, jadwal2) -> Integer.compare(
                jadwal1.getStart().get(Calendar.DAY_OF_MONTH), 
                jadwal2.getStart().get(Calendar.DAY_OF_MONTH)
            ));
            return true;
        };
    };
    

    // METHODS
    public void initializeDummyUserData() {
        //future dummy user data goes here
        Database.daftarUsers.add(new User("Ferdian", "123"));
        Database.daftarUsers.add(new User("abc", "123"));
        
        // ADMIN
        Database.daftarUsers.add(new Admin("Syahreza", "123"));
    }

    public void initializeDummyJadwalData() {
        Database.daftarJadwal.add(new Jadwal(18, 11, 2023, 8, 0, 9, 0));
        Database.daftarJadwal.add(new Jadwal(18, 11, 2023, 9, 0, 10, 0));
        Database.daftarJadwal.add(new Jadwal(18, 11, 2023, 10, 0, 11, 0));
        Database.daftarJadwal.add(new Jadwal(15, 11, 2023, 10, 0, 11, 0));
        Database.daftarJadwal.add(new Jadwal(16, 11, 2023, 10, 0, 11, 0));
        Database.daftarJadwal.add(new Jadwal(16, 11, 2023, 20, 0, 21, 0));
    }

    // public Linked
}
