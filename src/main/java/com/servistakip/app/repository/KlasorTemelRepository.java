package com.servistakip.app.repository;

import com.servistakip.app.domain.KlasorTemel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the KlasorTemel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KlasorTemelRepository extends JpaRepository<KlasorTemel, Long> {}
