/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package criptografíaasimétrica;

import java.security.SecureRandom;
import java.util.Base64;

/**
 *
 * @author ifons
 */
public class CriptografíaAsimétrica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        //Definimos un texto a cifrar
		byte[] llave = new byte[16];
                byte[] llaveDes;
                SecureRandom.getInstanceStrong().nextBytes(llave);
                
                System.out.println("Llave:");
                System.out.println(Base64.getEncoder().encodeToString(llave));
		
		//Instanciamos la clase
		RSA rsa = new RSA();
		
		//Generamos un par de claves
		//Admite claves de 512, 1024, 2048 y 4096 bits
		rsa.genKeyPair(2048);
		
		String keyPublic= rsa.getPublicKeyString();
                String keyPrivate= rsa.getPrivateKeyString();
                
		//String file_private = "/tmp/rsa.pri";
		//String file_public = "/tmp/rsa.pub";
		
		//Las guardamos asi podemos usarlas despues
		//a lo largo del tiempo
		//rsa.saveToDiskPrivateKey("/tmp/rsa.pri");
		//rsa.saveToDiskPublicKey("/tmp/rsa.pub");
		
                
		//Ciframos y e imprimimos, el texto cifrado
		//es devuelto en la variable secure
		String secure = rsa.Encrypt(Base64.getEncoder().encodeToString(llave));
		
		System.out.println("\nCifrado:");
		System.out.println(secure);
		
				
		
		//A modo de ejemplo creamos otra clase rsa
		RSA rsa2 = new RSA();
		
		//A diferencia de la anterior aca no creamos
		//un nuevo par de claves, sino que cargamos
		//el juego de claves que habiamos guadado
                rsa2.setPrivateKeyString(keyPrivate);
                rsa2.setPublicKeyString(keyPublic);
                
		//rsa2.openFromDiskPrivateKey("/tmp/rsa.pri");	
		//rsa2.openFromDiskPublicKey("/tmp/rsa.pub");
		
		//Le pasamos el texto cifrado (secure) y nos 
		//es devuelto el texto ya descifrado (unsecure) 
		String unsecure = rsa2.Decrypt(secure);
		
		//Imprimimos
		System.out.println("\nDescifrado:");
		System.out.println(unsecure);	
                
                System.out.println("Llave pública: " + keyPublic);
                System.out.println(keyPublic.getBytes().length);
                System.out.println("Llave privada: " + keyPrivate);
                System.out.println(keyPrivate.getBytes().length);
                
                llaveDes=Base64.getDecoder().decode(unsecure);
    }
    
}
