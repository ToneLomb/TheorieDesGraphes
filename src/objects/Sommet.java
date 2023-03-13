package objects;
import java.util.ArrayList;
import java.util.List;

public class Sommet {
    
    //Définition des sommets
    private int numero;
    private List<String> predecesseurs = new ArrayList<>();;
    private List<String> successeurs = new ArrayList<>();
    private int duree;

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

    @Override
    public String toString() {
        return "Nom : " + getNumero()+
                " Durée : " + getDuree() +
                " Prédécesseurs : " + getPredecesseurs() +
                " Successeurs : " + getSuccesseurs();
    }

}
