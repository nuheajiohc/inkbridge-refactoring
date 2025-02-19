package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.GeneralAddress;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: GeneralAddressRepository.
 *
 * @author jeongbyeonghun
 * @version 3/11/24
 */
public interface GeneralAddressRepository extends JpaRepository<GeneralAddress, Long> {
    Optional<GeneralAddress> findByZipCodeAndAddress(String zipCode, String address);
}
