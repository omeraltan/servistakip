package com.servistakip.app.repository;

import com.servistakip.app.domain.SorunTemel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SorunTemel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SorunTemelRepository extends JpaRepository<SorunTemel, Long> {}
