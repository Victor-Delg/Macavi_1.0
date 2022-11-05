package com.macavi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.macavi.IntegrationTest;
import com.macavi.domain.Locate;
import com.macavi.repository.LocateRepository;
import com.macavi.service.dto.LocateDTO;
import com.macavi.service.mapper.LocateMapper;
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
 * Integration tests for the {@link LocateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocateResourceIT {

    private static final String DEFAULT_CIUDAD = "AAAAAAAAAA";
    private static final String UPDATED_CIUDAD = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTAMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/locates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LocateRepository locateRepository;

    @Autowired
    private LocateMapper locateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocateMockMvc;

    private Locate locate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locate createEntity(EntityManager em) {
        Locate locate = new Locate().ciudad(DEFAULT_CIUDAD).departamento(DEFAULT_DEPARTAMENTO).pais(DEFAULT_PAIS);
        return locate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locate createUpdatedEntity(EntityManager em) {
        Locate locate = new Locate().ciudad(UPDATED_CIUDAD).departamento(UPDATED_DEPARTAMENTO).pais(UPDATED_PAIS);
        return locate;
    }

    @BeforeEach
    public void initTest() {
        locate = createEntity(em);
    }

    @Test
    @Transactional
    void createLocate() throws Exception {
        int databaseSizeBeforeCreate = locateRepository.findAll().size();
        // Create the Locate
        LocateDTO locateDTO = locateMapper.toDto(locate);
        restLocateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locateDTO)))
            .andExpect(status().isCreated());

        // Validate the Locate in the database
        List<Locate> locateList = locateRepository.findAll();
        assertThat(locateList).hasSize(databaseSizeBeforeCreate + 1);
        Locate testLocate = locateList.get(locateList.size() - 1);
        assertThat(testLocate.getCiudad()).isEqualTo(DEFAULT_CIUDAD);
        assertThat(testLocate.getDepartamento()).isEqualTo(DEFAULT_DEPARTAMENTO);
        assertThat(testLocate.getPais()).isEqualTo(DEFAULT_PAIS);
    }

    @Test
    @Transactional
    void createLocateWithExistingId() throws Exception {
        // Create the Locate with an existing ID
        locate.setId(1L);
        LocateDTO locateDTO = locateMapper.toDto(locate);

        int databaseSizeBeforeCreate = locateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Locate in the database
        List<Locate> locateList = locateRepository.findAll();
        assertThat(locateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCiudadIsRequired() throws Exception {
        int databaseSizeBeforeTest = locateRepository.findAll().size();
        // set the field null
        locate.setCiudad(null);

        // Create the Locate, which fails.
        LocateDTO locateDTO = locateMapper.toDto(locate);

        restLocateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locateDTO)))
            .andExpect(status().isBadRequest());

        List<Locate> locateList = locateRepository.findAll();
        assertThat(locateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDepartamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = locateRepository.findAll().size();
        // set the field null
        locate.setDepartamento(null);

        // Create the Locate, which fails.
        LocateDTO locateDTO = locateMapper.toDto(locate);

        restLocateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locateDTO)))
            .andExpect(status().isBadRequest());

        List<Locate> locateList = locateRepository.findAll();
        assertThat(locateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaisIsRequired() throws Exception {
        int databaseSizeBeforeTest = locateRepository.findAll().size();
        // set the field null
        locate.setPais(null);

        // Create the Locate, which fails.
        LocateDTO locateDTO = locateMapper.toDto(locate);

        restLocateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locateDTO)))
            .andExpect(status().isBadRequest());

        List<Locate> locateList = locateRepository.findAll();
        assertThat(locateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLocates() throws Exception {
        // Initialize the database
        locateRepository.saveAndFlush(locate);

        // Get all the locateList
        restLocateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locate.getId().intValue())))
            .andExpect(jsonPath("$.[*].ciudad").value(hasItem(DEFAULT_CIUDAD)))
            .andExpect(jsonPath("$.[*].departamento").value(hasItem(DEFAULT_DEPARTAMENTO)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)));
    }

    @Test
    @Transactional
    void getLocate() throws Exception {
        // Initialize the database
        locateRepository.saveAndFlush(locate);

        // Get the locate
        restLocateMockMvc
            .perform(get(ENTITY_API_URL_ID, locate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(locate.getId().intValue()))
            .andExpect(jsonPath("$.ciudad").value(DEFAULT_CIUDAD))
            .andExpect(jsonPath("$.departamento").value(DEFAULT_DEPARTAMENTO))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS));
    }

    @Test
    @Transactional
    void getNonExistingLocate() throws Exception {
        // Get the locate
        restLocateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLocate() throws Exception {
        // Initialize the database
        locateRepository.saveAndFlush(locate);

        int databaseSizeBeforeUpdate = locateRepository.findAll().size();

        // Update the locate
        Locate updatedLocate = locateRepository.findById(locate.getId()).get();
        // Disconnect from session so that the updates on updatedLocate are not directly saved in db
        em.detach(updatedLocate);
        updatedLocate.ciudad(UPDATED_CIUDAD).departamento(UPDATED_DEPARTAMENTO).pais(UPDATED_PAIS);
        LocateDTO locateDTO = locateMapper.toDto(updatedLocate);

        restLocateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locateDTO))
            )
            .andExpect(status().isOk());

        // Validate the Locate in the database
        List<Locate> locateList = locateRepository.findAll();
        assertThat(locateList).hasSize(databaseSizeBeforeUpdate);
        Locate testLocate = locateList.get(locateList.size() - 1);
        assertThat(testLocate.getCiudad()).isEqualTo(UPDATED_CIUDAD);
        assertThat(testLocate.getDepartamento()).isEqualTo(UPDATED_DEPARTAMENTO);
        assertThat(testLocate.getPais()).isEqualTo(UPDATED_PAIS);
    }

    @Test
    @Transactional
    void putNonExistingLocate() throws Exception {
        int databaseSizeBeforeUpdate = locateRepository.findAll().size();
        locate.setId(count.incrementAndGet());

        // Create the Locate
        LocateDTO locateDTO = locateMapper.toDto(locate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locate in the database
        List<Locate> locateList = locateRepository.findAll();
        assertThat(locateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocate() throws Exception {
        int databaseSizeBeforeUpdate = locateRepository.findAll().size();
        locate.setId(count.incrementAndGet());

        // Create the Locate
        LocateDTO locateDTO = locateMapper.toDto(locate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locate in the database
        List<Locate> locateList = locateRepository.findAll();
        assertThat(locateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocate() throws Exception {
        int databaseSizeBeforeUpdate = locateRepository.findAll().size();
        locate.setId(count.incrementAndGet());

        // Create the Locate
        LocateDTO locateDTO = locateMapper.toDto(locate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locateDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Locate in the database
        List<Locate> locateList = locateRepository.findAll();
        assertThat(locateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocateWithPatch() throws Exception {
        // Initialize the database
        locateRepository.saveAndFlush(locate);

        int databaseSizeBeforeUpdate = locateRepository.findAll().size();

        // Update the locate using partial update
        Locate partialUpdatedLocate = new Locate();
        partialUpdatedLocate.setId(locate.getId());

        partialUpdatedLocate.pais(UPDATED_PAIS);

        restLocateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocate))
            )
            .andExpect(status().isOk());

        // Validate the Locate in the database
        List<Locate> locateList = locateRepository.findAll();
        assertThat(locateList).hasSize(databaseSizeBeforeUpdate);
        Locate testLocate = locateList.get(locateList.size() - 1);
        assertThat(testLocate.getCiudad()).isEqualTo(DEFAULT_CIUDAD);
        assertThat(testLocate.getDepartamento()).isEqualTo(DEFAULT_DEPARTAMENTO);
        assertThat(testLocate.getPais()).isEqualTo(UPDATED_PAIS);
    }

    @Test
    @Transactional
    void fullUpdateLocateWithPatch() throws Exception {
        // Initialize the database
        locateRepository.saveAndFlush(locate);

        int databaseSizeBeforeUpdate = locateRepository.findAll().size();

        // Update the locate using partial update
        Locate partialUpdatedLocate = new Locate();
        partialUpdatedLocate.setId(locate.getId());

        partialUpdatedLocate.ciudad(UPDATED_CIUDAD).departamento(UPDATED_DEPARTAMENTO).pais(UPDATED_PAIS);

        restLocateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocate))
            )
            .andExpect(status().isOk());

        // Validate the Locate in the database
        List<Locate> locateList = locateRepository.findAll();
        assertThat(locateList).hasSize(databaseSizeBeforeUpdate);
        Locate testLocate = locateList.get(locateList.size() - 1);
        assertThat(testLocate.getCiudad()).isEqualTo(UPDATED_CIUDAD);
        assertThat(testLocate.getDepartamento()).isEqualTo(UPDATED_DEPARTAMENTO);
        assertThat(testLocate.getPais()).isEqualTo(UPDATED_PAIS);
    }

    @Test
    @Transactional
    void patchNonExistingLocate() throws Exception {
        int databaseSizeBeforeUpdate = locateRepository.findAll().size();
        locate.setId(count.incrementAndGet());

        // Create the Locate
        LocateDTO locateDTO = locateMapper.toDto(locate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locate in the database
        List<Locate> locateList = locateRepository.findAll();
        assertThat(locateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocate() throws Exception {
        int databaseSizeBeforeUpdate = locateRepository.findAll().size();
        locate.setId(count.incrementAndGet());

        // Create the Locate
        LocateDTO locateDTO = locateMapper.toDto(locate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locate in the database
        List<Locate> locateList = locateRepository.findAll();
        assertThat(locateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocate() throws Exception {
        int databaseSizeBeforeUpdate = locateRepository.findAll().size();
        locate.setId(count.incrementAndGet());

        // Create the Locate
        LocateDTO locateDTO = locateMapper.toDto(locate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocateMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(locateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Locate in the database
        List<Locate> locateList = locateRepository.findAll();
        assertThat(locateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocate() throws Exception {
        // Initialize the database
        locateRepository.saveAndFlush(locate);

        int databaseSizeBeforeDelete = locateRepository.findAll().size();

        // Delete the locate
        restLocateMockMvc
            .perform(delete(ENTITY_API_URL_ID, locate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Locate> locateList = locateRepository.findAll();
        assertThat(locateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
