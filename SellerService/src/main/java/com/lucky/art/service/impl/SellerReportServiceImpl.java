package com.lucky.art.service.impl;

import com.lucky.art.model.Seller;
import com.lucky.art.model.SellerReport;
import com.lucky.art.repository.SellerReportRepository;
import com.lucky.art.service.SellerReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerReportServiceImpl implements SellerReportService {
    private final SellerReportRepository sellerReportRepository;
    @Override
    public SellerReport getSellerReport(Seller seller) {
        SellerReport sr = sellerReportRepository.findBySellerId(seller.getId());

        if (sr==null){
            SellerReport newsellerReport= new SellerReport();
            newsellerReport.setSeller(seller);
            return sellerReportRepository.save(newsellerReport);
        }
        return sr;
    }

    @Override
    public SellerReport updateSellerReport(SellerReport sellerReport) {
        return sellerReportRepository.save(sellerReport);
    }
}
