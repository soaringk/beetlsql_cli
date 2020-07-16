package io.beetlsql.cli.common.beetlsql;

import org.beetl.sql.core.*;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.gen.GenConfig;
import org.beetl.sql.ext.gen.MapperCodeGen;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * @功能：beetl读取数据库，自动生成实体类、md文件、dao
 */
@Component
public class GenSqlTool {
    private static String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static String URL = "jdbc:mysql://127.0.0.1:3306/user?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
    private static String USER_NAME = "root";
    private static String PASSWORD = "123456";
    private static String MAPPER_TEMPLATE;
    private static String templatePath = "src/main/java/io/beetlsql/cli/common/beetlsql/mapper.btl";

    static {
        try {
            MAPPER_TEMPLATE = new Scanner(new File(templatePath)).useDelimiter("\\Z").next();
            System.out.println(MAPPER_TEMPLATE);
        } catch (FileNotFoundException e) {
            System.out.println("[Error] " + templatePath + " did not exist!");
        }
    }

    public static void main(String[] args) {
        try {
            sqlGenerator();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void sqlGenerator() throws Exception {
        ConnectionSource source = ConnectionSourceHelper.getSimple(DRIVER_CLASS, URL, USER_NAME, PASSWORD);
        SQLLoader loader = new ClasspathLoader("/sql");
        UnderlinedNameConversion nc = new UnderlinedNameConversion();
        SQLManager sqlManager = new SQLManager(new MySqlStyle(), loader, source, nc, new Interceptor[]{new DebugInterceptor()});

        // sqlManager.genPojoCodeToConsole("article","com.springboot.start.entity"); //单独生成pojo实体类  注：article是数据库表名
        // sqlManager.genSQLFileToConsole("article",config); //单独生成生成sql文件

        MapperCodeGen mapper = new MapperCodeGen("io.beetlsql.cli.dao");
        mapper.setMapperTemplate(MAPPER_TEMPLATE); // 自定义模版
        GenConfig config = new GenConfig();
        config.codeGens.add(mapper);

        config.setDisplay(true); // 输出到 console

        //生成所有的数据库pojo实体和sql文件
        sqlManager.genALL("io.beetlsql.cli.entity", config, tableName -> {
            if (tableName.equals("vehicle_location")) {
                return true;
            } else {
                return false;
            }
            // return true; //生成所有的代码
        });
    }
}
