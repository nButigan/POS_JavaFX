package sample.models;

import utils.ConnectionUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Racun {
    private int id;
    private Date datum;
    private int korisnik;


  /*  public static Racun add(Racun b) {
       *//* try {
            PreparedStatement stmnt = ConnectionUtil.conDB().prepareStatement("INSERT INTO racun VALUES (null, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            stmnt.setDate(1, b.getDate());
            stmnt.setString(2, b.getNumber());
            stmnt.setInt(3, b.getUser());
            stmnt.executeUpdate();

            ResultSet rs = stmnt.getGeneratedKeys();
            if (rs.next()) {
                b.setID(rs.getInt(1));
            }
            return b;
        } catch (SQLException e) {
            System.out.println("Nisam uspio dodati račun: " + e.getMessage());
            return null;
        }*//*
    }*/
}
