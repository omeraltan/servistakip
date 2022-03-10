package com.servistakip.app.repository;

import com.servistakip.app.domain.ProtokolTemel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProtokolTemel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProtokolTemelRepository extends JpaRepository<ProtokolTemel, Long> {}
