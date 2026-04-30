package parking;

public class Booking {

    int id;
    String vehicleNo;
    String slotNo;
    String entryTime;
    String exitTime;
    double fee;

    public Booking() {
    }

    public Booking(int id, String vehicleNo, String slotNo,
                   String entryTime, String exitTime, double fee) {
        this.id = id;
        this.vehicleNo = vehicleNo;
        this.slotNo = slotNo;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.fee = fee;
    }

    public int getId() {
        return id;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public String getSlotNo() {
        return slotNo;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public String getExitTime() {
        return exitTime;
    }

    public double getFee() {
        return fee;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public void setSlotNo(String slotNo) {
        this.slotNo = slotNo;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}