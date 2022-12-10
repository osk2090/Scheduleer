package com.www.scheduleer.web.dto.member;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;

public interface SocialMemberInterface extends OAuth2User, Serializable, UserDetails, CredentialsContainer {

}
