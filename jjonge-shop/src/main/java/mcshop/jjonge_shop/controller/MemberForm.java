package mcshop.jjonge_shop.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수입니다")
    private String name;

    @NotBlank(message = "도시를 입력하세요.")
    private String city;

    @NotBlank(message = "거리를 입력하세요.")
    private String street;

    @NotBlank(message = "우편번호를 입력하세요.")
    private String zipcode;
}