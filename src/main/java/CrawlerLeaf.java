import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Dorin Luca on 27.05.2017.
 */
public class CrawlerLeaf {
    private static final String user_Agent =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    private List<String> links = new LinkedList<String>();
    private Document htmlDocument;

    public boolean crawl(String url) {
        try {
            Connection connection = Jsoup.connect(url).userAgent(user_Agent);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;

            if(connection.response().statusCode() == 200) {
                System.out.println("\n**Visiting** Received web page at " + url);
            }

            if(!connection.response().contentType().contains("text/html")) {
                System.out.println("**Failure** Retrieved something other than HTML");

                return false;
            }

            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");

            for(Element link: linksOnPage) {
                this.links.add(link.absUrl("href"));
            }
            outputCrawledSites(this.links);
            System.out.println();
            System.out.println("Web Sites found by keyword");
            searchForWebSitesByKeyWord("java", this.links);
            return true;
        } catch (IOException ioe) {

            return false;
        }
    }

    public boolean searchForWord(String searchWord) {
        if(this.htmlDocument == null) {
            System.out.println("ERROR! Call crawl() before performing analysis on the document");
            return false;
        }

        System.out.println("Searching for the word " + searchWord + "...");
        String bodyText = this.htmlDocument.body().text();
        return bodyText.toLowerCase().contains(searchWord.toLowerCase());
    }


    public void searchForWebSitesByKeyWord (String searchWord, List <String> links) {
        int count = 0;
        if(this.htmlDocument == null) {
            System.out.println("ERROR! Call crawl() before performing analysis on the document");
        }

        for(String link: links) {
            if(link.toLowerCase().contains(searchWord.toLowerCase())) {
                System.out.println(link);
                count += 1;
            }
        }
        System.out.println("Found " + count + "web site links that contain the key word" + searchWord);
    }

    private void outputCrawledSites(List <String> links){
        for(String link: links) {
            System.out.println(link);
        }
    }
    public List<String> getLinks()
    {
        return this.links;
    }

}
