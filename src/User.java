import java.util.Scanner;

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
        final int MAX_POSSIBLE_USER_INPUT = 6;

        inputCycle: while (true) {
            System.out.println("\n=============================================");
            System.out.printf("Selamat datang %s\n", this.getUsername());
            System.out.println("\nMenu Corner Sense: ");
            System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n",
                "1. Lihat Jadwal",
                "2. Booking Online",
                "3. Lihat detail device",
                "4. Cancel Booking",
                "5. Kritik dan Saran",
                "6. Logout"
            );
            System.out.print("Input [1/2/3/4/5]: ");
            
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
                    Jadwal jdw = new Jadwal();
                    jdw.showJadwal();
                    break;
                case MAX_POSSIBLE_USER_INPUT:
                    this.stopUserInput = true;
                    break inputCycle;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return ((User)obj).getUsername().equals(this.getUsername());
    }
}
