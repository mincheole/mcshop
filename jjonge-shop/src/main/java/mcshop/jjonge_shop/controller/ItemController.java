package mcshop.jjonge_shop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mcshop.jjonge_shop.domain.Item;
import mcshop.jjonge_shop.dto.ItemDto;
import mcshop.jjonge_shop.dto.ItemForm;
import mcshop.jjonge_shop.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 상품 관련 요청을 처리하는 컨트롤러
 * 상품 등록, 수정, 조회 등의 기능을 담당
 */
@Controller
@RequiredArgsConstructor  // 생성자 주입을 위한 lombok 어노테이션
public class ItemController {

    // 서비스 계층에 대한 의존성 주입
    private final ItemService itemService;

    /**
     * [GET] 상품 등록 페이지 출력
     * 빈 폼 객체를 View에 전달하여 폼 렌더링 준비
     */
    @GetMapping("/items/new")
    public String showItemForm(Model model) {
        model.addAttribute("itemForm", new ItemForm()); // 신규 등록용 빈 DTO 객체 추가
        return "items/createItemForm"; // 상품 등록 폼 HTML로 이동
    }

    /**
     * [POST] 상품 등록 처리
     * 사용자 입력값을 검증 후, 서비스 계층에 저장 요청
     */
    @PostMapping("/items/new")
    public String registerItem(
            @Valid @ModelAttribute("itemForm") ItemForm form,
            BindingResult bindingResult
    ) {
        // 입력값 검증 실패 시 등록 폼으로 되돌아감
        if (bindingResult.hasErrors()) {
            return "items/createItemForm";
        }

        // 입력값 검증 성공 → 서비스 계층을 통해 DB 저장 처리
        itemService.saveItem(form);
        return "redirect:/"; // 등록 후 홈 화면으로 리다이렉트
    }

    /**
     * [GET] 전체 상품 목록 조회
     * 서비스 계층에서 상품 리스트를 DTO로 받아 View에 전달
     */
    @GetMapping("/items")
    public String listItems(Model model) {
        List<ItemDto> items = itemService.findAllItems(); // 전체 상품 조회
        model.addAttribute("items", items); // 모델에 상품 리스트 저장
        return "items/itemList"; // 상품 목록 View로 이동
    }

    /**
     * [GET] 상품 수정 폼 조회
     * 수정 대상 상품을 식별하여 View에 데이터 출력
     */
    @GetMapping("/items/{itemId}/edit")
    public String showEditForm(@PathVariable Long itemId, Model model) {
        ItemDto itemDto = itemService.getItem(itemId); // 대상 상품 조회 (DTO 형태)
        model.addAttribute("itemForm", itemDto); // 수정 폼에 전달할 DTO 저장
        return "items/editItemForm"; // 수정 폼 View로 이동
    }

    /**
     * [POST] 상품 수정 처리
     * 사용자의 수정 내용을 받아 서비스 계층에서 DB 업데이트 수행
     */
    @PostMapping("/items/{itemId}/edit")
    public String updateItem(
            @PathVariable Long itemId,
            @Valid @ModelAttribute("itemForm") ItemForm form,
            BindingResult bindingResult
    ) {
        // 입력값 검증 실패 시 수정 폼으로 복귀
        if (bindingResult.hasErrors()) {
            return "items/editItemForm";
        }

        // 검증 성공 시 상품 수정 처리
        itemService.updateItem(itemId, form);
        return "redirect:/items"; // 수정 완료 후 상품 목록으로 이동
    }
}