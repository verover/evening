package com.enigmacamp.evening.event.listener;

import com.enigmacamp.evening.event.OnUserLogoutSuccessEvent;
import com.enigmacamp.evening.dto.DeviceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OnUserLogoutSuccessEventListener implements ApplicationListener<OnUserLogoutSuccessEvent> {

   private static final Logger logger = LoggerFactory.getLogger(OnUserLogoutSuccessEventListener.class);
    public void onApplicationEvent(OnUserLogoutSuccessEvent event) {
        if (null != event) {
            DeviceInfo deviceInfo = event.getLogOutRequest().getDeviceInfo();
            logger.info(String.format("Log out success event received for user [%s] for device [%s]", event.getUserEmail(), deviceInfo));

        }
    }
}
