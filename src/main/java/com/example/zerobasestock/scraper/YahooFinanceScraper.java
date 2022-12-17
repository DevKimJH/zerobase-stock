package com.example.zerobasestock.scraper;

import com.example.zerobasestock.model.Company;
import com.example.zerobasestock.model.Dividend;
import com.example.zerobasestock.model.ScrapedResult;
import com.example.zerobasestock.model.constants.Month;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class YahooFinanceScraper implements Scraper{


    private static final String STATISTICS_URL = "https://finance.yahoo.com/quote/%s/history?period1=%d&period2=%d&interval=1mo";
    private static final String SUMMARY_URL = "https://finance.yahoo.com/quote/%s?p=%s";


    private static final long START_TIME = 86400; // 60초 * 60분 * 24시간



    @Override
    public ScrapedResult scrap(Company company){
        var scrapResult = new ScrapedResult();
        scrapResult.setCompany(company);
        try{

            System.out.println("A");

            long end = System.currentTimeMillis() / 1000; // 현재 시간을 초단위로
            String url = String.format(STATISTICS_URL, company.getTicker(), START_TIME, end);


            Connection connection = Jsoup.connect(url);
            Document document = connection.get();

            System.out.println("B");

            Elements parsingDivs = document.getElementsByAttributeValue("data-test", "historical-prices");
            Element tableEle = parsingDivs.get(0);

            Element tbody = tableEle.children().get(1);

            List<Dividend> dividends = new ArrayList<>();

            System.out.println("C");

            for(Element e : tbody.children()){
                String txt = e.text();

                if(!txt.endsWith("Dividend")){
                    continue;
                }

                String[] splits = txt.split(" ");
                int month = Month.strToNumber(splits[0]);
                int day = Integer.valueOf(splits[1].replace(",", ""));
                int year = Integer.valueOf(splits[2]);
                String dividend = splits[3];

                if(month < 0){
                    throw new RuntimeException("Unexpected Month enum value -> " + splits[0]);
                }

                //System.out.println(year + "/" + month + "/" + day + " -> " + dividend);
                dividends.add(Dividend.builder()
                        .date(LocalDateTime.of(year, month, day, 0, 0))
                        .dividend(dividend)
                        .build());


            }
            scrapResult.setDividends(dividends);
        }
        catch(IOException e){

            // TODO
            e.printStackTrace();
        }


        return scrapResult;
    }


    @Override
    public Company scrapCompanyByTicker(String ticker){
        String url = String.format(SUMMARY_URL, ticker, ticker);

        try{
            Document document = Jsoup.connect(url).get();
            Element titleEle = document.getElementsByTag("h1").get(0);
            String title = titleEle.text().split(" - ")[1].trim(); // abc - def - xzy 의 경우 3개의 원소를 가진 배열이 생김

            return Company.builder()
                    .ticker(ticker)
                    .name(title)
                    .build();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
