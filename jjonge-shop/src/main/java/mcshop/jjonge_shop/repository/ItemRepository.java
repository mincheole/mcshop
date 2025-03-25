package mcshop.jjonge_shop.repository;

import mcshop.jjonge_shop.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}