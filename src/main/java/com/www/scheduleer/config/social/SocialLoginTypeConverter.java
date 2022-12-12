package com.www.scheduleer.config.social;

import com.www.scheduleer.web.domain.Type;
import org.springframework.core.convert.converter.Converter;

public class SocialLoginTypeConverter implements Converter<String, Type> {
    @Override
    public Type convert(String source) {
        return Type.valueOf(source.toUpperCase());
    }
}
