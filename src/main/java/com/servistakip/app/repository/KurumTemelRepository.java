package com.servistakip.app.repository;

import com.servistakip.app.domain.KurumTemel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the KurumTemel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KurumTemelRepository extends JpaRepository<KurumTemel, Long> {}
