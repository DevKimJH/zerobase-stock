package com.example.zerobasestock;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
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


