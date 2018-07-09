package com.jackie.crawler.doubanmovie.crawl;

import com.jackie.crawler.doubanmovie.constants.Constants;
import com.jackie.crawler.doubanmovie.dbUtils.dbOperation;
import com.jackie.crawler.doubanmovie.entity.Movie;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * START
 * 代码功能描述：各种链接解析的函数
 * @author 李耀鹏、马雨昂
 */

public class DouBanParsePage {

    protected static Movie movie;
    public static int movieId = 0;
    public static int urlId = 0;
    public static String pictureUrl = null;      //图片链接


    /**
     * 函数描述：把某一链接的界面解析获取更多的待解析子链接
     * 参数描述：String content；content对应的是父级链接解析出来的html内容
     * 返回值描述：返回通过某一父级链接迭代出的子链接
     */
    public static List<String> parseFromString(String content) throws Exception {

    	Parser parser = new Parser(content);
    	//筛选器
        HasAttributeFilter filter = new HasAttributeFilter("href");
        List<String> nextLinkList = new ArrayList<String>();

        NodeList list = parser.parse(filter);
        //分析符合第一步筛选后的结果
        int count = list.size();
        for (int i = 0; i < count; i++) {
            Node node = list.elementAt(i);
            if (node instanceof LinkTag) {
                LinkTag link = (LinkTag) node;
                String nextLink = link.extractLink();
                String mainUrl = Constants.MAINURL;

                //进一步筛选
                if (nextLink.startsWith(mainUrl + "subject/")) {
                    nextLinkList.add(nextLink);
                }
            }
        }
        return nextLinkList;
    }


