package com.macavi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.macavi.IntegrationTest;
import com.macavi.domain.TipoDni;
import com.macavi.repository.TipoDniRepository;
import com.macavi.service.dto.TipoDniDTO;
import com.macavi.service.mapper.TipoDniMapper;
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
 * Integration tests for the {@link TipoDniResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoDniResourceIT {

    private static final String DEFAULT_SIGLAS_DNI = "AAAA";
    private static final String UPDATED_SIGLAS_DNI = "BBBB";

    private static final String DEFAULT_NOMBRE_DNI = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DNI = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-dnis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoDniRepository tipoDniRepository;

    @Autowired
    private TipoDniMapper tipoDniMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoDniMockMvc;

    private TipoDni tipoDni;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoDni createEntity(EntityManager em) {
        TipoDni tipoDni = new TipoDni().siglasDni(DEFAULT_SIGLAS_DNI).nombreDni(DEFAULT_NOMBRE_DNI);
        return tipoDni;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoDni createUpdatedEntity(EntityManager em) {
        TipoDni tipoDni = new TipoDni().siglasDni(UPDATED_SIGLAS_DNI).nombreDni(UPDATED_NOMBRE_DNI);
        return tipoDni;
    }

    @BeforeEach
    public void initTest() {
        tipoDni = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoDni() throws Exception {
        int databaseSizeBeforeCreate = tipoDniRepository.findAll().size();
        // Create the TipoDni
        TipoDniDTO tipoDniDTO = tipoDniMapper.toDto(tipoDni);
        restTipoDniMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoDniDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoDni in the database
        List<TipoDni> tipoDniList = tipoDniRepository.findAll();
        assertThat(tipoDniList).hasSize(databaseSizeBeforeCreate + 1);
        TipoDni testTipoDni = tipoDniList.get(tipoDniList.size() - 1);
        assertThat(testTipoDni.getSiglasDni()).isEqualTo(DEFAULT_SIGLAS_DNI);
        assertThat(testTipoDni.getNombreDni()).isEqualTo(DEFAULT_NOMBRE_DNI);
    }

    @Test
    @Transactional
    void createTipoDniWithExistingId() throws Exception {
        // Create the TipoDni with an existing ID
        tipoDni.setId(1L);
        TipoDniDTO tipoDniDTO = tipoDniMapper.toDto(tipoDni);

        int databaseSizeBeforeCreate = tipoDniRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoDniMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoDniDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDni in the database
        List<TipoDni> tipoDniList = tipoDniRepository.findAll();
        assertThat(tipoDniList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSiglasDniIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDniRepository.findAll().size();
        // set the field null
        tipoDni.setSiglasDni(null);

        // Create the TipoDni, which fails.
        TipoDniDTO tipoDniDTO = tipoDniMapper.toDto(tipoDni);

        restTipoDniMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoDniDTO)))
            .andExpect(status().isBadRequest());

        List<TipoDni> tipoDniList = tipoDniRepository.findAll();
        assertThat(tipoDniList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreDniIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDniRepository.findAll().size();
        // set the field null
        tipoDni.setNombreDni(null);

        // Create the TipoDni, which fails.
        TipoDniDTO tipoDniDTO = tipoDniMapper.toDto(tipoDni);

        restTipoDniMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoDniDTO)))
            .andExpect(status().isBadRequest());

        List<TipoDni> tipoDniList = tipoDniRepository.findAll();
        assertThat(tipoDniList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTipoDnis() throws Exception {
        // Initialize the database
        tipoDniRepository.saveAndFlush(tipoDni);

        // Get all the tipoDniList
        restTipoDniMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDni.getId().intValue())))
            .andExpect(jsonPath("$.[*].siglasDni").value(hasItem(DEFAULT_SIGLAS_DNI)))
            .andExpect(jsonPath("$.[*].nombreDni").value(hasItem(DEFAULT_NOMBRE_DNI)));
    }

    @Test
    @Transactional
    void getTipoDni() throws Exception {
        // Initialize the database
        tipoDniRepository.saveAndFlush(tipoDni);

        // Get the tipoDni
        restTipoDniMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoDni.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoDni.getId().intValue()))
            .andExpect(jsonPath("$.siglasDni").value(DEFAULT_SIGLAS_DNI))
            .andExpect(jsonPath("$.nombreDni").value(DEFAULT_NOMBRE_DNI));
    }

    @Test
    @Transactional
    void getNonExistingTipoDni() throws Exception {
        // Get the tipoDni
        restTipoDniMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoDni() throws Exception {
        // Initialize the database
        tipoDniRepository.saveAndFlush(tipoDni);

        int databaseSizeBeforeUpdate = tipoDniRepository.findAll().size();

        // Update the tipoDni
        TipoDni updatedTipoDni = tipoDniRepository.findById(tipoDni.getId()).get();
        // Disconnect from session so that the updates on updatedTipoDni are not directly saved in db
        em.detach(updatedTipoDni);
        updatedTipoDni.siglasDni(UPDATED_SIGLAS_DNI).nombreDni(UPDATED_NOMBRE_DNI);
        TipoDniDTO tipoDniDTO = tipoDniMapper.toDto(updatedTipoDni);

        restTipoDniMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoDniDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoDniDTO))
            )
            .andExpect(status().isOk());

        // Validate the TipoDni in the database
        List<TipoDni> tipoDniList = tipoDniRepository.findAll();
        assertThat(tipoDniList).hasSize(databaseSizeBeforeUpdate);
        TipoDni testTipoDni = tipoDniList.get(tipoDniList.size() - 1);
        assertThat(testTipoDni.getSiglasDni()).isEqualTo(UPDATED_SIGLAS_DNI);
        assertThat(testTipoDni.getNombreDni()).isEqualTo(UPDATED_NOMBRE_DNI);
    }

    @Test
    @Transactional
    void putNonExistingTipoDni() throws Exception {
        int databaseSizeBeforeUpdate = tipoDniRepository.findAll().size();
        tipoDni.setId(count.incrementAndGet());

        // Create the TipoDni
        TipoDniDTO tipoDniDTO = tipoDniMapper.toDto(tipoDni);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDniMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoDniDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoDniDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoDni in the database
        List<TipoDni> tipoDniList = tipoDniRepository.findAll();
        assertThat(tipoDniList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoDni() throws Exception {
        int databaseSizeBeforeUpdate = tipoDniRepository.findAll().size();
        tipoDni.setId(count.incrementAndGet());

        // Create the TipoDni
        TipoDniDTO tipoDniDTO = tipoDniMapper.toDto(tipoDni);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoDniMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoDniDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoDni in the database
        List<TipoDni> tipoDniList = tipoDniRepository.findAll();
        assertThat(tipoDniList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoDni() throws Exception {
        int databaseSizeBeforeUpdate = tipoDniRepository.findAll().size();
        tipoDni.setId(count.incrementAndGet());

        // Create the TipoDni
        TipoDniDTO tipoDniDTO = tipoDniMapper.toDto(tipoDni);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoDniMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoDniDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoDni in the database
        List<TipoDni> tipoDniList = tipoDniRepository.findAll();
        assertThat(tipoDniList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoDniWithPatch() throws Exception {
        // Initialize the database
        tipoDniRepository.saveAndFlush(tipoDni);

        int databaseSizeBeforeUpdate = tipoDniRepository.findAll().size();

        // Update the tipoDni using partial update
        TipoDni partialUpdatedTipoDni = new TipoDni();
        partialUpdatedTipoDni.setId(tipoDni.getId());

        partialUpdatedTipoDni.siglasDni(UPDATED_SIGLAS_DNI).nombreDni(UPDATED_NOMBRE_DNI);

        restTipoDniMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoDni.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoDni))
            )
            .andExpect(status().isOk());

        // Validate the TipoDni in the database
        List<TipoDni> tipoDniList = tipoDniRepository.findAll();
        assertThat(tipoDniList).hasSize(databaseSizeBeforeUpdate);
        TipoDni testTipoDni = tipoDniList.get(tipoDniList.size() - 1);
        assertThat(testTipoDni.getSiglasDni()).isEqualTo(UPDATED_SIGLAS_DNI);
        assertThat(testTipoDni.getNombreDni()).isEqualTo(UPDATED_NOMBRE_DNI);
    }

    @Test
    @Transactional
    void fullUpdateTipoDniWithPatch() throws Exception {
        // Initialize the database
        tipoDniRepository.saveAndFlush(tipoDni);

        int databaseSizeBeforeUpdate = tipoDniRepository.findAll().size();

        // Update the tipoDni using partial update
        TipoDni partialUpdatedTipoDni = new TipoDni();
        partialUpdatedTipoDni.setId(tipoDni.getId());

        partialUpdatedTipoDni.siglasDni(UPDATED_SIGLAS_DNI).nombreDni(UPDATED_NOMBRE_DNI);

        restTipoDniMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoDni.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoDni))
            )
            .andExpect(status().isOk());

        // Validate the TipoDni in the database
        List<TipoDni> tipoDniList = tipoDniRepository.findAll();
        assertThat(tipoDniList).hasSize(databaseSizeBeforeUpdate);
        TipoDni testTipoDni = tipoDniList.get(tipoDniList.size() - 1);
        assertThat(testTipoDni.getSiglasDni()).isEqualTo(UPDATED_SIGLAS_DNI);
        assertThat(testTipoDni.getNombreDni()).isEqualTo(UPDATED_NOMBRE_DNI);
    }

    @Test
    @Transactional
    void patchNonExistingTipoDni() throws Exception {
        int databaseSizeBeforeUpdate = tipoDniRepository.findAll().size();
        tipoDni.setId(count.incrementAndGet());

        // Create the TipoDni
        TipoDniDTO tipoDniDTO = tipoDniMapper.toDto(tipoDni);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDniMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoDniDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoDniDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoDni in the database
        List<TipoDni> tipoDniList = tipoDniRepository.findAll();
        assertThat(tipoDniList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoDni() throws Exception {
        int databaseSizeBeforeUpdate = tipoDniRepository.findAll().size();
        tipoDni.setId(count.incrementAndGet());

        // Create the TipoDni
        TipoDniDTO tipoDniDTO = tipoDniMapper.toDto(tipoDni);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoDniMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoDniDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoDni in the database
        List<TipoDni> tipoDniList = tipoDniRepository.findAll();
        assertThat(tipoDniList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoDni() throws Exception {
        int databaseSizeBeforeUpdate = tipoDniRepository.findAll().size();
        tipoDni.setId(count.incrementAndGet());

        // Create the TipoDni
        TipoDniDTO tipoDniDTO = tipoDniMapper.toDto(tipoDni);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoDniMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoDniDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoDni in the database
        List<TipoDni> tipoDniList = tipoDniRepository.findAll();
        assertThat(tipoDniList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoDni() throws Exception {
        // Initialize the database
        tipoDniRepository.saveAndFlush(tipoDni);

        int databaseSizeBeforeDelete = tipoDniRepository.findAll().size();

        // Delete the tipoDni
        restTipoDniMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoDni.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoDni> tipoDniList = tipoDniRepository.findAll();
        assertThat(tipoDniList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
