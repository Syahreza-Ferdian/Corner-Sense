import java.util.Scanner;

@SuppressWarnings("unused")

public class Main {
    public static void main(String[] args) {
        Database db = new Database();

        Scanner sc = new Scanner(System.in);

        mainMenuInputCycle: while(true) {
            System.out.printf("\n%sSelamat datang di sistem %sCORNER SENSE%s\n", AnsiColor.YELLOW, AnsiColor.WHITE_BOLD, AnsiColor.RESET);
            System.out.println("Pilih Action: ");
            System.out.printf("%s\n%s\n%s\n", 
                "1. Register",
                "2. Login",
                "3. Keluar dari sistem"
            );

            int userMainMenuChoice;
            System.out.print("Input [1/2/3]: ");

            try {
                userMainMenuChoice = sc.nextInt();
                if(userMainMenuChoice < 1 || userMainMenuChoice > 3) {
                    throw new Exception();
                }
                sc.nextLine();
            } catch(Exception e) {
                sc.nextLine();
                System.out.printf("\n%s%sSistem menerima input yang tidak valid. Harap masukkan input yang sesuai!%s\n",
                    AnsiColor.RED_BACKGROUND, 
                    AnsiColor.WHITE_BOLD,
                    AnsiColor.RESET
                );
                continue mainMenuInputCycle;
            }

            switch (userMainMenuChoice) {
                case 1:
                    Register rgs = new Register();
                    rgs.onUserRegisterCallback();
                    break;
                case 2: 
                    Login login = new Login();
                    login.onUserLoginCallback();
                    login.onUserLoggedInCallback();

                    if(login.getUsr().isStopUserInput()) {
                        break;
                    }
                    break;
                case 3:
                    System.out.printf("\n%s%sProgram dihentikan.. Terima kasih telah menggunakan layanan kami :)%s\n", 
                        AnsiColor.YELLOW_BACKGROUND,
                        AnsiColor.WHITE_BOLD,
                        AnsiColor.RESET
                    );
                    break mainMenuInputCycle;
            }
        }

        sc.close();
    }
}
