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

## 2020/8/30 更新

把原来的方法写成异步的，但是又带来新的问题，按照现在的代码逻辑，并发时两个几乎同时进行的事务查询会查到一样的记录，导致这两个线程下的两个事务执行完全一样的任务。按现在的代码逻辑，暂时没想到好主意，似乎只能靠使用更高级的隔离级别来应对（那我写成多线程干嘛？喵喵喵？？？）

从代码逻辑入手的话，初步想法是用回以前的分页查询，每次事务自增，这样事务之间就互不干涉了，似乎也更符合并发的场景，但这样又需要开始考虑对公共资源的管理。前两天稍微试了一下，还是会一定程度降低代码的可阅读性。

但更关键地是，在百万数据时分页到后面因为要略过大量 OFFSET 所以 SELECT 会很慢，现在又用回去我不是白优化？而且这样真的会比但线程要快吗？

反正就这样了，以后想到可以明显提升执行效率的方法再回来填坑。
