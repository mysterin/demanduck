package com.nib.demanduck.controller;

import com.nib.demanduck.request.term.FindTermsRequest;
import com.nib.demanduck.response.Response;
import com.nib.demanduck.response.file.UploadFileDTO;
import com.nib.demanduck.service.TermAssociationService;
import com.nib.demanduck.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Administrator
 * @Description
 * @date 2023/11/12 11:12
 */
@RestController
@RequestMapping("/term/association")
@Slf4j
public class TermAssociationController {
    @Autowired
    private TermAssociationService termAssociationService;

    /**
     * 查询关联词条
     * @param request
     * @return
     */
    @PostMapping("/findTerms")
    public Response<String> findTerms(@RequestBody @Validated FindTermsRequest request) {
        Set<String> terms = termAssociationService.findTerms(request.getKey());
        return Response.success(new ArrayList<>(terms));
    }
}
