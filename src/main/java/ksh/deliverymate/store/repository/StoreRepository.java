package ksh.deliverymate.store.repository;

import ksh.deliverymate.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {
}
