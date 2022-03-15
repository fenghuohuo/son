package com.tencent.wxcloudrun.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * index控制器
 */
@Controller
public class IndexController {

    /**
     * 主页页面
     *
     * @return API response html
     */
    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/checkSignature")
    public String checkSignature(String signature, String timestamp, String nonce, String echostr)
        throws NoSuchAlgorithmException {
        ArrayList<String> strings = new ArrayList<>();

        strings.add(signature);
        strings.add(timestamp);
        strings.add(nonce);

        List<String> collect = strings.stream()
            .sorted()
            .collect(Collectors.toList());

        MessageDigest instance = MessageDigest.getInstance("SHA-1");

        String s = Hex.encodeHexString(instance.digest(String.join("", collect).getBytes()));

        if (s.equals(signature)) {
            return echostr;
        }
        return "";
    }

}
