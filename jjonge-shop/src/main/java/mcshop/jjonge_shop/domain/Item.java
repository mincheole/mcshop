package mcshop.jjonge_shop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name; // 상품명
    private int price; // 가격
    private int stockQuantity; // 재고 수량
    private String description; // 상품 상세 정보

    //== 비즈니스 로직 ==//
    /**
     * 재고 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * 재고 감소
     */
    public void removeStock(int quantity) {
        this.stockQuantity -= quantity;
//        int restStock = this.stockQuantity - quantity;
//        if (restStock < 0) {
//            throw new NotEnoughStockException("재고 부족: 더 많은 재고가 필요합니다.");
//        }
//        this.stockQuantity = restStock;
    }
}