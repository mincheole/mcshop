package mcshop.jjonge_shop.dto;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class GoogleReponse implements OAuth2Response{

    private final Map<String, Object> attribute;

    public GoogleReponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

//    @Override
//    public String getName() {
//        return attribute.get("name").toString();
//    }
//    @Override
//    public String getName() {
//        // UTF-8로 디코딩
//        try {
//            String name = (String) attribute.get("name");
//            return new String(name.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
//        } catch (Exception e) {
//            return attribute.get("name").toString();
//        }
//    }
//    @Override
//    public String getName() {
//        // null 체크 추가
//        Object nameAttribute = attribute.get("name");
//        return nameAttribute != null ? nameAttribute.toString() : "";
//    }
//    @Override
//    public String getName() {
//        return new String(
//                ((String)attribute.get("name")).getBytes(StandardCharsets.ISO_8859_1),
//                StandardCharsets.UTF_8
//        );
//    }
    @Override
    public String getName() {
        try {
            String name = (String) attribute.get("name");
            return new String(name.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        } catch (Exception e) {
            // 변환 실패 시 원래 값 반환
            return attribute.get("name").toString();
        }
    }
}