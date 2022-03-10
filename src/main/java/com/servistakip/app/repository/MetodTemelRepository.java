package com.servistakip.app.repository;

import com.servistakip.app.domain.MetodTemel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MetodTemel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetodTemelRepository extends JpaRepository<MetodTemel, Long> {}
