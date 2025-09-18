package com.cedric_guette.portfolio.repositories;

import com.cedric_guette.portfolio.entities.ContactForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactFormRepository extends JpaRepository<ContactForm, Long> {
}
