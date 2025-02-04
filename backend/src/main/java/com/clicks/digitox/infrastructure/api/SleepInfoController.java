package com.clicks.digitox.infrastructure.api;

import com.clicks.digitox.application.sleep_info.payload.UpdateSleepInfoRequest;
import com.clicks.digitox.application.sleep_info.ports.api.SleepInfoApi;
import com.clicks.digitox.application.sleep_info.use_case.AddNewScreenTime;
import com.clicks.digitox.application.sleep_info.use_case.GetTodaySleepInfo;
import com.clicks.digitox.domain.sleep_info.dto.SleepInfoDto;
import com.clicks.digitox.shared.utils.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("api/v1/sleep-info")
public class SleepInfoController implements SleepInfoApi {

    private final AddNewScreenTime addNewScreenTime;
    private final GetTodaySleepInfo getTodaySleepInfo;

    public SleepInfoController(AddNewScreenTime addNewScreenTime, GetTodaySleepInfo getTodaySleepInfo) {
        this.addNewScreenTime = addNewScreenTime;
        this.getTodaySleepInfo = getTodaySleepInfo;
    }

    @Override
    @PutMapping
    public ApiResponse updateSleepInfo(@RequestBody UpdateSleepInfoRequest request) {
        String updatedScreenTime = addNewScreenTime.execute(request.currentScreenTime(), request.userEmail());
        return new ApiResponse(true, updatedScreenTime);
    }

    @Override
    @GetMapping("{userEmail}")
    public ApiResponse getTodaySleepInfo(@PathVariable String userEmail) {
        SleepInfoDto sleepInfo = getTodaySleepInfo.execute(userEmail);
        return new ApiResponse(true, sleepInfo);
    }
}
