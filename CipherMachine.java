import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CipherMachine {
    private SecretKey key;
    private final int KEY_SIZE = 128;
    private final int DATA_LENGTH = 128;
    public Cipher encryptionCipher;

    private byte[] IV;

    public CipherMachine() throws Exception {
        init();
    }

    public void init() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(KEY_SIZE);
        key = keyGenerator.generateKey();
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        IV = encryptionCipher.getIV();

    }
        public String encrypt(String data) throws Exception {
            byte[] dataInBytes = data.getBytes();

            byte[] encryptedBytes = encryptionCipher.doFinal(dataInBytes);
            return encode(encryptedBytes);
        }

    public String decrypt(String encryptedData, SecretKey secretKey, String iv) throws Exception {
        byte[] ivInBytes = decode(iv);
        byte[] dataInBytes = decode(encryptedData);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");

        GCMParameterSpec spec = new GCMParameterSpec(DATA_LENGTH, ivInBytes);
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(dataInBytes);
        return new String(decryptedBytes);
    }
    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    private SecretKey getKey(){
        return key;
    }

    private String getIv() {return encode(IV); }

    public static SecretKey convertStringToSecretKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        return originalKey;
    }

    public static String convertSecretKeyToString(SecretKey secretKey) throws NoSuchAlgorithmException {
        byte[] rawData = secretKey.getEncoded();
        String encodedKey = Base64.getEncoder().encodeToString(rawData);
        return encodedKey;
    }


    public static void main(String[] args) throws Exception {

            CipherMachine cipherMachine = new CipherMachine();
            CipherMachine cipherMachine2 = new CipherMachine();

            String encryptedData = cipherMachine.encrypt("Hello");
            String newEncryptedData = new String(encryptedData);
            String iv = cipherMachine.getIv();
            System.out.println("IV: " + iv);

            SecretKey secretKey = cipherMachine.getKey();
            System.out.println(cipherMachine.encryptionCipher);

            System.out.println("SecretKey format: " + secretKey);

            String stringSecretKey = convertSecretKeyToString(secretKey);
            System.out.println("String format: " + stringSecretKey);

            String decryptedData = cipherMachine.decrypt(newEncryptedData, secretKey, iv);

            String encryptedData2 = new String("oz81lS+qBE7i+j0jJvDzpt2E+c1q");
            String stringSecretKey2 = new String("uQ1GJ1MRv4EjqolIUQ1euA==");
            String iv2 = new String("xS29V2dfxjDPuWw2");
            SecretKey secretKey2 = convertStringToSecretKey(stringSecretKey2);

            String decryptedData2 = cipherMachine2.decrypt(encryptedData2, secretKey2, iv2);
            System.out.println(decryptedData2);
            System.out.println("Encrypted Data : " + encryptedData);
            System.out.println("Decrypted Data : " + decryptedData);

    }
}
