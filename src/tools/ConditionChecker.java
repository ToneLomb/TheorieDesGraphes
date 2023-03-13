package tools;

import java.util.HashMap;
import java.util.List;

import objects.Graphe;
import objects.Sommet;

public class ConditionChecker {

    public boolean checkCircuit(Graphe graphe){
        
        //On récupère les sommets du graphe
        HashMap<Integer,Sommet> listeSommets = graphe.getSommets();
        
        //On initialise un booléen qui permettra de savoir si on peut supprimer un sommet ou non
        boolean sommetSupprime = true;
        
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
                    //System.out.println("On doit supprimer le sommet : " + numeroSommet);


                    //On prend la liste de ses successeurs
                    List<String> successeurs = sommet.getSuccesseurs();
                    //System.out.println("Sommets dont on doit supprimer le prédecesseur " + numeroSommet + " : " + successeurs);
                    
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
        String checkCircuit = (listeSommets.isEmpty())? "Il n'y a pas de circuits" : "Il y a un circuit";
        System.out.println(checkCircuit);
        return listeSommets.isEmpty();
    }
    
}
