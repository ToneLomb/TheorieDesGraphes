package tools;

import java.util.HashMap;
import java.util.List;

import objects.Graphe;
import objects.Sommet;

public class ConditionChecker {

    public boolean checkCircuit(Graphe graphe){

        
        //On récupère une copie des sommets du graphe
        HashMap<Integer,Sommet> listeSommets = graphe.getSommetsCopy();
        
        //On initialise un booléen qui permettra de savoir si on peut supprimer un sommet ou non
        boolean sommetSupprime = true;
        
        System.out.println("\nDetection de circuits à l'aide de la méthode de suppression des entrées\n");
        //Tant qu'il y a des sommets à supprimer
        while(sommetSupprime && !listeSommets.isEmpty()){
            

            sommetSupprime = false;
            
            //On parcourt la liste de sommets
            for(Sommet sommet : listeSommets.values()){

                if(sommet.getDuree() < 0){
                    System.out.println("Il y a des arcs à valeur négatifs");
                    return false;
                }
    
                //Si on peut en supprimer un, cad qu'il n'a pas de prédécesseurs
                if(sommet.getPredecesseurs().isEmpty()){
    
                    //On récupère le numéro du sommet à supprimer
                    int numeroSommet = sommet.getNumero();
                    System.out.println("On doit supprimer l'entrée : " + numeroSommet);

                    //On prend la liste de ses successeurs
                    List<String> successeurs = sommet.getSuccesseurs();
                    System.out.println("Sommet(s) dont on doit supprimer comme prédecesseur " + numeroSommet + " : " + successeurs +"\n");
                    
                    //Pour chaque successeur
                    for(String successeur : successeurs){
                        Sommet suivant = listeSommets.get(Integer.parseInt(successeur));
    
                        //On enleve des prédécesseurs le numéro du sommet

                        //System.out.println("Prédécesseurs de " + successeur + " Avant : " +  suivant.getPredecesseurs());
                        suivant.getPredecesseurs().remove(String.valueOf(numeroSommet));
                        //System.out.println("Prédécesseurs de " + successeur + " Apres : " +  suivant.getPredecesseurs());
                    }
    
                    //On enlève de la liste des sommets le sommet traité
                    listeSommets.remove(numeroSommet);
                    sommetSupprime = true;
                    break;
                }
            }
        }
        String result = listeSommets.isEmpty()? "Il n'y a plus d'entrées" : "On ne peut plus rien supprimer car il y a un circuit";
        System.out.println(result);
        return listeSommets.isEmpty();
    }
}
