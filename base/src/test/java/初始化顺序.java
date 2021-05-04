import org.junit.Test;

import static java.lang.System.out;

/**
 * 新建 和 初始化 应该是两个概念，java 利用 构造方法的概念 把它俩放到一起了
 *
 * 那么我们说 字段的 默认值 应该是 新建 时 分配内存 时 格式化的
 *
 *
 * 你可以认为 初始化 是由构造方法 执行的，包括 字段初始化、代码块初始化，
 *      因为 子类的 构造函数 必须在 第一句 调用 父类的构造函数，默认是调用 父类的 无参构造方法
 *      这是导致 父类 在 子类 之前初始化的 关键
 *
 */
public class 初始化顺序 {

    /**
     * 单个类的初始化顺序
     * 1.静态字段（在初次访问类的静态成员时，构造器也算）
     *      1.默认初始化，基本类型是 0、0.0、false、[]，引用类型是 null
     *      2.静态字段初始化
     *      3.静态初始化代码块执行
     *
     * 2.实例字段(新建对象时）
     *      1.默认初始化
     *      2.实例字段初始化
     *      3.初始化代码块执行
     *      4.构造方法执行
     */
    @Test
    public void 单个类() {
        out.println("访问类的静态成员（构造器也算）会执行静态初始化:");
        int a = SingleClass.b;
        out.println("\n新建对象时，会执行非静态变量的初始化:");
        new SingleClass();
    }

    /**
     * 都是先执行父类的
     *
     * 1.访问类的静态成员（构造器也算）会执行静态初始化:
     *      ParentClass 静态字段初始化
     *      ParentClass 静态初始化代码块执行
     *      SubClass 静态字段初始化
     *      SubClass 静态初始化代码块执行
     *
     * 2.新建对象时，会执行非静态变量的初始化:
     *      ParentClass 实例字段初始化
     *      ParentClass 初始化代码块执行
     *      ParentClass 构造方法执行
     *
     *      SubClass 实例字段初始化
     *      SubClass 初始化代码块执行
     *      SubClass 构造方法执行
     */
    @Test
    public void 子类() {
        out.println("访问类的静态成员（构造器也算）会执行静态初始化:");
        int a = SubClass.c;

        out.println("\n新建对象时，会执行非静态变量的初始化:");
        new SubClass();
    }
}

/**
 * 单个类
 */
class SingleClass {

    public static int b = initB();

    public static int initB() {
        out.println("静态字段初始化");
        return 1000;
    }


    static {
        out.println("静态初始化代码块执行");
    }

    public int a = initA();

    public int initA() {
        out.println("实例字段初始化");
        return 1000;
    }

    {
        out.println("初始化代码块执行");
    }

    public SingleClass() {
        out.println("构造方法执行");
    }
}

/**
 * 子类
 */
class SubClass extends ParentClass {
    public static int c = initC();

    public static int initC() {
        out.println("SubClass 静态字段初始化");
        return 1000;
    }


    static {
        out.println("SubClass 静态初始化代码块执行");
    }

    public int d = initD();

    public int initD() {
        out.println("SubClass 实例字段初始化");
        return 1000;
    }

    {
        out.println("SubClass 初始化代码块执行");
    }

    /**
     * 子类的构造方法 必须 调用 父类的 构造方法
     *
     * 如果父类有默认的 无参 那么自动添加， 否则 必须 手动添加
     *
     * 这就是 父类 在 子类 之前 初始化的关键
     */
    public SubClass() {
        super(2);
        out.println("SubClass 构造方法执行");
    }
}

/**
 * 父类
 */
class ParentClass {
    public static int b = initB();

    public static int initB() {
        out.println("ParentClass 静态字段初始化");
        return 1000;
    }


    static {
        out.println("ParentClass 静态初始化代码块执行");
    }

    public int a = initA();

    public int initA() {
        out.println("ParentClass 实例字段初始化");
        return 1000;
    }

    {
        out.println("ParentClass 初始化代码块执行");
    }

    public ParentClass(int i) {
        out.println("ParentClass 构造方法执行");
    }
}