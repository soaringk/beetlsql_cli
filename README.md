# 简介

这是一个spring boot集成 beetl 和 beetlSQL 的框架demo，主要看 DAO 层和 Service 代码即可。
resource 下有操作数据库的 md 文件。
application.yml 中的配置需要修改，比如数据库 url。

这是一个 CLI 项目，因此没有 web 服务。该项目用于批量操作更新数据库。

# Package

在项目根目录下运行：

    ./mvnw clean verify spring-boot:repackage
    
如果希望跳过测试：

    ./mvnw clean verify spring-boot:repackage -Dmaven.test.skip=true
    
# 运行

打包完成以后：

    java -jar ./target/beeltlsql_cli-0.0.1-SNAPSHOT.jar
