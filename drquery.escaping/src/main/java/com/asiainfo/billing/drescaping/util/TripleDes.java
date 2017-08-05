package com.asiainfo.billing.drescaping.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * This class defines methods for encrypting and decrypting using the Triple DES
 * algorithm and for generating, reading, and writing Triple DES keys. It also
 * defines a main( ) method that allows these methods to be used from the
 * command line.
 */
/**
 *
 * @author zhouquan3
 */
public class TripleDes {

    private final static Log log = LogFactory.getLog(EscapingUtil.class);
    private static Log monitor_log = LogFactory.getLog("monitor");
    private static final String _keyFile = ".TripleDes.key";

    /**
     * The program. The first argument must be -e, -d, or -g to encrypt,
     * decrypt, or generate a key. The second argument is the name of a file
     * from which the key is read or to which it is written for -g. The -e and
     * -d arguments cause the program to read from standard input and encrypt or
     * decrypt to standard output.
     */

    /*
     * java com.asiainfo.billing.drquery.utils.cryptography.digester.TripleDes
     * -g (生成密钥) java
     * com.asiainfo.billing.drquery.utils.cryptography.digester.TripleDes -e
     * sysinfo_file (加密) java
     * com.asiainfo.billing.drquery.utils.cryptography.digester.TripleDes -d
     * sysinfo_file (解密)
     */
    public static void main(String[] args) {
        if (args[0].equals("-g")) {
            doGenerateKey();
        } else if (args[0].equals("-e")) {
            doEncrypt(args[1]);
        } else if (args[0].equals("-d")) {
            System.out.println(doDecrypt(args[1]));
        } else {
            System.err.println("Usage:");
            System.err.println("	java com.asiainfo.billing.drescaping.util.TripleDes -g ");
            System.err.println("	java com.asiainfo.billing.drescaping.util.TripleDes -e sysinfo_file ");
            System.err.println("	java com.asiainfo.billing.drescaping.util.TripleDes -d sysinfo_file ");
        }

    }

    public static void doGenerateKey() {

        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource resource = resolver.getResource(_keyFile);
            File keyfile = resource.getFile();
            SecretKey key = generateKey();
            writeKey(key, keyfile);
            System.out.println("Generate Key Done.");
            System.out.println("Secret key written to " + _keyFile
                    + ". Protect that file carefully!");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }

