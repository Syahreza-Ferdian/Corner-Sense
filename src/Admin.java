import java.util.Scanner;

public class Admin extends User {
    private final Scanner sc = new Scanner(System.in);

    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public void prompt() {
        int admInputChoice;
        final int MAX_POSSIBLE_ADM_INPUT = 6;

        inputCycle: while (true) {
            System.out.printf("\n%s%sAnda login sebagai Admin%s\n", AnsiColor.YELLOW_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
            System.out.println("\n=============================================");
            System.out.println(">>> ADMIN DASHBOARD <<<");
            System.out.printf("Selamat datang admin %s%s%s\n", AnsiColor.RED, this.getUsername(), AnsiColor.RESET);
            System.out.printf("\n%sAdministrative Controls: %s\n", AnsiColor.CYAN_BOLD, AnsiColor.RESET);
            System.out.printf("%s\n%s\n%s\n%s\n%s\n", 
                "1. Tambah Jadwal",
                "2. Lihat Semua Jadwal",
                "3. Lihat Akun Terdaftar",
                "4. Corner Pass", 
                "5. Logout"
            );
            System.out.printf("\n%sAdditional: %s\n", AnsiColor.CYAN_BOLD, AnsiColor.RESET);
            System.out.println("6. Login sebagai User biasa.");
            System.out.print("Input [1/2/3/4/5]: ");

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
                case 5:
                    break inputCycle;
                case 6:
                    System.out.printf("\n%s%sBerpindah tampilan sebagai user biasa...%s\n", AnsiColor.YELLOW_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
                    super.prompt();
                    break;
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
                System.out.printf("\n%s%sTerjadi kesalahan dalam penambahan jadwal!%s\n", AnsiColor.RED_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
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



    // public static void main(String[] args) {
    //     Admin adm = new Admin("syah", "Reza");
    //     // adm.prompt();
    //     adm.showAllUsers();
    // }
}
