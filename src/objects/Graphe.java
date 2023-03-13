package objects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graphe {
    
    //Permet de lier un sommet à un numéro
    private HashMap<Integer,Sommet> sommets = new HashMap<>();
    private ArrayList<List<String>> matrice;

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

}
