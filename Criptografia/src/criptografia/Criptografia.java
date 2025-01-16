/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package criptografia;
/**
 *
 * @author ifons
 */
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Criptografia {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final String secretKey = "433N27ctPggYx6FK";
        long startTime,endTime;
        
        File a=new File("C:\\Users\\ifons\\Documents\\WireShark\\Prueba.txt");
        //TXT
        try(Scanner lec = new Scanner(a)){
            String originalString ="";
            
            while(lec.hasNextLine()){
                originalString+=lec.nextLine();
                originalString+=" ";
            }
            
            System.out.println("AES");
            System.out.println("Original String: " + originalString);
            startTime=System.nanoTime();
            String encryptedString = AES.encrypt(originalString, secretKey) ;
            endTime=System.nanoTime()-startTime;
            System.out.println("Encrypted String: " + encryptedString);
            System.out.println("Tiempo de Ejecución: " + endTime);
            
            startTime=System.nanoTime();
            String decryptedString = AES.decrypt(encryptedString, secretKey) ;
            endTime=System.nanoTime()-startTime;
            System.out.println("Decrypted String: " + decryptedString);
            System.out.println("Tiempo de Ejecución: " + endTime);
     
            System.out.println("TripleDES");
            System.out.println("Original String: " + originalString);
            startTime=System.nanoTime();
            String data = TripleDESTest.encrypt(originalString, secretKey);
            endTime=System.nanoTime()-startTime;
            System.out.println("Encrypted String: " + data);
            System.out.println("Tiempo de Ejecución: " + endTime);
            
            startTime=System.nanoTime();
            String decryptData = TripleDESTest.decrypt(data, secretKey);
            endTime=System.nanoTime()-startTime;
            System.out.println("Decrypted String: " + decryptData);
            System.out.println("Tiempo de Ejecución: " + endTime);
        }
        catch(FileNotFoundException e){
            System.err.print(e);
            System.exit(-1);
        }
    }
    
}
