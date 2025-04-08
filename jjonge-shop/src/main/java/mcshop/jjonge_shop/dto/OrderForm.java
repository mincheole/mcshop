package mcshop.jjonge_shop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderForm {
    @NotNull(message = "회원을 선택해주세요")
    private Long memberId;

    @NotNull(message = "상품을 선택해주세요")
    private Long itemId;

    @NotNull(message = "수량을 입력해주세요")
    @Min(value = 1, message = "수량은 최소 1개 이상이어야 합니다")
    private Integer count;
}