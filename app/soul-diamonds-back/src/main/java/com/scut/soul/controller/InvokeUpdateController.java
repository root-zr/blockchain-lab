package com.scut.soul.controller;

import com.scut.soul.common.dto.LaunchTXDto;
import com.scut.soul.common.dto.SendToChain;
import com.scut.soul.common.lang.Result;
import com.scut.soul.service.sdkService.InvokeUpdate;
import org.apache.http.client.utils.DateUtils;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class InvokeUpdateController {

    @Autowired
    private InvokeUpdate invokeUpdate;

    @PostMapping("/update")
    @ResponseBody
    public Result query(@RequestBody LaunchTXDto launchTXDto) throws Exception {

        if(launchTXDto.getNameFrom() == null ||
                launchTXDto.getNameFrom().equals(""))
            return Result.fail("key == null is not allowed!");

        SendToChain sendToChain = new SendToChain();
        sendToChain.setNameFrom(launchTXDto.getNameFrom());
        sendToChain.setNameTo(launchTXDto.getNameTo());

//        String dateToString = DateUtils.formatDate(launchTXDto.getDate(),"yyyy-MM-dd HH:mm:ss");
//        sendToChain.setDateToString(dateToString);

        sendToChain.setDateToString(launchTXDto.getDateToString());

//        String str = JSON.toJSONString(sendToChain);
//        System.out.println("String is :" + str);
//       SendToChain obj = JSONObject.parseObject(str, SendToChain.class);
//        System.out.println(obj.getDateToString());

        invokeUpdate.update(launchTXDto.getNameFrom(), JSON.toJSONString(sendToChain));
        return Result.succ("update success!");
    }
}
