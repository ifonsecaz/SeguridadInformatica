/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decifrar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author ifons
 */
public class Decifrar {
    static String llave="nFQ$YL#M{_Lp?-5py&";
    static Cipher cipher;
    static SecretKey secretKey;
    static byte[] key;
    /**
     * @param args the command line arguments
     */
    public static void setKey(String myKey) 
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    private static void recorre(File dir,List arc){
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; children != null && i < children.length; i++) {
                recorre(new File(dir, children[i]),arc);
            }
        }
        if (dir.isFile()) {
            try{
                if(dir.getName().endsWith(".dec")&&!dir.getName().equals("archivos.dec")){
                    FileInputStream archivo = new FileInputStream(dir);
                    String[] nombre = dir.getName().split("\\.");
                    //System.out.println(dir);
                    String extension="txt";
                    if(arc.contains(nombre[0])){
                        extension=""+arc.get(arc.indexOf(nombre[0])+1);
                    }

                    FileOutputStream archivo2= new FileOutputStream(dir.getParent() + "/" + nombre[0] + "." + extension);
                    byte[] input = new byte[64];
                    int bytesRead;

                    try {

                        while ((bytesRead = archivo.read(input)) != -1) {
                            byte[] output = cipher.update(input, 0, bytesRead);
                            if (output != null)
                                archivo2.write(output);
                        }

                        byte[] output;
                        try {
                            output = cipher.doFinal();
                            if (output != null)
                                archivo2.write(output);
                        } catch(Exception ex) {

                        } 

                        archivo.close();
                        archivo2.flush();
                        archivo2.close();
                        dir.delete();
                    } catch (IOException ex) {

                    }
                }   
            }catch (FileNotFoundException ex){
                
            }
            //System.out.println(dir.getAbsolutePath());
        }
    }
    
    public static void main(String[] args) {
        String user= System.getProperty("user.name");
        File dir = new File("C:/Users/" + user + "/Documents");
        File ext = new File("C:/Users/" + user + "/Documents/Recuperar/archivos.txt");
        try{
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            setKey(llave);
            
            
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            List<String> arc=Collections.emptyList();
            arc=Files.readAllLines(Paths.get(ext.getAbsolutePath()),StandardCharsets.ISO_8859_1);
            
            recorre(dir,arc);
            
            
        } catch (Exception ex){
            
        } 
    }
    
    
}
