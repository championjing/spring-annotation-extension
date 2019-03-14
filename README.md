# 项目说明

当请求的content-type为application/json时实现一下功能：

1. 对请求体中的参数做required判断
2. 解析参数为指定类型
3. 提供默认值

# 项目使用方法
- 见示例项目代码 [https://github.com/championjing/spring-annotation-extension/tree/master/demo](https://github.com/championjing/spring-annotation-extension/tree/master/demo)


## 发布
- pom.xml配置好<plugin>、<distributionManagement>并在setting.xml中配置用户名和密码之后，终端中执行mvn deploy -P release即可发布
