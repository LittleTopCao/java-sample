package java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * https://colobu.com/2016/03/02/Java-Stream/
 * <p>
 * Stream代表数据流，流中的数据元素的数量可能是有限的，也可能是无限的。
 * <p>
 * Java Stream提供了提供了串行和并行两种类型的流，保持一致的接口，提供函数式编程方式，以管道方式提供中间操作和最终执行操作
 * <p>
 * <p>
 * 1. 不存储数据。流是基于数据源的对象，它本身不存储数据元素，而是通过管道将数据源的元素传递给操作。
 * 2. 函数式编程。流的操作不会修改数据源，例如filter不会将数据源中的数据删除。
 * 3. 延迟操作。流的很多操作如 filter,map 等中间操作是延迟执行的，只有到终点操作才会将操作顺序执行。
 * 4. 可以解绑。对于无限数量的流，有些操作是可以在有限的时间完成的，比如limit(n) 或 findFirst()，这些操作可是实现"短路"(Short-circuiting)，访问到有限的元素后就可以返回。
 * 5. 纯消费。流的元素只能访问一次，类似Iterator，操作没有回头路，如果你想从头重新访问流的元素，对不起，你得重新生成一个新的流。
 *
 * <p>
 * 流的操作是以管道的方式串起来的。流管道包含一个数据源，接着包含零到N个中间操作，最后以一个终点操作结束。
 */
public class StreamTest {


    /**
     * 创建 Stream
     * <p>
     * <p>
     * 1、通过集合的 stream() 方法或者 parallelStream()，比如 Arrays.asList(1,2,3).stream()。
     * 2、通过 Arrays.stream(Object[]) 方法, 比如 Arrays.stream(new int[]{1,2,3})。
     * 3、使用流的静态方法，比如 Stream.of(Object[]), IntStream.range(int, int) 或者 Stream.iterate(Object, UnaryOperator)，如 Stream.iterate(0, n -> n * 2)，或者generate(Supplier<T> s)如Stream.generate(Math::random)。
     * 4、BufferedReader.lines() 从文件中获得行的流。
     * 5、Files 类的操作路径的方法，如 list、find、walk 等。
     * 6、随机数流 Random.ints()。
     * 7、其它一些类提供了创建流的方法，如BitSet.stream(), Pattern.splitAsStream(java.lang.CharSequence), 和 JarFile.stream()。
     * 8、更底层的使用StreamSupport，它提供了将Spliterator转换成流的方法。
     */
    @Test
    public void test01() {
        // 要确定一个数据源啊, 例如：集合 数组

        // 从集合创建
        Stream<Object> stream = new ArrayList<>().stream();

        // 从数组创建
        Stream<Character> stream1 = Stream.of('a', 'b', 'c');
        Stream<int[]> stream2 = Stream.of(new int[]{1, 2, 3});

    }

    /**
     * 中间操作
     * <p>
     * 中间操作会返回一个新的流，并且操作是延迟执行的(lazy)，它不会修改原始的数据源，而且是由在终点操作开始的时候才真正开始执行。
     * <p>
     * 1. distinct 保证输出的流中包含唯一的元素，它是通过Object.equals(Object)来检查是否包含相同的元素。
     * 2. filter 返回的流中只包含满足断言(predicate)的数据。
     * 3. map 方法将流中的元素映射成另外的值，新的值类型可以和原来的元素的类型不同。
     * 4. flatmap 方法混合了map + flattern的功能，它将映射后的流的元素全部放入到一个新的流中。它的方法定义如下：
     * 5. limit 方法指定数量的元素的流。对于串行流，这个方法是有效的，这是因为它只需返回前n个元素即可，但是对于有序的并行流，它可能花费相对较长的时间，如果你不在意有序，可以将有序并行流转换为无序的，可以提高性能。
     * 6. peek 方法方法会使用一个Consumer消费流中的元素，但是返回的流还是包含原来的流中的元素。
     * 7. sorted 将流中的元素按照自然排序方式进行排序，如果元素没有实现Comparable，则终点操作执行时会抛出java.lang.ClassCastException异常。
     * 8. skip 返回丢弃了前 n 个元素的流，如果流中的元素小于或者等于n，则返回空的流。
     */
    @Test
    public void test02() {
        // 中间操作 延迟执行，执行完 还是一个流
        System.out.println(Stream.of('a', 'b', 'c', 'c').distinct().toArray());


    }


    /**
     * 终点操作
     * <p>
     * 1. Match 检查流中的元素是否满足断言，如果满足返回 true，不满足返回 false. 有三个具体的方法：
     * public boolean 	allMatch(Predicate<? super T> predicate)
     * public boolean 	anyMatch(Predicate<? super T> predicate)
     * public boolean 	noneMatch(Predicate<? super T> predicate)
     * <p>
     * 2. count方法返回流中的元素的数量。
     * <p>
     * 3. collect 收集元素。
     * 使用一个collector执行 mutable reduction 操作。辅助类 Collectors 提供了很多的 collector，可以满足我们日常的需求，你也可以创建新的 collector 实现特定的需求。
     * <p>
     * 4.
     * 5.
     * 6.
     * 7.
     * 8. toArray() 将流中的元素放入到一个数组中。
     */
    @Test
    public void test03() {
//        new ArrayList<String>()


    }

    /**
     * 组合
     */

    /**
     * 转换
     */

}
