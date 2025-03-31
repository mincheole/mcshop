package mcshop.jjonge_shop.service;

import lombok.RequiredArgsConstructor;
import mcshop.jjonge_shop.domain.Item;
import mcshop.jjonge_shop.dto.ItemDto;
import mcshop.jjonge_shop.dto.ItemForm;
import mcshop.jjonge_shop.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 모든 상품 목록을 조회하여 ItemDto 리스트로 반환하는 메서드
     */
    public List<ItemDto> findAllItems() {
        List<Item> items = itemRepository.findAll(); // 모든 상품 조회
        return items.stream()
                .map(ItemDto::new)  // Item 엔티티를 ItemDto 로 변환
                .collect(Collectors.toList());
    }
}