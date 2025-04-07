package mcshop.jjonge_shop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 상품 등록/수정 폼에서 사용되는 DTO 클래스
 * - 사용자의 입력값을 받으며, 검증 로직이 포함됨
 */
@Getter
@Setter
public class ItemForm {

    @NotBlank(message = "상품명을 입력하세요.")  // 공백 불가
    private String name;

    @NotNull(message = "가격을 입력하세요.")      // null 불가
    @Min(value = 1000, message = "가격은 최소 1000 이상이어야 합니다.")  // 최소값 제한
    private Integer price;

    @NotNull(message = "수량을 입력하세요.")
    @Min(value = 1, message = "수량은 최소 1개 이상이어야 합니다.")
    private Integer stockQuantity;

    private String description;  // 상품 상세 정보 (선택 입력)
}