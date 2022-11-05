package com.macavi.repository;

import com.macavi.domain.ProdFact;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProdFact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdFactRepository extends JpaRepository<ProdFact, Long> {}
