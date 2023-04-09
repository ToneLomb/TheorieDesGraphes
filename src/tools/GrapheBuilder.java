package tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
        StringUtilities su = StringUtilities.getInstance();
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
        
        System.out.println("\n\nAffichage du graphe sous forme de jeu de triplets");
        //On parcourt de nouveau chaque sommet
        for (Sommet s : graphe.getSommets().values()){

            //S'il n'a pas de successeurs et que ce n'est pas omega
            if(s.getSuccesseurs().isEmpty() && s.getNumero() != taille){

                //On lui ajoute omega comme successeur, et on ajoute à omega ce sommet comme prédécesseur
                s.getSuccesseurs().add(String.valueOf(taille));
                omega.getPredecesseurs().add(String.valueOf(s.getNumero()));
            }
            //Affichage du graphe
            System.out.println(s.sansDates());
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
        System.out.println("\nAffichage de la matrice : \n");
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
                if(matrice.get(i).get(j).length() < 2){
                    System.out.print(matrice.get(i).get(j) + "   ");
                }
                else{
                    System.out.print(matrice.get(i).get(j) + "  ");
                }
            }
            System.out.println();
        }
        System.out.println("\n");
        return matrice;
    }

    public void initRang(Graphe graphe){

        //On récupère la copie de la liste des sommets du graphe
        HashMap<Integer,Sommet> listeSommets = graphe.getSommetsCopy();

        //On instancie la liste des entrées qui va nous servir pour l'algorithme de rangs
        ArrayList<Sommet> entrees = new ArrayList<>();
        int rang = 0;

        System.out.println("Calcul des rangs : \n");
        //Tant que tout le graphe n'a pas été traité
        while(!listeSommets.isEmpty()){

            //On parcourt les sommets restants
            for(Sommet sommet : listeSommets.values()){

                //Si un sommet n'a pas de prédécesseurs, c'est une entrée
                if(sommet.getPredecesseurs().isEmpty()){
                    entrees.add(sommet);
                }
            }

            List<Integer> entreesToInteger = new ArrayList<>();
            for(Sommet s : entrees){
                entreesToInteger.add(s.getNumero());
            }
            System.out.println("Les entrées sont : " + entreesToInteger + ". On les supprime");
            //Tant qu'on a pas traité les entrées de l'itération actuelle
            while(!entrees.isEmpty()){

                Sommet entree = entrees.get(0);
                int numeroSommet = entree.getNumero();

                //On récupère ses sucesseurs
                List<String> successeurs = entree.getSuccesseurs();

                //Pour chaque sucesseur, on supprime l'entrée de ses prédecesseurs
                for(String successeur : successeurs){

                    Sommet enfantSommet = listeSommets.get(Integer.parseInt(successeur));
                    enfantSommet.getPredecesseurs().remove(String.valueOf(numeroSommet));

                }

                //Le sommet étant traité, on lui attribue son rang, et on le supprime de la liste des sommets à traiter
                graphe.getSommets().get(numeroSommet).setRang(rang);
                listeSommets.remove(numeroSommet);

                //On oublie pas d'enlever l'entrée pour la boucle
                entrees.remove(0);

            }
            //On passe à l'itération suivante
            System.out.println("Leur rang est : " + rang + "\n");
            rang++;
        }
        
    }

    public void initCheminCritique(Graphe graphe){

        List<Sommet> sommets = new ArrayList<>();
        sommets.add(graphe.getSommets().get(0));

        //On va chercher les chemins critiques à partir du sommet 0
        trouverCheminCritique(graphe, sommets);
       
    }

    public void trouverCheminCritique(Graphe graphe, List<Sommet> instanceDuSommetPrecedant){
        
        //Marge sous la forme "duree succeseur critique" On prend donc la String marge du dernier sommet precedant sous forme de tableau
        String[] margeSplit = instanceDuSommetPrecedant.get(instanceDuSommetPrecedant.size()-1).getMarge().split(" ");
        List<Sommet> instanceDuSommetActuelle = new ArrayList<>();
        List<Sommet> sommetATraiter = new ArrayList<>();

        //On récupère les sommets précedants
        for(Sommet sommet : instanceDuSommetPrecedant){
            instanceDuSommetActuelle.add(sommet);
        }

        //Si le sommet suivant est final, on ajoute la liste actuelle aux chemins critiques car on est à la fin
        if(margeSplit[1].equals("/")){

            graphe.getCheminCritique().add(instanceDuSommetActuelle);
            return;
        }

        //Sinon, on ajoute tous les successeurs critiques aux sommets à traiter
        for(int i = 1; i < margeSplit.length ; i++){
            sommetATraiter.add(graphe.getSommets().get(Integer.parseInt(margeSplit[i])));
        }

        //Tant qu'il reste des sommets à traiter
        while(!sommetATraiter.isEmpty()){

            //On ajoute le prochain sommet à la liste, puis avec la récursivité il va continuer le chemin critique à partir de cette nouvelle liste
            instanceDuSommetActuelle.add(sommetATraiter.get(0));
            trouverCheminCritique(graphe, instanceDuSommetActuelle);

            //Une fois arrivé au chemin final, il enleve le chemin qui vient d'être traité et passe au suivant
            sommetATraiter.remove(0);

            //On revient avant la bifurcation
            instanceDuSommetActuelle.remove(instanceDuSommetActuelle.size()-1);

        }

        return;
     
    }
}
