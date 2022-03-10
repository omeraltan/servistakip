package com.servistakip.app.repository;

import com.servistakip.app.domain.ServisTemel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ServisTemel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServisTemelRepository extends JpaRepository<ServisTemel, Long> {}
