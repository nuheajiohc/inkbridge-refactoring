package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.ShoppingCart;
import com.nhnacademy.inkbridge.backend.entity.ShoppingCart.Pk;
import com.nhnacademy.inkbridge.backend.repository.custom.ShoppingCartRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: CartRepository.
 *
 * @author minm063
 * @version 2024/03/12
 */
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Pk>,
    ShoppingCartRepositoryCustom {

}
