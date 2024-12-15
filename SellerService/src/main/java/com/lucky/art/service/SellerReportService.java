package com.lucky.art.service;

import com.lucky.art.model.Seller;
import com.lucky.art.model.SellerReport;

public interface SellerReportService {

    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport(SellerReport sellerReport);
}
