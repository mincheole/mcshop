//package mcshop.jjonge_shop.domain;
//
//import jakarta.persistence.Embeddable;
//import lombok.Getter;
//
//@Embeddable
//@Getter
//public class Address {
//
//    private String city;
//    private String street;
//    private String zipcode;
//
//    protected Address() {}  //함부로 new로 생성하지 말라는 뜻, 안정성 높임
//                            //JPA가 이런 제약을 두는 이유는 JPA 구현 라이브러리가 객체를 생성할 때 리플렉션 같은 기술을 사용할 수 있도록 지원하기 때문이다.
//                            //Java Reflection : 구체적인 클래스 타입을 알지 못해도, 그 클래스의 메소드, 타입, 변수들에 접근할 수 있도록 해주는 자바 API
//
//    public Address(String city, String street, String zipcode) {
//        this.city = city;
//        this.street = street;
//        this.zipcode = zipcode;
//    }
//}