package io.muzoo.ooc.webapp.basic;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Scanner;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Cryptography {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        System.out.print("Password: ");
        String strPassword = new Scanner(System.in).nextLine();
        byte[] bSalt = Salt();
        String strSalt = encoder.encodeToString(bSalt); // Byte to String
        System.out.println("Salt: " + strSalt);
        System.out.println("String to be hashed: " + strPassword + strSalt);
        String strHash = encoder.encodeToString(Hash(strPassword, bSalt)); // Byte to String
        System.out.println("Hashed value (Password + Salt value): " + strHash);
    }

    public static byte[] Salt() {
        SecureRandom random = new SecureRandom();
        byte salt[] = new byte[6];
        random.nextBytes(salt);
        return salt;
    }

    public static byte[] Hash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return hash;
    }

}