    /**
     * 函数描述：把某一链接的界面解析获取对应电影的相关信息并存入数据库的相应表中
     * 参数描述：String url；url是需要解析的界面，String content；content是该界面对应的html内容
     * 返回值描述：返回通过某一父级链接迭代出的子链接
     */
    public static void extractMovie(String url, String content) {
        Document doc = Jsoup.parse(content);
        String sql = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        dbOperation db = new dbOperation();
        Connection conn = db.getConnection();

        String sql1 = null;
        ResultSet rs1 = null;
        Statement stmt1 = null;

        //用于计算movieId值（实现自增长）
        try {
            sql1 = "select count(*) as movieCount from movie";
            stmt1 = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            rs1 = stmt1.executeQuery(sql1);
            if (rs1.next()) {
                movieId = rs1.getString("movieCount") != null ? Integer.parseInt(rs1.getString("movieCount")) : 0;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        finally {

            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (SQLException e1) {
                }
            }
            rs1 = null;

            if (stmt1 != null) {
                try {
                    stmt1.close();
                } catch (SQLException e3) {
                }
            }
            stmt1 = null;
        }

        //对链接进行筛选分析
        Pattern moviePattern = Pattern.compile(Constants.MOVIE_REGULAR_EXP);
        Matcher movieMatcher = moviePattern.matcher(url);
        if (movieMatcher.find()) {
            movie = new Movie(++movieId);
            Document movieDoc = Jsoup.parse(content);

            if (movieDoc.html().contains("导演") && movieDoc.html().contains("主演") && movieDoc.html().contains("类型") &&
                    movieDoc.html().contains("语言") && doc.getElementById("info") != null) {
                Elements infos = doc.getElementById("info").children();

                for (Element info : infos){
                    if (info.childNodeSize() > 0) {
                        String key = info.getElementsByAttributeValue("class", "pl").text();
                        if ("导演".equals(key)) {
                            movie.setDirector(info.getElementsByAttributeValue("class", "attrs").text());
                        } else if ("编剧".equals(key)) {
                            movie.setScenarist(info.getElementsByAttributeValue("class", "attrs").text());
                        } else if ("主演".equals(key)) {
                            movie.setActors(info.getElementsByAttributeValue("class", "attrs").text());
                        } else if ("类型:".equals(key)) {
                            movie.setType(doc.getElementsByAttributeValue("property", "v:genre").text());
                        } else if ("制片国家/地区:".equals(key)) {
                            Pattern patternCountry = Pattern.compile(".制片国家/地区:</span>.+[\\u4e00-\\u9fa5]+.+[\\u4e00-\\u9fa5]+\\s+<br>");
                            //Pattern patternCountry = Pattern.compile(".制片国家/地区:</span>.+ [\\u4e00-\\u9fa5]+.+<br>");
                            Matcher matcherCountry = patternCountry.matcher(doc.html());
                            if (matcherCountry.find()) {
                                movie.setCountry(matcherCountry.group().split("</span>")[1].split("<br>")[0].trim());// for example: >制片国家/地区:</span> 中国大陆 / 香港     <br>
                            }
                        } else if ("语言:".equals(key)) {
                            Pattern patternLanguage = Pattern.compile(".语言:</span>.+[\\u4e00-\\u9fa5]+.+[\\u4e00-\\u9fa5]+\\s+<br");
                            Matcher matcherLanguage = patternLanguage.matcher(doc.html());
                            if (matcherLanguage.find()) {
                                movie.setLanguage(matcherLanguage.group().split("</span>")[1].split("<br>")[0].trim());
                            }
                        } else if ("上映日期:".equals(key)) {
                            movie.setReleaseDate(doc.getElementsByAttributeValue("property", "v:initialReleaseDate").text());
                        } else if ("片长:".equals(key)) {
                            movie.setRuntime(doc.getElementsByAttributeValue("property", "v:runtime").text());
                        }
                    }
                }
                //获取电影标签
                movie.setTags(doc.getElementsByClass("tags-body").text());
                //获取电影名
                movie.setName(doc.getElementsByAttributeValue("property", "v:itemreviewed").text());
                //获取电影评分
                movie.setRatingNum(doc.getElementsByAttributeValue("property", "v:average").text());
                //获取电影描述
                movie.setDescription(doc.getElementsByAttributeValue("property", "v:summary").text());

                //如果该影片还未评分
                if(movie.getRatingNum().length() <=1){
                    movie.setRatingNum(doc.getElementsByClass("rating_sum").text());
                }

                //部分界面通过循环中的正则匹配获取不到相应内容时使用该方法进一步获取
                if(movie.getCountry() == null){
                    String  country = "制片国家/地区:</span>";
                    int country_index = doc.html().indexOf(country);

                    Pattern patternCountry = Pattern.compile(doc.html().substring(country_index,country_index+20));
                    Matcher matcherCountry = patternCountry.matcher(doc.html());
                    if (matcherCountry.find()) {
                        movie.setCountry(matcherCountry.group().split("</span>")[1].split("<br>")[0].trim());// for example: >制片国家/地区:</span> 中国大陆 / 香港     <br>
                    }
                }

                if(movie.getLanguage() == null) {
                    String language = "语言:</span>";
                    int language_index = doc.html().indexOf(language);

                    Pattern patternLanguage = Pattern.compile(doc.html().substring(language_index, language_index + 20));
                    Matcher matcherLanguage = patternLanguage.matcher(doc.html());
                    if (matcherLanguage.find()) {
                        movie.setLanguage(matcherLanguage.group().split("</span>")[1].split("<br>")[0].trim());
                    }
                }

            }

            //获取图片链接
            String temp = doc.html();
            if(temp.contains("<div id=\"mainpic\" class=\"\">")) {
                Elements picture = doc.getElementById("mainpic").children();
                for (Element pic : picture) {
                    if (pic.childNodeSize() > 0) {
                        //Element temp = pic.child(1);
                        String tempString = pic.toString();
                        int start_index = tempString.indexOf("<img src=\"https:");
                        int end_index = tempString.indexOf("jpg\" title");
                        pictureUrl = tempString.substring(start_index + 10, end_index + 3);
                        //System.out.print(pictureUrl);
                    }
                }
            }

        }

        //通过查询数据库来检查该movie是否已经存入了数据库
        try {
            sql = "SELECT * FROM movie WHERE `name` = '" + movie.getName() + "'";
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);

            if (rs.next()) {        //重名即更新Description（多次导入数据库）
                System.out.println(movie.getName() + "is already existed!!!");
                /*sql = "UPDATE movie SET Description = " + "'"  + movie.getDescription() + "'" +
                        //"', '" + "Country = " + "'"  + movie.getCountry() + "'" +
                       // "', '" + "`Language` = " + "'"  + movie.getLanguage() + "'" +
                        "WHERE `NAME` = " + "'" + movie.getName() + "'" ;

                String  temp1 =  "UPDATE movie SET Country = " + "'"  + movie.getCountry() + "'" +
                        "WHERE `NAME` = " + "'" + movie.getName() + "'" ;

                String temp2 = "UPDATE movie SET `Language` = " + "'"  + movie.getLanguage() + "'" +
                        "WHERE `NAME` = " + "'" + movie.getName() + "'" ;

                pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.execute();
                pstmt = conn.prepareStatement(temp1, Statement.RETURN_GENERATED_KEYS);
                pstmt.execute();
                pstmt = conn.prepareStatement(temp2, Statement.RETURN_GENERATED_KEYS);
                pstmt.execute();*/
            }

            else{
                sql = "INSERT INTO movie (MovieId,`Name`,Scenarist,RatingNum,Direction,Actors,`Type`,Country,`Language`,Tags,ReleaseDate,RunTime,Description) VALUES " +
                        "('" + movie.getMovieId() + "','" + movie.getName() + "', '" + movie.getScenarist() + "', '" + movie.getRatingNum() + "'" +
                        ", '" + movie.getDirector() + "', '" + movie.getActors() + "', '" + movie.getType() + "', '" + movie.getCountry() + "'" +
                        ", '" + movie.getLanguage() + "', '" + movie.getTags() + "', '" + movie.getReleaseDate() + "', '"+ movie.getRuntime() + "', '"+ movie.getDescription() + "')";
                pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.execute();

                //下载图片到本地path
                String path = "G:\\crawlerImages\\" + movie.getName() + ".jpg";
                downloadPicture(pictureUrl,path);
            }
        }
        catch (SQLException e) {
            //handle the exceptions
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            //close and release the resources of PreparedStatement, ResultSet and Statement

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e0) {
                }
            }
            conn = null;

            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e2) {
                }
            }
            pstmt = null;

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e1) {
                }
            }
            rs = null;

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e3) {
                }
            }
            stmt = null;
        }
    }


    /**
     * 函数描述：//把url插入数据库中的url表
     * 参数描述：String url；url是需要插入数据库的链接
     * 返回值描述：无返回值
     */
    public static void extractUrl(String url) {
        String sql = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        dbOperation db = new dbOperation();
        Connection conn = db.getConnection();

        String sql1 = null;
        ResultSet rs1 = null;
        Statement stmt1 = null;

        try {
        sql1 = "select count(*) as urlCount from url";
        stmt1 = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        rs1 = stmt1.executeQuery(sql1);
        if (rs1.next()) {
            urlId = rs1.getString("urlCount") != null ? Integer.parseInt(rs1.getString("urlCount")) : 0;
            }
        } catch (SQLException e) {
            //handle the exceptions
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        finally {
            //close and release the resources of PreparedStatement, ResultSet and Statement

            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (SQLException e1) {
                }
            }
            rs1 = null;

            if (stmt1 != null) {
                try {
                    stmt1.close();
                } catch (SQLException e3) {
                }
            }
            stmt1 = null;
        }

        urlId++;
        try {
            sql = "SELECT * FROM movie.url WHERE url = '" + url + "'";
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                System.out.println(url + "is already existed!!!");
            }

            else{
                sql = "INSERT INTO url (url,urlId) VALUES " +
                        "('" + url + "', '" + urlId + "')";
                pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.execute();
            }
        }
        catch (SQLException e) {
            //handle the exceptions
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            //close and release the resources of PreparedStatement, ResultSet and Statement
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e0) {
                }
            }
            conn = null;

            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e2) {
                }
            }
            pstmt = null;

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e1) {
                }
            }
            rs = null;

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e3) {
                }
            }
            stmt = null;
        }

    }


    /**
     * 函数描述：下载urlList链接对应的电影海报至Path路径处
     * 参数描述：String urlList；urlList是海报所在的电影界面，String path；path是图片下载的存储路径
     * 返回值描述：返回通过某一父级链接迭代出的子链接
     */
    public static void downloadPicture(String urlList,String path) {            //根据图片链接下载图片到path路径下
        URL url = null;
        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**
 * END
 * @author 李耀鹏、马雨昂
 */

