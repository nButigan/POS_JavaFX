package sample.models;

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
}
