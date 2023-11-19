import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class Admin extends User {
    private final Scanner sc = new Scanner(System.in);

    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public void prompt() {
        int admInputChoice;
        final int MAX_POSSIBLE_ADM_INPUT = 8;

        inputCycle: while (true) {
            System.out.printf("\n%s%sAnda login sebagai Admin%s\n", AnsiColor.YELLOW_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
            System.out.println("\n=============================================");
            System.out.println(">>> ADMIN DASHBOARD <<<");
            System.out.printf("Selamat datang admin %s%s%s\n", AnsiColor.RED, this.getUsername(), AnsiColor.RESET);
            System.out.printf("\n%sAdministrative Controls: %s\n", AnsiColor.CYAN_BOLD, AnsiColor.RESET);
            System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n", 
                "1. Tambah Jadwal",
                "2. Lihat Semua Jadwal",
                "3. Lihat Akun Terdaftar",
                "4. Lihat Semua Booking Aktif",
                "5. Corner Pass", 
                "6. Respond Feedback",
                "7. Logout"
            );
            System.out.printf("\n%sAdditional: %s\n", AnsiColor.CYAN_BOLD, AnsiColor.RESET);
            System.out.println("8. Login sebagai User biasa.");
            System.out.printf("Input [1 - %d]: ", MAX_POSSIBLE_ADM_INPUT);

            try {
                admInputChoice = sc.nextInt();
                if (admInputChoice < 1 || admInputChoice > MAX_POSSIBLE_ADM_INPUT) {
                    throw new Exception();
                }
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                System.out.printf("\n%s%sSistem menerima input yang tidak valid! Harap masukkan input sesuai pilihan yang tersedia%s\n",
                    AnsiColor.RED_BACKGROUND,
                    AnsiColor.WHITE_BOLD,
                    AnsiColor.RESET
                );
                continue inputCycle;
            }

            switch (admInputChoice) {
                case 1:
                    this.tambahJadwal();
                    break;
                case 2:
                    Jadwal jdwl = new Jadwal();
                    jdwl.showJadwal();
                    break;
                case 3:
                    this.showAllUsers();
                    break;
                case 4:
                    this.showAllBookings();
                    break;
                case 6:
                    this.respondFeedback();
                    break;
                case 7:
                    break inputCycle;
                case MAX_POSSIBLE_ADM_INPUT:
                    System.out.printf("\n%s%sBerpindah tampilan sebagai user biasa...%s\n", AnsiColor.YELLOW_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
                    super.prompt();
                    break inputCycle;
            }
        }
    }

    // ADMIN SPECIAL METHODS
    private void tambahJadwal() {
        int date, month, year, hour_start, hour_finish, minute_start, minute_finish;

        while(true) {
            try {
                System.out.println("\n====[FORM PENAMBAHAN JADWAL]====");
                
                System.out.print("Tanggal\t\t: ");
                date = sc.nextInt();
                
                System.out.print("Bulan\t\t: ");
                month = sc.nextInt();
                
                System.out.print("Tahun\t\t: ");
                year = sc.nextInt();
                
                System.out.print("Jam mulai\t: ");
                hour_start = sc.nextInt();
                System.out.printf("%3s Menit\t: ", ">");
                minute_start = sc.nextInt();
                
                System.out.print("Jam selesai\t: ");
                hour_finish = sc.nextInt();
                System.out.printf("%3s Menit\t: ", ">");
                minute_finish = sc.nextInt();

                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                System.out.printf("\n%s%sSistem menerima input yang tidak valid! Hindari memasukkan input selain Integer%s\n",
                    AnsiColor.RED_BACKGROUND,
                    AnsiColor.WHITE_BOLD,
                    AnsiColor.RESET
                );
                continue;
            } 

            boolean successfullyAdded = Database.daftarJadwal.add(new Jadwal(date, month, year, hour_start, minute_start, hour_finish, minute_finish));

            if (successfullyAdded) {
                System.out.printf("\n%s%sBerhasil menambahkan jadwal baru!%s\n", AnsiColor.GREEN_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
                break;
            } else {
                System.out.printf("\n%s%sJadwal yang anda tambahkan sudah ada di database!%s\n", AnsiColor.RED_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
                continue;
            }
        }
    }

    public void showAllUsers() {
        int counter = 0;
        for(User usr : Database.daftarUsers) {
            System.out.printf("%d. %s %s\n", 
                ++counter, 
                usr.getUsername(),
                (usr instanceof Admin) ? "(ADMIN)" : ""
            );
        }
    }

    public void showAllBookings() {
        int counter = 0;
        System.out.printf("\n%sDaftar semua booking aktif: %s\n", AnsiColor.CYAN_BOLD, AnsiColor.RESET);
        for (Booking bkg : Database.daftarBookings) {
            Jadwal currentJadwal = bkg.getJadwal();
            System.out.printf("\n%d. Kode Booking\t: %s\n", ++counter, bkg.getKodeBooking());
            System.out.printf("%3sUser\t\t: %s\n", "", bkg.getUser().getUsername());
            System.out.printf("%3sStation\t: %d\n", "", bkg.getStation().getStationID());
            System.out.printf("%3sJam\t\t: %s - %s\n", "", currentJadwal.formatJam(currentJadwal.getStart()), currentJadwal.formatJam(currentJadwal.getEnd()));
            System.out.printf("%3sHari, Tgl\t: %s, %s\n", "", currentJadwal.getHari(), currentJadwal.formatTanggal(currentJadwal.getStart()));
        }
    }

    public void respondFeedback() {
        int counter = 0;
        int respondChoice;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
        
        if (Database.daftarFeedbacks.size() == 0) {
            System.out.printf("\n%s%sTidak ada pesan feedback di database!%s\n", AnsiColor.RED_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
            return;
        }

        inputCycle: while(true) {
            System.out.printf("\nPilih Feedback yang ingin Anda respon: ");
            for (Feedback fdbk : Database.daftarFeedbacks) {
                System.out.printf("\n%d. Requested by\t\t: %s\n", ++counter, fdbk.getRequestedBy().getUsername());
                System.out.printf("%3sPesan Feedback\t: %s\n", "", fdbk.getFeedbackMsg());
                System.out.printf("%3sRequested On\t\t: %s\n", "", fdbk.getrequestedOn().format(dtf));
                System.out.printf("%3sResponded By\t\t: %s\n", "", fdbk.isResponded() ? fdbk.getRespondedBy().getUsername() : "-");
            }
            System.out.printf("Input [1 - %d]: ", Database.daftarFeedbacks.size());

            try {
                respondChoice = sc.nextInt();
                if (respondChoice < 1 || respondChoice > Database.daftarFeedbacks.size()) {
                    throw new Exception();
                }
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                System.out.printf("\n%s%sSistem menerima input yang tidak valid. Harap masukkan input sesuai dengan nomor feedback!%s\n",
                    AnsiColor.RED_BACKGROUND,
                    AnsiColor.WHITE_BOLD,
                    AnsiColor.RESET
                );
                continue inputCycle;
            }

            if (Database.daftarFeedbacks.get(respondChoice - 1).isResponded()) {
                System.out.printf("\n%s%sFeedback tersebut sudah direspond oleh Admin lain!%s\n", AnsiColor.RED_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
                break inputCycle;
            }

            System.out.printf("\nMasukkan pesan respon di bawah ini: \n");
            System.out.printf("%3s> ", "");
            String respondMsg = sc.nextLine();

            Database.daftarFeedbacks.get(respondChoice - 1).setRespondMsg(respondMsg);
            Database.daftarFeedbacks.get(respondChoice - 1).setRespondedBy(this);

            System.out.printf("\n%s%sPesan respon berhasil disimpan!%s\n", AnsiColor.GREEN_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
            break inputCycle;
        }
    }

    // public static void main(String[] args) {
    //     Database db = new Database();
    //     User usr = Database.daftarUsers.get(0);
    //     Jadwal jdwl = Database.daftarJadwal.get(0);
    //     Station sts = Database.daftarStation.get(3);

    //     Booking bkg = new Booking(usr, jdwl, sts);
    //     Database.daftarBookings.add(bkg);

    //     Admin adm = new Admin("Syahreza", "123");
    //     adm.prompt();
    // }
}
