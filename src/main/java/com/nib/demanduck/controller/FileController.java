package com.nib.demanduck.controller;

import com.nib.demanduck.response.Response;
import com.nib.demanduck.response.file.UploadFileDTO;
import com.nib.demanduck.service.TermAssociationService;
import com.nib.demanduck.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author Administrator
 * @Description
 * @date 2023/11/12 11:12
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Value("${demanduck.file.dir}")
    private String fileDir;
    @Value("${demanduck.file.domain}")
    private String fileDomain;
    @Autowired
    private TermAssociationService termAssociationService;

    /**
     * 上传文件
     * @param objectName
     * @param multipartFile
     * @return
     */
    @PostMapping("/upload")
    public Response<UploadFileDTO> upload(@RequestParam("objectName") String objectName,
                                          @RequestParam("file") MultipartFile multipartFile,
                                          @RequestParam("scene") String scene) {
        try {
            String filePath = fileDir + objectName;
            File file = new File(filePath);
            FileUtils.createDir(file);
            multipartFile.transferTo(file);
            String url = fileDomain + objectName;
            UploadFileDTO uploadFileDTO = new UploadFileDTO().setUrl(url);

            // 处理不同场景
            if ("termAssociation".equals(scene)) {
                termAssociationService.initTerm(file);
            }

            return Response.success(uploadFileDTO);
        } catch (Exception e) {
            log.error("上传文件失败，name={}", objectName, e);
            return Response.error();
        }
    }
}
