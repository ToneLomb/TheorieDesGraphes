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
        ArrayList<Sommet> file = new ArrayList<>();
        file.add(listeSommets.get(0));
        while(!file.isEmpty()){
            readDates(file.get(0), listeSommets);
            for(int i = 0; i< file.get(0).getSuccesseurs().size(); i++){
                List<String> successeurs = file.get(0).getSuccesseurs();
                file.add(listeSommets.get(Integer.parseInt(successeurs.get(i))));
            }
            file.remove(0);
            Collections.sort(file, new Comparator<Sommet>() {
                @Override
                public int compare(Sommet s1, Sommet s2){
                    return s1.getRang() - s2.getRang();
                }
            });
        }
    }

    public void readDates(Sommet sommet, HashMap<Integer,Sommet> listeSommets){

        StringUtilities su = StringUtilities.getInstance();
        if(sommet.getDatePlusTot() != null) return;
        if(sommet.getPredecesseurs().isEmpty()) {
            System.out.println("\n--------------------------------------\n\nCalcul des dates au plus tôt\n");
            System.out.println("Initilisation de la date au plus tôt de alpha à 0");
            List<String> datesPredecesseurs = new ArrayList<>();
            List<String> dateAuPlusTot = new ArrayList<>();
            dateAuPlusTot.add("0");
            datesPredecesseurs.add("0");
            sommet.setDatesPredecesseurs(datesPredecesseurs);
            sommet.setDatePlusTot(dateAuPlusTot);

        }
        else {

            List<String> predecesseurs = sommet.getPredecesseurs();
            List<String> datesPredecesseurSommet = new ArrayList<>();
            System.out.println("\nLe sommet " + sommet.getNumero() + " a pour prédecesseurs : " + predecesseurs );

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
            String[] duree_string = su.getMaxDates(sommet.getDatesPredecesseurs()).split(" ");
            int duree_max = Integer.parseInt(duree_string[0]);
            int provenance = Integer.parseInt(duree_string[1]);
            System.out.println("Sa date au plus tôt est " + duree_max + " venant de : " + provenance + " (maximum)");
            sommet.setDatePlusTot(su.getEqualDates(sommet.getDatesPredecesseurs(), duree_max));
        }
    }

    public void initDatesPlusTard(Graphe graphe){

        HashMap<Integer,Sommet> listeSommets = graphe.getSommets();
        ArrayList<Sommet> file = new ArrayList<>();
        file.add(listeSommets.get(listeSommets.size() - 1));
        while(!file.isEmpty()){
            reverseDates(file.get(0), listeSommets);
            for(int i = 0; i< file.get(0).getPredecesseurs().size(); i++){
                List<String> predecesseurs = file.get(0).getPredecesseurs();
                file.add(listeSommets.get(Integer.parseInt(predecesseurs.get(i))));
            }
            file.remove(0);
            Collections.sort(file, new Comparator<Sommet>() {
                @Override
                public int compare(Sommet s1, Sommet s2){
                    return s2.getRang() - s1.getRang();
                }
            });
        }
        //listeSommets.keySet().stream().sorted(Comparator.reverseOrder()).forEachOrdered(e -> reverseDates(listeSommets.get(e), listeSommets));

    }

    public void reverseDates(Sommet sommet, HashMap<Integer,Sommet> listeSommets){

        StringUtilities su = StringUtilities.getInstance();
        if(sommet.getDatePlusTard() != null) return;
        if(sommet.getSuccesseurs().isEmpty()) {
            List<String> datesSuccesseurs = new ArrayList<>();
            String[] dateAuPlusTotOmega = listeSommets.get(listeSommets.size() - 1).getDatePlusTot().get(0).split(" ");
            datesSuccesseurs.add(dateAuPlusTotOmega[0]);

            List<String> test = new ArrayList<>();
            test.add(dateAuPlusTotOmega[0]);
            System.out.println("\n--------------------------------------\n\nCalcul des dates au plus tard\n");
            System.out.println("Initilisation de la date au plus tard de omega à sa date au plus tôt : " + dateAuPlusTotOmega[0]);

            sommet.setDatesSuccesseurs(datesSuccesseurs);
            sommet.setDatePlusTard(test);

        }
        else {

            List<String> successeurs = sommet.getSuccesseurs();
            //System.out.println("Succeseurs de : " + sommet.getNumero() + successeurs);
            List<String> datesSuccesseurSommet = new ArrayList<>();
            System.out.println("\nLe sommet " + sommet.getNumero() + " a pour successeurs : " + successeurs );


            for(String successeur : successeurs){

                int numeroSommet = Integer.parseInt(successeur);
                Sommet sommetSuccesseur = listeSommets.get(numeroSommet);
                
                //System.out.println(sommetSuccesseur.getNumero() + " successeurs : " + sommetSuccesseur.getDatesSuccesseurs());
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
            String[] duree_string = su.getMinDates(sommet.getDatesSuccesseurs()).split(" ");
            int duree_min = Integer.parseInt(duree_string[0]);
            int provenance = Integer.parseInt(duree_string[1]);
            System.out.println("Sa date au plus tard est " + duree_min + " venant de : " + provenance + " (minimum)");
            sommet.setDatePlusTard(su.getEqualDates(sommet.getDatesSuccesseurs(), duree_min));

        }

    }

    public void initMarge(Graphe graphe){

        HashMap<Integer,Sommet> listeSommets = graphe.getSommets();
        for(Sommet sommet : listeSommets.values()){

            int marge;
            List<String> datePlusTard = sommet.getDatePlusTard();
            List<String> datePlusTot= sommet.getDatePlusTot();

            int numeroPlusTot = Integer.parseInt(datePlusTot.get(0).split(" ")[0]);
            int numeroPlusTard = Integer.parseInt(datePlusTard.get(0).split(" ")[0]);
            marge = numeroPlusTard - numeroPlusTot;

            sommet.setMarge(String.valueOf(marge) + getNumeroPlusTard(datePlusTard));
        }
    }

    public String getNumeroPlusTard(List<String> datePlusTard){

        String margeNumero = "";
        for(String date : datePlusTard){
            String[] tab = date.split(" ");
            if(tab.length == 1){
                return " /";
            }
            margeNumero += " " + tab[1]; 
        }
        return margeNumero;
    }



}
