## 背景
禅道、TAPD 等需求管理收费贵，而且功能繁杂，不适合中小型公司使用，而 demanduck（中文译名：需求鸭）开源，并且功能简单，目的是为了更好管理敏捷开发进度。

## 功能
- 用户管理
- 公司管理
- 项目管理
- 需求管理
- 任务管理
- 缺陷管理
- 数据统计
- 消息通知

## 技术栈
- 前端：Vue + ElementUI
- 后端：SpringBoot + MyBatis + MySQL + Redis
- 部署：jar
- 项目管理：Maven
- 版本控制：Git
- 代码托管：Github

## 启动
```shell
mvn clean install
cd target
java -jar demanduck-0.0.1-SNAPSHOT.jar --spring.port=8045
```

## 启动参数
常用需要自定义参数，支持 **-D** 选项或 **--**

| 参数 | 说明 | 默认值                                                |
| --- | --- |----------------------------------------------------|
| server.port | 服务端口 | 8045                                               |
| spring.datasource.url | 数据库地址 | jdbc:mysql://localhost:3306/demanduck?useUnicode=true&characterEncoding=UTF-8 |
| spring.datasource.username | 数据库用户名 | root                                               |
| spring.datasource.password | 数据库密码 | root                                               |
| spring.redis.host | Redis 地址 | 127.0.0.1                                          |
| spring.redis.port | Redis 端口 | 6379                                               |
| spring.redis.password | Redis 密码 |                                                    |

## 详细模块说明
### 用户管理
- 用户注册
  - 邮箱注册
- 用户登录
  - 邮箱登录
- 密码重置
- 用户角色
  - 系统角色
  - 公司角色
  - 项目角色

### 公司管理
- 增删改查

### 项目管理
- 增删改查