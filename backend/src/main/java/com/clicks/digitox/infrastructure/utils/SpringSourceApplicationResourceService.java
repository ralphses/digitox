package com.clicks.digitox.infrastructure.utils;

import com.clicks.digitox.shared.utils.ApplicationResourceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SpringSourceApplicationResourceService implements ApplicationResourceService {

    @Value("${application.user.image.default-profile}")
    private String defaultProfileImage;

    @Value("${application.user.image.default-banner}")
    private String defaultBannerImage;


    @Override
    public String getDefaultProfileImage() {
        return defaultProfileImage;
    }

    @Override
    public String getDefaultBannerImage() {
        return defaultBannerImage;
    }
}
