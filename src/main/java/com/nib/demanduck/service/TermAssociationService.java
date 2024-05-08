package com.nib.demanduck.service;

import java.io.File;
import java.util.Set;

/**
 * @author linxiaobin
 * @Description 词条关联服务
 * @date 2024/5/8 15:49
 */
public interface TermAssociationService {
    void initTerm(File file);

    Set<String> findTerms(String searchKey);
}
