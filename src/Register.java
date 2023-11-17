import java.util.Scanner;

public class Register {
    private final Scanner sc = new Scanner(System.in);
    private String inputUsername;
    private String inputPassword;
    private String inputNim;
    private String inputEmail;


    public void prompt() {
        System.out.println("======[REGISTRATION FORM]======");
        System.out.println("Silakan masukkan data berikut ini untuk membuat akun baru");

        while(true) {
            System.out.print("Username Anda\t\t: ");
            inputUsername = sc.nextLine();

            boolean isUsernameNotAvail = Database.daftarUsers.contains(new User(inputUsername));
            
            if (isUsernameNotAvail) {
                System.out.println("\nUsername telah digunakan. Masukkan username lain!\n");
                continue;
            }
            break;
        }

        System.out.print("Password\t\t: ");
        inputPassword = sc.nextLine();

        while(true) {
            System.out.print("Konfirmasi Password\t: ");
            String passConfirm = sc.nextLine();

            if(passConfirm.equals(inputPassword)) {
                break;
            }
            System.out.println("\nKonfirmasi password tidak sesuai, silakan masukkan konfirmasi password kembali!\n");
            continue;
        }

        User newlyRegistered = new User(inputUsername, inputPassword);
        // newlyRegistered.setEmail(inputEmail);
        // newlyRegistered.setNIM(inputNim);

        Database.daftarUsers.add(newlyRegistered);
    }

    public void onUserRegisterCallback() {
        prompt();
    }
}
