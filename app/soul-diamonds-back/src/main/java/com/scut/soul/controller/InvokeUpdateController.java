package com.scut.soul.controller;

import com.scut.soul.common.dto.LaunchTXDto;
import com.scut.soul.service.sdkService.InvokeUpdate;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

@Controller
public class InvokeUpdateController {

    @Autowired
    private InvokeUpdate invokeUpdate;

    @PostMapping("/update")
    @ResponseBody
    public String query(@RequestBody LaunchTXDto launchTXDto) throws Exception {

        if(launchTXDto.getNameFrom() == null ||
                launchTXDto.getNameFrom().equals(""))
            return "key == null is not allowed!";

        invokeUpdate.update(launchTXDto.getNameFrom(), JSON.toJSONString(launchTXDto));
        return "update success!";
    }
}
