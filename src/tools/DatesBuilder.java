package tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import objects.Graphe;
import objects.Sommet;

public class DatesBuilder {
    
    
    public void initDatesPlusTot(Graphe graphe){

        HashMap<Integer,Sommet> listeSommets = graphe.getSommets();
        StringUtilities su = new StringUtilities();

        for(Sommet sommet : listeSommets.values()){

            if(sommet.getPredecesseurs().isEmpty()) {

                List<String> datesPredecesseurs = new ArrayList<>();
                datesPredecesseurs.add("0");
                sommet.setDatesPredecesseurs(datesPredecesseurs);
                sommet.setDatePlusTot("0");

            }
            else {

                List<String> predecesseurs = sommet.getPredecesseurs();
                List<String> datesPredecesseurSommet = new ArrayList<>();

                for(String predecesseur : predecesseurs){

                    int numeroSommet = Integer.parseInt(predecesseur);
                    Sommet sommetPredecesseur = listeSommets.get(numeroSommet);

                    String maxDatePred = su.getMaxDates(sommetPredecesseur.getDatesPredecesseurs());
                    

                    String[] paire = maxDatePred.split(" ");
                    int dureCumulee = Integer.parseInt(paire[0]); 
                    //String pred = paire[1];
                        
                    int duree = dureCumulee + sommetPredecesseur.getDuree();
                    datesPredecesseurSommet.add(duree + " " + predecesseur);

                }

                sommet.setDatesPredecesseurs(datesPredecesseurSommet);
                sommet.setDatePlusTot(su.getMaxDates(sommet.getDatesPredecesseurs()));

            }
        }
    }

}
