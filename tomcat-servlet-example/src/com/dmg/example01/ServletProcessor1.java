package com.dmg.example01;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * @description:处理对Servlet的请求
 * @author:tz
 * @date:Created in 下午1:09 2018/4/26
 */
public class ServletProcessor1 {

    public void process(Request request, Response response) {
        // 1.切割uri，获取servletName
        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);

        // 2.使用URLClassLoader载入servlet类
        URLClassLoader urlClassLoader = null;
        try {
            // 2.1 获取servlet类目录地址
            File classpath = new File(Constants.WEB_ROOT);
            // url以"/"结尾，则表明是一个目录，否则默认指向一个jar文件
            String resposity = new URL("file", null, classpath.getCanonicalPath()).toString();
            // 2.2 创建URL对象，指明servlet类的目录
            URLStreamHandler urlStreamHandler = null;
            URL[] urls = new URL[1];
            urls[0] = new URL(null, resposity, urlStreamHandler);
            urlClassLoader = new URLClassLoader(urls);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3.根据第1步获取的servlet名称载入servlet类
        Class myClass = null;
        try {
            myClass = urlClassLoader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 4.创建servlet类的示例，调用service方法
        Servlet servlet = null;
        try {
            servlet = (Servlet) myClass.newInstance();
            servlet.service(request, response);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
