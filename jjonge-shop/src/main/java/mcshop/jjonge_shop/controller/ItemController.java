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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

//@Controller
//@RequiredArgsConstructor
//public class ItemController {
//
//    @GetMapping(value = "/items/new")
//    public String createForm(Model model) {
//        return "items/createItemForm";
//    }
//
//    /**
//     * 상품 목록
//     */
//    @GetMapping(value = "/items")
//    public String list(Model model) {
//        return "items/itemList";
//    }
//
//    /**
//     * 상품 수정 폼
//     */
////    @GetMapping(value = "/items/{itemId}/edit")
////    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
////
////        return "items/updateItemForm";
////    }
//    @GetMapping(value = "/items/edit")
//    public String updateItemForm(Model model) {
//        return "items/updateItemForm";
//    }
//}
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    // 상품 등록 폼 페이지
    @GetMapping("/items/new")
    public String showItemForm(Model model) {
        model.addAttribute("form", new ItemForm());
        return "items/createItemForm";  // createItemForm.html 페이지로 이동
    }

    // 상품 등록 처리
    @PostMapping("/items/new")
    public String registerItem(@Valid @ModelAttribute("form") ItemForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "items/createItemForm";
        }

        itemService.saveItem(form);  // 서비스 계층을 통해 저장
        return "redirect:/items";  // 등록 후 상품 목록 페이지로 이동
    }
}