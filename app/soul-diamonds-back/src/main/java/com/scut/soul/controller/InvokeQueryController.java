package com.scut.soul.controller;


import com.alibaba.fastjson.JSONObject;
import com.scut.soul.common.dto.LaunchTXDto;
import com.scut.soul.common.dto.SendToChain;
import com.scut.soul.common.lang.Result;
import com.scut.soul.entity.User;
import com.scut.soul.service.UserService;
import com.scut.soul.service.sdkService.InvokeQuery;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
public class InvokeQueryController {


    @Autowired
    private InvokeQuery invokeQuery;


    @GetMapping("/query/{key}")
    @ResponseBody
    public Result query(@PathVariable("key") String key) throws Exception {

        String res = invokeQuery.query("getValue", key);

        if (res.equals("")) {
            return Result.succ(new ArrayList());
        }

        ArrayList arrayList = JSONObject.parseObject(res, ArrayList.class);


        ArrayList returnToApp = new ArrayList();
        for(Object item:arrayList){

            returnToApp.add( JSONObject.parseObject(String.valueOf(item)));
            System.out.println("item: " + item.toString());
        }
        return Result.succ(returnToApp);


    }

    @GetMapping("/queryAll")
    @ResponseBody
    public Result queryAll() throws Exception {

        String res = invokeQuery.query("queryAll");

        if (res == null) {
            return Result.succ(res);
        }

        Map<String,Object> map = JSONObject.parseObject(res, HashMap.class);

        ArrayList returnToApp = new ArrayList();
        for (Map.Entry<String,Object> entry : map.entrySet()) {

            ArrayList arrayList = JSONObject.parseObject(String.valueOf(entry.getValue()), ArrayList.class);

            for(Object item:arrayList){

                returnToApp.add( JSONObject.parseObject(String.valueOf(item)));
            }

        }

        return Result.succ(returnToApp);


    }

    @GetMapping("/getKey")
    @ResponseBody
    public Result getKey() throws Exception {

        String res = invokeQuery.query("getKey");

        if (res == null) {
            return Result.succ("key is null");
        }

        ArrayList<String> list = JSONObject.parseObject(res,ArrayList.class);
        return Result.succ(list);


    }

}


