package com.app.spendeasyjava.domain.repositories;

import com.app.spendeasyjava.domain.entities.Goals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GoalsRepository extends JpaRepository<Goals, UUID> {
}
