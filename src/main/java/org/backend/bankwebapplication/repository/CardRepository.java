package org.backend.bankwebapplication.repository;

import org.backend.bankwebapplication.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
