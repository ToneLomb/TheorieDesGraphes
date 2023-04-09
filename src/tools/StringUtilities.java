package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import objects.Graphe;


public class StringUtilities {

    private static StringUtilities instance = null;

    private StringUtilities(){};

    //Singleton
    public static StringUtilities getInstance(){

        if(instance == null){
            instance = new StringUtilities();
        }
        return instance;
    }
    
    public List<String> readFile(String fileName) throws IOException{

        try {
            if(!fileName.equals("exit")){

                List<String> liste = new ArrayList<>();

                String filePath = new File("").getAbsolutePath();
                filePath += "\\" + fileName + ".txt";
                System.out.print(filePath);

                BufferedReader br = new BufferedReader(new FileReader(filePath));
                String line;

                while((line = br.readLine()) != null){
                    liste.add(line);
                }

                br.close();
                return liste;
            }
    
        } catch (FileNotFoundException e) {
            System.out.println("Le nom de fichier est incorrect");
        }
        return null;
        
    }

    public String getMaxDates(List<String> datesPlusTot){

        int max = 0;
        String numero = "0";
        for(String dates : datesPlusTot){

            String[] duree = dates.split(" ");
            int intDuree = Integer.parseInt(duree[0]);
            if(intDuree > max){
                max = intDuree;
                numero = duree[1];
            }
        }

        return max + " " + numero;

    }

    public String getMinDates(List<String> datesPlusTard){

        //System.out.println(datesPlusTard);
        String[] init = datesPlusTard.get(0).split(" ");
        int min = Integer.parseInt(init[0]);

        String numero = (init.length < 2 )? "": init[1];

        for(String dates : datesPlusTard){

            String[] duree = dates.split(" ");
            int intDuree = Integer.parseInt(duree[0]);
            if(intDuree < min){
                min = intDuree;
                numero = duree[1];
            }
        }

        return min + " " + numero;

    }

    public List<String> getEqualDates(List<String> dates, int value){

        List<String> equalsDates = new ArrayList<>();
        for(String date : dates){
            
            String[] dateToString = date.split(" ");

            int duree = Integer.parseInt(dateToString[0]);
            int numero = Integer.parseInt(dateToString[1]);

            if(duree == value){
                equalsDates.add(duree+ " " + numero);
            }
        }

        return equalsDates;
    }

    public void afficherCheminCritique(Graphe graphe){

        for(int i = 0; i < graphe.getCheminCritique().size(); i++){
            
            for(int j = 0; j < graphe.getCheminCritique().get(i).size(); j++){

                if(j == graphe.getCheminCritique().get(i).size()-1){
                    System.out.print(graphe.getCheminCritique().get(i).get(j).getNumero());
                }else{
                    System.out.print(graphe.getCheminCritique().get(i).get(j).getNumero() + "->");
                }
                
            }
            System.out.println("\n");
        }
    }


}
