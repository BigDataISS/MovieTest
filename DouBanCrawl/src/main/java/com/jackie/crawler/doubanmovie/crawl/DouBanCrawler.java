package com.jackie.crawler.doubanmovie.crawl;

import com.jackie.crawler.doubanmovie.dbUtils.dbOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * START
 * 代码功能描述：爬虫主入口，先爬取电影信息界面打的链接存入数据库
 * 再从数据库导出电影信息界面链接爬取电影的具体信息
 * @author 李耀鹏
 */
public class DouBanCrawler extends DouBanParsePage {
    
    private static List<String> moviePageUrl = new ArrayList<String>();
    public static final Logger log = LoggerFactory.getLogger(DouBanCrawler.class);

    public static void main(String args[]) throws Exception {

    	 //String rootUrl = "https://movie.douban.com/";        //首链接
         //List<String> seedList = new ArrayList<String>();
         //seedList.add(rootUrl);

        /* if (seedList == null) {
             log.info("No seed to crawl, please check again");
             return;
         }
         int count = 0;

         while (true) {                 //拿到子链接
             List<String> temp = DouBanHttpGetUtil.getByString(seedList);
             count+=temp.size() + 1;
             seedList.clear();
             for(int i = 0;i < temp.size();i++){
                 seedList.add(temp.get(i));
                 moviePageUrl.add(temp.get(i));
             }
             if(count > 2000){
                 break;
             }
         }
         System.out.print(moviePageUrl.size());*/

        //DouBanHttpGetUtil.insertUrls(moviePageUrl);            //把url存入数据库

        dbOperation db = new dbOperation();
        moviePageUrl = db.QuerySql();                   //读取数据库中已经导入了的子链接
        //String temp = "https://movie.douban.com/subject/26752088/?from=showing";      //调试所用链接
        //String temp = "https://movie.douban.com/subject/30198241/";           //调试所用链接
        //moviePageUrl.add(temp);
        DouBanHttpGetUtil.insertMoviesByUrls(moviePageUrl);   //把电影信息存入数据库
    }
}

/**
 * END
 * @author 李耀鹏
 */

