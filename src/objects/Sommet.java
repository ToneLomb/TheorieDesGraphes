package objects;
import java.util.ArrayList;
import java.util.List;

public class Sommet {
    
    //Définition des sommets
    private int numero;
    private List<String> predecesseurs = new ArrayList<>();;
    private List<String> successeurs = new ArrayList<>();
    private int duree;
    private int rang;

    //Pour calcul des dates si ordonnancement
    private List<String> datesPredecesseurs = new ArrayList<>();
    private String datePlusTot;

    private List<String> datesSuccesseurs = new ArrayList<>();
    private String datePlusTard;

    //private int marge;

    public Sommet(int numero, int duree) {
        this.numero = numero;
        this.duree = duree;
    }


    public List<String> getSuccesseurs() {
        return successeurs;
    }

    public void setSuccesseurs(List<String> successeurs) {
        this.successeurs = successeurs;
    }

    public List<String> getPredecesseurs() {
        return predecesseurs;
    }

    public void setPredecesseurs(List<String> predecesseurs) {
        this.predecesseurs = predecesseurs;
    }

    public int getNumero() {
        return numero;
    }

    public int getDuree() {
        return duree;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public List<String> getDatesPredecesseurs() {
        return datesPredecesseurs;
    }


    public void setDatesPredecesseurs(List<String> datesPredecesseurs) {
        this.datesPredecesseurs = datesPredecesseurs;
    }

    public String getDatePlusTot() {
        return datePlusTot;
    }

    public void setDatePlusTot(String datePlusTot) {
        this.datePlusTot = datePlusTot;
    }

    public List<String> getDatesSuccesseurs() {
        return datesSuccesseurs;
    }

    public void setDatesSuccesseurs(List<String> datesSuccesseurs) {
        this.datesSuccesseurs = datesSuccesseurs;
    }

    public String getDatePlusTard() {
        return datePlusTard;
    }

    public void setDatePlusTard(String datePlusTard) {
        this.datePlusTard = datePlusTard;
    }

    @Override
    public String toString() {
        return "Nom : " + getNumero() +
                " Durée : " + getDuree() +
                " Prédécesseurs : " + getPredecesseurs() +
                " Successeurs : " + getSuccesseurs() + 
                " Rang : " + getRang() + 
                " Date par prédécesseur : " + getDatesPredecesseurs() +
                " Date au plus tôt : " + getDatePlusTot() + 
                "\n";
    }

}
