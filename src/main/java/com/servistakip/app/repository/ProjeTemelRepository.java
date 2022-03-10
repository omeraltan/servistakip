package com.servistakip.app.repository;

import com.servistakip.app.domain.ProjeTemel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProjeTemel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjeTemelRepository extends JpaRepository<ProjeTemel, Long> {}
