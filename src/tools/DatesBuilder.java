package tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public void initDatesPlusTard(Graphe graphe){

        HashMap<Integer,Sommet> listeSommets = graphe.getSommets();
        listeSommets.keySet().stream().sorted(Comparator.reverseOrder()).forEachOrdered(e -> reverseDates(listeSommets.get(e), listeSommets));

    }

    public void reverseDates(Sommet sommet, HashMap<Integer,Sommet> listeSommets){

        StringUtilities su = new StringUtilities();
        if(sommet.getSuccesseurs().isEmpty()) {

            List<String> datesSuccesseurs = new ArrayList<>();
            String[] dateAuPlusTotOmega = listeSommets.get(listeSommets.size() - 1).getDatePlusTot().split(" ");
            datesSuccesseurs.add(dateAuPlusTotOmega[0]);

            sommet.setDatesSuccesseurs(datesSuccesseurs);
            sommet.setDatePlusTard(dateAuPlusTotOmega[0]);

        }
        else {

            List<String> successeurs = sommet.getSuccesseurs();
            //System.out.println("Succeseurs de : " + sommet.getNumero() + successeurs);
            List<String> datesSuccesseurSommet = new ArrayList<>();

            for(String successeur : successeurs){

                int numeroSommet = Integer.parseInt(successeur);
                Sommet sommetSuccesseur = listeSommets.get(numeroSommet);

                String minDateSucc = su.getMinDates(sommetSuccesseur.getDatesSuccesseurs());
                //System.out.println("Date min du successeur : " + minDateSucc);

                String[] paire = minDateSucc.split(" ");
                int dureCumulee = Integer.parseInt(paire[0]); 
                //String pred = paire[1];
                    
                int duree = dureCumulee - sommet.getDuree();
                //System.out.println("Duree cumulee : " + dureCumulee + "\nDuree de " + sommet.getNumero() +" : " + sommet.getDuree());
                datesSuccesseurSommet.add(duree + " " + successeur);
                //System.out.println("Date successeur du sommet actuel : " + datesSuccesseurSommet  + "\n");

            }

            sommet.setDatesSuccesseurs(datesSuccesseurSommet);
            sommet.setDatePlusTard(su.getMinDates(sommet.getDatesSuccesseurs()));

        }

    }

}
