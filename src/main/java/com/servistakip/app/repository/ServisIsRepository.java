package com.servistakip.app.repository;

import com.servistakip.app.domain.ServisIs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ServisIs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServisIsRepository extends JpaRepository<ServisIs, Long> {}
