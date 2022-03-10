package com.servistakip.app.repository;

import com.servistakip.app.domain.IrtibatTemel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IrtibatTemel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IrtibatTemelRepository extends JpaRepository<IrtibatTemel, Long> {}
