package com.macavi.service;

import com.macavi.service.dto.ProdFactDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.macavi.domain.ProdFact}.
 */
public interface ProdFactService {
    /**
     * Save a prodFact.
     *
     * @param prodFactDTO the entity to save.
     * @return the persisted entity.
     */
    ProdFactDTO save(ProdFactDTO prodFactDTO);

    /**
     * Updates a prodFact.
     *
     * @param prodFactDTO the entity to update.
     * @return the persisted entity.
     */
    ProdFactDTO update(ProdFactDTO prodFactDTO);

    /**
     * Partially updates a prodFact.
     *
     * @param prodFactDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProdFactDTO> partialUpdate(ProdFactDTO prodFactDTO);

    /**
     * Get all the prodFacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProdFactDTO> findAll(Pageable pageable);

    /**
     * Get the "id" prodFact.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProdFactDTO> findOne(Long id);

    /**
     * Delete the "id" prodFact.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
