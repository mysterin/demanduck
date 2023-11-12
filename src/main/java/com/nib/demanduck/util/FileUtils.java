package com.nib.demanduck.util;

import java.io.File;

/**
 * @author Administrator
 * @Description
 * @date 2023/11/12 11:55
 */
public class FileUtils {

    /**
     * 创建文件目录
     */
    public static boolean createDir(String filePath) {
        File file = new File(filePath);
        return createDir(file);
    }

    public static boolean createDir(File file) {
        return file.getParentFile().mkdirs();
    }

}
