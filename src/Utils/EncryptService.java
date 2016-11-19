package Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class EncryptService {
    
    
    public static String getHashOfString(String s) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(s.getBytes());
        byte[] b = md.digest();
        StringBuilder sb = new StringBuilder();
        for(byte x:b){
            sb.append(Integer.toHexString(x & 0xff));
        }
        return sb.toString();
    }   
}