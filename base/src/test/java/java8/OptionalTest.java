package java8;

import org.junit.Test;

import java.util.Optional;

/**
 * java 8 引入，为了解决空指针异常
 *
 * 单词翻译为 可选的
 *
 * 可以把它看成一个容器，既可以包含值，也可以为空
 *
 * 在 java 中，下边这行代码 每次 . 调用都可能引发 NullPointerException 空指针异常
 *      String isocode = user.getAddress().getCountry().getIsocode().toUpperCase();
 *
 * 如果要检查，就需要每次调用都检查
 *      if (user != null) {
 *          Address address = user.getAddress();
 *          if (address != null) {
 *              Country country = address.getCountry();
 *              if (country != null) {
 *                  String isocode = country.getIsocode();
 *                  if (isocode != null) {
 *                      isocode = isocode.toUpperCase();
 *                  }
 *              }
 *          }
 *      }
 *
 * https://www.cnblogs.com/zhangboyu/p/7580262.html
 */
public class OptionalTest {

    /**
     * 创建一个 optional
     */
    @Test
    public void test01() {
        //创建一个空的
        Optional.empty();
        //创建包含值的，如果为值为 null，将引发 NullPointerException
        Optional<Integer> optional = Optional.of(1);
        //创建包含值的，值可以为 null
        Optional.ofNullable(null);
    }

    /**
     * 使用
     */
    @Test
    public void test02() {
        Optional<Integer> optional = Optional.of(1);

        //拿值, 如果没有值 抛出 NoSuchElementException
        Integer integer = optional.get();

        //检测是否有值
        boolean present = optional.isPresent();

        //如果有值 才执行
        optional.ifPresent(i -> System.out.println(i));

        //返回可选值, 如果有值就返回，否则返回 默认值
        optional.orElse(3);

        //默认值用 计算方式来 获得
        optional.orElseGet(() -> 4);

        //为空时抛出异常
        optional.orElseThrow(() -> new IllegalArgumentException());
    }

    /**
     * 转换值
     */
    @Test
    public void test03() {
        Optional<String> optional = Optional.of("hahah");

        //对值进行计算，然后对返回值进行 Optional 包装
        optional.map(i -> i + 1).map(i -> i + 1);

        //对值进行计算, 必须手动返回 Optional 类型
        optional.flatMap(i -> Optional.empty());
    }

    /**
     * 过滤值
     */
    @Test
    public void test04() {
        Optional<Integer> optional = Optional.of(2);

        //传入一个 谓词， 如果为空 返回 空的 Optional
        Optional<Integer> integer = optional.filter(i -> i > 0);
    }

    /**
     * 链式调用 及 实践
     *
     * 主要用在 方法返回值 上
     */
    @Test
    public void test05() {
        User user = new User("anna@gmail.com", "1234");

        String result = Optional.ofNullable(user)
                .flatMap(u -> u.getAddress())
                .flatMap(a -> a.getCountry())
                .map(c -> c.getIsocode())
                .orElse("default");



        //使用方法引用
        String result2 = Optional.ofNullable(user)
                .flatMap(User::getAddress)
                .flatMap(Address::getCountry)
                .map(Country::getIsocode)
                .orElse("default");
    }



}

class User {
    private Address address;

    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }

    // ...
}

class Address {
    private Country country;

    public Optional<Country> getCountry() {
        return Optional.ofNullable(country);
    }

    // ...
}

class Country {

}