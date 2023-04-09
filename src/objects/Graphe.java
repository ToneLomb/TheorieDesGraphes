package objects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graphe {
    
    //Permet de lier un sommet à un numéro
    private HashMap<Integer,Sommet> sommets = new HashMap<>();
    private ArrayList<List<String>> matrice;
    private ArrayList<List<Sommet>> cheminCritique = new ArrayList<>();


    public Graphe (){
    }

    public void setSommets(HashMap<Integer,Sommet> sommets) {
        this.sommets = sommets;
    }

    public void setMatrice(ArrayList<List<String>> matrice) {
        this.matrice = matrice;
    }

    public HashMap<Integer,Sommet> getSommets() {
        return sommets;
    }

    public ArrayList<List<String>> getMatrice() {
        return matrice;
    }

    public ArrayList<List<Sommet>> getCheminCritique() {
        return cheminCritique;
    }

    public void setCheminCritique(ArrayList<List<Sommet>> cheminCritique) {
        this.cheminCritique = cheminCritique;
    }

    public HashMap<Integer,Sommet> getSommetsCopy() {

        HashMap<Integer,Sommet> copy = new HashMap<>();
        for(Sommet sommet : sommets.values()){

            int numeroSommet = sommet.getNumero();
            int duree = sommet.getDuree();
            Sommet copySommet =  new Sommet(numeroSommet, duree);

            List<String> predecesseurCopy = new ArrayList<>(); 
            List<String> successeurCopy = new ArrayList<>(); 

            for(String predecesseur : sommet.getPredecesseurs()){
                predecesseurCopy.add(predecesseur);
            }
            for(String successeur : sommet.getSuccesseurs()){
                successeurCopy.add(successeur);
            }

            copySommet.setPredecesseurs(predecesseurCopy);
            copySommet.setSuccesseurs(successeurCopy);
            copySommet.setRang(sommet.getRang());

            copy.put(numeroSommet,copySommet);
            
        }
        return copy;
    }

    
}
