package sample.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import utils.ConnectionUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Artikl {
    private int id;
    private String naziv;
    private String dobavljac;
    private float cijena;
    private Image slika;
    private String kategorija;
    private int kolicina;

    public Artikl(int id, String naziv, String dobavljac, float cijena, Image slika, String kategorija, int kolicina) {
        this.id = id;
        this.naziv = naziv;
        this.dobavljac = dobavljac;
        this.cijena = cijena;
        this.slika = slika;
        this.kategorija = kategorija;
        this.kolicina=kolicina;
    }

    public Artikl(String naziv) {
        this.naziv=naziv;
    }

    public Artikl(int kolicina, int id) {
        this.kolicina=kolicina;
        this.id=id;
    }

    public Artikl(String name, Integer quantity) {
        this.naziv=name;
        this.kolicina=quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getDobavljac() {
        return dobavljac;
    }

    public void setDobavljac(String dobavljac) {
        this.dobavljac = dobavljac;
    }

    public float getCijena() {
        return cijena;
    }

    public void setCijena(float cijena) {
        this.cijena = cijena;
    }

    public Image getSlika() {
        return slika;
    }

    public void setSlika(Image slika) {
        this.slika = slika;
    }

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public static Artikl add(Artikl a){
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage(a.getSlika(), null), "jpg", os);
            InputStream fis = new ByteArrayInputStream(os.toByteArray());

            PreparedStatement stmnt = ConnectionUtil.conDB().prepareStatement("INSERT INTO artikl VALUES (null, ?, ?, ?, ?, ?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            stmnt.setString(1, a.getNaziv());
            stmnt.setString(2, a.getDobavljac());
            stmnt.setFloat(3, a.getCijena());
            stmnt.setBinaryStream(4, fis);
            stmnt.setString(5, a.getKategorija());
            stmnt.setInt(6,a.getKolicina());
            stmnt.executeUpdate();

            ResultSet rs = stmnt.getGeneratedKeys();
            if (rs.next()) {
                a.setId(rs.getInt(1));
            }
            return a;
        } catch (SQLException e) {
            System.out.println("Nisam uspio dodati proizvod: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println("Nisam uspio dodati proizvodd: " + e.getMessage());
            return null;
        }
    }

    public  boolean updateA(Artikl a) {

        try {

            PreparedStatement stmnt = ConnectionUtil.conDB().prepareStatement("UPDATE artikl SET kolicina=?  WHERE id=?");

            stmnt.setInt(1,this.getKolicina());
            stmnt.setInt(2,this.getId());
            stmnt.executeUpdate();

            System.out.println("Uspjesno ažurirana količina artikla u skladištu.");
            return true;

        } catch (SQLException ex) {
            System.out.println("Greška prilikom ažuriranja količine artikla u skaldištu: " + ex.getMessage());
            return false;
        }

    }

    public  boolean updateK(Artikl a) {

        try {

            PreparedStatement stmnt = ConnectionUtil.conDB().prepareStatement("UPDATE artikl SET kolicina=?  WHERE naziv=?");

            stmnt.setInt(1,this.getKolicina());
            stmnt.setString(2,this.getNaziv());
            stmnt.executeUpdate();

            System.out.println("Uspjesno ažurirana količina artikla u skladištu.");
            return true;

        } catch (SQLException ex) {
            System.out.println("Greška prilikom ažuriranja količine artikla u skaldištu: " + ex.getMessage());
            return false;
        }

    }

    public static boolean remove(Artikl a){
        try {
            PreparedStatement stmnt = ConnectionUtil.conDB().prepareStatement("DELETE FROM artikl WHERE id=?");
            stmnt.setInt(1, a.getId());
            stmnt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Nisam uspio pobrisati proizvod: " + e.getMessage());
            return false;
        }
    }

    public static boolean update(Artikl a) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage(a.getSlika(), null), "jpg", os);
            InputStream fis = new ByteArrayInputStream(os.toByteArray());


            PreparedStatement stmnt = ConnectionUtil.conDB().prepareStatement("UPDATE proizvod SET naziv=?, dobavljac=?, cijena=?, slika=?, kategorija=?, kolicina=?  WHERE id=?");
            stmnt.setString(1, a.getNaziv());
            stmnt.setString(2, a.getDobavljac());
            stmnt.setFloat(3, a.getCijena());
            stmnt.setBinaryStream(4, fis);
            stmnt.setString(5, a.getKategorija());
            stmnt.setInt(6,a.getKolicina());
            stmnt.setInt(7, a.getId());
            stmnt.executeUpdate();
            return true;
        } catch (SQLException | IOException e) {
            System.out.println("Nisam uspio urediti proizvod: " + e.getMessage());
            return false;
        }
    }



    public static List<Artikl> readAll() {
        ObservableList<Artikl> articles = FXCollections.observableArrayList();
        try {
            Statement stmnt = ConnectionUtil.conDB().createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM artikl");

            while(rs.next()){
                Image fxSlika = null;
                try {
                    BufferedImage bImage = ImageIO.read(rs.getBinaryStream(5));
                    fxSlika = SwingFXUtils.toFXImage(bImage, null);
                } catch (NullPointerException | IOException ex) {
                    fxSlika = null;
                }

                articles.add(new Artikl(
                        rs.getInt(1),
                        rs.getString(2), //naziv
                        rs.getString(3),
                        rs.getFloat(4),
                        fxSlika,
                        rs.getString(6),
                        rs.getInt(7)

                ));
            }
            return articles;
        } catch (SQLException e) {
            System.out.println("Nisam uspio izvući artikle iz baze: " + e.getMessage());
            return null;
        }
    }


    public static List<Artikl> readByCategory(String kategorija){
        ObservableList<Artikl> articles = FXCollections.observableArrayList();;
        try {
            Statement stmnt = ConnectionUtil.conDB().createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM artikl WHERE kategorija='" + kategorija + "'");


            while(rs.next()){
                Image fxSlika = null;
                try {
                    BufferedImage bImage = ImageIO.read(rs.getBinaryStream(5));
                    fxSlika = SwingFXUtils.toFXImage(bImage, null);
                } catch (NullPointerException | IOException ex) {
                    fxSlika = null;
                }

                articles.add(new Artikl(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getFloat(4),
                        fxSlika,
                        rs.getString(6),
                        rs.getInt(7)

                ));
            }
            return articles;
        } catch (SQLException e) {
            System.out.println("Nisam uspio izvuci artikle iz baze: " + e.getMessage());
            return null;
        }

    }

}
