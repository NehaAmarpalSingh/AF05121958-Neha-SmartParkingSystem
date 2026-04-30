package parking;

import java.sql.*;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.Duration;

public class ParkingDAO {

    Scanner sc = new Scanner(System.in);

    public void addVehicle() {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter User ID: ");
            int uid = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Vehicle Number: ");
            String no = sc.nextLine();

            System.out.print("Enter Vehicle Type: ");
            String type = sc.nextLine();

            String sql = "insert into vehicles(user_id,vehicle_no,type) values(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setString(2, no);
            ps.setString(3, type);

            ps.executeUpdate();

            System.out.println("Vehicle Added");

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void showSlots() {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "select * from slots where status='Available'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.println("\nAvailable Slots:");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "  " + rs.getString(2));
            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void bookSlot() {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Vehicle Number: ");
            String vno = sc.nextLine();

            System.out.print("Enter Slot Number: ");
            String sno = sc.nextLine();

            String time = LocalDateTime.now().toString();

            String sql = "insert into bookings(vehicle_no,slot_no,entry_time,exit_time,fee) values(?,?,?,null,0)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, vno);
            ps.setString(2, sno);
            ps.setString(3, time);

            ps.executeUpdate();

            String sql2 = "update slots set status='Booked' where slot_no=?";
            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setString(1, sno);
            ps2.executeUpdate();

            System.out.println("Slot Booked");

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void vehicleExit() {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Vehicle Number: ");
            String vno = sc.nextLine();

            String sql = "select * from bookings where vehicle_no=? and exit_time is null";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, vno);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String slot = rs.getString("slot_no");
                String entry = rs.getString("entry_time");

                LocalDateTime in = LocalDateTime.parse(entry);
                LocalDateTime out = LocalDateTime.now();

                long hours = Duration.between(in, out).toHours();

                if (hours == 0)
                    hours = 1;

                double fee = hours * 20;

                String sql2 = "update bookings set exit_time=?, fee=? where vehicle_no=? and exit_time is null";
                PreparedStatement ps2 = con.prepareStatement(sql2);

                ps2.setString(1, out.toString());
                ps2.setDouble(2, fee);
                ps2.setString(3, vno);

                ps2.executeUpdate();

                String sql3 = "update slots set status='Available' where slot_no=?";
                PreparedStatement ps3 = con.prepareStatement(sql3);
                ps3.setString(1, slot);
                ps3.executeUpdate();

                System.out.println("Vehicle Exit Done");
                System.out.println("Parking Fee = " + fee);

            } else {
                System.out.println("No Active Booking Found");
            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void searchBooking() {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Vehicle Number: ");
            String vno = sc.nextLine();

            String sql = "select * from bookings where vehicle_no=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, vno);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("ID : " + rs.getInt(1));
                System.out.println("Vehicle : " + rs.getString(2));
                System.out.println("Slot : " + rs.getString(3));
                System.out.println("Entry : " + rs.getString(4));
                System.out.println("Exit : " + rs.getString(5));
                System.out.println("Fee : " + rs.getDouble(6));
                System.out.println("-------------------");
            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void cancelBooking() {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Vehicle Number: ");
            String vno = sc.nextLine();

            String slot = "";

            String q1 = "select slot_no from bookings where vehicle_no=? and exit_time is null";
            PreparedStatement p1 = con.prepareStatement(q1);
            p1.setString(1, vno);

            ResultSet rs = p1.executeQuery();

            if (rs.next()) {
                slot = rs.getString(1);
            }

            String sql = "delete from bookings where vehicle_no=? and exit_time is null";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, vno);

            ps.executeUpdate();

            String sql2 = "update slots set status='Available' where slot_no=?";
            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setString(1, slot);
            ps2.executeUpdate();

            System.out.println("Booking Cancelled");

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addSlot() {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Slot Number: ");
            String slot = sc.nextLine();

            String sql = "insert into slots(slot_no,status) values(?,'Available')";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, slot);
            ps.executeUpdate();

            System.out.println("Slot Added");

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateSlot() {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Old Slot Number: ");
            String oldslot = sc.nextLine();

            System.out.print("Enter New Slot Number: ");
            String newslot = sc.nextLine();

            String sql = "update slots set slot_no=? where slot_no=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, newslot);
            ps.setString(2, oldslot);

            ps.executeUpdate();

            System.out.println("Slot Updated");

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteSlot() {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Slot Number: ");
            String slot = sc.nextLine();

            String sql = "delete from slots where slot_no=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, slot);
            ps.executeUpdate();

            System.out.println("Slot Deleted");

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}