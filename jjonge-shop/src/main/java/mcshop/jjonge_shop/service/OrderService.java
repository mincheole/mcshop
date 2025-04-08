package mcshop.jjonge_shop.service;

import lombok.RequiredArgsConstructor;
import mcshop.jjonge_shop.domain.*;
        import mcshop.jjonge_shop.dto.OrderDto;
import mcshop.jjonge_shop.dto.OrderSearch;
import mcshop.jjonge_shop.repository.ItemRepository;
import mcshop.jjonge_shop.repository.MemberRepository;
import mcshop.jjonge_shop.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다. ID: " + memberId));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("상품을 찾을 수 없습니다. ID: " + itemId));

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        Order order = Order.createOrder(member, orderItem);
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("주문을 찾을 수 없습니다. ID: " + orderId));
        order.cancel();
    }

    public List<Order> findOrdersByMember(Long memberId) {
        return orderRepository.findByMemberId(memberId);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * 회원 ID로 주문 DTO 목록 조회
     */
    public List<OrderDto> findOrderDtosByMember(Long memberId) {
        return findOrdersByMember(memberId).stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 전체 주문 DTO 목록 조회
     */
    public List<OrderDto> findAllOrderDtos() {
        return findAllOrders().stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
    }

    public List<OrderDto> findOrders(OrderSearch orderSearch) {
        List<Order> orders = orderRepository.findAllBySearch(
                orderSearch.getMemberName(),
                orderSearch.getOrderStatus()
        );

        return orders.stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
    }

}