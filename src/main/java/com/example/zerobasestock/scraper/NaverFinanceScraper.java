package com.example.zerobasestock.scraper;

import com.example.zerobasestock.model.Company;
import com.example.zerobasestock.model.ScrapedResult;
import org.springframework.stereotype.Component;

@Component
public class NaverFinanceScraper implements Scraper{
    @Override
    public Company scrapCompanyByTicker(String ticker) {
        return null;
    }

    @Override
    public ScrapedResult scrap(Company company) {
        return null;
    }
}
