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
 * 상품 등록, 조회 등의 기능을 담당함
 */
@Controller
@RequiredArgsConstructor  // 생성자 주입을 위한 lombok 어노테이션
public class ItemController {

    // 생성자 주입으로 의존성 주입 (@RequiredArgsConstructor와 final 키워드를 통해 자동 주입됨)
    private final ItemService itemService;

    /**
     * 상품 등록 페이지를 보여주는 메서드
     * @param model View에 전달할 데이터를 담는 객체
     * @return 상품 등록 폼 화면 경로
     */
    @GetMapping("/items/new")
    public String showItemForm(Model model) {
        // 빈 상품 폼 객체를 모델에 추가하여 View에 전달
        model.addAttribute("itemForm", new ItemForm());
        return "items/createItemForm";  // 상품 등록 폼 화면으로 이동
    }

    /**
     * 상품 등록 요청을 처리하는 메서드
     * @param form 사용자가 입력한 상품 정보
     * @param bindingResult 입력값 검증 결과
     * @return 성공 시 홈 화면으로 리다이렉트, 실패 시 등록 폼으로 이동
     */
    @PostMapping("/items/new")
    public String registerItem(@Valid @ModelAttribute("itemForm") ItemForm form, BindingResult bindingResult) {
        // 입력값 검증 오류가 있는 경우 다시 입력 폼으로 이동
        if (bindingResult.hasErrors()) {
            return "items/createItemForm";
        }

        // 상품 정보 저장 (서비스 계층을 통해 처리)
        itemService.saveItem(form);
        return "redirect:/";  // 등록 완료 후 홈 화면으로 리다이렉트
    }

    /**
     * 모든 상품 목록을 조회하여 화면에 표시하는 메서드
     * @param model View에 전달할 데이터를 담는 객체
     * @return 상품 목록 화면 경로
     */
    @GetMapping("/items")
    public String listItems(Model model) {
        // 서비스 계층을 통해 모든 상품 정보를 DTO 형태로 조회
        List<ItemDto> items = itemService.findAllItems();

        // 조회한 상품 목록을 모델에 추가하여 View에 전달
        model.addAttribute("items", items);
        return "items/itemList";  // 상품 목록 화면으로 이동
    }

    @GetMapping("/items/{itemId}/edit")
    public String showEditForm(@PathVariable Long itemId, Model model) {
        ItemDto itemDto = itemService.getItem(itemId);
        model.addAttribute("itemForm", itemDto);
        return "items/editItemForm";
    }


    @PostMapping("/items/{itemId}/edit")
    public String editItem(@PathVariable Long itemId,
                           @Valid @ModelAttribute("itemForm") ItemForm form,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "items/editItemForm";
        }

        itemService.updateItem(itemId, form);
        return "redirect:/items";
    }


/*    @GetMapping("/items/{id}/edit")
    public String editItemForm(@PathVariable("id") Long id, Model model) {
        Item item = itemService.findOne(id);
        model.addAttribute("item", item);
        return "items/editItemForm"; //  Thymeleaf 템플릿 경로
    }*/

}