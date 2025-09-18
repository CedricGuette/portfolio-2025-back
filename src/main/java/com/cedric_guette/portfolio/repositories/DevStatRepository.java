package com.cedric_guette.portfolio.repositories;

import com.cedric_guette.portfolio.entities.DevStat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevStatRepository extends JpaRepository<DevStat, Long> {
}
