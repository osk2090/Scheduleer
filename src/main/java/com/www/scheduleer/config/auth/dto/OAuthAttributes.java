package com.www.scheduleer.config.auth.dto;

import com.www.scheduleer.web.domain.Auth;
import com.www.scheduleer.web.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;

    private String registrationId;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String registrationId, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.registrationId = registrationId;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        switch (registrationId) {
            case "google":
                return ofGoogle(registrationId, "id", attributes);
        }
        return null;
    }

    public static OAuthAttributes ofGoogle(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder().name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .auth(Auth.USER)//기본권한
                .build();
    }
}