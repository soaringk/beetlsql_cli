package io.beetlsql.cli.common.interceptor;

import org.beetl.sql.core.BatchUpdateInterceptorContext;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.InterceptorContext;
import org.beetl.sql.core.engine.SQLParameter;
import org.beetl.sql.core.kit.EnumKit;
import org.beetl.sql.ext.DebugInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class BatchUpdateInterceptor extends DebugInterceptor implements Interceptor {

    @Value("${beetlsql.intercept-max-params}")
    private long INTERCEPT_MAX_PARAMS;

    @Override
    public void before(InterceptorContext ctx) {
        String sqlId = ctx.getSqlId();
        if (this.isDebugEanble(sqlId)) {
            ctx.put("debug.time", System.currentTimeMillis());
        }
        if (this.isSimple(sqlId)) {
            return;
        }
        StringBuilder sb = new StringBuilder();

        List<String> params;
        try {
            params = formatBatchParas(((BatchUpdateInterceptorContext) ctx).getBatchParas());
        } catch (ClassCastException cce) {
            params = super.formatParas(ctx.getParas());
        }

        String lineSeparator = System.getProperty("line.separator", "\n");
        sb.append("┏━━━━━ Debug [").append(this.getSqlId(formatSql(sqlId))).append("] ━━━").append(lineSeparator)
                .append("┣ SQL：\t " + formatSql(ctx.getSql()) + lineSeparator)
                .append("┣ 参数：\t " + params).append(lineSeparator);
        RuntimeException ex = new RuntimeException();
        StackTraceElement[] traces = ex.getStackTrace();
        int index = lookBusinessCodeInTrace(traces);
        StackTraceElement bussinessCode = traces[index];
        String className = bussinessCode.getClassName();
        String mehodName = bussinessCode.getMethodName();
        int line = bussinessCode.getLineNumber();
        sb.append("┣ 位置：\t " + className + "." + mehodName + "(" + bussinessCode.getFileName() + ":" + line + ")"
                + lineSeparator);
        ctx.put("logs", sb);
    }

    protected List<String> formatBatchParas(List<List<SQLParameter>> list) {
        List<String> data = new ArrayList<String>(list.size());
        for (List<SQLParameter> paraList : list) {
            if (data.size() > INTERCEPT_MAX_PARAMS) break;
            for (SQLParameter para : paraList) {
                Object obj = para.value;
                if (obj == null) {
                    data.add(null);
                } else if (obj instanceof String) {
                    String str = (String) obj;
                    if (str.length() > 20) {
                        data.add(str.substring(0, 20) + "...(" + str.length() + ")");
                    } else {
                        data.add(str);
                    }
                } else if (obj instanceof Date) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    data.add(sdf.format((Date) obj));
                } else if (obj instanceof Enum) {
                    Object value = EnumKit.getValueByEnum(obj);
                    data.add(String.valueOf(value));
                } else {
                    data.add(obj.toString());
                }
            }
        }
        return data;
    }

}