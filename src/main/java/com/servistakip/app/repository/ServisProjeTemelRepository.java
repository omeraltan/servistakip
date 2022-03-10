package com.servistakip.app.repository;

import com.servistakip.app.domain.ServisProjeTemel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ServisProjeTemel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServisProjeTemelRepository extends JpaRepository<ServisProjeTemel, Long> {}
