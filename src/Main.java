import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        db.initializeDummyUserData();

        Scanner sc = new Scanner(System.in);

        mainMenuInputCycle: while(true) {
            System.out.println("\nSelamat datang di sistem CORNER SENSE");
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
                System.out.println("\nSistem menerima input yang tidak valid. Harap masukkan input yang sesuai!");
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
                    login.userAction();

                    if(login.getUsr().isStopUserInput()) {
                        break;
                    }
                    break;
                case 3:
                    break mainMenuInputCycle;
            }
        }

        sc.close();
    }
}
