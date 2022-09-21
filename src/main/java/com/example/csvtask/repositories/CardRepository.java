package com.example.csvtask.repositories;

import com.example.csvtask.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for {@link Card} entity.
 *
 * @author Otabek Sherman
 * @since 2022/09/20
 */
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}
