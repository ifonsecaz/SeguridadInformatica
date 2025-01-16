/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package criptografia;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * @author ifons
 */
public class PDFC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File a = new File("C:\\Users\\ifons\\Documents\\WireShark\\3._Network-security-essentials-4th-edition-william-stallings.pdf");
        final String secretKey = "433N27ctPggYx6FK";
        long startTime,endTime;
        
        try{
            PDDocument doc= PDDocument.load(a);
            PDFTextStripper pdfStripper= new PDFTextStripper();
            
            String text = pdfStripper.getText(doc);
            
            doc.close();
            
            System.out.println("AES");
            //System.out.println("Original String: " + text);
            startTime=System.nanoTime();
            String encryptedString = AES.encrypt(text, secretKey) ;
            endTime=System.nanoTime()-startTime;
            System.out.println("Encrypted String: " + encryptedString);
            System.out.println("Tiempo de Ejecución: " + endTime);
            
            startTime=System.nanoTime();
            String decryptedString = AES.decrypt(encryptedString, secretKey) ;
            endTime=System.nanoTime()-startTime;
            //System.out.println("Decrypted String: " + decryptedString);
            System.out.println("Tiempo de Ejecución: " + endTime);
     
            System.out.println("TripleDES");
            //System.out.println("Original String: " + text);
            startTime=System.nanoTime();
            String data = TripleDESTest.encrypt(text, secretKey);
            endTime=System.nanoTime()-startTime;
            System.out.println("Encrypted String: " + data);
            System.out.println("Tiempo de Ejecución: " + endTime);
            
            startTime=System.nanoTime();
            String decryptData = TripleDESTest.decrypt(data, secretKey);
            endTime=System.nanoTime()-startTime;
            //System.out.println("Decrypted String: " + decryptData);
            System.out.println("Tiempo de Ejecución: " + endTime);
        
            
        } catch (IOException ex) {
            Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
