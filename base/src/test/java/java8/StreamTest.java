package java8;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://colobu.com/2016/03/02/Java-Stream/
 *
 * Stream代表数据流，流中的数据元素的数量可能是有限的，也可能是无限的。
 *
 * Java Stream提供了提供了串行和并行两种类型的流，保持一致的接口，提供函数式编程方式，以管道方式提供中间操作和最终执行操作
 *
 * 特点
 *      1. 不存储数据。流是基于数据源的对象，它本身不存储数据元素，而是通过管道将数据源的元素传递给操作。
 *      2. 函数式编程。流的操作不会修改数据源，例如filter不会将数据源中的数据删除。
 *      3. 延迟操作。流的很多操作如 filter,map 等中间操作是延迟执行的，只有到终点操作才会将操作顺序执行。
 *      4. 可以解绑。对于无限数量的流，有些操作是可以在有限的时间完成的，比如limit(n) 或 findFirst()，这些操作可是实现"短路"(Short-circuiting)，访问到有限的元素后就可以返回。
 *      5. 纯消费。流的元素只能访问一次，类似Iterator，操作没有回头路，如果你想从头重新访问流的元素，对不起，你得重新生成一个新的流。
 *
 * 流的操作是以管道的方式串起来的。流管道包含一个数据源，接着包含零到N个中间操作，最后以一个终点操作结束。
 *
 * 1. 并行 Parallelism
 *      所有的流操作都可以串行执行或者并行执行。除非显示地创建并行流，否则Java库中创建的都是串行流。
 *      Collection.parallelStream()为集合创建并行流。
 *      IntStream.range(int, int)创建的是串行流。
 *      通过parallel()方法可以将串行流转换成并行流,sequential()方法将流转换成串行流。
 *
 * 2. Non-interference
 *      流可以从非线程安全的集合中创建，当流的管道执行的时候，非concurrent数据源不应该被改变。否则 抛出 java.util.ConcurrentModificationException 异常
 *      对于concurrent数据源，不会有这样的问题
 *
 * 3. 无状态 Stateless behaviors
 *      大部分流的操作的参数都是函数式接口，可以使用Lambda表达式实现。它们用来描述用户的行为，称之为行为参数(behavioral parameters)。
 *      如果 操作是有状态的，  在并行执行时多次的执行结果可能是不同的
 *
 * 4. 副作用 Side-effects
 *      有副作用的行为参数是被不鼓励使用的。副作用指的是行为参数在执行的时候有输入输入，比如网络输入输出等。
 *      这是因为 Java 不保证这些副作用对其它线程可见，也不保证相同流管道上的同样的元素的不同的操作运行在同一个线程中。
 *
 *      和 上边 无状态 一个原因， 流的操作 会遇到 并行 或 顺序 的影响
 *
 * 5. 排序 Ordering
 *      某些流的返回的元素是有确定顺序的，我们称之为 encounter order。
 *      一个流是否是 encounter order 主要依赖数据源和它的中间操作，比如数据源List和Array上创建的流是有序的(ordered)，但是在HashSet创建的流不是有序的。
 *      sorted()方法可以将流转换成encounter order的，unordered可以将流转换成encounter order的。
 *      除此之外，一个操作可能会影响流的有序,比如map方法，但是对于filter方法来说，它只是丢弃掉一些值而已，输入元素的有序性还是保障的。
 *
 * 6. 结合性 Associativity
 *      一个操作或者函数op满足结合性意味着 它 先执行 那个 操作都可以
 *      对于并发流来说，如果操作满足结合性，我们就可以并行计算，比如min、max以及字符串连接都是满足结合性的。
 *
 */
public class StreamTest {


