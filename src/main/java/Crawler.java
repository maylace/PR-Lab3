import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;

/**
 * Created by Dorin Luca on 27.05.2017.
 */
public class Crawler {
    private static final int maxPagesToSearch = 10;
    private Set<String> pagesVisited = new HashSet<String>();
    private List<String> pagesToVisit = new LinkedList<String>();

    public void search(String url, String searchWord) {
        while(this.pagesToVisit.size() < maxPagesToSearch) {
            String currentUrl;
            CrawlerLeaf leaf =  new CrawlerLeaf();
            if(this.pagesToVisit.isEmpty()){
                currentUrl = url;
                this.pagesVisited.add(url);

            } else {
                currentUrl = this.nextUrl();
            }

            leaf.crawl(currentUrl);

            boolean success = leaf.searchForWord(searchWord);
            if(success)
            {
                System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
                break;
            }
            this.pagesToVisit.addAll(leaf.getLinks());
        }
        System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
    }

    private String nextUrl() {
        String nextUrl;

        do {
           nextUrl = this.pagesToVisit.remove(0);
        } while(this.pagesVisited.contains(nextUrl));

        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }
}
