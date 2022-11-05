package com.macavi.service.impl;

import com.macavi.domain.TipoDni;
import com.macavi.repository.TipoDniRepository;
import com.macavi.service.TipoDniService;
import com.macavi.service.dto.TipoDniDTO;
import com.macavi.service.mapper.TipoDniMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoDni}.
 */
@Service
@Transactional
public class TipoDniServiceImpl implements TipoDniService {

    private final Logger log = LoggerFactory.getLogger(TipoDniServiceImpl.class);

    private final TipoDniRepository tipoDniRepository;

    private final TipoDniMapper tipoDniMapper;

    public TipoDniServiceImpl(TipoDniRepository tipoDniRepository, TipoDniMapper tipoDniMapper) {
        this.tipoDniRepository = tipoDniRepository;
        this.tipoDniMapper = tipoDniMapper;
    }

    @Override
    public TipoDniDTO save(TipoDniDTO tipoDniDTO) {
        log.debug("Request to save TipoDni : {}", tipoDniDTO);
        TipoDni tipoDni = tipoDniMapper.toEntity(tipoDniDTO);
        tipoDni = tipoDniRepository.save(tipoDni);
        return tipoDniMapper.toDto(tipoDni);
    }

    @Override
    public TipoDniDTO update(TipoDniDTO tipoDniDTO) {
        log.debug("Request to save TipoDni : {}", tipoDniDTO);
        TipoDni tipoDni = tipoDniMapper.toEntity(tipoDniDTO);
        tipoDni = tipoDniRepository.save(tipoDni);
        return tipoDniMapper.toDto(tipoDni);
    }

    @Override
    public Optional<TipoDniDTO> partialUpdate(TipoDniDTO tipoDniDTO) {
        log.debug("Request to partially update TipoDni : {}", tipoDniDTO);

        return tipoDniRepository
            .findById(tipoDniDTO.getId())
            .map(existingTipoDni -> {
                tipoDniMapper.partialUpdate(existingTipoDni, tipoDniDTO);

                return existingTipoDni;
            })
            .map(tipoDniRepository::save)
            .map(tipoDniMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoDniDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoDnis");
        return tipoDniRepository.findAll(pageable).map(tipoDniMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoDniDTO> findOne(Long id) {
        log.debug("Request to get TipoDni : {}", id);
        return tipoDniRepository.findById(id).map(tipoDniMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoDni : {}", id);
        tipoDniRepository.deleteById(id);
    }
}
