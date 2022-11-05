package com.macavi.service;

import com.macavi.service.dto.TipoDniDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.macavi.domain.TipoDni}.
 */
public interface TipoDniService {
    /**
     * Save a tipoDni.
     *
     * @param tipoDniDTO the entity to save.
     * @return the persisted entity.
     */
    TipoDniDTO save(TipoDniDTO tipoDniDTO);

    /**
     * Updates a tipoDni.
     *
     * @param tipoDniDTO the entity to update.
     * @return the persisted entity.
     */
    TipoDniDTO update(TipoDniDTO tipoDniDTO);

    /**
     * Partially updates a tipoDni.
     *
     * @param tipoDniDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoDniDTO> partialUpdate(TipoDniDTO tipoDniDTO);

    /**
     * Get all the tipoDnis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoDniDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tipoDni.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoDniDTO> findOne(Long id);

    /**
     * Delete the "id" tipoDni.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
