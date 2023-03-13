package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class StringUtilities {
    
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


}
