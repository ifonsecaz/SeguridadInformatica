/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package criptografia;

import java.awt.Rectangle;
import java.awt.print.PageFormat;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
/**
 *
 * @author ifons
 */
public class PDF {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File a = new File("C:\\Users\\ifons\\Documents\\WireShark\\3._Network-security-essentials-4th-edition-william-stallings.pdf");
        
        try{
            PDDocument doc= PDDocument.load(a);
            PDFTextStripper pdfStripper= new PDFTextStripper();
            
            String text = pdfStripper.getText(doc);
            
            doc.close();
            
            //System.out.println(text);
        
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
 
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            
            InputStream fontStream = new FileInputStream("C:\\Users\\ifons\\Documents\\WireShark\\Batang.ttf");
            PDType0Font font = PDType0Font.load(document, fontStream);
            
            contentStream.setFont(font, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);
            contentStream.showText(text);
            contentStream.endText();
            contentStream.close();
 
            document.save("pdfC.pdf");
            document.close();
            
        } catch (IOException ex) {
            Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
