package com.xjf.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xjf
 * @date 2020/2/14 18:14
 */
public class SafeSimpleDateFormat {
    private final String _format;
    private static final ThreadLocal _dateFormats = new ThreadLocal() {
        public Object initialValue() {
            return new HashMap();
        }
    };

    private SimpleDateFormat getDateFormat(String format) {
        Map<String, SimpleDateFormat> formatters = (Map)_dateFormats.get();
        SimpleDateFormat formatter = (SimpleDateFormat)formatters.get(format);
        if (formatter == null) {
            formatter = new SimpleDateFormat(format);
            formatters.put(format, formatter);
        }

        return formatter;
    }

    public SafeSimpleDateFormat(String format) {
        this._format = format;
    }

    public String format(Date date) {
        return this.getDateFormat(this._format).format(date);
    }

    public String format(Object date) {
        return this.getDateFormat(this._format).format(date);
    }

    public Date parse(String day) throws ParseException {
        return this.getDateFormat(this._format).parse(day);
    }
}
