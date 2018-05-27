package com.dmg.example02;

import java.io.IOException;

/**
 * @description:处理对静态资源的请求
 * @author:tz
 * @date:Created in 下午1:07 2018/4/26
 */
public class StaticResourceProcessor {

    public void process(Request request, Response response) {
        try {
            response.sendStaticResources();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