    public static void doEncrypt(String sysinfoFile) {

        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource resource = resolver.getResource(_keyFile);
            File keyfile = resource.getFile();
            SecretKey key = readKey(keyfile);
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            Resource sysinfoFileRes = resolver.getResource(sysinfoFile);
            File sysFile = sysinfoFileRes.getFile();
            FileInputStream inStream = new FileInputStream(sysFile);
            byte[] buffer = new byte[20480]; // This is the max length of the file
            int bytesRead = inStream.read(buffer);
            if (bytesRead == -1) {
                System.out.println("Encrypt Error!");
                return;
            }

            inStream.close();
            FileOutputStream outStream = new FileOutputStream(sysFile);
            CipherOutputStream cos = new CipherOutputStream(outStream, cipher);
            cos.write(buffer, 0, bytesRead);
            cos.close();
            // For extra security, don't leave any plaintext hanging around memory.
            java.util.Arrays.fill(buffer, (byte) 0);
            System.out.println("Encrypt Done.");

        } catch (Exception e) {
            e.printStackTrace();
            return;

        }

    }

    public static String doDecrypt(String sysinfoFile) {

        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource resource = resolver.getResource(_keyFile);
            File keyfile = resource.getFile();
            SecretKey key = readKey(keyfile);
            Resource sysinfoFileRes = resolver.getResource(sysinfoFile);
            File sysFile = sysinfoFileRes.getFile();
            FileInputStream inStream = new FileInputStream(sysFile);
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.DECRYPT_MODE, key);
            StringWriter sw = new StringWriter();
            byte[] buffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                sw.write(new String(cipher.update(buffer, 0, bytesRead)));
            }

            sw.write(new String(cipher.doFinal()));
            sw.flush();
            String plainText = sw.toString();
            if (plainText.endsWith("\n")) {
                plainText = plainText.substring(0, plainText.length() - 1);
            }
            return plainText;

        } catch (Exception e) {
            e.printStackTrace();
//            log.error("Decrypt failed.");
//            monitor_log.error(EscapingUtil.getLocalHostIP()+","+"Decrypt password failed.");
            return "";
        }

    }

    public static String doDecryptText(String encryptText) {
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource resource = resolver.getResource(_keyFile);
            File keyfile = resource.getFile();
            SecretKey key = readKey(keyfile);
            ByteArrayInputStream inStream = new ByteArrayInputStream(
                    encryptText.getBytes());
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.DECRYPT_MODE, key);
            StringWriter sw = new StringWriter();
            byte[] buffer = new byte[2048];

            int bytesRead;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                sw.write(new String(cipher.update(buffer, 0, bytesRead)));
            }
            sw.write(new String(cipher.doFinal()));
            sw.flush();
            String plainText = sw.toString();
            if (plainText.endsWith("\r\n")) {
                plainText = plainText.substring(0, plainText.length() - 2);
            }
            return plainText;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * Generate a secret TripleDES encryption/decryption key
     */
    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        // Get a key generator for Triple DES (a.k.a DESede)
        KeyGenerator keygen = KeyGenerator.getInstance("DESede");
        // Use it to generate a key
        return keygen.generateKey();
    }

    /**
     * Save the specified TripleDES SecretKey to the specified file
     */
    public static void writeKey(SecretKey key, File f) throws IOException,
            NoSuchAlgorithmException, InvalidKeySpecException {
        // Convert the secret key to an array of bytes like this
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
        DESedeKeySpec keyspec = (DESedeKeySpec) keyfactory.getKeySpec(key,
                DESedeKeySpec.class);
        byte[] rawkey = keyspec.getKey();
        // Write the raw key to the file
        FileOutputStream out = new FileOutputStream(f);
        out.write(rawkey);
        out.close();

    }

    /**
     * Read a TripleDES secret key from the specified file
     */
    public static SecretKey readKey(File f) throws IOException,
            NoSuchAlgorithmException, InvalidKeyException,
            InvalidKeySpecException {
        // Read the raw bytes from the keyfile
        DataInputStream in = new DataInputStream(new FileInputStream(f));
        byte[] rawkey = new byte[(int) f.length()];
        in.readFully(rawkey);
        in.close();
        // Convert the raw bytes to a secret key like this
        DESedeKeySpec keyspec = new DESedeKeySpec(rawkey);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
        SecretKey key = keyfactory.generateSecret(keyspec);
        return key;

    }

    /**
     * Use the specified TripleDES key to encrypt bytes from the input stream
     * and write them to the output stream. This method uses CipherOutputStream
     * to perform the encryption and write bytes at the same time.
     */
    public static void encrypt(SecretKey key, InputStream in, OutputStream out)
            throws NoSuchAlgorithmException, InvalidKeyException,
            NoSuchPaddingException, IOException {
        System.out.println("Do Encrypt");
        // Create and initialize the encryption engine
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // Create a special output stream to do the work for us
        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        // Read from the input and write to the encrypting output stream
        byte[] buffer = new byte[2048];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            cos.write(buffer, 0, bytesRead);
        }
        cos.close();
        // For extra security, don't leave any plaintext hanging around memory.
        java.util.Arrays.fill(buffer, (byte) 0);

    }

    /**
     * Use the specified TripleDES key to decrypt bytes ready from the input
     * stream and write them to the output stream. This method uses Cipher
     * directly to show how it can be done without CipherInputStream and
     * CipherOutputStream.
     */
    public static void decrypt(SecretKey key, InputStream in, OutputStream out)
            throws NoSuchAlgorithmException, InvalidKeyException, IOException,
            IllegalBlockSizeException, NoSuchPaddingException,
            BadPaddingException {

        // Create and initialize the decryption engine
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(Cipher.DECRYPT_MODE, key);
        StringWriter sw = new StringWriter();
        // Read bytes, decrypt, and write them out.
        byte[] buffer = new byte[2048];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            // out.write(cipher.update(buffer, 0, bytesRead));
            sw.write(new String(cipher.update(buffer, 0, bytesRead)));
        }

        // Write out the final bunch of decrypted bytes
        // out.write(cipher.doFinal( ));
        sw.write(new String(cipher.doFinal()));
        // out.flush( );
        sw.flush();
        System.out.println("------------- The Decrypt Output --------------");
        System.out.println(sw.toString());
        System.out.println("------------- end --------------");
    }
}
