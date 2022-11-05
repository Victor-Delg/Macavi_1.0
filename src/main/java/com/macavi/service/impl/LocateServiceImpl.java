package com.macavi.service.impl;

import com.macavi.domain.Locate;
import com.macavi.repository.LocateRepository;
import com.macavi.service.LocateService;
import com.macavi.service.dto.LocateDTO;
import com.macavi.service.mapper.LocateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Locate}.
 */
@Service
@Transactional
public class LocateServiceImpl implements LocateService {

    private final Logger log = LoggerFactory.getLogger(LocateServiceImpl.class);

    private final LocateRepository locateRepository;

    private final LocateMapper locateMapper;

    public LocateServiceImpl(LocateRepository locateRepository, LocateMapper locateMapper) {
        this.locateRepository = locateRepository;
        this.locateMapper = locateMapper;
    }

    @Override
    public LocateDTO save(LocateDTO locateDTO) {
        log.debug("Request to save Locate : {}", locateDTO);
        Locate locate = locateMapper.toEntity(locateDTO);
        locate = locateRepository.save(locate);
        return locateMapper.toDto(locate);
    }

    @Override
    public LocateDTO update(LocateDTO locateDTO) {
        log.debug("Request to save Locate : {}", locateDTO);
        Locate locate = locateMapper.toEntity(locateDTO);
        locate = locateRepository.save(locate);
        return locateMapper.toDto(locate);
    }

    @Override
    public Optional<LocateDTO> partialUpdate(LocateDTO locateDTO) {
        log.debug("Request to partially update Locate : {}", locateDTO);

        return locateRepository
            .findById(locateDTO.getId())
            .map(existingLocate -> {
                locateMapper.partialUpdate(existingLocate, locateDTO);

                return existingLocate;
            })
            .map(locateRepository::save)
            .map(locateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LocateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Locates");
        return locateRepository.findAll(pageable).map(locateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LocateDTO> findOne(Long id) {
        log.debug("Request to get Locate : {}", id);
        return locateRepository.findById(id).map(locateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Locate : {}", id);
        locateRepository.deleteById(id);
    }
}
