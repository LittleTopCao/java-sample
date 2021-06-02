import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * 针对 commons-lang 的测试
 * <p>
 * 提供了包括： 字符串操作、数值操作、反射、并发、序列化、系统属性、Date增强、hashcode、toString、equals
 */
public class LangTest {


    @Test
    public void test01() {
        assertTrue(StringUtils.isEmpty(""));
    }
}
