package mcshop.jjonge_shop.repository;

import mcshop.jjonge_shop.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}