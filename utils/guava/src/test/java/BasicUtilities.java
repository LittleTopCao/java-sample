import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static com.google.common.base.Preconditions.*;

/**
 * 基本工具
 */
public class BasicUtilities {

    /**
     * 可选空指针, java8 已经有了
     */
    @Test
    public void OptionalTest() {
        Optional<Integer> possible = Optional.of(5);
        possible.isPresent(); // returns true
        possible.get(); // returns 5
    }

    /**
     * 检测条件, 都在 Preconditions 类中, 使用静态导入
     * <p>
     * 每个方法有 三种 变体
     * 1.
     * 2.
     * 3.
     */
    @Test
    public void PreconditionsTest() {
        int i = 0;
        checkArgument(i > 0); //抛出异常 IllegalArgumentException
        checkNotNull(i); //抛出异常 NullPointerException
        checkState(i < 0); //抛出异常 IllegalStateException
        checkElementIndex(i, 2); //检测 index 是否在 数组 size 内, 抛出异常 IndexOutOfBoundsException
        checkPositionIndex(i, 3); //检测 index 是否在 数组 size 内, 抛出异常 IndexOutOfBoundsException
        checkPositionIndexes(i, 3, 9); //检测 index 是否在 数组 size 内, 抛出异常 IndexOutOfBoundsException
    }

    /**
     * Object 的 equals \ hash \ toString \ Comparable 实现帮助类
     *
     * java7 内置了 Objects
     *
     */
    @Test
    public void ObjectsTest() {
        Objects.equal(null, null);
        Objects.hashCode(null, null);

        MoreObjects.toStringHelper(this).add("abc", 1).toString();

//        ComparisonChain.start()
//                .compare(this.aString, that.aString)
//                .compare(this.anInt, that.anInt)
//                .compare(this.anEnum, that.anEnum, Ordering.natural().nullsLast())
//                .result();
    }


    /**
     * 排序器
     * <p>
     * Ordering 实例就是一个特殊的 Comparator 实例
     * 创建方法
     * natural()  对可排序类型做自然排序，如数字按大小，日期按先后排序
     * usingToString()  按对象的字符串形式做字典排序[lexicographical ordering]
     * from(Comparator)  把给定的Comparator转化为排序器
     * <p>
     * 转换方法
     * reverse()	获取语义相反的排序器
     * nullsFirst()	使用当前排序器，但额外把null值排到最前面。
     * nullsLast()	 使用当前排序器，但额外把null值排到最后面。
     * compound(Comparator)	合成另一个比较器，以处理当前排序器中的相等情况。
     * lexicographical()	基于处理类型T的排序器，返回该类型的可迭代对象Iterable<T>的排序器。
     * onResultOf(Function)	对集合中元素调用Function，再按返回值用当前排序器排序。
     * <p>
     * 使用方法
     * greatestOf(Iterable iterable, int k)	获取可迭代对象中最大的k个元素。
     * isOrdered(Iterable)	判断可迭代对象是否已按排序器排序：允许有排序值相等的元素。
     * sortedCopy(Iterable)	判断可迭代对象是否已严格按排序器排序：不允许排序值相等的元素。
     * min(E, E)	返回两个参数中最小的那个。如果相等，则返回第一个参数。
     * min(E, E, E, E...)	返回多个参数中最小的那个。如果有超过一个参数都最小，则返回第一个最小的参数。
     * min(Iterable)	返回迭代器中最小的元素。如果可迭代对象中没有元素，则抛出NoSuchElementException。
     */
    @Test
    public void OrderingTest() {

        //还可以自己实现

        Ordering<String> byLengthOrdering = new Ordering<String>() {
            public int compare(String left, String right) {
                return Ints.compare(left.length(), right.length());
            }
        };

    }


    /**
     * 异常帮助类
     * <p>
     * 有时捕获到异常 需要重新抛出
     * void propagateIfPossible(Throwable, Class<X extends Throwable>)
     * void throwIfInstanceOf(Throwable, Class<X extends Exception>)
     * void throwIfUnchecked(Throwable)
     * <p>
     * 异常链的工具
     * Throwable getRootCause(Throwable)
     * List<Throwable> getCausalChain(Throwable)
     * String getStackTraceAsString(Throwable)
     */
    @Test
    public void ThrowablesTest() throws IOException, SQLException {
//        try {
//            someMethodThatCouldThrowAnything(); //抛出异常
//        } catch (IKnowWhatToDoWithThisException e) { //捕获需要的异常处理
//            handle(e);
//        } catch (Throwable t) {
//            Throwables.throwIfInstanceOf(t, IOException.class); //如果是 IOException 异常时抛出
//            Throwables.throwIfInstanceOf(t, SQLException.class); //如果是 SQLException 异常时抛出
//            Throwables.throwIfUnchecked(t); //如果是 RuntimeException 异常时抛出
//            throw new RuntimeException(t); //抛出一个新的异常
//        }
    }

}
