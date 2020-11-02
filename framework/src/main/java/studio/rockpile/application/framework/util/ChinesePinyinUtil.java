package studio.rockpile.application.framework.util;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;

public final class ChinesePinyinUtil {
    private final static int[] LETTERS_SEC_POS_VAL = { 1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212,
        3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590 };
    private final static String[] FIRST_LETTER_ARR = { "a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "l", "m", "n",
        "o", "p", "q", "r", "s", "t", "w", "x", "y", "z" };

    // 1 取得给定汉字串的首字母串,即声母串
    public static String firstLetter(String buff, String charset) {
        if (buff == null || buff.trim().length() == 0) {
            return "";
        }
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < buff.length(); i++) {
			String letter = firstLetter4SingleCn(buff.substring(i, i + 1), charset);
			if (StringUtils.isNotEmpty(letter)) {
				str.append(letter);
			}
        }
        return str.toString();
    }

    // 1 取得给定汉字的首字母,即声母
    private static String firstLetter4SingleCn(String cn, String charsetName) {
        if (cn == null || cn.trim().length() == 0) {
            return "";
        }

        String iso = new String(cn.getBytes(Charset.forName(charsetName)), Charset.forName("ISO8859-1"));
        String letter = null;
        if (iso.length() > 1) {
            int sector = (int) iso.charAt(0) - 160; // 1 汉字区码
            int position = (int) iso.charAt(1) - 160; // 1汉字位码
            int code = sector * 100 + position; // 汉字区位码
            if (code > 1600 && code < 5590) {
                for (int i = 0; i < FIRST_LETTER_ARR.length; i++) {
                    if (code >= LETTERS_SEC_POS_VAL[i] && code < LETTERS_SEC_POS_VAL[i + 1]) {
                        letter = FIRST_LETTER_ARR[i];
                        break;
                    }
                }
            } else {
                // 1 非汉字字符，如图形符号或ASCII码
                String gb = new String(cn.getBytes(Charset.forName(charsetName)), Charset.forName("GB2312"));
                letter = gb.substring(0, 1);
            }
        }
        return letter;
    }

}
