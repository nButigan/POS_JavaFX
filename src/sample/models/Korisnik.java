package sample.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.ConnectionUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Korisnik {

   private int ID;
   private String ime;
   private String prezime;
   private String korisnickoIme;
   private int uloga;
   private String sifra;

    public Korisnik(int ID, String ime, String prezime, String korisnickoIme, String sifra) {
        this.ID = ID;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.uloga = 0;
        this.sifra = sifra;
    }

    public Korisnik(String korisnickoIme, int uloga){
        this.korisnickoIme = korisnickoIme;
        this.uloga = uloga;
    }

    public int getID() {
        return ID;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public int getUloga() {
        return uloga;
    }

    public String getSifra() {
        return sifra;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public void setUloga(int uloga) {
        this.uloga = uloga;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public static boolean add(Korisnik noviKorisnik){
        try{
            PreparedStatement stmnt = ConnectionUtil.conDB().prepareStatement("INSERT INTO korisnik VALUES (null, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            stmnt.setString(1, noviKorisnik.getIme());
            stmnt.setString(2, noviKorisnik.getPrezime());
            stmnt.setString(3, noviKorisnik.getKorisnickoIme());
            stmnt.setInt(4, noviKorisnik.getUloga());
            stmnt.setString(5, noviKorisnik.getSifra());
            stmnt.executeUpdate();


            return true;

        } catch (SQLException e){
            System.out.println("Neuspješno dodavanje korisnika: " + e.getMessage());
            return false;
        }
    }

    public static boolean update(Korisnik postojeciKorisnik){
        try {
            PreparedStatement stmnt = ConnectionUtil.conDB().prepareStatement("UPDATE korisnik SET ime=?, prezime=?, korisnickoIme=?, uloga=?, sifra=?,  WHERE id=?");
            stmnt.setString(1, postojeciKorisnik.getIme());
            stmnt.setString(2, postojeciKorisnik.getPrezime());
            stmnt.setString(3, postojeciKorisnik.getKorisnickoIme());
            stmnt.setInt(4, postojeciKorisnik.getUloga());
            stmnt.setString(5, postojeciKorisnik.getSifra());
            stmnt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Neuspješno uređivanje korisnika: " + e.getMessage());
            return false;
        }

    }

    public static void remove(Korisnik postojeciKorisnik){

    }

    public static List<Korisnik> readAll(){
        ObservableList<Korisnik> users = FXCollections.observableArrayList();
        try {
            Statement stmnt = ConnectionUtil.conDB().createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM korisnik");


            while(rs.next()){
                users.add(new Korisnik(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                ));
            }
            return users;
        } catch (SQLException e) {
            System.out.println("Neuspješno čitanje korisnika iz baze: " + e.getMessage());
            return users;
        }
    }
}
