package com.macavi.service.impl;

import com.macavi.domain.ProdFact;
import com.macavi.repository.ProdFactRepository;
import com.macavi.service.ProdFactService;
import com.macavi.service.dto.ProdFactDTO;
import com.macavi.service.mapper.ProdFactMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProdFact}.
 */
@Service
@Transactional
public class ProdFactServiceImpl implements ProdFactService {

    private final Logger log = LoggerFactory.getLogger(ProdFactServiceImpl.class);

    private final ProdFactRepository prodFactRepository;

    private final ProdFactMapper prodFactMapper;

    public ProdFactServiceImpl(ProdFactRepository prodFactRepository, ProdFactMapper prodFactMapper) {
        this.prodFactRepository = prodFactRepository;
        this.prodFactMapper = prodFactMapper;
    }

    @Override
    public ProdFactDTO save(ProdFactDTO prodFactDTO) {
        log.debug("Request to save ProdFact : {}", prodFactDTO);
        ProdFact prodFact = prodFactMapper.toEntity(prodFactDTO);
        prodFact = prodFactRepository.save(prodFact);
        return prodFactMapper.toDto(prodFact);
    }

    @Override
    public ProdFactDTO update(ProdFactDTO prodFactDTO) {
        log.debug("Request to save ProdFact : {}", prodFactDTO);
        ProdFact prodFact = prodFactMapper.toEntity(prodFactDTO);
        prodFact = prodFactRepository.save(prodFact);
        return prodFactMapper.toDto(prodFact);
    }

    @Override
    public Optional<ProdFactDTO> partialUpdate(ProdFactDTO prodFactDTO) {
        log.debug("Request to partially update ProdFact : {}", prodFactDTO);

        return prodFactRepository
            .findById(prodFactDTO.getId())
            .map(existingProdFact -> {
                prodFactMapper.partialUpdate(existingProdFact, prodFactDTO);

                return existingProdFact;
            })
            .map(prodFactRepository::save)
            .map(prodFactMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProdFactDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProdFacts");
        return prodFactRepository.findAll(pageable).map(prodFactMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProdFactDTO> findOne(Long id) {
        log.debug("Request to get ProdFact : {}", id);
        return prodFactRepository.findById(id).map(prodFactMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProdFact : {}", id);
        prodFactRepository.deleteById(id);
    }
}
