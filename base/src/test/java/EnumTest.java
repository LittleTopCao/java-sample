import org.junit.Test;

import java.util.Arrays;

/**
 * 枚举类型
 *
 * 拥有 有限个实例的 类型
 *
 * 编译器 其实是 为你生成一个 集成 java.lang.Enum 的类
 *
 * 我们可以这样理解枚举：一个 只能 拥有 有限个实例的 普通类型， 只能在定义时 实例化，不能被继承
 *
 */
public class EnumTest {

    /**
     *
     */
    @Test
    public void test01() {
        Spiciness spiciness = Spiciness.NOT;
        //编译器自动添加 toString 方法，为自己的名字
        System.out.println(spiciness);
        //ordinal 方法获得实例定义的顺序，从 0 开始
        System.out.println(Spiciness.MEDIUM.ordinal());
        //values 方法获得所有实例的数组
        System.out.println(Arrays.asList(Spiciness.values()));


        //枚举配合 switch 使用
        switch (spiciness) {
            case NOT:
            case MILD:
            case MEDIUM:
            case HOT:
            case FLAMING:
                System.out.println("abc");
                break;
            default:
                break;
        }
    }

    /**
     * 静态导入，这样就不用 带 类型前缀了
     */
    @Test
    public void test02() {
//        import static Spiciness.*
    }

    /**
     * 添加方法
     */
    @Test
    public void test03() {
//        import static Spiciness.*
    }

}

/**
 * 简单定义枚举类型，编译器会添加
 *          name 方法，返回实例的名字
 *          ordinal 方法，返回实例的顺序，从 0 开始
 *          getDeclaringClass 方法，返回实例所属的 枚举 类型
 *
 *          values 静态方法，返回所有的 实例，按照定义的顺序
 *          valueOf 静态方法，根据给定的名字返回 实例，如果没有找到 抛出异常
 *
 *          toString 方法，返回实例的名字
 *          equals 方法
 *          hashCode 方法
 *
 *          实现 Comparable 接口，有 compareTo 方法
 *          实现 Serializable 接口
 */
enum Spiciness {
    /**
     * 它的实例是常量，用大写字母表示
     */
    NOT, MILD, MEDIUM, HOT, FLAMING
}


/**
 * 作为普通类型的枚举
 *
 * 枚举就是一个普通的类
 *
 * 它和 普通类型的 唯一区别 就是 必须在 第一行 定义实例
 *
 */
enum Shape {

    //抛开这一行，其实和普通类 一样
    TRIANGLE("this is a triangle"), ROUND("this is a round"), SQUARE("this is a square");

    String description;

    //自定义构造方法
    Shape(String description) {
        this.description = description;
    }

    //普通方法
    public String getDescription() {
        return description;
    }

    //覆盖 方法
    @Override
    public String toString() {
        return "toString " + this.description;
    }
}