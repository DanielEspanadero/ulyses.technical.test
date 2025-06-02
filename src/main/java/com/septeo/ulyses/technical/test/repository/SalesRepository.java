package com.septeo.ulyses.technical.test.repository;

import com.septeo.ulyses.technical.test.entity.Brand;
import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Sales entity.
 */
@Repository
public interface SalesRepository {
    /**
     * Find all sales.
     *
     * @return a list of all sales
     */
    List<Sales> findAll();

    /**
     * Find a sale by its ID.
     *
     * @param id the ID of the sale to find
     * @return an Optional containing the sale if found, or empty if not found
     */
    Optional<Sales> findById(Long id);

    /**
     * Find sales by brand ID.
     *
     * @param brandId the ID of the brand
     * @return a list of sales associated with the specified brand
     */
    List<Sales> findByBrandId(Long brandId);

    /**
     * Find sales by vehicle ID.
     *
     * @param vehicleId the ID of the vehicle
     * @return a list of sales associated with the specified vehicle
     */
    List<Sales> findByVehicleId(Long vehicleId);


    /**
     * Find all sales with pagination.
     *
     * @return a list of all sales
     */
    Page<Sales> getAllSales(Pageable pageable);

}
