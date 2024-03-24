package sit.int371.modride_service.services;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Service;
import sit.int371.modride_service.controllers.BaseController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class SecureService extends BaseController {

    public String encryptAES(String data, String key) throws Exception {
        // System.out.println("----------------");
        // System.out.println("before encrypt: "+ data);
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public Integer decryptAES(String encryptedData, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));

        return Integer.parseInt(new String(decryptedBytes));
    }
}
