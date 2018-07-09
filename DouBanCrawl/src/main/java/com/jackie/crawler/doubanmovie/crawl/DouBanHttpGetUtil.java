package com.jackie.crawler.doubanmovie.crawl;

import com.jackie.crawler.doubanmovie.entity.Movie;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *代码功能描述：解析各种链接，从链接拿取子链接以及解析电影界面的链接
 *@author 李耀鹏、马雨昂
 */

public class DouBanHttpGetUtil {

    /**
     * 函数描述：通过List<String> urlList中的链接进行迭代获取更多的子链接
     * 参数描述：List<String> urlList 父级url解析出来的链接用于迭代更多的子链接
     * 返回值描述：返回更多的子链接信息
     */
    public final static  List<String> getByString(List<String> urlList) throws Exception {
    	List<String> pageUrlList = new ArrayList<String>();
    	List<String> tempUrlList = new ArrayList<String>();
    	List<Movie> Movies = new ArrayList<Movie>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            for (String url : urlList) {
                    HttpGet httpGet = new HttpGet(url);
                   // System.out.println("executing request " + httpGet.getURI());
                ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                    public String handleResponse(
                            final HttpResponse response) throws ClientProtocolException, IOException {
                        int status = response.getStatusLine().getStatusCode();
                        // System.out.println("------------status:" + status);
                        if (status >= 200 && status < 300) {
                            HttpEntity entity = response.getEntity();
                            return entity != null ? EntityUtils.toString(entity) : null;
                        } else if (status == 300 || status ==301 || status == 302 ||status == 304 || status == 400 ||
                                status == 401 || status == 403 || status == 404 || new String(status + "").startsWith("5")){ //refer to link http://blog.csdn.net/u012043391/article/details/51069441
                            return null;
                        }else {
                            throw new ClientProtocolException("Unexpected response status: " + status);
                        }
                    }
                };
                    friendlyToDouban();
                    String responseBody = httpClient.execute(httpGet, responseHandler);
                    if (responseBody != null) {
                        tempUrlList = DouBanParsePage.parseFromString(responseBody);//解析链接
                      }
                      for(int i = 0; i < tempUrlList.size();i++){
                          pageUrlList.add(tempUrlList.get(i));
                      }
            }
        } finally {
            httpClient.close();
        }
        return pageUrlList;
    }

    /**
     * 函数描述：把List<String> moviePageUrl中的链接对应的电影信息解析出来并存入数据库中
     * 参数描述：List<String> moviePageUrl 电影具体信息界面对应的链接
     * 返回值描述：无返回值
     */

    public final static void insertMoviesByUrls(List<String> moviePageUrl) throws Exception {

        for(int i = 0; i < moviePageUrl.size(); i++) {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            try {
                HttpGet httpGet = new HttpGet(moviePageUrl.get(i));
                //Thread.sleep((new Random().nextInt(10) + 1)*1000);
                ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                 public String handleResponse(
                    final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                //System.out.println("------------status:" + status);
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else if (status == 300 || status ==301 || status == 302 ||status == 304 || status == 400 ||
                        status == 401 || status == 403 || status == 404 || new String(status + "").startsWith("5")){ //refer to link http://blog.csdn.net/u012043391/article/details/51069441
                    return null;
                }else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
                friendlyToDouban();
                String responseBody = httpClient.execute(httpGet, responseHandler);     //html
                if (responseBody != null) {
                    DouBanParsePage.extractMovie(moviePageUrl.get(i), responseBody);        //Movie的具体信息插入数据库
                }
            } finally {
                httpClient.close();
            }
        }
    }

    /**
     * 函数描述：边解析链接边把筛选出子链接url存入数据库的url表中
     * 参数描述：List<String> moviePageUrl 解析出的需要导入数据库的url表中的链接
     * 返回值描述：无返回值
     */
    public final static void insertUrls(List<String> moviePageUrl) throws Exception {
        for(int i = 0; i < moviePageUrl.size();i++){
            DouBanParsePage.extractUrl(moviePageUrl.get(i));
        }
    }


    /**
     * 函数描述：爬取时等待一定时间防止豆瓣发爬虫机制触发
     * 参数描述：无参数
     * 返回值描述：无返回值
     */

    private static void friendlyToDouban() throws InterruptedException {
        Thread.sleep((new Random().nextInt(5) + 1)*500);//sleep for the random second so that avoiding to be listed into blacklist
    }
}


/**
 * END
 * @author 李耀鹏、马雨昂
 */

