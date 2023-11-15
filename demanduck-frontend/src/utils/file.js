/**
 * 根据文件名字获取文件后缀，包含 .
 */
export function getFileSuffix(fileName) {
    return fileName.substring(fileName.lastIndexOf('.')) || ''
}

export function getFileNanme(url) {
    return url.substring(url.lastIndexOf('/') + 1)
}

export function convertUploadFile(url) {
    // 字符串根据逗号分隔，返回数组，对象包含 name 和 url
    if (url) {
        const arr = url.split(',')
        return arr.map(item => {
            return {
                name: getFileNanme(item),
                url: item
            }
        })
    }
    return [];
}