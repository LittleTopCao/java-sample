import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 阿里开源，速度快 https://github.com/alibaba/fastjson
 * <p>
 * com.alibaba.fastjson.JSON 静态方法
 * public static final Object parse(String text); // 把 JSON文本 parse 为 JSONObject 或者 JSONArray
 * public static final JSONObject parseObject(String text)； // 把 JSON文本 parse 成 JSONObject
 * public static final <T> T parseObject(String text, Class<T> clazz); // 把 JSON文本 parse为 JavaBean
 * public static final JSONArray parseArray(String text); // 把 JSON文本 parse 成 JSONArray
 * public static final <T> List<T> parseArray(String text, Class<T> clazz); //把JSON文本parse成JavaBean集合
 * <p>
 * public static final String toJSONString(Object object); // 将JavaBean序列化为JSON文本
 * public static final String toJSONString(Object object, boolean prettyFormat); // 将JavaBean序列化为带格式的JSON文本
 * public static final Object toJSON(Object javaObject); // 将JavaBean转换为JSONObject或者JSONArray。
 * <p>
 * com.alibaba.fastjson.JSONArray
 * <p>
 * com.alibaba.fastjson.JSONObject
 */
public class FastjsonTest {

    /**
     * list 和 map
     */
    @Test
    public void test01() {

        /**
         * 三种序列化
         */
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        System.out.println(JSON.toJSONString(list)); //序列化 简单 list 到 json 字符串
        System.out.println(JSON.toJSONString(list, true)); //输出格式化的json

        Object obj = JSON.toJSON(list); //转为 JSONArray
        System.out.println(obj.getClass());
        System.out.println(obj);

        System.out.println();

        HashMap<String, String> map = new HashMap<>();
        map.put("a", 'a' + 0 + "");
        map.put("b", 'b' + 0 + "");
        map.put("c", 'c' + 0 + "");

        System.out.println(JSON.toJSONString(map)); //序列化 简单 map 到 json 字符串
        System.out.println(JSON.toJSONString(map, true)); //输出格式化的json

        Object obj2 = JSON.toJSON(map); //转为 JSONObject
        System.out.println(obj2.getClass());
        System.out.println(obj2);


        /**
         * 五种解析
         */
        //解析字符串为 JSONObject 或 JSONArray， 自动适配
        Object obj3 = JSON.parse("[\"a\",\"b\",\"c\"]");
        Object obj4 = JSON.parse("{\"a\":\"97\",\"b\":\"98\",\"c\":\"99\"}");

        //明确指定解析为 JSONObject 或 JSONArray
        JSONArray obj5 = JSON.parseArray("[\"a\",\"b\",\"c\"]");
        JSONObject obj6 = JSON.parseObject("{\"a\":\"97\",\"b\":\"98\",\"c\":\"99\"}");
        //JSONObject obj7 = JSON.parseObject("[\"a\",\"b\",\"c\"]"); //如果不能转换会报错

        //两个复杂类型的 解析，需要指定 类型
        List<String> obj7 = JSON.parseArray("[\"a\",\"b\",\"c\"]", String.class);
        Map obj8 = JSON.parseObject("{\"a\":\"97\",\"b\":\"98\",\"c\":\"99\"}", HashMap.class);
    }

    /**
     * 自定义对象
     */
    @Test
    public void test02() {


    }

    /**
     * 自定义对象 的 list 和 map
     */
    @Test
    public void test03() {


    }

    /**
     * 泛型
     */
    @Test
    public void test04() {

    }


}
