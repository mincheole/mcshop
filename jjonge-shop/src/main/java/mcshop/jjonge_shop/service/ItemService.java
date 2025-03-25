package mcshop.jjonge_shop.service;

import lombok.RequiredArgsConstructor;
import mcshop.jjonge_shop.domain.item.Book;
import mcshop.jjonge_shop.dto.ItemForm;
import mcshop.jjonge_shop.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(ItemForm form) {
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemRepository.save(book);
    }
}