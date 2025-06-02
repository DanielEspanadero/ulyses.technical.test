package com.septeo.ulyses.technical.test.controller;

import com.septeo.ulyses.technical.test.dto.VehicleBestSalesDto;
import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @GetMapping
    public ResponseEntity<Page<Sales>> getAllSales(
            @RequestParam(value = "page", defaultValue = "0") int page) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Sales> salesPage = salesService.getAllSales(pageable);
        return ResponseEntity.ok(salesPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sales> getSalesById(@PathVariable Long id) {
        return salesService.getSalesById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/brands/{brandId}")
    public ResponseEntity<List<Sales>> getSalesByBrand(@PathVariable Long brandId) {
        List<Sales> sales = salesService.getSalesByBrandId(brandId);
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/vehicles/{vehicleId}")
    public ResponseEntity<List<Sales>> getSalesByVehicle(@PathVariable Long vehicleId) {
        List<Sales> sales = salesService.getSalesByVehicleId(vehicleId);
        return ResponseEntity.ok(sales);
    }


    @GetMapping("/vehicles/bestSelling")
    public ResponseEntity<List<VehicleBestSalesDto>> getBestSellingVehicles(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate startDate = null;
        LocalDate endDate = null;

        try {
            if (startDateStr != null) {
                startDate = LocalDate.parse(startDateStr, formatter);
            }
            if (endDateStr != null) {
                endDate = LocalDate.parse(endDateStr, formatter);
            }
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }

        List<VehicleBestSalesDto> bestSellingVehicles = salesService.getBestSellingVehicles(startDate, endDate);
        return ResponseEntity.ok(bestSellingVehicles);
    }

}
