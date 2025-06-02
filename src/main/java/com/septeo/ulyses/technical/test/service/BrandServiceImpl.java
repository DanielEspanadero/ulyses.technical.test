package com.septeo.ulyses.technical.test.service;

import com.septeo.ulyses.technical.test.cache.BrandCache;
import com.septeo.ulyses.technical.test.entity.Brand;
import com.septeo.ulyses.technical.test.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the BrandService interface.
 * This class provides the implementation for all brand-related operations.
 */
@Service
@Transactional(readOnly = false)
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    private final BrandCache brandCache = new BrandCache();

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Brand> getAllBrands() {
        return brandCache.getOrLoad(brandRepository::findAll);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Brand> getBrandById(Long id) {
        return brandRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Brand saveBrand(Brand brand) {
        Brand saved = brandRepository.save(brand);
        brandCache.clear();
        return saved;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
        brandCache.clear();
    }
}
