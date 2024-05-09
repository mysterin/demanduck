package com.nib.demanduck.service.impl;

import com.nib.demanduck.exception.ErrorCode;
import com.nib.demanduck.exception.ServiceException;
import com.nib.demanduck.service.TermAssociationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * @author linxiaobin
 * @Description
 * @date 2024/5/8 15:50
 */
@Service
@Slf4j
public class TermAssociationServiceImpl implements TermAssociationService {

    private Map<String, Set<String>> termMap;

    @Override
    public void initTerm(File file) {
        Map<String, Set<String>> map = new HashMap<>();
        // 读取文件内容
        // 解析文件内容
        // 初始化map
        try (FileInputStream fis = new FileInputStream(file)) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row row = iterator.next();
                String key = getTermFromExcel(row, 0);
                String value = getTermFromExcel(row, 1);
                if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
                    continue;
                }
                Set<String> set = map.get(key);
                if (set == null) {
                    set = new HashSet<>();
                    map.put(key, set);
                }
                set.add(value);
            }
            termMap = map;
            file.delete();
        } catch (Exception e) {
            log.error("init term error", e);
        }
    }

    @Override
    public Set<String> findTerms(String searchKey) {
        if (termMap == null) {
            log.error("词条关联未初始化，请上传 excel 文档，第一列是关键词，第二列是关联词条");
            throw new ServiceException(ErrorCode.NOT_INIT_TERM_ASSOCIATION);
        }
        // 精确匹配
        Set<String> set = termMap.get(searchKey);
        if (set != null) {
            log.info("精确匹配成功，searchKey={}, 匹配数量={}", searchKey, set.size());
            return set;
        }

        // 模糊匹配
        Set<String> keySet = termMap.keySet();
        set = new HashSet<>();
        for (String key : keySet) {
            if (key.contains(searchKey)) {
                set.addAll(termMap.get(key));
            }
        }
        log.info("模糊匹配成功，searchKey={}, 匹配数量={}", searchKey, set.size());
        return set;
    }

    private String getTermFromExcel(Row row, int index) {
        Cell cell = row.getCell(index);
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return "";
        }
    }
}
