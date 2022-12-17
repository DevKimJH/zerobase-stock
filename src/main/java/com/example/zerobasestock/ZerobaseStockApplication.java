package com.example.zerobasestock;

import com.example.zerobasestock.model.Company;
import com.example.zerobasestock.model.ScrapedResult;
import com.example.zerobasestock.scraper.NaverFinanceScraper;
import com.example.zerobasestock.scraper.Scraper;
import com.example.zerobasestock.scraper.YahooFinanceScraper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ZerobaseStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZerobaseStockApplication.class, args);

        //Scraper scraper = new YahooFinanceScraper();

        // 인터페이스 사용시 이점
        // Scraper scraper = new NaverFinanceScraper();
        //ScrapedResult result = scraper.scrap(Company.builder().ticker("O").build());
        //Company result = scraper.scrapCompanyByTicker("MMM");
        //System.out.println(result);
    }
}
