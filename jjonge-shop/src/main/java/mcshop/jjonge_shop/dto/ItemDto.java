package mcshop.jjonge_shop.dto;

import lombok.Getter;
import lombok.Setter;
import mcshop.jjonge_shop.domain.Item;

/**
 * 상품 목록 출력을 위한 DTO
 * - 불필요한 필드를 제외하고 필요한 정보만 포함
 */
@Getter
@Setter
public class ItemDto {

    private Long id;        // 상품 ID
    private String name;    // 상품명
    private Integer price;  // 가격
    private Integer stockQuantity; // 재고 수량
    private String description;


    /**
     * Entity → DTO 변환 생성자
     * @param item Entity 객체
     */
    public ItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        this.description = item.getDescription();
    }
}