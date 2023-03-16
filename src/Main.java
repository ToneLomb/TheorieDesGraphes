import java.util.Scanner;

import objects.Graphe;
import tools.ConditionChecker;
import tools.GrapheBuilder;


public class Main {
    public static void main(String[] args) throws Exception {
        
        Scanner sc = new Scanner(System.in);
        String fileName = "";
        ConditionChecker checker = new ConditionChecker();

        //Tant que l'utilisateur n'entre pas exit
        while(!(fileName = sc.nextLine()).equals("exit")){

            GrapheBuilder builder = new GrapheBuilder();
            
            //On construit le graphe que l'utilisateur a demandé
            Graphe graphe = builder.build(fileName);


            if(checker.checkCircuit(graphe)){
                System.out.println("Il n'y a pas de circuits, on calcule les rangs et les dates");

                //Calcul des rangs
                builder.initRang(graphe);

                //Affichage des rangs
                System.out.println(graphe.getSommets());

            }else{
                System.out.println("Il y au circuit, on ne peut pas faire d'ordonnancement. Veuillez choisir un nouveau graphe");
            }
        }
        sc.close();
    }
}

