public class Station {
    private int stationID;
    private String stationName;
    private String stationDesc;
    private User usedBy;

    public Station(int stationID, String stationName) {
        this.stationID = stationID;
        this.stationName = stationName;
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

    public void setStationDesc(String stationDesc) {
        this.stationDesc = stationDesc;
    }

    public String getStationDesc() {
        return stationDesc;
    }

    public void setUsedBy(User usedBy) {
        this.usedBy = usedBy;
    }

    public String getStationName() {
        return stationName;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Station)obj).getStationID() == this.getStationID();
    }
}