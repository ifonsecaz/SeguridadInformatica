/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package criptografia;

import com.spire.presentation.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ifons
 */
public class PowerPoint {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Create a Presentation instance
        Presentation ppt = new Presentation();
        final String secretKey = "433N27ctPggYx6FK";
        long startTime,endTime;
        //Load the PowerPoint document
        try{
            ppt.loadFromFile("C:\\Users\\ifons\\Documents\\WireShark\\CriptografiaSimetrica.ppt");
 
            String buffer = "";
 
        //Loop through the slides in the document and extract text
            for (Object slide : ppt.getSlides()) {
                for (Object shape : ((ISlide) slide).getShapes()) {
                    if (shape instanceof IAutoShape) {
                        for (Object tp : ((IAutoShape) shape).getTextFrame().getParagraphs()) {
                            buffer+=("\n" + (((ParagraphEx) tp).getText()));
                        }
                    }
                }
            }
            
            System.out.println("AES");
            //System.out.println("Original String: " + buffer);
            startTime=System.nanoTime();
            String encryptedString = AES.encrypt(buffer, secretKey) ;
            endTime=System.nanoTime()-startTime;
            System.out.println("Encrypted String: " + encryptedString);
            System.out.println("Tiempo de Ejecución: " + endTime);
            
            startTime=System.nanoTime();
            String decryptedString = AES.decrypt(encryptedString, secretKey) ;
            endTime=System.nanoTime()-startTime;
            //System.out.println("Decrypted String: " + decryptedString);
            System.out.println("Tiempo de Ejecución: " + endTime);
     
            System.out.println("TripleDES");
            //System.out.println("Original String: " + buffer);
            startTime=System.nanoTime();
            String data = TripleDESTest.encrypt(buffer, secretKey);
            endTime=System.nanoTime()-startTime;
            System.out.println("Encrypted String: " + data);
            System.out.println("Tiempo de Ejecución: " + endTime);
            
            startTime=System.nanoTime();
            String decryptData = TripleDESTest.decrypt(data, secretKey);
            endTime=System.nanoTime()-startTime;
            //System.out.println("Decrypted String: " + decryptData);
            System.out.println("Tiempo de Ejecución: " + endTime);
        } catch (Exception ex) {
            Logger.getLogger(PowerPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Save text

    }
    
}
