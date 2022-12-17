package com.example.zerobasestock.scraper;

import com.example.zerobasestock.model.Company;
import com.example.zerobasestock.model.ScrapedResult;

public interface Scraper {
    Company scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(Company company);
}
