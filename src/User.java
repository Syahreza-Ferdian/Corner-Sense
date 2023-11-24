import java.util.InputMismatchException;
import java.util.Scanner;

// @SuppressWarnings("unused")
public class User {
    private final Scanner sc = new Scanner(System.in);

    private String username;
    private String password;
    private String nomorHP;
    private String NIM;
    private String email;
    private JenisKelamin jenisKelamin;
    private int userMenuChoice;
    private boolean stopUserInput;
    private Jadwal userSelectedJdwlBooking;

    private enum JenisKelamin {
        LAKI_LAKI,
        PEREMPUAN;
    }

    private String alamat;
    private String nama_user;

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean authenticatePass(String password) {
        return this.password.equals(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public void setNomorHP(String nomorHP) {
        this.nomorHP = nomorHP;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNama_user() {
        return nama_user;
    }

    public String getNomorHP() {
        return nomorHP;
    }

    public void setJenisKelamin(JenisKelamin jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public JenisKelamin getJenisKelamin() {
        return jenisKelamin;
    }

    public String getNIM() {
        return NIM;
    }

    public void setNIM(String nIM) {
        NIM = nIM;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserMenuChoice() {
        return userMenuChoice;
    }

    public boolean isStopUserInput() {
        return stopUserInput;
    }

    public void setStopUserInput(boolean stopUserInput) {
        this.stopUserInput = stopUserInput;
    }

    public void prompt() {
        final int MAX_POSSIBLE_USER_INPUT = 8;

        inputCycle: while (true) {
            boolean isUserSubscribed = this.isUserSubscribed();
            System.out.println("\n=============================================");
            System.out.printf("Selamat datang %s\n", this.getUsername());

            @SuppressWarnings("unused")
            Notifikasi ntf = new Notifikasi(this);

            if (isUserSubscribed) {
                System.out.printf("\n%sUSERINFO: Anda mempunyai subscription Corner Pass! Anda berhak mendapat privilege Corner Pass%s\n", AnsiColor.CYAN_BOLD, AnsiColor.RESET);
            }

            System.out.println("\nMenu Corner Sense: ");
            System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n",
                "1. Lihat Jadwal",
                "2. Booking Online",
                "3. Lihat Booking",
                "4. Cancel Booking",
                "5. Lihat detail device",
                "6. Corner Pass",
                "7. Kritik, Saran dan Request Game",
                "8. Logout"
            );
            System.out.printf("Input [1 - %d]: ", MAX_POSSIBLE_USER_INPUT);
            
            try {
                this.userMenuChoice = sc.nextInt();
                if (this.userMenuChoice < 1 || this.userMenuChoice > MAX_POSSIBLE_USER_INPUT) {
                    throw new Exception();
                }
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("\nSistem menerima input yang tidak valid! Harap masukkan input sesuai pilihan yang tersedia\n");
                continue inputCycle;
            }

            switch(this.userMenuChoice) {
                case 1:
                    this.lihatJadwal();
                    break;
                case 2:
                    this.booking();
                    break;
                case 3:
                    this.showUserDetailBooking();
                    break;
                case 4:
                    this.cancelBooking();
                    break;
                case 5:
                    this.showDeviceDetails();
                    break;
                case 6:
                    this.cornerPass();
                    break;
                case 7:
                    this.giveFeedback();
                    break;
                case MAX_POSSIBLE_USER_INPUT:
                    this.stopUserInput = true;
                    break inputCycle;
            }
        }
    }

    public void lihatJadwal() {
        System.out.printf("\n%sJadwal yang tersedia:%s\n", AnsiColor.CYAN, AnsiColor.RESET);
        Jadwal jdw = new Jadwal();
        jdw.showJadwal();
    }
 
    public boolean isUserHasBooking() {
        for(Booking bkg : Database.daftarBookings) {
            if (bkg.getUser().getUsername().equals(this.username)) {
                return true;
            }
        }
        return false;
    }

    public void showUserDetailBooking() {
        if (!isUserHasBooking()) {
            System.out.printf("\n%s%sAnda tidak memiliki booking aktif!%s\n", AnsiColor.RED_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
            System.out.println("Pilih menu \'Booking Online\' untuk membuat booking baru!");
            return;
        }

        for(Booking bkg : Database.daftarBookings) {
            if (bkg.getUser().getUsername().equals(this.username)) {
                bkg.showBookingDetails();
                break;
            }
        }
    }

    public void booking() {
        int userJadwalInputChoice;

        if (this.isUserHasBooking()) {
            System.out.printf("\n%s%sAnda sudah memiliki booking aktif!%s\n", AnsiColor.RED_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
            return;
        }

        inputCycle: while(true) {
            final int MAX_POSSIBLE_USER_INPUT = this.isUserSubscribed() ? Database.daftarJadwal.size() + 2 : Database.daftarJadwal.size() + 1;

            lihatJadwal();
            if (this.isUserSubscribed()) System.out.printf("\n%d.%s[CORNER PASS]%s Input jadwal booking sesuai keinginan Anda: \n", 
                MAX_POSSIBLE_USER_INPUT - 1,
                AnsiColor.CYAN_BOLD,
                AnsiColor.RESET
            );

            System.out.printf("%d. Batal", MAX_POSSIBLE_USER_INPUT);
            System.out.print("\nPilih salah satu jadwal yang anda inginkan: ");

            try {
                userJadwalInputChoice = sc.nextInt();
                if (userJadwalInputChoice < 1 || userJadwalInputChoice > MAX_POSSIBLE_USER_INPUT) {
                    throw new Exception();
                }
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                System.out.printf("\n%s%sSistem menerima input yang tidak valid. Harap masukkan input sesuai dengan nomor pada jadwal!%s\n",
                    AnsiColor.RED_BACKGROUND,
                    AnsiColor.WHITE_BOLD,
                    AnsiColor.RESET
                );
                continue inputCycle;
            }

            if (userJadwalInputChoice == MAX_POSSIBLE_USER_INPUT) {
                break inputCycle;
            }

            userSelectedJdwlBooking = Database.daftarJadwal.get(userJadwalInputChoice - 1);
            userSelectedJdwlBooking.showStations();
            System.out.printf("\n%d. Kembali\n", Database.daftarStation.size() + 1);
            System.out.print("\nPilih station number yang Anda inginkan: ");
            int userStationNumberInput, indexStationInDb = -1;

            try {
                userStationNumberInput = sc.nextInt();

                if (userStationNumberInput == Database.daftarStation.size() + 1) {
                    break inputCycle;
                }

                if (userStationNumberInput < 1 || userStationNumberInput > Database.daftarStation.size() + 1) {
                    throw new InputMismatchException();
                }
                indexStationInDb = Database.daftarStation.indexOf(new Station(userStationNumberInput));

                if (Database.daftarStation.get(indexStationInDb).isOccupied()) {
                    throw new Exception();
                }
                sc.nextLine();

            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.printf("\n%s%sSistem menerima input yang tidak valid. Harap masukkan input sesuai dengan nomor stationl!%s\n",
                    AnsiColor.RED_BACKGROUND,
                    AnsiColor.WHITE_BOLD,
                    AnsiColor.RESET
                );
                continue;

            } catch (Exception e) {
                sc.nextLine();
                System.out.printf("\n%s%sSudah ada orang lain yang mem-booking station tersebut!%s\n",
                    AnsiColor.RED_BACKGROUND,
                    AnsiColor.WHITE_BOLD,
                    AnsiColor.RESET
                );
                continue;
            }

            if (indexStationInDb != -1) {
                Station stationSelected = Database.daftarStation.get(indexStationInDb);
                Database.daftarStation.get(indexStationInDb).setUsedBy(this);
                Booking bk = new Booking(this, userSelectedJdwlBooking, stationSelected);
                Database.daftarBookings.add(bk);

                System.out.printf("\n%s%sBooking berhasil!%s\n", AnsiColor.GREEN_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
                break inputCycle;
            }
        }
    }

    public void cancelBooking() {
        if (!this.isUserHasBooking()) {
            System.out.printf("\n%s%sAnda tidak mempunyai booking aktif!%s\n", AnsiColor.RED_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
            return;
        }
        Booking userBkg = null;

        for(Booking bkg : Database.daftarBookings) {
            if (bkg.getUser().getUsername().equals(this.username)) {
                userBkg = bkg;
                break;
            }
        }

        System.out.printf("\nSistem mendeteksi Anda memiliki booking aktif pada %s, %s %s di Station Number %d\n", userBkg.getJadwal().getHari(), userBkg.getJadwal().formatTanggal(userBkg.getJadwal().getStart()), userBkg.getJadwal().formatJam(userBkg.getJadwal().getStart()), userBkg.getStation().getStationID());
        inputConfirmCycle: while(true) {
            System.out.println("Apakah anda yakin ingin membatalkan booking tersebut?");
            System.out.print("> Y/N: ");
            char userConfirm;

            try {
                userConfirm = sc.nextLine().toLowerCase().charAt(0);
            } catch (Exception e) {
                System.out.printf("\n%s%sSistem menerima input yang tidak valid. Harap masukkan input hanya Y atau N!%s\n",
                    AnsiColor.RED_BACKGROUND,
                    AnsiColor.WHITE_BOLD,
                    AnsiColor.RESET
                );
                continue inputConfirmCycle;
            }

            switch(userConfirm) {
                case 'y':
                    Database.daftarBookings.remove(Database.daftarBookings.indexOf(userBkg));
                    System.out.printf("\n%s%sBooking tersebut berhasil dibatalkan!%s\n", 
                        AnsiColor.GREEN_BACKGROUND,
                        AnsiColor.WHITE_BOLD,
                        AnsiColor.RESET
                    );
                    System.out.println("Anda bisa membuat booking baru jika diinginkan");
                    break inputConfirmCycle;
                case 'n':
                    break inputConfirmCycle;
            }
        }
    }

    public void giveFeedback() {
        System.out.printf("\n%sSilakan ketik pesan feedback di bawah ini: %s\n", AnsiColor.CYAN_BOLD, AnsiColor.RESET);
        System.out.printf("%sNB: Anda dapat juga melakukan request game yang Anda inginkan untuk bisa ditambahkan ke salah satu station di Corner Sense%s\n", AnsiColor.CYAN_BOLD, AnsiColor.RESET);
        System.out.printf("%3s> ", "");
        String feedbackMsg = sc.nextLine();

        Feedback userFeedback = new Feedback(this, feedbackMsg);
        Database.daftarFeedbacks.add(userFeedback);
        
        System.out.printf("\n%s%sFeedback yang Anda masukkan berhasil disimpan%s\n", 
            AnsiColor.GREEN_BACKGROUND,
            AnsiColor.WHITE_BOLD,
            AnsiColor.RESET
        );
        System.out.println("\nKami akan meneruskan feedback Anda ke Admin dan Developer untuk pengembangan layanan kami kedepannya\n");
    }

    public void showDeviceDetails() {
        inputCycle: while(true) {
            System.out.printf("\n%sDaftar Station: %s\n", AnsiColor.CYAN_BOLD, AnsiColor.RESET);
            for (Station sts : Database.daftarStation) {
                System.out.printf("%d. %s\n", sts.getStationID(), sts.getStationName());
            }
            System.out.print("Pilih Station Number yang ingin Anda lihat detailnya: ");
            int userSelection;

            try {
                userSelection = sc.nextInt();
                if (userSelection < 1 || userSelection > Database.daftarStation.size()) {
                    throw new Exception();
                }
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                System.out.printf("\n%s%sSistem menerima input yang tidak valid. Harap masukkan input sesuai dengan nomor station%s\n",
                    AnsiColor.RED_BACKGROUND,
                    AnsiColor.WHITE_BOLD,
                    AnsiColor.RESET
                );
                continue inputCycle;
            }

            Station selected = Database.daftarStation.get(userSelection - 1);
            selected.showDetailsStation();
            break inputCycle;
        }
    }

    // TODO: SYSTEM CORNER PASS & TAMBAHIN INPUT BUAT REQUEST GAME
    
    public void cornerPass() {
        inputCycle: while(true) {
            System.out.printf("Corner Pass Menu: \n%s\n%s\n%s\n", 
                "1. Subscribe to Corner Pass",
                "2. Lihat status subscription",
                "3. Kembali"
            );
            System.out.print("Input [1/2/3]: ");
            int userInput;

            try {
                userInput = sc.nextInt();
                if (userInput < 1 || userInput > 3) {
                    throw new Exception();
                }
                sc.nextLine();
            } catch (Exception e) {
                sc.nextLine();
                System.out.printf("\n%s%sSistem menerima input yang tidak valid. Harap masukkan input sesuai pada menu%s\n",
                    AnsiColor.RED_BACKGROUND,
                    AnsiColor.WHITE_BOLD,
                    AnsiColor.RESET
                );
                continue inputCycle;
            }

            switch (userInput) {
                case 1:
                    CornerPass cp = new CornerPass();
                    cornerPassInputChoiceCycle: while(true) {
                        cp.showPackageDetails();
                        System.out.printf("%d. Batal\n", paket.values().length  + 1);
                        System.out.print("\nMasukkan input paket Corner Pass yang Anda inginkan: ");
                        int userPckgChoice;

                        try {
                            userPckgChoice = sc.nextInt();
                            if (userPckgChoice < 1 || userPckgChoice > paket.values().length) {
                                throw new Exception();
                            }
                            sc.nextLine();
                        } catch (Exception e) {
                            sc.nextLine();
                            System.out.printf("\n%s%sSistem menerima input yang tidak valid. Harap masukkan input sesuai pada menu%s\n",
                                AnsiColor.RED_BACKGROUND,
                                AnsiColor.WHITE_BOLD,
                                AnsiColor.RESET
                            );
                            continue cornerPassInputChoiceCycle;
                        }
                        
                        paket selected = null;

                        switch(userPckgChoice) {
                            case 1:
                                selected = paket.Elite;
                                break;
                            case 2:
                                selected = paket.Master;
                                break;
                            case 3:
                                selected = paket.Legend;
                                break;
                            default:
                                break cornerPassInputChoiceCycle;
                        }

                        cp = new CornerPass(this, selected);

                        paymentChoiceCycle: while(true) {
                            System.out.printf("\n%sPilih metode pembayaran: %s\n", AnsiColor.WHITE_BOLD, AnsiColor.RESET);
                            System.out.printf("%s\n%s\n%s\n", "1. QRIS", "2. Kartu Kredit", "3. Kartu Debit");
                            System.out.print("Input [1/2/3]: ");
                            int userPymtChoice;

                            try {
                                userPymtChoice = sc.nextInt();
                                if (userPymtChoice < 1 || userPymtChoice > 3) {
                                    throw new Exception();
                                }
                                sc.nextLine();
                            } catch (Exception e) {
                                sc.nextLine();
                                System.out.printf("\n%s%sSistem menerima input yang tidak valid. Harap masukkan input sesuai pada menu%s\n",
                                    AnsiColor.RED_BACKGROUND,
                                    AnsiColor.WHITE_BOLD,
                                    AnsiColor.RESET
                                );
                                continue paymentChoiceCycle;
                            }

                            boolean isUserSuccessfullyPaid = false;

                            switch(userPymtChoice) {
                                case 1:
                                    isUserSuccessfullyPaid = cp.paymentInput(metodePembayaran.QRIS);
                                    cp.setSelectedPaymentMethod(metodePembayaran.QRIS);
                                    break;
                                case 2:
                                    isUserSuccessfullyPaid = cp.paymentInput(metodePembayaran.Kartu_Kredit);
                                    cp.setSelectedPaymentMethod(metodePembayaran.Kartu_Kredit);
                                    break;
                                case 3:
                                    isUserSuccessfullyPaid = cp.paymentInput(metodePembayaran.Kartu_Debit);
                                    cp.setSelectedPaymentMethod(metodePembayaran.Kartu_Debit);
                                    break;
                            }

                            if (isUserSuccessfullyPaid) {
                                cp.setStatusSubs(status.PAID);
                                Database.daftarPelangganCornerPass.add(cp);
                            }
                            break paymentChoiceCycle;
                        }
                        break cornerPassInputChoiceCycle;
                    }
                    break;
                // TODO: Case 2: Status Subscription
                case 2:
                    int counter = 0;
                    System.out.printf("\n%sDaftar riwayat subscription Anda%s\n", AnsiColor.CYAN_BOLD, AnsiColor.RESET);
                    for(CornerPass iterate : Database.daftarPelangganCornerPass) {
                        if (iterate.getUser().getUsername().equals(this.username)) {
                            System.out.printf("%d. Kode Pembayaran\t: %s\n", ++counter, iterate.getKodePembayaran());
                            System.out.printf("%3sPaket yang dipilih\t: %s\n", "", iterate.getSelectedPaket());
                            System.out.printf("%3sTanggal Subscription\t: %s\n", "", iterate.getSubsTimeStamp());
                            System.out.printf("%3sTanggal Expired\t: %s\n", "", iterate.getSubsExpiredTime());
                            System.out.printf("%3sStatus\t\t: %s\n", "", iterate.getStatusSubs());
                        }
                    }
                    if (counter == 0) {
                        System.out.printf("\n%s%sAnda belum pernah berlangganan Corner Pass!%s\n", AnsiColor.RED_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
                    }
                case 3 :
                    break inputCycle;
            }
        }
    }

    public boolean isUserSubscribed() {
        for(CornerPass cp : Database.daftarPelangganCornerPass) {
            if (cp.getUser().getUsername().equals(this.username) && cp.isApproved()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        return ((User)obj).getUsername().equals(this.getUsername());
    }
}
