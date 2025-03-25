package mcshop.jjonge_shop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemForm {

    @NotBlank(message = "상품명을 입력하세요.")
    private String name;

    @NotNull(message = "가격을 입력하세요.")
    @Min(value = 1000, message = "가격은 최소 1000 이상이어야 합니다.")
    private Integer price;

    @NotNull(message = "수량을 입력하세요.")
    @Min(value = 1, message = "수량은 최소 1개 이상이어야 합니다.")
    private Integer stockQuantity;

    private String author;
    private String isbn;
}