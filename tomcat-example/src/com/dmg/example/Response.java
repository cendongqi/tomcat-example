package com.dmg.example;

import java.io.*;

/**
 * request类提供了读取原始http请求数据的方法
 *
 * @author tanzhe
 */
public class Response {

    private static final int BUFFER_SIZE = 1024;
    Request request;
    OutputStream outputStream;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * 根据uri查找静态资源并返回浏览器
     */
    public void sendStaticResources() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            // 根据uri查找静态资源
            File file = new File(HttpServer.WEB_ROOT, request.getUri());
            if (file.exists()) {
                // 存在，输出到浏览器
                fis = new FileInputStream(file);
                int ch = fis.read(bytes, 0, BUFFER_SIZE);
                outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                outputStream.write("Content-Type:text/html;charset=UTF-8\r\n".getBytes());
                outputStream.write("\r\n".getBytes());
                while (ch != -1) {
                    outputStream.write(bytes, 0, ch);
                    ch = fis.read(bytes, 0, BUFFER_SIZE);
                }
            } else {
                // 不存在，返回错误信息
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File NOT Found<h1>";
                outputStream.write(errorMessage.getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }

    }
}
