package com.septeo.ulyses.technical.test.dto;

import com.septeo.ulyses.technical.test.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehicleBestSalesDto {
    private Vehicle vehicle;
    private Long totalSales;
}
