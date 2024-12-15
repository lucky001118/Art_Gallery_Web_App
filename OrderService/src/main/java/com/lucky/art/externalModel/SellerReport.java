package com.lucky.art.externalModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerReport {
    private Long Id;
    private Seller seller;
    private Long totalEarnings=0L;
    private Long totalSales = 0L;
    private Long totalRefund = 0L;
    private Long totalTax = 0L;
    private Long netEarnings = 0L;
    private Integer totalOrders=0;
    private Integer canceledOrders = 0;
    private Integer totalTransactions = 0;
}
