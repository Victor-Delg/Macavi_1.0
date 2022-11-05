package com.macavi.web.rest;

import com.macavi.repository.ProdFactRepository;
import com.macavi.service.ProdFactService;
import com.macavi.service.dto.ProdFactDTO;
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
 * REST controller for managing {@link com.macavi.domain.ProdFact}.
 */
@RestController
@RequestMapping("/api")
public class ProdFactResource {

    private final Logger log = LoggerFactory.getLogger(ProdFactResource.class);

    private static final String ENTITY_NAME = "prodFact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProdFactService prodFactService;

    private final ProdFactRepository prodFactRepository;

    public ProdFactResource(ProdFactService prodFactService, ProdFactRepository prodFactRepository) {
        this.prodFactService = prodFactService;
        this.prodFactRepository = prodFactRepository;
    }

    /**
     * {@code POST  /prod-facts} : Create a new prodFact.
     *
     * @param prodFactDTO the prodFactDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prodFactDTO, or with status {@code 400 (Bad Request)} if the prodFact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prod-facts")
    public ResponseEntity<ProdFactDTO> createProdFact(@Valid @RequestBody ProdFactDTO prodFactDTO) throws URISyntaxException {
        log.debug("REST request to save ProdFact : {}", prodFactDTO);
        if (prodFactDTO.getId() != null) {
            throw new BadRequestAlertException("A new prodFact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProdFactDTO result = prodFactService.save(prodFactDTO);
        return ResponseEntity
            .created(new URI("/api/prod-facts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prod-facts/:id} : Updates an existing prodFact.
     *
     * @param id the id of the prodFactDTO to save.
     * @param prodFactDTO the prodFactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prodFactDTO,
     * or with status {@code 400 (Bad Request)} if the prodFactDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prodFactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prod-facts/{id}")
    public ResponseEntity<ProdFactDTO> updateProdFact(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProdFactDTO prodFactDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProdFact : {}, {}", id, prodFactDTO);
        if (prodFactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prodFactDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prodFactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProdFactDTO result = prodFactService.update(prodFactDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prodFactDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /prod-facts/:id} : Partial updates given fields of an existing prodFact, field will ignore if it is null
     *
     * @param id the id of the prodFactDTO to save.
     * @param prodFactDTO the prodFactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prodFactDTO,
     * or with status {@code 400 (Bad Request)} if the prodFactDTO is not valid,
     * or with status {@code 404 (Not Found)} if the prodFactDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the prodFactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/prod-facts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProdFactDTO> partialUpdateProdFact(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProdFactDTO prodFactDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProdFact partially : {}, {}", id, prodFactDTO);
        if (prodFactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prodFactDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prodFactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProdFactDTO> result = prodFactService.partialUpdate(prodFactDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prodFactDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /prod-facts} : get all the prodFacts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prodFacts in body.
     */
    @GetMapping("/prod-facts")
    public ResponseEntity<List<ProdFactDTO>> getAllProdFacts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ProdFacts");
        Page<ProdFactDTO> page = prodFactService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prod-facts/:id} : get the "id" prodFact.
     *
     * @param id the id of the prodFactDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prodFactDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prod-facts/{id}")
    public ResponseEntity<ProdFactDTO> getProdFact(@PathVariable Long id) {
        log.debug("REST request to get ProdFact : {}", id);
        Optional<ProdFactDTO> prodFactDTO = prodFactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prodFactDTO);
    }

    /**
     * {@code DELETE  /prod-facts/:id} : delete the "id" prodFact.
     *
     * @param id the id of the prodFactDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prod-facts/{id}")
    public ResponseEntity<Void> deleteProdFact(@PathVariable Long id) {
        log.debug("REST request to delete ProdFact : {}", id);
        prodFactService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
