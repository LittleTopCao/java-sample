import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.io.IOException;
import java.util.List;

public class RetrofitTest {

    /**
     * 以接口方法的 方式 声明 api
     */
    public interface GitHubService {
        @GET("users/{user}/repos")
        Call<List<String>> listRepos(@Path("user") String user);

    }

    Retrofit retrofit;
    GitHubService service;

    /**
     * 构造 Retrofit 实例
     *
     *      url 组合规则
     *          baseUrl 是 目录 http://host:port/a/b/
     *              方法上是 绝对路径 /apath ，得到路径
     *              方法上是 相对路径 apath ，得到路径 http://host:port/a/b/apath （建议使用这种）
     *
     *          baseUrl 是 文件 http://host:port/a/b
     *              方法上是 绝对路径 /apath ，得到 路径 http://host:port/apath
     *              方法上是 相对路径 apath， 得到路径 http://host:port/a/apath
     *
     *       接口返回值
     *          默认支持 retrofit2.Call 类型，用来封装 okhttp 的 call
     *          如果我们需要 直接返回 Observable ，那么就需要 添加 adapter ，
     */
    @Before
    public void before() {
        //底层的配置需要在这里 ，例如 拦截器 添加header 等等
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/") //指定 服务器地址
                .addConverterFactory(MoshiConverterFactory.create()) //添加 转换 body 的 converter，这样就能 使用 json 自动转换 @Body 参数 和  接收 Call<User> 返回值了
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //添加接口返回值的适配，这样就能直接返回 rxjava 中的 Observable 了
                .client(client) //指定 使用的 okhttp client
                .build();

        //反射 、动态代理 生成了一个 实例, 可以重复使用
        service = retrofit.create(GitHubService.class);
    }


    /**
     *
     */
    @Test
    public void test() throws IOException {
        Call<List<String>> call = service.listRepos("abc");

        //同步请求，获得 response ，body 直接是 List<String>
        Response<List<String>> response = call.execute();
        List<String> body = response.body();


        //异步请求
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });

    }
}
