package mcshop.jjonge_shop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mcshop.jjonge_shop.exception.NotEnoughStockException;

/**
 * 상품 도메인 엔티티
 * DB와 매핑되며, 비즈니스 로직 포함
 */
@Entity
@Getter @Setter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id") // DB 컬럼명 지정
    private Long id;

    private String name;            // 상품명
    private int price;              // 상품 가격
    private int stockQuantity;      // 재고 수량
    private String description;     // 상품 상세 설명

    // =============================
    // ===== 비즈니스 로직 영역 =====
    // =============================

    /**
     * 재고 증가 메서드
     * @param quantity 증가시킬 수량
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * 재고 감소 메서드
     * 수량 부족 시 예외 발생
     * @param quantity 감소시킬 수량
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("재고 부족: 현재 재고는 " + stockQuantity + "개입니다.");
        }
        this.stockQuantity = restStock;
    }
}