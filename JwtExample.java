import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class JwtExample {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {

        // Dados do Cabeçalho e Payload
        String header = "{\"alg\": \"HS256\", \"typ\": \"JWT\"}";
        String payload = "{\"sub\": \"1234567890\", \"iat\": 1631893200, \"exp\": 1631896800}";

        // Codificação Base64 URL do Cabeçalho e Payload
        String base64UrlHeader = base64UrlEncode(header);
        String base64UrlPayload = base64UrlEncode(payload);

        // Concatenação do Cabeçalho e Payload com um ponto
        String encodedString = base64UrlHeader + "." + base64UrlPayload;

        // Chave Secreta
        String secret = "sua_chave_secreta_aqui";

        // Geração da Assinatura HMAC-SHA256
        String signature = HMACSHA256(encodedString, secret);

        // Token JWT completo
        String jwtToken = encodedString + "." + signature;

        System.out.println("Token JWT gerado:");
        System.out.println(jwtToken);
    }

    public static String base64UrlEncode(String input) {
        byte[] encodedBytes = Base64.getUrlEncoder().withoutPadding().encode(input.getBytes(StandardCharsets.UTF_8));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    public static String HMACSHA256(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256HMAC.init(secretKey);
        byte[] hmacData = sha256HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return base64UrlEncode(new String(hmacData, StandardCharsets.UTF_8));
    }
}
