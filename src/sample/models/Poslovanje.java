package sample.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import utils.ConnectionUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Poslovanje {
    private String nazivArtikla;
    private int kolicina;
    private Date datum;

    public Poslovanje(String nazivArtikla, int kolicina, Date datum) {
        this.nazivArtikla = nazivArtikla;
        this.kolicina = kolicina;
        this.datum = datum;
    }

    public String getNazivArtikla() {
        return nazivArtikla;
    }

    public int getKolicina() {
        return kolicina;
    }

    public Date getDatum() {
        return datum;
    }

    public static List<Poslovanje> dohvatiDanasnjeRacune(){
        Date danasnjiDatum = new Date(System.currentTimeMillis());
        ObservableList<Poslovanje> informacije = FXCollections.observableArrayList();
        try {
            Statement stmnt = ConnectionUtil.conDB().createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT artikl.naziv, SUM(artikl_racun.kolicina) AS ukupno, racun.datum " +
                                                    "FROM artikl " +
                                                    "INNER JOIN artikl_racun ON artikl.id=artikl_racun.artikl_fk " +
                                                    "INNER JOIN racun ON artikl_racun.racun_fk=racun.id " +
                                                    "WHERE racun.datum='" + danasnjiDatum +
                                                    "' GROUP BY artikl.naziv");

            while(rs.next()){
                    informacije.add(new Poslovanje(
                            rs.getString(1),
                            rs.getInt(2),
                            rs.getDate(3)
                    ));
            }
            return informacije;
        } catch (SQLException e) {
            System.out.println("Nisam uspio dohvatiti današnje račune iz baze: " + e.getMessage());
            return null;
        }
    }

    public static List<Poslovanje> dohvatiRacunePosljednjih7dana(){
        //Date danasnjiDatum = new Date(System.currentTimeMillis());
        ObservableList<Poslovanje> informacije = FXCollections.observableArrayList();

        try {
            Statement stmnt = ConnectionUtil.conDB().createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT artikl.naziv, SUM(artikl_racun.kolicina) AS ukupno, racun.datum " +
                                                    "FROM artikl " +
                                                    "INNER JOIN artikl_racun ON artikl.id=artikl_racun.artikl_fk " +
                                                    "INNER JOIN racun ON artikl_racun.racun_fk=racun.id " +
                                                    "WHERE racun.datum BETWEEN DATE_SUB(now(), INTERVAL 1 WEEK) and now() "+
                                                    "GROUP BY artikl.naziv");

            while(rs.next()){
                informacije.add(new Poslovanje(
                        rs.getString(1),
                        rs.getInt(2),
                        rs.getDate(3)
                ));
            }
            return informacije;
        } catch (SQLException e) {
            System.out.println("Nisam uspio dohvatiti račune od posljednjih 7 dana iz baze: " + e.getMessage());
            return null;
        }
    }

    public static List<Poslovanje> dohvatiRacunePosljednjih30dana(){
        Date danasnjiDatum = new Date(System.currentTimeMillis());
        ObservableList<Poslovanje> informacije = FXCollections.observableArrayList();

        try {
            Statement stmnt = ConnectionUtil.conDB().createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT artikl.naziv, SUM(artikl_racun.kolicina) AS ukupno, racun.datum " +
                                                    "FROM artikl " +
                                                    "INNER JOIN artikl_racun ON artikl.id=artikl_racun.artikl_fk " +
                                                    "INNER JOIN racun ON artikl_racun.racun_fk=racun.id " +
                                                    "WHERE racun.datum BETWEEN DATE_SUB(now(), INTERVAL 1 MONTH) and now() " +
                                                    "GROUP BY artikl.naziv");

            while(rs.next()){
                informacije.add(new Poslovanje(
                        rs.getString(1),
                        rs.getInt(2),
                        rs.getDate(3)
                ));
            }
            return informacije;
        } catch (SQLException e) {
            System.out.println("Nisam uspio dohvatiti račune od posljednjih 30 dana iz baze: " + e.getMessage());
            return null;
        }
    }
}
