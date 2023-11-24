import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CornerPass {
    private final Scanner sc = new Scanner(System.in);
    private final int EXPIRE_DURATION = 14;
    private User user;
    private paket selectedPaket;
    private metodePembayaran selectedPaymentMethod;
    private String kodePembayaran;
    private Admin approvedBy;
    private String cardHolderName;
    private long cardNumber;
    private int ccv;
    private status statusSubs;
    private LocalDateTime subsTimeStamp;
    private LocalDateTime subsExpiredTime;
    private ScheduledExecutorService scheduler;


    public CornerPass() {

    }

    public CornerPass(User user, paket paket) {
        this.user = user;
        this.kodePembayaran = autoGenerateKodePembayaran();
        this.selectedPaket = paket;
        this.subsTimeStamp = LocalDateTime.now();
        this.subsExpiredTime = subsTimeStamp.plusDays(EXPIRE_DURATION);
        scheduleExpirationCheck();
    }

    public CornerPass(String kodePembayaran) {
        this.kodePembayaran = kodePembayaran;
    }

    private void scheduleExpirationCheck() {
        this.scheduler = Executors.newScheduledThreadPool(1);

        Runnable expirationCheckTask = () -> {
            checkExpiration();
        };

        this.scheduler.scheduleAtFixedRate(expirationCheckTask, 0, 24, TimeUnit.HOURS); //akan mengecek status expired setiap 24 jam
    }

    private void checkExpiration() {
        LocalDateTime skrg = LocalDateTime.now();

        if (skrg.isAfter(subsExpiredTime) || skrg.equals(subsExpiredTime)) {
            this.setStatusSubs(status.EXPIRED);
        }
    }

    public void shutdownScheduler() {
        if (this.scheduler != null) {
            this.scheduler.shutdown();
        }
    }

    public String autoGenerateKodePembayaran() {
        StringBuilder kodePembayaranSb = new StringBuilder();
        String possibleChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        final int PANJANG_KODE_PEMBAYARAN = 12;

        for (int i = 1; i <= PANJANG_KODE_PEMBAYARAN; i++) {
            int index = (int) (Math.random() * possibleChar.length());
            kodePembayaranSb.append(possibleChar.charAt(index));
        }

        if (!Database.daftarPelangganCornerPass.contains(new CornerPass(kodePembayaranSb.toString()))) {
            return kodePembayaranSb.toString();
        }

        return autoGenerateKodePembayaran();
    }

    public void setApprovedBy(Admin approvedBy) {
        this.approvedBy = approvedBy;
        this.setStatusSubs(status.VERIFIED);
    }

    public Admin getApprovedBy() {
        return approvedBy;
    }
    
    public void setSelectedPaymentMethod(metodePembayaran selectedPaymentMethod) {
        this.selectedPaymentMethod = selectedPaymentMethod;
    }

    public boolean isApproved() {
        return approvedBy != null;
    }

    public metodePembayaran getSelectedPaymentMethod() {
        return selectedPaymentMethod;
    }

    public User getUser() {
        return user;
    }

    public paket getSelectedPaket() {
        return selectedPaket;
    }

    public void setSelectedPaket(paket selectedPaket) {
        this.selectedPaket = selectedPaket;
    }

    public int getCcv() {
        return ccv;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setStatusSubs(status statusSubs) {
        this.statusSubs = statusSubs;
    }

    public status getStatusSubs() {
        return statusSubs;
    }

    public String getKodePembayaran() {
        return kodePembayaran;
    }

    public String getSubsTimeStamp() {
        return String.format("%02d-%02d-%d %02d:%02d:%02d", subsTimeStamp.getDayOfMonth(), subsTimeStamp.getMonthValue(), subsTimeStamp.getYear(), subsTimeStamp.getHour(), subsTimeStamp.getMinute(), subsTimeStamp.getSecond());
    }

    public String getSubsExpiredTime() {
        return String.format("%02d-%02d-%d %02d:%02d:%02d", subsExpiredTime.getDayOfMonth(), subsExpiredTime.getMonthValue(), subsExpiredTime.getYear(), subsExpiredTime.getHour(), subsExpiredTime.getMinute(), subsExpiredTime.getSecond());
    }

    public void showPackageDetails() {
        System.out.printf("\n%sDaftar paket Corner Pass yang tersedia: %s\n", AnsiColor.WHITE_BOLD, AnsiColor.RESET);
        
        for (int i = 0; i < paket.values().length; i++) {
            System.out.printf("%d. %s%s%s\n", i+1, AnsiColor.CYAN_BOLD, paket.values()[i], AnsiColor.RESET);
            System.out.printf("%4s %s\n", ">", paket.values()[i].getPaketDesc());
            System.out.printf("%4s %sHarga: Rp %,.2f%s\n", ">", AnsiColor.CYAN_BOLD, paket.values()[i].getHarga(), AnsiColor.RESET);
        }
    }

    public boolean paymentInput(metodePembayaran payment) {
        boolean isUserPaid = false;
        if (payment == metodePembayaran.QRIS) {
            System.out.printf("\n =======================%4s%s>> Detail Pembayaran <<%s\n", "", AnsiColor.CYAN_BOLD, AnsiColor.RESET);
            System.out.printf("|\t\t\t|\n");
            System.out.printf("|\t\t\t|%3sKode Pembayaran\t: %s\n", "", this.kodePembayaran);
            System.out.printf("|\t\t\t|%3sPembayaran\t\t: %s\n", "", "Subscription");
            System.out.printf("|\tQR CODE\t\t|%3sJenis\t\t: %s\n", "", this.selectedPaket);
            System.out.printf("|\t  QRIS\t\t|%3sTotal Bayar\t\t: Rp %,.2f\n", "", this.selectedPaket.getHarga());
            System.out.printf("|\t\t\t|\n");
            System.out.printf("|\t\t\t|\n");
            System.out.printf("|\t\t\t|\n");
            System.out.printf(" =======================\n");
            System.out.println("Scan QR Code tersebut menggunakan aplikasi QRIS Anda");
            System.out.print("Is scanned? Y/N: ");
            char input = sc.nextLine().toLowerCase().charAt(0);
            switch (input) {
                case 'y':
                    System.out.printf("\n%s%sPembayaran berhasil! Silakan menunggu verifikasi pembayaran dari Admin%s\n",
                        AnsiColor.GREEN_BACKGROUND, 
                        AnsiColor.WHITE_BOLD,
                        AnsiColor.RESET
                    );
                    isUserPaid = true;
                    break;
                case 'n':
                    System.out.printf("\n%s%sPembayaran gagal! Anda dapat mengulang pembayaran atau pilih metode pembayaran lain!%\n",
                        AnsiColor.RED_BACKGROUND, 
                        AnsiColor.WHITE_BOLD,
                        AnsiColor.RESET
                    );
                    break;
                default:
                    System.out.printf("\n%s%sERROR: Input invalid%\n",
                        AnsiColor.RED_BACKGROUND, 
                        AnsiColor.WHITE_BOLD,
                        AnsiColor.RESET
                    );
                    break;
            }
        } else if (payment == metodePembayaran.Kartu_Debit || payment == metodePembayaran.Kartu_Kredit) {
            inputCycle: while(true) {
                try {
                    System.out.printf("\nMasukkan Nama Pemegang Kartu\t: ");
                    cardHolderName = sc.nextLine();
                    System.out.printf("Card Number\t\t\t: ");
                    cardNumber = sc.nextLong();
                    
                    System.out.printf("CCV\t\t\t\t: ");
                    ccv = sc.nextInt();
                    sc.nextLine();
                } catch (Exception e) {
                    sc.nextLine();
                    System.out.printf("\n%s%sSistem mendeteksi input yang tidak valid. Harap masukkan input sesuai dengan informasi kartu Anda!%s\n",
                        AnsiColor.RED_BACKGROUND,
                        AnsiColor.WHITE_BOLD,
                        AnsiColor.RESET
                    );
                    continue inputCycle;
                }
                inputConfirm: while(true) {
                    System.out.printf("Lanjutkan pembayaran? Y/N: ");
                    char confirm;
                    confirm = sc.nextLine().toLowerCase().charAt(0);
                    
                    switch(confirm) {
                        case 'y':
                            System.out.printf("\n%s>> Detail Pembayaran <<%s\n", AnsiColor.CYAN_BOLD, AnsiColor.RESET);
                            System.out.printf("Kode Pembayaran\t\t: %s\n", this.kodePembayaran);
                            System.out.printf("Pembayaran\t\t: %s\n", "Subscription");
                            System.out.printf("Paket yang dipilih\t: %s\n", this.selectedPaket);
                            System.out.printf("Total Bayar\t\t: Rp %,.2f\n", this.selectedPaket.getHarga());
                            System.out.printf("\nAmount Rp %,.2f will be charged to your Credit/Debit card once Corner Pass Admin has confirm this transaction\n", this.selectedPaket.getHarga());
                            isUserPaid = true;
                            break inputCycle;
                        case 'n':
                            System.out.printf("\n%s%sAnda membatalkan pembayaran%s\n", AnsiColor.YELLOW_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
                            break inputCycle;
                        default :
                            System.out.printf("\n%s%sInput invalid. Harap inputkan hanya Y atau N!%s\n", AnsiColor.RED_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
                            continue inputConfirm;
                    }
                }
            }
        }

        return isUserPaid;
    }

    @Override
    public boolean equals(Object obj) {
        return ((CornerPass)obj).kodePembayaran.equals(this.kodePembayaran);
    }
}

enum paket {
    Elite ("Mendapatkan satu kali booking kesempatan selama 14 hari kedepan", 4900.0),
    Master ("Mendapatkan dua kali booking kesempatan selama 14 hari kedepan", 8900.0),
    Legend ("Mendapatkan tiga kali booking kesempatan selama 14 hari kedepan", 12900.0);

    private String paketDesc;
    private double harga;

    private paket(String paketDesc, Double harga) {
        this.paketDesc = paketDesc;
        this.harga = harga;
    }

    public double getHarga() {
        return harga;
    }
    public String getPaketDesc() {
        return paketDesc;
    }
}

enum metodePembayaran {
    QRIS,
    Kartu_Kredit,
    Kartu_Debit
}

enum status {
    PAID,
    VERIFIED,
    EXPIRED
}
