package tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import objects.Graphe;
import objects.Sommet;

public class GrapheBuilder {
    

    public Graphe build(String fileName) throws IOException{

        //On instancie un graphe
        Graphe graphe = new Graphe();

        //Création du sommet alpha de numéro 0 et de durée 0
        Sommet alpha = new Sommet(0, 0);
        graphe.getSommets().put(0, alpha);

        //On lit les lignes du fichier texte
        StringUtilities su = new StringUtilities();
        List<String> fileLines = su.readFile(fileName);
        
        //Pour chaque ligne (autrement dit sommet) faire :
        for(String sommet : fileLines){

            //On sépare la ligne de texte sous forme de tableau
            String[] line = sommet.split(" ");

            //La 1ere chaine de caractère correspond forcément au numéro du sommet, et la 2ème à sa durée
            int numero = Integer.parseInt(line[0]);
            int duree = Integer.parseInt(line[1]);

            //On créer le sommet correspondant et on l'ajoute au graphe
            Sommet s = new Sommet(numero, duree);
            graphe.getSommets().put(numero,s);

            //S'il y a plus de 2 chaines de caractères sur la ligne, alors ce sont les prédécesseurs
            //On les ajoute au sommet, sinon ils ont le sommet 0 (alpha) comme prédécesseur
            if(line.length > 2){
                for(int i = 2; i < line.length; i++)
                s.getPredecesseurs().add(line[i]);
            }else{
                s.getPredecesseurs().add("0");
            }
        }

        //Création du sommet omega, de numéro N + 1 et de durée 0 
        int taille = graphe.getSommets().size();
        Sommet omega = new Sommet(taille, 0);
        graphe.getSommets().put(taille, omega);

        //On parcourt chaque sommet du graphe grâce à la HashMap
        for (Sommet sommet : graphe.getSommets().values()) {

            //Pour chaque prédécesseurs du sommet
            for(String predecesseurs : sommet.getPredecesseurs()){

                
                String[] successeurs = predecesseurs.split(" ");
                
                //On ajoute à ce prédécesseur le sommet actuel comme successeur
                for(int i = 0; i < successeurs.length; i++){

                    int numeroSommet = Integer.parseInt(successeurs[i]);  
                    graphe.getSommets().get(numeroSommet).getSuccesseurs().add(String.valueOf(sommet.getNumero()));

                }
            }
        }
        
        //On parcourt de nouveau chaque sommet
        for (Sommet s : graphe.getSommets().values()){

            //S'il n'a pas de successeurs et que ce n'est pas omega
            if(s.getSuccesseurs().isEmpty() && s.getNumero() != taille){

                //On lui ajoute omega comme successeur, et on ajoute à omega ce sommet comme prédécesseur
                s.getSuccesseurs().add(String.valueOf(taille));
                omega.getPredecesseurs().add(String.valueOf(s.getNumero()));
            }
            //Affichage du graphe
            System.out.println(s);
        }
        // On crée la matrice de valeur
        graphe.setMatrice(buildMatrice(graphe)); 
        
        return graphe;
    }

    public ArrayList<List<String>> buildMatrice (Graphe graphe) {
        //Initialisation de la matrice et de list qui va servir de tampon
        ArrayList<List<String>> matrice = new ArrayList<>();
        List<String> list;

        //On parcourt tous les sommets du graphes
        for (int i = 0; i < graphe.getSommets().size(); i++) {
            // On remplit la liste d'"*"
            list = new ArrayList<String>(Collections.nCopies(graphe.getSommets().size(), "*"));
            // On parcourt les successeurs du sommet
            for (int j = 0; j < graphe.getSommets().get(i).getSuccesseurs().size(); j++) {
                // On va remplacer l'étoile de list par la durée du sommet à l'index de son successeur
                list.set(Integer.parseInt(graphe.getSommets().get(i).getSuccesseurs().get(j)), String.valueOf(graphe.getSommets().get(i).getDuree()));
            }
            // On ajoute la list à la matrice puis on la reset
            matrice.add(list);
        }
        // Affichage
        for (int k = 0; k < matrice.size(); k++) {
            if (k < 10)
                System.out.print("   " + k);
            else
                System.out.print("  " + k);
        }
        System.out.println();
        for (int i = 0; i < matrice.size(); i++) {
            if (i < 10)
                System.out.print(i + "  ");
            else
                System.out.print(i + " ");
            for (int j = 0; j < matrice.size(); j++) {
                System.out.print(matrice.get(i).get(j) + "   ");
            }
            System.out.println();
        }
        return matrice;
    }
}
