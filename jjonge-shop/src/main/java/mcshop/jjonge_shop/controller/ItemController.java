package mcshop.jjonge_shop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mcshop.jjonge_shop.dto.ItemForm;
import mcshop.jjonge_shop.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    // 상품 등록 폼 페이지
    @GetMapping("/items/new")
    public String showItemForm(Model model) {
        model.addAttribute("itemForm", new ItemForm());
        return "items/createItemForm";  // createItemForm.html 페이지로 이동
    }

    // 상품 등록 처리
    @PostMapping("/items/new")
    public String registerItem(@Valid @ModelAttribute("itemForm") ItemForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "items/createItemForm";  // 오류 발생 시 다시 입력 폼으로 이동
        }

        itemService.saveItem(form);  // 서비스 계층을 통해 저장
        return "redirect:/";  // 등록 후 상품 목록 페이지로 이동
    }
}