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

    /**
     * 상품 저장
     * - ItemForm으로부터 Item Entity 생성 후 저장
     */
    @Transactional
    public void saveItem(ItemForm form) {
        Item item = new Item();
        item.setName(form.getName());
        item.setPrice(form.getPrice());
        item.setStockQuantity(form.getStockQuantity());
        item.setDescription(form.getDescription());

        itemRepository.save(item);
    }

    /**
     * 모든 상품 조회 (DTO 변환 포함)
     */
    public List<ItemDto> findAllItems() {
        return itemRepository.findAll().stream()
                .map(ItemDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 단일 상품 조회 (DTO)
     */
    public ItemDto getItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. ID=" + itemId));
        return new ItemDto(item);
    }

    /**
     * 상품 수정
     * - Dirty Checking 적용: 트랜잭션 종료 시 자동 감지됨
     */
    @Transactional
    public void updateItem(Long itemId, ItemForm form) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. ID=" + itemId));

        item.setName(form.getName());
        item.setPrice(form.getPrice());
        item.setStockQuantity(form.getStockQuantity());
        item.setDescription(form.getDescription());
    }
}