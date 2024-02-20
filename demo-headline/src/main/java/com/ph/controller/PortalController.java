package com.ph.controller;


import com.ph.pojo.dto.PortalDto;
import com.ph.service.HeadlineService;
import com.ph.service.TypeService;
import com.ph.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("portal")
@CrossOrigin
public class PortalController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private HeadlineService headlineService;

    @GetMapping("findAllTypes")
    public Result findAllTypes(){
        Result result = typeService.findAllTypes();
        return result;
    }
    @PostMapping("findNewsPage")
    public Result findNewsPage(@RequestBody PortalDto portalDto){
        Result result = headlineService.findNewsPage(portalDto);
        return result;
    }
    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(Integer hid){
        Result result = headlineService.showHeadlineDetail(hid);
        return result;
    }
}
