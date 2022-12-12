package com.www.scheduleer.service.Member;

import com.www.scheduleer.config.social.SocialOauth;
import com.www.scheduleer.web.domain.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final List<SocialOauth> socialOauthList;
    private final HttpServletResponse response;

    public void request(Type type) {
        SocialOauth socialOauth = this.findSocialOauthByType(type);
        String redirectURL = socialOauth.getOauthRedirectURL();
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String requestAccessToken(Type type, String code) {
        SocialOauth socialOauth = this.findSocialOauthByType(type);
        return socialOauth.requestAccessToken(code);
    }

    private SocialOauth findSocialOauthByType(Type type) {
        return socialOauthList.stream().filter(x -> x.type() == type).findFirst().orElseThrow(() -> new IllegalArgumentException("알 수 없는 Social Type 입니다."));
    }
}
