package com.example.zerobasestock.web;

import com.example.zerobasestock.model.Company;
import com.example.zerobasestock.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/autocomplete")
    public ResponseEntity<?> autocomplete(@RequestParam String keyword){
        return null;
    }

    @GetMapping()
    public ResponseEntity<?> searchCompany(){
        return null;
    }

    @PostMapping()
    public ResponseEntity<?> addCompany(@RequestBody Company request){

        System.out.println("enter");

        String ticker = request.getTicker().trim();

        if(ObjectUtils.isEmpty(ticker)){
            throw new RuntimeException("ticker is empty");
        }

        System.out.println("1");

        Company company = this.companyService.save(ticker);

        return ResponseEntity.ok(company);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteCompany(){
        return null;
    }
}
