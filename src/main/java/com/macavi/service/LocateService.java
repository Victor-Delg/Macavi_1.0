package com.macavi.service;

import com.macavi.service.dto.LocateDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.macavi.domain.Locate}.
 */
public interface LocateService {
    /**
     * Save a locate.
     *
     * @param locateDTO the entity to save.
     * @return the persisted entity.
     */
    LocateDTO save(LocateDTO locateDTO);

    /**
     * Updates a locate.
     *
     * @param locateDTO the entity to update.
     * @return the persisted entity.
     */
    LocateDTO update(LocateDTO locateDTO);

    /**
     * Partially updates a locate.
     *
     * @param locateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LocateDTO> partialUpdate(LocateDTO locateDTO);

    /**
     * Get all the locates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LocateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" locate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LocateDTO> findOne(Long id);

    /**
     * Delete the "id" locate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
