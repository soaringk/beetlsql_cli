package io.beetlsql.cli.common.utils;

/**
 * @功能：字符串处理
 * @开发者： 大BUG
 * @编写时间： 2019/1/22 15:30
 */
public class StringUtil {
    /**
     * 首字母转换为小写
     *
     * @param caseStr
     * @return
     */
    public static String firstCharToLowerCase(String caseStr) {
        if (Character.isLowerCase(caseStr.charAt(0))) {
            return caseStr;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(caseStr.charAt(0))).append(caseStr.substring(1)).toString();
        }
    }
}
