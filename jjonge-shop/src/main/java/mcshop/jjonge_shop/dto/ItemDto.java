package mcshop.jjonge_shop.dto;

import lombok.Getter;
import lombok.Setter;
import mcshop.jjonge_shop.domain.Item;

/**
 * 상품 정보를 화면에 출력할 때 사용하는 DTO 클래스
 * - Entity를 직접 노출하지 않도록 안전하게 감싸는 역할
 */
@Getter
@Setter
public class ItemDto {

    private Long id;              // 상품 ID
    private String name;          // 상품명
    private Integer price;        // 상품 가격
    private Integer stockQuantity; // 상품 재고 수량
    private String description;   // 상품 상세 설명

    /**
     * Entity → DTO 변환을 위한 생성자
     * @param item 상품 엔티티 객체
     */
    public ItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        this.description = item.getDescription();
    }
}