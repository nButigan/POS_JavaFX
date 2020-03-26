package sample.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableIntegerArray;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import utils.ConnectionUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Racun {
    private int id;
    private Date datum;
    private int korisnik;

    public Racun(int id, Date datum, int korisnik) {
        this.id = id;
        this.datum = datum;
        this.korisnik = korisnik;
    }

    public Racun(Date datum, int korisnik){
        this.datum=datum;
        this.korisnik=korisnik;
    }

    public Racun(int id){
        this.id=id;
    }

    public Racun(){}

    public int getId() {
        return id;
    }

    public Date getDatum() {
        return datum;
    }

    public int getKorisnik() {
        return korisnik;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public void setKorisnik(int korisnik) {
        this.korisnik = korisnik;
    }

    public static Racun add(Racun racun) {
     try {
            PreparedStatement stmnt = ConnectionUtil.conDB().prepareStatement("INSERT INTO racun VALUES (null, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            stmnt.setDate(1, racun.getDatum());
            stmnt.setInt(2, racun.getKorisnik());
            stmnt.executeUpdate();

            ResultSet rs = stmnt.getGeneratedKeys();
            if (rs.next()) {
                racun.setId(rs.getInt(1));
            }
            return racun;
        } catch (SQLException e) {
         System.out.println(racun.getKorisnik());
            System.out.println("Nisam uspio dodati račun: " + e.getMessage());
            return null;
        }
    }

    public static List<Racun> readAll(){
        ObservableList<Racun> racuns = FXCollections.observableArrayList();
        try {
            Statement stmnt = ConnectionUtil.conDB().createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM racun");

            while(rs.next()){
                racuns.add(new Racun(
                        rs.getInt(1), //id
                        rs.getDate(2), //datum
                        rs.getInt(3) //korisnik_fk
                ));
            }
            return racuns;
        } catch (SQLException e) {
            System.out.println("Nisam uspio dohvatiti račune iz baze: " + e.getMessage());
            return null;
        }
    }

    public static boolean remove(int r){
        try {
            PreparedStatement stmnt = ConnectionUtil.conDB().prepareStatement("DELETE  FROM racun WHERE id=?");
            stmnt.setInt(1, r);
            stmnt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Nisam uspio stornirati račun: " + e.getMessage());
            return false;
        }
    }

}
