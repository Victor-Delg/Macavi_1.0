package com.macavi.repository;

import com.macavi.domain.Locate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Locate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocateRepository extends JpaRepository<Locate, Long> {}
