package com.macavi.web.rest;

import com.macavi.repository.LocateRepository;
import com.macavi.service.LocateService;
import com.macavi.service.dto.LocateDTO;
import com.macavi.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.macavi.domain.Locate}.
 */
@RestController
@RequestMapping("/api")
public class LocateResource {

    private final Logger log = LoggerFactory.getLogger(LocateResource.class);

    private static final String ENTITY_NAME = "locate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocateService locateService;

    private final LocateRepository locateRepository;

    public LocateResource(LocateService locateService, LocateRepository locateRepository) {
        this.locateService = locateService;
        this.locateRepository = locateRepository;
    }

    /**
     * {@code POST  /locates} : Create a new locate.
     *
     * @param locateDTO the locateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locateDTO, or with status {@code 400 (Bad Request)} if the locate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/locates")
    public ResponseEntity<LocateDTO> createLocate(@Valid @RequestBody LocateDTO locateDTO) throws URISyntaxException {
        log.debug("REST request to save Locate : {}", locateDTO);
        if (locateDTO.getId() != null) {
            throw new BadRequestAlertException("A new locate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocateDTO result = locateService.save(locateDTO);
        return ResponseEntity
            .created(new URI("/api/locates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /locates/:id} : Updates an existing locate.
     *
     * @param id the id of the locateDTO to save.
     * @param locateDTO the locateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locateDTO,
     * or with status {@code 400 (Bad Request)} if the locateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/locates/{id}")
    public ResponseEntity<LocateDTO> updateLocate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LocateDTO locateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Locate : {}, {}", id, locateDTO);
        if (locateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LocateDTO result = locateService.update(locateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /locates/:id} : Partial updates given fields of an existing locate, field will ignore if it is null
     *
     * @param id the id of the locateDTO to save.
     * @param locateDTO the locateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locateDTO,
     * or with status {@code 400 (Bad Request)} if the locateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the locateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the locateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/locates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LocateDTO> partialUpdateLocate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LocateDTO locateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Locate partially : {}, {}", id, locateDTO);
        if (locateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LocateDTO> result = locateService.partialUpdate(locateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /locates} : get all the locates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locates in body.
     */
    @GetMapping("/locates")
    public ResponseEntity<List<LocateDTO>> getAllLocates(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Locates");
        Page<LocateDTO> page = locateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /locates/:id} : get the "id" locate.
     *
     * @param id the id of the locateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/locates/{id}")
    public ResponseEntity<LocateDTO> getLocate(@PathVariable Long id) {
        log.debug("REST request to get Locate : {}", id);
        Optional<LocateDTO> locateDTO = locateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locateDTO);
    }

    /**
     * {@code DELETE  /locates/:id} : delete the "id" locate.
     *
     * @param id the id of the locateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/locates/{id}")
    public ResponseEntity<Void> deleteLocate(@PathVariable Long id) {
        log.debug("REST request to delete Locate : {}", id);
        locateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
