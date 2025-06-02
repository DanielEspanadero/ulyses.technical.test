package com.septeo.ulyses.technical.test.service;

import com.septeo.ulyses.technical.test.dto.VehicleBestSalesDto;
import com.septeo.ulyses.technical.test.entity.Brand;
import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Sales operations.
 */
public interface SalesService {

    /**
     * Get all sales.
     *
     * @return a list of all sales
     */
    List<Sales> getAllSales();

    /**
     * Get a sales by its ID.
     *
     * @param id the ID of the sales to find
     * @return an Optional containing the sales if found, or empty if not found
     */
    Optional<Sales> getSalesById(Long id);

    /**
     * Get sales by brand ID.
     *
     * @param brandId the ID of the brand
     * @return a list of sales associated with the specified brand
     */
    List<Sales> getSalesByBrandId(Long brandId);

    /**
     * Get sales by vehicle ID.
     *
     * @param vehicleId the ID of the vehicle
     * @return a list of sales associated with the specified vehicle
     */
    List<Sales> getSalesByVehicleId(Long vehicleId);

    /**
     * Get all sales with Pageable.
     *
     * @return a list of all sales
     */
    Page<Sales> getAllSales(Pageable pageable);

    /**
     * Get best sales by date range.
     *
     * @param startDate the start date of the range
     * @param endDate   the end date of the range
     * @return a list of the best sales within the specified date range
     */
    List<VehicleBestSalesDto> getBestSellingVehicles(LocalDate startDate, LocalDate endDate);
}
