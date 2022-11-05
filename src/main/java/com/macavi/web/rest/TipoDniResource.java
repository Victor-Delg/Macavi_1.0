package com.macavi.web.rest;

import com.macavi.repository.TipoDniRepository;
import com.macavi.service.TipoDniService;
import com.macavi.service.dto.TipoDniDTO;
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
 * REST controller for managing {@link com.macavi.domain.TipoDni}.
 */
@RestController
@RequestMapping("/api")
public class TipoDniResource {

    private final Logger log = LoggerFactory.getLogger(TipoDniResource.class);

    private static final String ENTITY_NAME = "tipoDni";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoDniService tipoDniService;

    private final TipoDniRepository tipoDniRepository;

    public TipoDniResource(TipoDniService tipoDniService, TipoDniRepository tipoDniRepository) {
        this.tipoDniService = tipoDniService;
        this.tipoDniRepository = tipoDniRepository;
    }

    /**
     * {@code POST  /tipo-dnis} : Create a new tipoDni.
     *
     * @param tipoDniDTO the tipoDniDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoDniDTO, or with status {@code 400 (Bad Request)} if the tipoDni has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-dnis")
    public ResponseEntity<TipoDniDTO> createTipoDni(@Valid @RequestBody TipoDniDTO tipoDniDTO) throws URISyntaxException {
        log.debug("REST request to save TipoDni : {}", tipoDniDTO);
        if (tipoDniDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoDni cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoDniDTO result = tipoDniService.save(tipoDniDTO);
        return ResponseEntity
            .created(new URI("/api/tipo-dnis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-dnis/:id} : Updates an existing tipoDni.
     *
     * @param id the id of the tipoDniDTO to save.
     * @param tipoDniDTO the tipoDniDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoDniDTO,
     * or with status {@code 400 (Bad Request)} if the tipoDniDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoDniDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-dnis/{id}")
    public ResponseEntity<TipoDniDTO> updateTipoDni(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoDniDTO tipoDniDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TipoDni : {}, {}", id, tipoDniDTO);
        if (tipoDniDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoDniDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoDniRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoDniDTO result = tipoDniService.update(tipoDniDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoDniDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-dnis/:id} : Partial updates given fields of an existing tipoDni, field will ignore if it is null
     *
     * @param id the id of the tipoDniDTO to save.
     * @param tipoDniDTO the tipoDniDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoDniDTO,
     * or with status {@code 400 (Bad Request)} if the tipoDniDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tipoDniDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoDniDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-dnis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoDniDTO> partialUpdateTipoDni(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoDniDTO tipoDniDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoDni partially : {}, {}", id, tipoDniDTO);
        if (tipoDniDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoDniDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoDniRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoDniDTO> result = tipoDniService.partialUpdate(tipoDniDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoDniDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-dnis} : get all the tipoDnis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoDnis in body.
     */
    @GetMapping("/tipo-dnis")
    public ResponseEntity<List<TipoDniDTO>> getAllTipoDnis(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TipoDnis");
        Page<TipoDniDTO> page = tipoDniService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-dnis/:id} : get the "id" tipoDni.
     *
     * @param id the id of the tipoDniDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoDniDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-dnis/{id}")
    public ResponseEntity<TipoDniDTO> getTipoDni(@PathVariable Long id) {
        log.debug("REST request to get TipoDni : {}", id);
        Optional<TipoDniDTO> tipoDniDTO = tipoDniService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoDniDTO);
    }

    /**
     * {@code DELETE  /tipo-dnis/:id} : delete the "id" tipoDni.
     *
     * @param id the id of the tipoDniDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-dnis/{id}")
    public ResponseEntity<Void> deleteTipoDni(@PathVariable Long id) {
        log.debug("REST request to delete TipoDni : {}", id);
        tipoDniService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
