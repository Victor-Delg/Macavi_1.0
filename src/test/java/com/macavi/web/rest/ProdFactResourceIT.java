package com.macavi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.macavi.IntegrationTest;
import com.macavi.domain.Factura;
import com.macavi.domain.ProdFact;
import com.macavi.domain.Producto;
import com.macavi.repository.ProdFactRepository;
import com.macavi.service.dto.ProdFactDTO;
import com.macavi.service.mapper.ProdFactMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProdFactResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProdFactResourceIT {

    private static final String ENTITY_API_URL = "/api/prod-facts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProdFactRepository prodFactRepository;

    @Autowired
    private ProdFactMapper prodFactMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProdFactMockMvc;

    private ProdFact prodFact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProdFact createEntity(EntityManager em) {
        ProdFact prodFact = new ProdFact();
        // Add required entity
        Factura factura;
        if (TestUtil.findAll(em, Factura.class).isEmpty()) {
            factura = FacturaResourceIT.createEntity(em);
            em.persist(factura);
            em.flush();
        } else {
            factura = TestUtil.findAll(em, Factura.class).get(0);
        }
        prodFact.setFactura(factura);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        prodFact.setProducto(producto);
        return prodFact;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProdFact createUpdatedEntity(EntityManager em) {
        ProdFact prodFact = new ProdFact();
        // Add required entity
        Factura factura;
        if (TestUtil.findAll(em, Factura.class).isEmpty()) {
            factura = FacturaResourceIT.createUpdatedEntity(em);
            em.persist(factura);
            em.flush();
        } else {
            factura = TestUtil.findAll(em, Factura.class).get(0);
        }
        prodFact.setFactura(factura);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createUpdatedEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        prodFact.setProducto(producto);
        return prodFact;
    }

    @BeforeEach
    public void initTest() {
        prodFact = createEntity(em);
    }

    @Test
    @Transactional
    void createProdFact() throws Exception {
        int databaseSizeBeforeCreate = prodFactRepository.findAll().size();
        // Create the ProdFact
        ProdFactDTO prodFactDTO = prodFactMapper.toDto(prodFact);
        restProdFactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prodFactDTO)))
            .andExpect(status().isCreated());

        // Validate the ProdFact in the database
        List<ProdFact> prodFactList = prodFactRepository.findAll();
        assertThat(prodFactList).hasSize(databaseSizeBeforeCreate + 1);
        ProdFact testProdFact = prodFactList.get(prodFactList.size() - 1);
    }

    @Test
    @Transactional
    void createProdFactWithExistingId() throws Exception {
        // Create the ProdFact with an existing ID
        prodFact.setId(1L);
        ProdFactDTO prodFactDTO = prodFactMapper.toDto(prodFact);

        int databaseSizeBeforeCreate = prodFactRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdFactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prodFactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProdFact in the database
        List<ProdFact> prodFactList = prodFactRepository.findAll();
        assertThat(prodFactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProdFacts() throws Exception {
        // Initialize the database
        prodFactRepository.saveAndFlush(prodFact);

        // Get all the prodFactList
        restProdFactMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prodFact.getId().intValue())));
    }

    @Test
    @Transactional
    void getProdFact() throws Exception {
        // Initialize the database
        prodFactRepository.saveAndFlush(prodFact);

        // Get the prodFact
        restProdFactMockMvc
            .perform(get(ENTITY_API_URL_ID, prodFact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prodFact.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingProdFact() throws Exception {
        // Get the prodFact
        restProdFactMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProdFact() throws Exception {
        // Initialize the database
        prodFactRepository.saveAndFlush(prodFact);

        int databaseSizeBeforeUpdate = prodFactRepository.findAll().size();

        // Update the prodFact
        ProdFact updatedProdFact = prodFactRepository.findById(prodFact.getId()).get();
        // Disconnect from session so that the updates on updatedProdFact are not directly saved in db
        em.detach(updatedProdFact);
        ProdFactDTO prodFactDTO = prodFactMapper.toDto(updatedProdFact);

        restProdFactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prodFactDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prodFactDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProdFact in the database
        List<ProdFact> prodFactList = prodFactRepository.findAll();
        assertThat(prodFactList).hasSize(databaseSizeBeforeUpdate);
        ProdFact testProdFact = prodFactList.get(prodFactList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingProdFact() throws Exception {
        int databaseSizeBeforeUpdate = prodFactRepository.findAll().size();
        prodFact.setId(count.incrementAndGet());

        // Create the ProdFact
        ProdFactDTO prodFactDTO = prodFactMapper.toDto(prodFact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdFactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prodFactDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prodFactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdFact in the database
        List<ProdFact> prodFactList = prodFactRepository.findAll();
        assertThat(prodFactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProdFact() throws Exception {
        int databaseSizeBeforeUpdate = prodFactRepository.findAll().size();
        prodFact.setId(count.incrementAndGet());

        // Create the ProdFact
        ProdFactDTO prodFactDTO = prodFactMapper.toDto(prodFact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdFactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prodFactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdFact in the database
        List<ProdFact> prodFactList = prodFactRepository.findAll();
        assertThat(prodFactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProdFact() throws Exception {
        int databaseSizeBeforeUpdate = prodFactRepository.findAll().size();
        prodFact.setId(count.incrementAndGet());

        // Create the ProdFact
        ProdFactDTO prodFactDTO = prodFactMapper.toDto(prodFact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdFactMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prodFactDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProdFact in the database
        List<ProdFact> prodFactList = prodFactRepository.findAll();
        assertThat(prodFactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProdFactWithPatch() throws Exception {
        // Initialize the database
        prodFactRepository.saveAndFlush(prodFact);

        int databaseSizeBeforeUpdate = prodFactRepository.findAll().size();

        // Update the prodFact using partial update
        ProdFact partialUpdatedProdFact = new ProdFact();
        partialUpdatedProdFact.setId(prodFact.getId());

        restProdFactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProdFact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProdFact))
            )
            .andExpect(status().isOk());

        // Validate the ProdFact in the database
        List<ProdFact> prodFactList = prodFactRepository.findAll();
        assertThat(prodFactList).hasSize(databaseSizeBeforeUpdate);
        ProdFact testProdFact = prodFactList.get(prodFactList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateProdFactWithPatch() throws Exception {
        // Initialize the database
        prodFactRepository.saveAndFlush(prodFact);

        int databaseSizeBeforeUpdate = prodFactRepository.findAll().size();

        // Update the prodFact using partial update
        ProdFact partialUpdatedProdFact = new ProdFact();
        partialUpdatedProdFact.setId(prodFact.getId());

        restProdFactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProdFact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProdFact))
            )
            .andExpect(status().isOk());

        // Validate the ProdFact in the database
        List<ProdFact> prodFactList = prodFactRepository.findAll();
        assertThat(prodFactList).hasSize(databaseSizeBeforeUpdate);
        ProdFact testProdFact = prodFactList.get(prodFactList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingProdFact() throws Exception {
        int databaseSizeBeforeUpdate = prodFactRepository.findAll().size();
        prodFact.setId(count.incrementAndGet());

        // Create the ProdFact
        ProdFactDTO prodFactDTO = prodFactMapper.toDto(prodFact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdFactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prodFactDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prodFactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdFact in the database
        List<ProdFact> prodFactList = prodFactRepository.findAll();
        assertThat(prodFactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProdFact() throws Exception {
        int databaseSizeBeforeUpdate = prodFactRepository.findAll().size();
        prodFact.setId(count.incrementAndGet());

        // Create the ProdFact
        ProdFactDTO prodFactDTO = prodFactMapper.toDto(prodFact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdFactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prodFactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdFact in the database
        List<ProdFact> prodFactList = prodFactRepository.findAll();
        assertThat(prodFactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProdFact() throws Exception {
        int databaseSizeBeforeUpdate = prodFactRepository.findAll().size();
        prodFact.setId(count.incrementAndGet());

        // Create the ProdFact
        ProdFactDTO prodFactDTO = prodFactMapper.toDto(prodFact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdFactMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(prodFactDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProdFact in the database
        List<ProdFact> prodFactList = prodFactRepository.findAll();
        assertThat(prodFactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProdFact() throws Exception {
        // Initialize the database
        prodFactRepository.saveAndFlush(prodFact);

        int databaseSizeBeforeDelete = prodFactRepository.findAll().size();

        // Delete the prodFact
        restProdFactMockMvc
            .perform(delete(ENTITY_API_URL_ID, prodFact.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProdFact> prodFactList = prodFactRepository.findAll();
        assertThat(prodFactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
