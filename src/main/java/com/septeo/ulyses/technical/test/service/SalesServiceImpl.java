package com.septeo.ulyses.technical.test.service;

import com.septeo.ulyses.technical.test.dto.VehicleBestSalesDto;
import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.entity.Vehicle;
import com.septeo.ulyses.technical.test.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

/**
 * Implementation of the SalesService interface.
 * This class provides the implementation for all sales-related operations.
 */
@Service
@Transactional(readOnly = false)
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesRepository salesRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sales> getAllSales() {
        return salesRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Sales> getSalesById(Long id) {
        return salesRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sales> getSalesByBrandId(Long brandId) {
        return salesRepository.findByBrandId(brandId);
    }

    @Override
    public List<Sales> getSalesByVehicleId(Long vehicleId) {
        return salesRepository.findByVehicleId(vehicleId);
    }

    @Override
    public Page<Sales> getAllSales(Pageable pageable) {
        return salesRepository.getAllSales(pageable);
    }

    @Override
    public List<VehicleBestSalesDto> getBestSellingVehicles(LocalDate startDate, LocalDate endDate) {
        List<Sales> allSales = salesRepository.findAll();

        List<Sales> filteredSales = allSales.stream()
                .filter(sale -> {
                    LocalDate saleDate = sale.getSaleDate();
                    boolean afterStart = (startDate == null || !saleDate.isBefore(startDate));
                    boolean beforeEnd = (endDate == null || !saleDate.isAfter(endDate));
                    return afterStart && beforeEnd;
                })
                .toList();

        Map<Vehicle, Integer> salesCountMap = new HashMap<>();
        for (Sales sale : filteredSales) {
            Vehicle vehicle = sale.getVehicle();
            salesCountMap.put(vehicle, salesCountMap.getOrDefault(vehicle, 0) + 1);
        }

        List<VehicleBestSalesDto> allVehicleSales = salesCountMap.entrySet().stream()
                .map(entry -> new VehicleBestSalesDto(entry.getKey(), entry.getValue().longValue()))
                .toList();

        return getTopBestSellingVehicles(allVehicleSales);
    }

    private List<VehicleBestSalesDto> getTopBestSellingVehicles(List<VehicleBestSalesDto> allVehicleSales) {
        int topCount = 5;
        topCount = Math.min(topCount, allVehicleSales.size());

        List<VehicleBestSalesDto> minHeap = new ArrayList<>(allVehicleSales.subList(0, topCount));
        buildMinHeap(minHeap);

        for (int i = topCount; i < allVehicleSales.size(); i++) {
            if (allVehicleSales.get(i).getTotalSales() > minHeap.getFirst().getTotalSales()) {
                minHeap.set(0, allVehicleSales.get(i));
                heapifyMinHeap(minHeap, 0);
            }
        }

        List<VehicleBestSalesDto> extractedTopVehicles = new ArrayList<>(topCount);
        for (int i = 0; i < topCount; i++) {
            extractedTopVehicles.add(minHeap.getFirst());
            minHeap.set(0, minHeap.getLast());
            minHeap.removeLast();
            if (!minHeap.isEmpty()) {
                heapifyMinHeap(minHeap, 0);
            }
        }

        List<VehicleBestSalesDto> sortedTopVehicles = new ArrayList<>(topCount);
        for (int i = extractedTopVehicles.size() - 1; i >= 0; i--) {
            sortedTopVehicles.add(extractedTopVehicles.get(i));
        }

        return sortedTopVehicles;
    }

    private void buildMinHeap(List<VehicleBestSalesDto> heap) {
        for (int i = heap.size() / 2 - 1; i >= 0; i--) {
            heapifyMinHeap(heap, i);
        }
    }

    private void heapifyMinHeap(List<VehicleBestSalesDto> heap, int index) {
        int smallest = index;
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        int heapSize = heap.size();

        if (leftChild < heapSize && heap.get(leftChild).getTotalSales() < heap.get(smallest).getTotalSales()) {
            smallest = leftChild;
        }
        if (rightChild < heapSize && heap.get(rightChild).getTotalSales() < heap.get(smallest).getTotalSales()) {
            smallest = rightChild;
        }

        if (smallest != index) {
            VehicleBestSalesDto temp = heap.get(index);
            heap.set(index, heap.get(smallest));
            heap.set(smallest, temp);
            heapifyMinHeap(heap, smallest);
        }
    }
}
