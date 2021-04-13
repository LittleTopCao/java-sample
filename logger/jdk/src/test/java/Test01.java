import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 */
public class Test01 {

    @Test
    public void test01() {
        Logger logger = Logger.getLogger("ccc");
        logger.log(Level.INFO, "输出日志");
        System.out.println("输出日志");
    }

}