    /**
     * 创建 Stream
     *
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
     *
     * 中间操作会返回一个新的流，并且操作是延迟执行的(lazy)，它不会修改原始的数据源，而且是由在终点操作开始的时候才真正开始执行。
     *
     * 1. distinct 保证输出的流中包含唯一的元素，它是通过Object.equals(Object)来检查是否包含相同的元素。
     * 2. filter 返回的流中只包含满足断言(predicate)的数据。
     * 3. map 方法将流中的元素映射成另外的值，新的值类型可以和原来的元素的类型不同。
     * 4. flatmap 方法混合了map + flattern 的功能，它将映射后的流的元素全部放入到一个新的流中。
     *      <R> Stream<R> flatMap(Function<? super T,? extends Stream<? extends R>> mapper)
     * 5. limit 方法指定数量的元素的流。对于串行流，这个方法是有效的，这是因为它只需返回前n个元素即可，但是对于有序的并行流，它可能花费相对较长的时间，如果你不在意有序，可以将有序并行流转换为无序的，可以提高性能。
     * 6. peek 方法方法会使用一个 Consumer 消费流中的元素，但是返回的流还是包含原来的流中的元素。
     * 7. sorted 将流中的元素按照自然排序方式进行排序，如果元素没有实现Comparable，则终点操作执行时会抛出java.lang.ClassCastException异常。
     * 8. skip 返回丢弃了前 n 个元素的流，如果流中的元素小于或者等于n，则返回空的流。
     */
    @Test
    public void test02() {
        // 中间操作 延迟执行，执行完 还是一个流

        // distinct 去重
        System.out.println(Stream.of('a', 'b', 'c', 'c').distinct().collect(Collectors.toList()));

        // filter 过滤
        System.out.println(Stream.of('a', 'b', 'c', 'c').filter(i -> {
            return i != 'c';
        }).collect(Collectors.toList()));

        // map 映射成新的 流
        System.out.println(Stream.of('a', 'b', 'c', 'c').map(i -> {
            return (char) (i + 1);
        }).collect(Collectors.toList()));

        // flatmap , 接收一个方法，这个方法把每个元素再次转换成流，新的流 是所有这些 流的 和
        String poetry = "Where, before me, are the ages that have gone?\n" +
                "And where, behind me, are the coming generations?\n" +
                "I think of heaven and earth, without limit, without end,\n" +
                "And I am all alone and my tears fall down.";

        Stream<String> lines = Arrays.stream(poetry.split("\n"));

        Stream<String> words = lines.flatMap(line -> Arrays.stream(line.split(" ")));

        List<String> l = words.map(w -> {
            if (w.endsWith(",") || w.endsWith(".") || w.endsWith("?"))
                return w.substring(0, w.length() - 1).trim().toLowerCase();
            else
                return w.trim().toLowerCase();
        }).distinct().sorted().collect(Collectors.toList());
        System.out.println(l); //[ages, all, alone, am, and, are, before, behind, coming, down, earth, end, fall, generations, gone, have, heaven, i, limit, me, my, of, tears, that, the, think, where, without]

        // limit 限制数量
        System.out.println(Stream.of('a', 'b', 'c', 'c').limit(2).collect(Collectors.toList()));

        // peek 在中间执行一次方法，返回的还是原始的流
        System.out.println(Stream.of('a', 'b', 'c', 'c').peek(i -> {
            System.out.println(i);
        }).collect(Collectors.toList()));

        // sorted 排序流
        System.out.println(Stream.of('a', 'c', 'b', 'c').sorted().collect(Collectors.toList()));
        System.out.println(Stream.of('a', 'c', 'b', 'c').sorted(new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                return o2.compareTo(o1);
            }
        }).collect(Collectors.toList()));

        // skip 跳过
        System.out.println(Stream.of('a', 'c', 'b', 'c').skip(2).collect(Collectors.toList()));
    }


    /**
     * 终点操作
     *
     * 1. Match 检查流中的元素是否满足断言，如果满足返回 true，不满足返回 false. 有三个具体的方法：
     *      public boolean 	allMatch(Predicate<? super T> predicate)
     *      public boolean 	anyMatch(Predicate<? super T> predicate)
     *      public boolean 	noneMatch(Predicate<? super T> predicate)
     *
     * 2. count 方法返回流中的元素的数量。
     *
     * 3. collect 收集元素。
     *
     *      使用一个collector 执行 mutable reduction 操作。辅助类 Collectors 提供了很多的 collector，可以满足我们日常的需求，你也可以创建新的 collector 实现特定的需求。
     *      <R,A> R 	collect(Collector<? super T,A,R> collector)
     *      <R> R 	collect(Supplier<R> supplier, BiConsumer<R,? super T> accumulator, BiConsumer<R,R> combiner)
     *
     *      收集器：
     *          如聚合类 averagingInt、
     *          最大最小值 maxBy minBy、
     *          计数 counting、
     *          分组 groupingBy、
     *          字符串连接 joining、
     *          分区 partitioningBy、
     *          汇总 summarizingInt、
     *          化简 reducing、
     *          转换 toXXX(toList, toSet)
     *
     * 4. find
     *      两个方法 findAny 和 findFirst ， 对于并行流 findAny 性能要好于 findFirst
     *
     * 5. forEach、forEachOrdered。
     *      forEach 遍历，和 peek 不同的是，它是一个 终点操作。 而且 不保证 按照 流的 顺序 执行
     *      forEachOrdered 按照流的 顺序 遍历
     *
     * 6. max、min。max 返回流中的最大值，min 返回流中的最小值。
     *
     * 7. reduce。 累加 或 累减，这种 降到一维 的操作。 几个重载方法：
     *      pubic Optional<T> 	reduce(BinaryOperator<T> accumulator)
     *      pubic T 	reduce(T identity, BinaryOperator<T> accumulator)
     *      pubic <U> U 	reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
     *
     * 8. toArray() 将流中的元素放入到一个数组中。
     *
     *
     */
    @Test
    public void test03() {
        // 1. Match 类
        System.out.println(Stream.of(1, 2, 3, 4, 5).allMatch(i -> i > 0));
        System.out.println(Stream.of(1, 2, 3, 4, 5).anyMatch(i -> i < 0));
        System.out.println(Stream.of(1, 2, 3, 4, 5).noneMatch(i -> i > 0));

        // 2. count 计数
        System.out.println(Stream.of(1, 2, 3, 4, 5).count());

        // 3. collect 收集
        System.out.println(Stream.of(1, 2, 3, 4, 5).collect(Collectors.averagingInt(i -> i))); // 平均值
        System.out.println(Stream.of(1, 2, 3, 4, 5).collect(Collectors.maxBy((a, b) -> {
            return a - b;
        }))); // 最大值
        System.out.println(Stream.of(1, 2, 3, 4, 5).collect(Collectors.minBy((a, b) -> {
            return a - b;
        }))); // 最小值
        System.out.println(Stream.of(1, 2, 3, 4, 5).collect(Collectors.counting())); // 计数
//        System.out.println(Stream.of(1, 2, 3, 4, 5).collect(Collectors.groupingBy(a, b -> {
//
//        }))); // 分组
        System.out.println(Stream.of("a", "b", "c", "d").collect(Collectors.joining())); // 连接字符串
//        System.out.println(Stream.of("a", "b", "c", "d").collect(Collectors.partitioningBy())); // 分区
//        System.out.println(Stream.of("a", "b", "c", "d").collect(Collectors.summarizingInt())); // 汇总
//        System.out.println(Stream.of("a", "b", "c", "d").collect(Collectors.reducing())); // 化简
        System.out.println(Stream.of("a", "b", "c", "d").collect(Collectors.toList())); // 转换 toXXX

//        List<String> asList = stringStream.collect(ArrayList::new, ArrayList::add,
//                ArrayList::addAll);
//        String concat = stringStream.collect(StringBuilder::new, StringBuilder::append,
//                StringBuilder::append)
//                .toString();

        // 4. findAny 返回任意一个, findFirst 返回 第一个
        System.out.println(Stream.of("a", "b", "c", "d").findAny());
        System.out.println(Stream.of("a", "b", "c", "d").findFirst());


        // 5. forEach forEachOrdered
        System.out.println();
        Stream.of("a", "b", "c", "d").forEach(i -> {
            System.out.print(i);
        });
        System.out.println();
        Stream.of("a", "b", "c", "d").forEachOrdered(i -> {
            System.out.print(i);
        });

        // 6. max、min
        Stream.of(1, 2, 3, 4, 5).max((a, b) -> {
            return a - b;
        });

        // 7. reduce
        Optional<Integer> total = Stream.of(1, 2, 3, 4, 5).reduce((x, y) -> x + y); // 没有初始值
        Integer total2 = Stream.of(1, 2, 3, 4, 5).reduce(0, (x, y) -> x + y); // 有初始值

        //8. toArray()
        Stream.of(1, 2, 3, 4, 5).toArray(); // 有初始值
    }

    /**
     * 组合
     *
     * concat 用来连接类型一样的两个流。返回一个新流
     *
     * public static <T> Stream<T> 	concat(Stream<? extends T> a, Stream<? extends T> b)
     */
    @Test
    public void test04() {

    }

    /**
     * 转换
     *
     * 自带一个 toArray
     *
     * 剩下的就靠 collect 方法
     */
    @Test
    public void test05() {

    }


}
