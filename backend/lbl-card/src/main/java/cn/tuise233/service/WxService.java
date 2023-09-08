package cn.tuise233.service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import springfox.documentation.spring.web.json.Json;

import java.net.URI;

public class WxService {
    public static String getOpenId(String code) {
        String appId = "wx4164252abb45dd08";
        String secretKey = "b80872634f249341dd23d12fcb24d3a3";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        CloseableHttpResponse response;
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + secretKey + "&js_code=" + code + "&grant_type=authorization_code";
        try{
            URIBuilder uriBuilder = new URIBuilder(url);
            URI uri = uriBuilder.build();

            HttpGet httpGet = new HttpGet(uri);
            response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == 200){
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject)parser.parse(result);
        return json.get("openid").getAsString();
    }
}
