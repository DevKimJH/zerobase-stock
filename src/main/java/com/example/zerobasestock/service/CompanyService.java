package com.example.zerobasestock.service;

import com.example.zerobasestock.model.Company;
import com.example.zerobasestock.model.ScrapedResult;
import com.example.zerobasestock.persist.CompanyRepository;
import com.example.zerobasestock.persist.DividendRepository;
import com.example.zerobasestock.persist.entity.CompanyEntity;
import com.example.zerobasestock.persist.entity.DividendEntity;
import com.example.zerobasestock.scraper.Scraper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyService {

    private final Scraper yahooFinanceScraper;

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public Company save(String ticker){
        boolean exists = this.companyRepository.existsByTicker(ticker);

        if(exists){
            throw new RuntimeException("already exists ticker -> " + ticker);
        }

        return this.storeCompanyAndDividend(ticker);
    }

    public Page<CompanyEntity> getAllCompany(Pageable pageable){
        return this.companyRepository.findAll(pageable);
    }

    private Company storeCompanyAndDividend(String ticker){

        // ticker를 기준으로 회사를 스크래핑

        Company company = this.yahooFinanceScraper.scrapCompanyByTicker(ticker);

        if(ObjectUtils.isEmpty(company)){
            throw new RuntimeException("failed to scrap ticker -> " + ticker);
        }

        // 해당 회사가 존재할 경우, 회사의 배당금 정보를 스크래핑
        ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(company);

        // 스크래핑 결과
        CompanyEntity companyEntity = this.companyRepository.save(new CompanyEntity(company));

        // map : e를 다른 값으로 mapping해주는 역할..?
        List<DividendEntity> dividendEntityList = scrapedResult.getDividends().stream()
                .map(e -> new DividendEntity(companyEntity.getId(), e))
                .collect(Collectors.toList());


        this.dividendRepository.saveAll(dividendEntityList);

        return company;
    }
}