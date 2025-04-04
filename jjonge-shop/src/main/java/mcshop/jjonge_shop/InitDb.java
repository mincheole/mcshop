package mcshop.jjonge_shop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mcshop.jjonge_shop.domain.Item;
import mcshop.jjonge_shop.domain.Member;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        @Transactional
        public void dbInit1() {
            Member member1 = createMember("ADMIN", "admin@example.com", "adminPassword", "ROLE_ADMIN", "ADMIN");

            Item item1 = createItem("초코 아이스크림", 2500, 100);
            Item item2 = createItem("바닐라 쉐이크", 3200, 50);
            Item item3 = createItem("딸기 케이크", 4500, 30);

            em.persist(item1);
            em.persist(item2);
            em.persist(item3);
            em.persist(member1);
        }

        private Item createItem(String a, int n, int m) {
            Item item = new Item();
            item.setName(a);
            item.setPrice(n);
            item.setStockQuantity(m);
            return item;
        }


        private Member createMember(String username, String email, String password, String role, String realName) {
            Member member = new Member();
            member.setUsername(username);
            member.setEmail(email);
            member.setPassword(password);
            member.setRole(role);
            member.setRealName(realName);
            member.setLastLoginTime(LocalDateTime.now());
            return member;
        }
    }
}