import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * 地址类
 *
 * InetAddress
 *      Inet4Address
 *      Inet6Address
 *
 * 主要用 InetAddress 的静态方法获得实例
 *
 */
public class AddressTest {

    /**
     * 根据 域名 获得地址
     * @throws UnknownHostException
     */
    @Test
    public void getAddressByHostName() throws UnknownHostException {
        InetAddress[] addresses = InetAddress.getAllByName("baidu.com");
        for (int i = 0; i < addresses.length; i++) {
            System.out.println(addresses[i].getHostAddress());
        }
    }

    /**
     * 根据 ip 获得地址
     * @throws UnknownHostException
     */
    @Test
    public void getAddressByIP() throws UnknownHostException {
        InetAddress addresses = InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
        System.out.println(addresses.getHostName());
    }


    /**
     * 获得本机 地址
     * @throws UnknownHostException
     */
    @Test
    public void getLocalHost() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(localHost.getHostName());
        System.out.println(localHost.getHostAddress());
    }

    /**
     * 测试地址 连通 性
     *
     * 测试 baidu.com 不通，据说是通过 tcp 检测的
     *
     */
    @Test
    public void get() throws IOException {
        InetAddress addresses = InetAddress.getByName("localhost");
        boolean reachable = addresses.isReachable(2);
        System.out.println(reachable);
    }

}
