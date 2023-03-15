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

            //System.out.println(graphe.getSommets());
            checker.checkCircuit(graphe);
            //System.out.println(graphe.getSommets());


            
            builder.initRang(graphe);
            
           

        }
        sc.close();
    }
}

