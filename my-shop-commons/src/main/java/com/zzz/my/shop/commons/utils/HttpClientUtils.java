package com.zzz.my.shop.commons.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;

/**
 * HttpClient 工具类
 * <p>Title: HttpClientUtils</p>
 * <p>Description: </p>
 *
 * @author 111
 * @version 1.0.0
 * @date 2019-08-26
 */
public class HttpClientUtils {

    //请求方式
    public static final String GET = "get";
    public static final String POST = "post";

    //请求头信息
    public static final String REQUEST_HEADER_CONNECTION = "keep-alive";
    public static final String REQUEST_HEADER_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36";

    /**
     *  GET 请求不带 cookie
     * @param url
     * @return
     */
    public static final String  doGet(String url){
        return createRequest(url, GET, null);
    }

    /**
     * GET 请求带 cookie
     * @param url
     * @param cookie
     * @return
     */
    public static final String doGet(String url,String cookie){
        return createRequest(url, GET, cookie);
    }

    /**
     * POST 请求不带 cookie
     * @param url
     * @param params
     * @return
     */
    public static final String doPost(String url,BasicNameValuePair... params){
        return createRequest(url, POST, null, params);
    }

    /**
     * POST 请求带 cookie
     * @param url
     * @param cookie
     * @param params
     * @return
     */
    public static final String doPost(String url,String cookie,BasicNameValuePair... params){
        return createRequest(url, POST, cookie, params);
    }

    /**
     * 创建请求
     * @param url 请求地址
     * @param requestMethod 请求方法
     * @param cookie cookie
     * @param params 请求参数【可选】
     * @return
     */
    private static final String createRequest(String url, String requestMethod, String cookie, BasicNameValuePair... params){
        // 创建 HttpClient 客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //请求方式
        HttpGet httpGet = null;
        HttpPost httpPost = null;
        //请求结果
        String result = null;
        // 响应模型
        CloseableHttpResponse httpResponse = null;

        try {
            // get 请求
            if (GET.equals(requestMethod)){
                //创建 get 请求
                httpGet = new HttpGet(url);
                // 设置长连接
                httpGet.setHeader("Connection", REQUEST_HEADER_CONNECTION);
                // 设置代理（模拟浏览器版本）
                httpGet.setHeader("User-Agent", REQUEST_HEADER_USER_AGENT);
                // 设置 Cookie
                httpGet.setHeader("Cookie", cookie);

                httpResponse = httpClient.execute(httpGet);
            }

            // post 请求
            else if (POST.equals(requestMethod)){
                httpPost = new HttpPost(url);
                httpPost.setHeader("Connection", REQUEST_HEADER_CONNECTION);
                httpPost.setHeader("User-Agent", REQUEST_HEADER_USER_AGENT);
                httpPost.setHeader("Cookie", cookie);

                if (params != null && params.length > 0 ){
                    httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(params), "UTF-8"));
                }
                httpResponse = httpClient.execute(httpPost);
            }

            // 从响应模型中获取响应实体
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (httpResponse != null || httpClient != null){
                try {
                    httpResponse.close();
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return result;
    }
}
