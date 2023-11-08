/**
 * 随机生成指定长度的字符串
 */
export function randomString(len) {
    len = len || 32;
    const $chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
    const maxPos = $chars.length;
    let pwd = '';
    for (let i = 0; i < len; i++) {
        pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}

/**
 * 生成uuid
 */
export function uuid() {
    const len = 32; // 32长度
    const radix = 16; // 16进制
    const chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    const uuid = [];
    let i;
    for (i = 0; i < len; i++) {
        uuid[i] = chars[0 | Math.random() * radix];
    }
    return uuid.join('');
}