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

    public static LinkedList<CornerPass> daftarPelangganCornerPass = new LinkedList<>();

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
        Database.daftarJadwal.add(new Jadwal(18, 11, 2023, 9, 15, 10, 15));
        Database.daftarJadwal.add(new Jadwal(18, 11, 2023, 10, 30, 11, 30));
        Database.daftarJadwal.add(new Jadwal(18, 11, 2023, 11, 45, 12, 45));
        Database.daftarJadwal.add(new Jadwal(18, 11, 2023, 13, 0, 14, 0));
        Database.daftarJadwal.add(new Jadwal(18, 11, 2023, 14, 15, 15, 15));
        Database.daftarJadwal.add(new Jadwal(18, 11, 2023, 15, 30, 16, 30));
        Database.daftarJadwal.add(new Jadwal(18, 11, 2023, 16, 45, 17, 45));
    }

    public void initializeStationData() {
        Database.daftarStation.add(new Station(1, "PS5"){{
            setDesc("x86-64-AMD Ryzen \"Zen 2\"", "GDDR6 16GB", "AMD Radeon RDNA 2-based graphics engine Ray-Tracing Support", "27\" LG 27GL850 144hz 2560x1440", "DualSense Wireless Controller");
            setGameLists("EA Sport FC 24", "Grand Theft Auto V", "Fortnite", "Call of Duty: Modern Warfare II", "Gran Turismo 7", "Hogwarts Legacy", "Cyberpunk 2077");
        }});
        Database.daftarStation.add(new Station(2, "PS5"){{
            setDesc("x86-64-AMD Ryzen \"Zen 2\"", "GDDR6 16GB", "AMD Radeon RDNA 2-based graphics engine Ray-Tracing Support", "27\" LG 27GL850 144hz 2560x1440", "DualSense Wireless Controller");
            setGameLists("Grand Theft Auto V", "Assassin's Creed Mirage", "Elden Ring", "Genshin Impact", "Apex Legends", "Resident Evil 4", "Overwatch 2", "Final Fantasy XVI");
        }});
        Database.daftarStation.add(new Station(3, "XBOX Series X"){{
            setDesc("8X Cores @ 3.8 GHz (3.66 GHz w/SMT) Custom Zen 2 CPU", "16GB GDDR6", "12 TFLOPS, 52 CUs @1.825 GHz Custom RDNA 2 GPU", "27\" LG 27GL850 144hz 2560x1440", "Xbox Wireless Controller");
            setGameLists("Forza Horizon 5", "Starfield", "Baldur's Gate III", "Control", "FIFA 23", "Forza Motorsport 2023", "Microsoft Flight Simulator", "Tekken 8", "Battlefield 2042");
        }});
        Database.daftarStation.add(new Station(4, "XBOX"){{
            setDesc("8X Cores @ 3.8 GHz (3.66 GHz w/SMT) Custom Zen 2 CPU", "16GB GDDR6", "12 TFLOPS, 52 CUs @1.825 GHz Custom RDNA 2 GPU", "27\" LG 27GL850 144hz 2560x1440", "Xbox Wireless Controller");
            setGameLists("Forza Horizon 4", "Assasin's Creed Valhalla", "Far Cry 6", "Dead Island 2", "Call of Duty: Warzone 2.0", "Tom Clancy's Rainbow Six Siege");
        }});
        Database.daftarStation.add(new Station(5, "PC"){{
            setDescPC("Core i9 13900K", "32GB DDR5 6400", "Nvidia GeForce RTX 3090Ti", "27\" LG 27GL850 144hz 2560x1440", "Steelseries Apex Pro", "Logitech G502 HERO");
            setGameLists("Forza Horizon 5", "DIRT Rally 2.0", "Grand Theft Auto V", "FIFA 22", "Genshin Impact", "Red Dead Redemption 2", "F1 23", "Love is All Around");
        }});
    }

    public void initializeCornerPassData() {
        // Database.daftarPelangganCornerPass.add(new CornerPass(Database.daftarUsers.get(0)));
    }
}
