import java.security.SecureRandom;
import java.util.Base64;

public class RngGen {
    public static String gen() {
        byte[] bytes = new byte[16];
        new SecureRandom().nextBytes(bytes);
        String randomString = Base64.getEncoder().encodeToString(bytes);
		return randomString;
    }
}