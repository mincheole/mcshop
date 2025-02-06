package mcshop.jjonge_shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    @GetMapping(value = "/items/new")
    public String createForm(Model model) {
        return "items/createItemForm";
    }

    /**
     * 상품 목록
     */
    @GetMapping(value = "/items")
    public String list(Model model) {
        return "items/itemList";
    }

    /**
     * 상품 수정 폼
     */
//    @GetMapping(value = "/items/{itemId}/edit")
//    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
//
//        return "items/updateItemForm";
//    }
    @GetMapping(value = "/items/edit")
    public String updateItemForm(Model model) {
        return "items/updateItemForm";
    }
}
