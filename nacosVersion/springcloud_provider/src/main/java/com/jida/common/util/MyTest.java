package com.jida.common.util;

import lombok.SneakyThrows;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MyTest {
    static String cookie = null;
    @SneakyThrows
    public static void main(String[] args) {
//        httpPost();
//        httpGet();
        String url1 = "http://localhost:8001/order/newOrderNoNewMysql";
        String url2 = "http://localhost:8002/order/newOrderNoNewMysql";
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            int finalI = i;
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                httpGet(url1);
                httpGet(url2);
            });
            futures.add(completableFuture);
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
    }

    @SneakyThrows
    public static void httpGet(String url){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 2. 创建HttpPost实例
        HttpGet httpPost = new HttpGet(url);
        httpPost.setHeader("Cookie", cookie);
        // 3. 调用HttpClient实例来执行HttpPost实例
        CloseableHttpResponse response = httpclient.execute(httpPost);
        // 4. 读 response
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
            HttpEntity entity = response.getEntity();
            System.out.println(response);
            System.out.println("===================");
            String html = EntityUtils.toString(entity);
            System.out.println(html);
        } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
        // 5. 释放连接
        response.close();
        httpclient.close();
    }

    @SneakyThrows
    public static void httpPost(){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 2. 创建HttpPost实例
        HttpPost httpPost = new HttpPost("http://dbwap.com/login/Login?name=773203413&password=7732034");
        // 3. 调用HttpClient实例来执行HttpPost实例
        CloseableHttpResponse response = httpclient.execute(httpPost);
        Header[] allHeaders = response.getAllHeaders();
        Header[] headers = response.getHeaders("Set-Cookie");
        String value = headers[0].getValue();
        String substring = value.substring(0,value.indexOf(";"));
        cookie = substring;
        // 4. 读 response
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200) {
            HttpEntity entity = response.getEntity();
            System.out.println(response);
            System.out.println("===================");
            String html = EntityUtils.toString(entity);
            System.out.println(html);
        } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
        // 5. 释放连接
        response.close();
        httpclient.close();
    }
}
