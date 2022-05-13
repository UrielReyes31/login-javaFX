
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

public class bcryptTest {
    @Test
    void prueba(){
        String pass ="123456";
      String hashed = BCrypt.hashpw(pass,BCrypt.gensalt(10));
        System.out.println(hashed);
    }
}
