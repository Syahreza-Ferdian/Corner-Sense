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
                System.out.printf("\n%s%sToo many failed login attempts%s\n", AnsiColor.RED_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
                System.out.println("Pilih menu login kembali!");
                break;
            }

            if(!isUsernameValid) {
                System.out.printf("\n%s%sUsername belum terdaftar atau username yang Anda masukkan salah!%s\n", AnsiColor.RED_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
                ++loginAttempts;
                continue;
            }

            if(!isPasswordValid) {
                System.out.printf("\n%s%sPassword yang Anda masukkan salah!%s\n", AnsiColor.RED_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
                ++loginAttempts;
                continue;
            }

            // BERHASIL MASUK KE SISTEM
            System.out.printf("\n%s%sUSERINFO: Successfully logged in!%s\n", AnsiColor.GREEN_BACKGROUND, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
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

    public void onUserLoggedInCallback() {
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