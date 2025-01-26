package mcshop.jjonge_shop.dto;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class NaverResponse implements OAuth2Response {
    private final Map<String, Object> attribute;
    private final Map<String, Object> response;

    public NaverResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
        this.response = (Map<String, Object>) attribute.get("response");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        // null 체크 추가
        return response != null ? response.get("id").toString() : null;
    }

    @Override
    public String getEmail() {
        // null 체크 추가
        return response != null ? response.get("email").toString() : null;
    }

    @Override
    public String getName() {
        // Naver API 응답에서 'name'을 가져오기
        return response != null ? (String) response.get("name") : null;
    }
}