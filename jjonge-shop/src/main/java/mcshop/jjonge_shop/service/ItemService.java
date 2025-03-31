package mcshop.jjonge_shop.service;

import lombok.RequiredArgsConstructor;
import mcshop.jjonge_shop.domain.Item;
import mcshop.jjonge_shop.dto.ItemForm;
import mcshop.jjonge_shop.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(ItemForm form) {
        // Item 엔티티 생성
        Item item = new Item();
        item.setName(form.getName());
        item.setPrice(form.getPrice());
        item.setStockQuantity(form.getStockQuantity());
        item.setDescription(form.getDescription()); // 추가: 상세 정보 필드

        // 저장
        itemRepository.save(item);
    }
}