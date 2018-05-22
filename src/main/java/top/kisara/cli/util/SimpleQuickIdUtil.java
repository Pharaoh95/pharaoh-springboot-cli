package top.kisara.cli.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleQuickIdUtil {
    public static final String DEFAULT_FORMATTER = "yyyyMMddHHmmss";

    public final static synchronized String generate(String prefix) {
        prefix = prefix == null ? "" : (prefix + "_");
        return prefix + new SimpleDateFormat(DEFAULT_FORMATTER).format(new Date());
    }
}
