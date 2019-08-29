package com.zzz.my.shop.web.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传控制器
 */
@Controller
public class UploadController {

    public static final String UPLOAD_PATH = "/static/upload/";

    /**
     * 文件上传
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Map<String, Object> upload(MultipartFile dropzFile,MultipartFile editorFile, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        //区分上传的文件是从 dropzone或editor 上传的
        MultipartFile myFile = dropzFile == null ? editorFile : dropzFile;
        //文件名
        String fileName = myFile.getOriginalFilename();
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
        //文件存放路径
        String filePath = request.getSession().getServletContext().getRealPath(UPLOAD_PATH);
        //判断路径是否存在，不存在则新建文件夹
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        //将文件写入文件夹
        file = new File(filePath, UUID.randomUUID() + fileSuffix);
        try {
            myFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 提供完整的访问服务器内存储图片的路径
         * scheme:服务端提供的协议 http/https
         * serverName:服务器名称 localhost/ip/domain
         * serverPoty:服务器端口
         */
        String serverPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        if (dropzFile != null){
            result.put("fileName",serverPath + UPLOAD_PATH + file.getName());
        }
        else {
            result.put("errno",0);
            result.put("data",new String[]{serverPath + UPLOAD_PATH + file.getName()});
        }
        return result;
    }
}
