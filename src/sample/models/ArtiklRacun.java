package sample.models;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import utils.ConnectionUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArtiklRacun {
    private int id;
    private int IDracuna;
    private int IDartikla;
    private int kolicina;


    public ArtiklRacun(int id, int IDracuna, int IDartikla, int kolicina) {
        this.id = id;
        this.IDracuna = IDracuna;
        this.IDartikla = IDartikla;
        this.kolicina = kolicina;
    }

    public int getId() {
        return id;
    }

    public int getIDracuna() {
        return IDracuna;
    }

    public int getIDartikla() {
        return IDartikla;
    }

    public int getKolicina() {
        return kolicina;
    }

    public static boolean add(ArtiklRacun artiklRacun){
        try{
            PreparedStatement stmnt = ConnectionUtil.conDB().prepareStatement("INSERT INTO artikl_racun VALUES (null, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            stmnt.setInt(1, artiklRacun.getIDracuna());
            stmnt.setInt(2, artiklRacun.getIDartikla());
            stmnt.setInt(3, artiklRacun.getKolicina());
            stmnt.executeUpdate();


            return true;

        } catch (SQLException e){
            System.out.println("Neuspješno dodavanje računa: " + e.getMessage());
            return false;
        }
    }

    public static int getArtiklId(String nazivArtikla){
        int id=0;
        try {
            Statement stmnt = ConnectionUtil.conDB().createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT id FROM artikl WHERE naziv='" + nazivArtikla + "'");
            while(rs.next()){
                id=rs.getInt("id");
            }
            return id;
        } catch (SQLException e) {
            System.out.println("Nisam uspio dohvatit id artikla iz baze: " + e.getMessage());
            return -1;
        }
    }

    public static boolean remove(int r){
        try {
            PreparedStatement stmnt = ConnectionUtil.conDB().prepareStatement("DELETE  FROM artikl_racun WHERE racun_fk=?");
            stmnt.setInt(1, r);
            stmnt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Nisam uspio stornirati račun: " + e.getMessage());
            return false;
        }
    }
}
