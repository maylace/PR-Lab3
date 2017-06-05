/**
 * Created by Dorin Luca on 27.05.2017.
 */
public class Main {
    public static void main(String[] args)
    {
        Crawler webCrawler = new Crawler();
        webCrawler.search("https://www.mkyong.com/", "java");
    }
}
