package parking;

import java.util.Scanner;
import java.sql.*;

public class Login {

    Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n===== SMART PARKING SYSTEM =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1:
                    register();
                    break;

                case 2:
                    login();
                    break;

                case 3:
                    System.out.println("Thank You");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    public void register() {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Username: ");
            String user = sc.nextLine();

            System.out.print("Enter Password: ");
            String pass = sc.nextLine();

            String sql = "insert into users(name,username,password) values(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, user);
            ps.setString(3, pass);

            int i = ps.executeUpdate();

            if (i > 0)
                System.out.println("Registration Successful");
            else
                System.out.println("Failed");

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void login() {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Username: ");
            String user = sc.nextLine();

            System.out.print("Enter Password: ");
            String pass = sc.nextLine();

            String sql = "select * from users where username=? and password=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Login Successful");
                Dashboard d = new Dashboard();
                d.menu();
            } else {
                System.out.println("Invalid Username or Password");
            }

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}