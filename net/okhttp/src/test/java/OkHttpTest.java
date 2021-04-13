import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.sun.istack.internal.NotNull;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OkHttpTest {
    OkHttpClient client;


    /**
     * 构造 客户端
     */
    @Before
    public void before() {
        //官方提供的一个 打印日志的 拦截器 ，在 logging-interceptor 包中
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NotNull String s) {

            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); //设置日志登记, NONE BASIC HEADERS BODY

        //新建 客户端
        client = new OkHttpClient.Builder() //构造者模式
                .addInterceptor(httpLoggingInterceptor)//添加 一个 拦截器，用来打印日志
                .build();

        client.getClass().getName();
    }


    /**
     * 同步 get 请求
     *
     * Request 不设置body
     *
     * 返回时机 在 所有 响应 接收完成时，包括 body 已经接收完成
     *
     * string 方法 将 body 转换为 字符串，因为转换后的字符串在内存中，所以 如果字符串大于 1m 最好不要使用这个方法
     */
    @Test
    public void syncGetTest() {
        //新建一个 request
        Request request = new Request.Builder()
                .url("https://publicobject.com/helloworld.txt")
                .build();

        //使用 客户端 新建 call， 然后  执行 call  ，阻塞 获得 响应
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }

            System.out.println(response.body().string()); //string 方法 将 body 转换为 字符串，如果字符串大于 1m 最好不要使用这个方法
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 异步 get 请求
     *
     * Request 不设置body
     *
     * 回调 时机 在 http 的 headers 准备好后，body 可能仍然没有 接收完成，读取时 可能会 阻塞
     *
     * okhttp 没有提供 在 响应正文 接收完成后 的 回调 api
     *
     */
    @Test
    public void asyncGetTest() {

        //新建 request
        Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                .build();

        //使用 客户端新建 call，然后 将 call 放入 队列， 传入 回调
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    System.out.println(responseBody.string());
                }
            }
        });
    }

    /**
     * 操作 headers
     *
     * 通常 headers 都是 Map<String, String> , 但是有一些 header 允许有多个 value ，所以 headers 就变成了 Map<String, List<String>>
     *
     * 为了是访问方便
     *     添加header
     *          header(name, value) 设置值，删除旧的
     *          addHeader(name, value) 设置值，如果已经存在，不删除旧的
     *     读取header
     *          header(name) 拿到最后一个值，如果没有 返回 null
     *          headers(name) 返回所有的值
     */
    @Test
    public void headerTest() {
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/square/okhttp/issues")
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println("Server: " + response.header("Server"));
            System.out.println("Date: " + response.header("Date"));
            System.out.println("Vary: " + response.headers("Vary"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 使用 post 发送字符串， 并且是 同步
     *
     * 使用 RequestBody.create 方法 创建 body
     * 然后使用 Request.post 方法设置 body
     *
     * 因为 发送时 字符串 存储在内存，所以不要 发送 超过 1m 的
     */
    @Test
    public void postStringTest() throws IOException {
        String postBody = ""
                + "Releases\n"
                + "--------\n"
                + "\n"
                + " * _1.0_ May 6, 2013\n"
                + " * _1.1_ June 15, 2013\n"
                + " * _1.2_ August 11, 2013\n";

        MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

        //新建 request ， 设置 请求 body 为 字符串
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        }
    }

    /**
     * 使用 post 发送流
     *
     * 使用 自定义 RequestBody 对象，实现 写入流 writeTo 方法
     *
     * 这里 BufferedSink 采用的是 okio 的 zpi ， 如果你想使用 java io 的 api ，可以通过 BufferedSink.outputStream() 方法拿 OutputStream
     *
     */
    @Test
    public void postStreamTest() throws IOException {

        MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

        //自定义 body， 实现 writeTo 方法，这个方法 应该 在 发送请求时调用，你写入的就是 向 服务器发送的 socket
        RequestBody requestBody = new RequestBody() {

            @Override
            public MediaType contentType() {
                return MEDIA_TYPE_MARKDOWN;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("Numbers\n");
                sink.writeUtf8("-------\n");
                for (int i = 2; i <= 997; i++) {
                    sink.writeUtf8(String.format(" * %s = %s\n", i, factor(i)));
                }
            }

            private String factor(int n) {
                for (int i = 2; i < n; i++) {
                    int x = n / i;
                    if (x * i == n) return factor(x) + " × " + i;
                }
                return Integer.toString(n);
            }
        };

        //新建 request
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(requestBody)
                .build();

        //同步 执行 call
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        }
    }

    /**
     * post 一个 文件
     *
     * 使用 RequestBody.create 方法 创建 body 对象
     */
    @Test
    public void postFileTest() throws IOException {
        MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

        File file = new File("README.md");

        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        }
    }

    /**
     * post 一个 表单
     *
     * 使用 FormBody.Builder() 创建一个 body 对象
     *
     */
    @Test
    public void postFormTest() throws IOException {

        //构造一个 body
        RequestBody formBody = new FormBody.Builder()
                .add("search", "Jurassic Park")
                .build();

        Request request = new Request.Builder()
                .url("https://en.wikipedia.org/w/index.php")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        }
    }

    /**
     * post 一个 Multipart
     *
     * 使用 MultipartBody.Builder() 创建一个 body 对象
     *
     */
    @Test
    public void postMultipartTest() throws IOException {
        String IMGUR_CLIENT_ID = "...";
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "Square Logo")
                .addFormDataPart("image", "logo-square.png",
                        RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                .url("https://api.imgur.com/3/image")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        }
    }


    /**
     * 使用 Moshi 库 解析 json
     *
     * Moshi 库 也是 square 开发的，一个 json 库，能利用 okhttp 的缓冲区
     *
     * Response.body().charStream() 根据 Content-Type 解析字符串
     *
     */
    @Test
    public void parseJsonTest() throws IOException {

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Gist> gistJsonAdapter = moshi.adapter(Gist.class);

        Request request = new Request.Builder()
                .url("https://api.github.com/gists/c2a7c39532239ff261be")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Gist gist = gistJsonAdapter.fromJson(response.body().source());

            for (Map.Entry<String, GistFile> entry : gist.files.entrySet()) {
                System.out.println(entry.getKey());
                System.out.println(entry.getValue().content);
            }
        }
    }

    static class Gist {
        Map<String, GistFile> files;
    }

    static class GistFile {
        String content;
    }

    /**
     * 缓存
     *
     * 缓存需要一个 可读写的 目录，多个 OkHttpClient 不应该共享一个目录
     *      其实一个应用 通常只需要 new OkHttpClient 一次
     *
     * 如果你的请求想使用缓存 可以添加 Cache-Control: max-stale=3600
     * 服务器同样可以 配置 缓存 Cache-Control: max-age=9600
     */
    @Test
    public void cacheTest() throws IOException {

        File cacheDirectory = new File("./cache");

        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(cacheDirectory, cacheSize);

        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                .build();

        String response1Body;
        try (Response response1 = client.newCall(request).execute()) {
            if (!response1.isSuccessful()) throw new IOException("Unexpected code " + response1);

            response1Body = response1.body().string();
            System.out.println("Response 1 response:          " + response1);
            System.out.println("Response 1 cache response:    " + response1.cacheResponse());
            System.out.println("Response 1 network response:  " + response1.networkResponse());
        }

        String response2Body;
        try (Response response2 = client.newCall(request).execute()) {
            if (!response2.isSuccessful()) throw new IOException("Unexpected code " + response2);

            response2Body = response2.body().string();
            System.out.println("Response 2 response:          " + response2);
            System.out.println("Response 2 cache response:    " + response2.cacheResponse());
            System.out.println("Response 2 network response:  " + response2.networkResponse());
        }

        System.out.println("Response 2 equals Response 1? " + response1Body.equals(response2Body));
    }

    /**
     * 取消 一个 call
     *
     * 使用 Call.cancel() 取消 call， 如果你正在进行 读取响应，将收到 IOException
     *
     */
    @Test
    public void cancelTest() throws IOException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Request request = new Request.Builder()
                .url("http://httpbin.org/delay/2") // This URL is served with a 2 second delay.
                .build();

        final long startNanos = System.nanoTime();
        final Call call = client.newCall(request);

        // Schedule a job to cancel the call in 1 second.
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.printf("%.2f Canceling call.%n", (System.nanoTime() - startNanos) / 1e9f);
                call.cancel();
                System.out.printf("%.2f Canceled call.%n", (System.nanoTime() - startNanos) / 1e9f);
            }
        }, 1, TimeUnit.SECONDS);

        System.out.printf("%.2f Executing call.%n", (System.nanoTime() - startNanos) / 1e9f);
        try (Response response = call.execute()) {
            System.out.printf("%.2f Call was expected to fail, but completed: %s%n",
                    (System.nanoTime() - startNanos) / 1e9f, response);
        } catch (IOException e) {
            System.out.printf("%.2f Call failed as expected: %s%n",
                    (System.nanoTime() - startNanos) / 1e9f, e);
        }
    }

    /**
     * 超时
     *
     * 三个超时 可配置：链接超时、写入超时、读取超时
     *
     * 也可以直接 配置 总超时
     */
    @Test
    public void timeoutTest() throws IOException {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
//                .callTimeout()
                .build();

        Request request = new Request.Builder()
                .url("http://httpbin.org/delay/2") // This URL is served with a 2 second delay.
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Response completed: " + response);
        }
    }

    /**
     * 更改配置
     *
     * 如果你已经有一个 OkHttpClient ，然后想改变 单个 call 的 配置，那么需要调用 client.newBuilder() 实例方法，这将共享 client 的所有配置
     *
     * 包括 链接池 调度程序 和 配置
     *
     */
    @Test
    public void configTest() throws IOException {
        Request request = new Request.Builder()
                .url("http://httpbin.org/delay/1") // This URL is served with a 1 second delay.
                .build();

        // Copy to customize OkHttp for this request.
        // 构造一个 新的 client ，新client 是个 轻量级的，和 旧 client 共享所有 配置
        OkHttpClient client1 = client.newBuilder()
                .readTimeout(500, TimeUnit.MILLISECONDS)
                .build();

        try (Response response = client1.newCall(request).execute()) {
            System.out.println("Response 1 succeeded: " + response);
        } catch (IOException e) {
            System.out.println("Response 1 failed: " + e);
        }

        // Copy to customize OkHttp for this request.
        // 构造一个 新的 client ，新client 是个 轻量级的，和 旧 client 共享所有 配置
        OkHttpClient client2 = client.newBuilder()
                .readTimeout(3000, TimeUnit.MILLISECONDS)
                .build();

        try (Response response = client2.newCall(request).execute()) {
            System.out.println("Response 2 succeeded: " + response);
        } catch (IOException e) {
            System.out.println("Response 2 failed: " + e);
        }
    }


    /**
     * 处理身份验证
     *
     * 配置 身份验证 Authenticator
     *
     * 当 服务器返回 401 Not Authorized 时，okhttp 将调用 Authenticator 的 authenticate 方法，拿到 新的 request 重试
     *      如果不想重试 可以 返回 null
     *
     * Response.challenges() 得到 什么？
     *
     * Credentials.basic(username, password) 可以编码 basic auth 认证
     *
     * 可以使用 一下方法 统计 重试次数
     *   private int responseCount(Response response) {
     *     int result = 1;
     *     while ((response = response.priorResponse()) != null) {
     *       result++;
     *     }
     *     return result;
     *   }
     *
     */
    @Test
    public void authTest() throws IOException {

        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        if (response.request().header("Authorization") != null) {
                            return null; // Give up, we've already attempted to authenticate.
                        }

                        System.out.println("Authenticating for response: " + response);
                        System.out.println("Challenges: " + response.challenges());
                        String credential = Credentials.basic("jesse", "password1");

                        return response.request().newBuilder()
                                .header("Authorization", credential)
                                .build();
                    }
                })
                .build();

        Request request = new Request.Builder()
                .url("http://publicobject.com/secrets/hellosecret.txt")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        }
    }
}
