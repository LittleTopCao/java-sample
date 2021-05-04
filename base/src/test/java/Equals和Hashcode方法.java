import java.util.Objects;

/**
 * 1. 关于 equals 方法
 *      在 java 中 == 操作符 比较基本类型的值，比较 引用类型的地址
 *      Object 中的 equals 方法是用 == 比较的
 *
 * 2. 关于 hashCode 方法
 *      Object 实现为 native 方法，取的是地址值
 *
 * 这两个方法会影响对象在 Set 和 Map 中的行为，例如在向 HashSet 中插入对象时：
 *      1. 先调用这个对象的 hashCode 方法拿到 hash 值
 *      2. 找到 hash 值对应的 桶
 *      3. 使用对象的 equals 方法比较这个对象 和 桶中 对象，如果相等 证明 已经存在 不插入
 *
 * 当然如果不重写也是可以的，只是在向 HashSet 插入时，只会取决于是否是一个对象，没有业务上的逻辑
 *
 * 如果我们根据业务重写 equals 方法，需要满足以下：
 *      自反性：x.equals(x)必须为true；
 *      对称性：x.equals(y)和y.equals(x)返回值必须相等；
 *      传递性：x.equals(y)为true，和y.equals(z)为true，那么x.equals(z)也必须为true；
 *      一致性：如果对象x和y在equals()中使用的信息没有改变，那么x.equals(y)的值始终不变；
 *      非null ： x不是null，y是null，那么x.equals(y)必须为false。
 *
 * 如果我们根据业务重写 hashCode 方法，需要满足以下：
 *      如果重写equals()方法，检查条件“两个对象通过equals()方法判断相等，那么它们的hashCode()也应该相等”是否成立，如果不成立，则重写hashCode()方法。
 *      hashCode()不能太简单，否则容易造成hash冲突；
 *      hashCode()不能太复杂，否则会影响性能。
 *
 * 在 jdk、apache common、guava 都有 hashCode 的实现
 */
public class Equals和Hashcode方法 extends Object {
    private int a;

    /**
     * Object 实现为 return (this == obj);
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equals和Hashcode方法 that = (Equals和Hashcode方法) o;
        return a == that.a;
    }

    /**
     * Object 实现为 native 方法，取的是地址值
     */
    @Override
    public int hashCode() {
        return Objects.hash(a);
    }

    /**
     * Object 实现为 return getClass().getName() + "@" + Integer.toHexString(hashCode());
     */
    @Override
    public String toString() {
        return "Equals和Hashcode方法{" +
                "a=" + a +
                '}';
    }
}
