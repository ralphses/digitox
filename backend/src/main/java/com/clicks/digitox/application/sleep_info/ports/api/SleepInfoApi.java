package com.clicks.digitox.application.sleep_info.ports.api;


import com.clicks.digitox.application.sleep_info.payload.UpdateSleepInfoRequest;
import com.clicks.digitox.shared.utils.ApiResponse;

public interface SleepInfoApi {
    ApiResponse updateSleepInfo(UpdateSleepInfoRequest request);
    ApiResponse getTodaySleepInfo(String userEmail);
}
