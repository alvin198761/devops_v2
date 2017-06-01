package org.alvin.opsdev.monitor.system.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by tangzhichao on 2017/6/1.
 */
@RestController
@RequestMapping("plugin")
public class PluginController {

    @RequestMapping(value = "upload" ,method = RequestMethod.POST)
    public Long handleFileUpload(@RequestParam MultipartFile file) throws Exception {
        /**
         * 上传，解析，返回唯一标号
         */
        String name = file.getName();
        if (!file.isEmpty()) {
            try {
                String path = "E:/" + name;
                try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(Paths.get(path)))) {
                    out.write(file.getBytes());
                    out.flush();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        throw new Exception("no file");
    }

}
