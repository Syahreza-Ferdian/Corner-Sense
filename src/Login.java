import java.util.Scanner;

public class Login {
    private final Scanner sc = new Scanner(System.in);
    private String inputUsername;
    private String inputPassword;
    private int loginAttempts;
    private boolean isLoginSuccessfull;
    private User usr = new User("Guest");


    public User prompt() {
        loginAttempts = 1;
        System.out.println("======[LOGIN KE CORNER SENSE]======");
        while(true) {
            System.out.print("Username\t: ");
            inputUsername = sc.nextLine();
            System.out.print("Password\t: ");
            inputPassword = sc.nextLine();

            User userAttemptToLogin = new User(inputUsername, inputPassword);

            boolean isUsernameValid = Database.daftarUsers.contains(userAttemptToLogin);
            boolean isPasswordValid = isUsernameValid ? Database.daftarUsers.get(Database.daftarUsers.indexOf(userAttemptToLogin)).authenticatePass(inputPassword) : false;

            if(loginAttempts == 3 && (!isUsernameValid || !isPasswordValid)) {
                System.out.println("\nToo many failed login attempts");
                System.out.println("Pilih menu login kembali!");
                break;
            }

            if(!isUsernameValid) {
                System.out.println("\nUsername belum terdaftar atau username yang Anda masukkan salah");
                ++loginAttempts;
                continue;
            }

            if(!isPasswordValid) {
                System.out.println("\nPassword yang Anda masukkan salah!");
                ++loginAttempts;
                continue;
            }

            // BERHASIL MASUK KE SISTEM
            System.out.println("\nUSERINFO: Successfully logged in!");
            isLoginSuccessfull = true;
            usr = Database.daftarUsers.get(Database.daftarUsers.indexOf(userAttemptToLogin));
            break;
        }

        return usr;
    }

    public User getUsr() {
        return usr;
    }

    public void onUserLoginCallback() {
        this.usr = prompt();
    }

    public void userAction() {
        if(this.usr.getUsername().equals("Guest")) {
            this.usr.setStopUserInput(true);
        } else {
            usr.prompt();
        }
    }

    public boolean isLoginSuccessfull() {
        return isLoginSuccessfull;
    }
}