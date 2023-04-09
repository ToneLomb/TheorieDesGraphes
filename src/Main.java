import java.util.Scanner;

import objects.Graphe;
import objects.Sommet;
import tools.ConditionChecker;
import tools.DatesBuilder;
import tools.GrapheBuilder;
import tools.StringUtilities;


public class Main {
    public static void main(String[] args) throws Exception {
        
        Scanner sc = new Scanner(System.in);
        String fileName = "";
        ConditionChecker checker = new ConditionChecker();
        StringUtilities su = StringUtilities.getInstance();
        
        //Tant que l'utilisateur n'entre pas exit
        while(!(fileName = sc.nextLine()).equals("exit")){

            GrapheBuilder builder = new GrapheBuilder();
            
            //On construit le graphe que l'utilisateur a demandé
            Graphe graphe = builder.build(fileName);


            if(checker.checkCircuit(graphe)){
                System.out.println("\nIl n'y a pas de circuits, on calcule les rangs et les dates\n");

                //Calcul des rangs
                builder.initRang(graphe);

                //Affichage des rangs
                //System.out.println(graphe.getSommets());

                DatesBuilder db = new DatesBuilder();
                db.initDatesPlusTot(graphe);
                db.initDatesPlusTard(graphe);
                db.initMarge(graphe);

                System.out.println("\nCalcul des marges et récapitulatif des sommets :\n");
                for(Sommet s : graphe.getSommets().values()){
                    System.out.println(s);
                }
                

                builder.initCheminCritique(graphe);
                System.out.println("\nAffichage des chemins critiques :\n");
                su.afficherCheminCritique(graphe);

                //System.out.println("Calcul des dates au plus tôt : ");


            }else{
                System.out.println("Il y au circuit, on ne peut pas faire d'ordonnancement. Veuillez choisir un nouveau graphe");
            }
        }
        sc.close();
    }
}

