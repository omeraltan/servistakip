package com.servistakip.app.repository;

import com.servistakip.app.domain.TabloTemel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TabloTemel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TabloTemelRepository extends JpaRepository<TabloTemel, Long> {}
