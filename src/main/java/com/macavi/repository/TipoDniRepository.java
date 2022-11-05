package com.macavi.repository;

import com.macavi.domain.TipoDni;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TipoDni entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoDniRepository extends JpaRepository<TipoDni, Long> {}
