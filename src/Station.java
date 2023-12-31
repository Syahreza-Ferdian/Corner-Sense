import java.util.LinkedList;

public class Station {
    private int stationID;
    private String stationName;
    private String stationDesc[];
    private LinkedList<String> gameLists = new LinkedList<>();
    private User usedBy;

    public Station(int stationID, String stationName) {
        this.stationID = stationID;
        this.stationName = stationName;

        if (this.stationName.equals("PC")) {
            stationDesc = new String[6];
        } else {
            stationDesc = new String[5];
        }
    }

    public Station(int stationID) {
        this.stationID = stationID;
    }

    public boolean isOccupied() {
        return !(usedBy == null);
    }

    public int getStationID() {
        return stationID;
    }

    public void setDescPC(String proc, String RAM, String GPU, String monitor, String keyboard, String mouse) {
        stationDesc[0] = proc;
        stationDesc[1] = RAM;
        stationDesc[2] = GPU;
        stationDesc[3] = monitor;
        stationDesc[4] = keyboard;
        stationDesc[5] = mouse;
    }

    public void setDesc(String proc, String RAM, String GPU, String monitor, String controller) {
        stationDesc[0] = proc;
        stationDesc[1] = RAM;
        stationDesc[2] = GPU;
        stationDesc[3] = monitor;
        stationDesc[4] = controller;
    }

    public void setGameLists(String... games) {
        for(String str : games) {
            gameLists.add(str);
        }
    }

    public String[] getStationDesc() {
        return stationDesc;
    }

    public void setUsedBy(User usedBy) {
        this.usedBy = usedBy;
    }

    public String getStationName() {
        return stationName;
    }

    public void showDetailsStation() {
        if (this.stationName.equals("PC")) {
            System.out.printf("\n =======================%4s%s>> DETAIL Station <<%s\n", "", AnsiColor.CYAN_BOLD, AnsiColor.RESET);
            System.out.printf("|\t\t\t|\n");
            System.out.printf("|\t\t\t|%3sStation Number\t: %d\n", "", this.stationID);
            System.out.printf("|\t\t\t|%3sJenis\t\t: %s\n", "", this.stationName);
            System.out.printf("|\t Foto\t\t|%3sDescription \n", "");
            System.out.printf("|\tStation\t\t|%6s Processor\t: %s\n", ">", this.stationDesc[0]);
            System.out.printf("|\t\t\t|%6s RAM\t\t: %s\n", ">", this.stationDesc[1]);
            System.out.printf("|\t\t\t|%6s GPU\t\t: %s\n", ">", this.stationDesc[2]);
            System.out.printf("|\t\t\t|%6s Monitor\t\t: %s\n", ">", this.stationDesc[3]);
            System.out.printf(" =======================%7s Keyboard\t: %s\n", ">", this.stationDesc[4]);
            System.out.printf("%31s Mouse\t\t: %s\n", ">", this.stationDesc[5]);
            System.out.printf("%28sGame Lists\n", "");

            if (this.gameLists.isEmpty()) {
                System.out.printf("%31s Belum ada game di station ini\n", "-");
                return;
            }

            for(String str : this.gameLists) {
                System.out.printf("%31s %s\n", "-", str);
            }
        } else {
            System.out.printf("\n =======================%4s%s>> DETAIL Station <<%s\n", "", AnsiColor.CYAN_BOLD, AnsiColor.RESET);
            System.out.printf("|\t\t\t|\n");
            System.out.printf("|\t\t\t|%3sStation Number\t: %d\n", "", this.stationID);
            System.out.printf("|\t\t\t|%3sJenis\t\t: %s\n", "", this.stationName);
            System.out.printf("|\t Foto\t\t|%3sDevice Specs \n", "");
            System.out.printf("|\tStation\t\t|%6s Processor\t: %s\n", ">", this.stationDesc[0]);
            System.out.printf("|\t\t\t|%6s RAM\t\t: %s\n", ">", this.stationDesc[1]);
            System.out.printf("|\t\t\t|%6s GPU\t\t: %s\n", ">", this.stationDesc[2]);
            System.out.printf("|\t\t\t|%3sPeripherals\n", "");
            System.out.printf(" =======================%7s Monitor\t\t: %s\n", ">", this.stationDesc[3]);
            System.out.printf("%31s Controller\t: %s\n", ">", this.stationDesc[4]);
            System.out.printf("%28sGame Lists\n", "");

            if (this.gameLists.isEmpty()) {
                System.out.printf("%31s Belum ada game di station ini\n", "-");
                return;
            }

            for(String str : this.gameLists) {
                System.out.printf("%31s %s\n", "-", str);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return ((Station)obj).getStationID() == this.getStationID();
    }
}