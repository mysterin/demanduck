/**
 * 根据文件名字获取文件后缀，包含 .
 */
export function getFileSuffix(fileName) {
    return fileName.substring(fileName.lastIndexOf('.')) || ''
}