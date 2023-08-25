## 背景
禅道、TAPD 等需求管理收费贵，而且功能繁杂，不适合中小型公司使用，所以自己写了一个需求管理系统。

## 技术栈
- 前端：Vue + ElementUI
- 后端：SpringBoot + MyBatis + MySQL
- 部署：jar + Nginx
- 项目管理：Maven
- 版本控制：Git
- 代码托管：Github

## 功能
- 项目管理
- 需求管理
- 任务管理

## 启动
```shell
mvn clean install
cd target
java -jar demanduck-0.0.1-SNAPSHOT.jar --spring.port=8047
```

## 启动参数
常用需要自定义参数，支持 **-D** 选项或 **--**

| 参数 | 说明 | 默认值                                                |
| --- | --- |----------------------------------------------------|
| server.port | 服务端口 | 8047                                               |
| spring.datasource.url | 数据库地址 | jdbc:mysql://localhost:3306/demanduck?useSSL=false |
| spring.datasource.username | 数据库用户名 | root                                               |
| spring.datasource.password | 数据库密码 | root                                               |
| spring.redis.host | Redis 地址 | 127.0.0.1                                          |
| spring.redis.port | Redis 端口 | 6379                                               |
| spring.redis.password | Redis 密码 |                                                    |