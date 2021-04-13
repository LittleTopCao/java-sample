import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * com.sun.net.httpserver 功能测试
 *
 * https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/package-summary.html
 *
 * 一个简单的 http服务端 实现，支持 http1.1 和 https
 */
public class HttpServerTest {

    /**
     * http
     */
    @Test
    public void httpTest() throws IOException, InterruptedException {
        InetSocketAddress address = new InetSocketAddress(8000);

        HttpServer server = HttpServer.create(address, 0);

        server.createContext("/applications/myapp", new HttpHandler() {

            @Override
            public void handle(HttpExchange exchange) throws IOException {

                //拿 请求 的信息
                URI requestURI = exchange.getRequestURI();
                String requestMethod = exchange.getRequestMethod();
                Headers requestHeaders = exchange.getRequestHeaders();
                InputStream is = exchange.getRequestBody();

                //打印 uri 和 method
                System.out.print(requestURI.toString() + " ");
                System.out.println(requestMethod);

                //打印 headers
                for (Map.Entry<String, List<String>> entry : requestHeaders.entrySet()) {
                    String key = entry.getKey();
                    List<String> value = entry.getValue();

                    System.out.println(key + "=" + value.toString());
                }

                System.out.println();

                //打印 body，先判断 body 类型
                List<String> typeList = requestHeaders.get("Content-type"); //拿 Content-type header
                Set<String> printableTypeSet = new HashSet<String>(Arrays.asList("application/x-www-form-urlencoded", "multipart/form-data")); //可打印的 body 类型
                if (typeList != null && typeList.size() != 0) { //如果有 header
                    if (printableTypeSet.contains(typeList.get(0))) { //如果 body 类型可打印
                        String line = null;
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                        while ((line = bufferedReader.readLine()) != null) {
                            System.out.println(line);
                        }
                        bufferedReader.close();
                    } else {
                        System.out.println("body不能打印");
                    }
                }

                System.out.println();


                String response = "This is the response";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

//        server.setExecutor(null); //使用默认的 执行器
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        server.setExecutor(executorService); //自定义执行器，单线程
        server.start(); //开始服务，这里不阻塞当前线程

        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS); //等待服务执行完毕，不然主线程就退出了
    }
}
