# mybatis-helper
一款完全无侵入的mybatis插件

测试方式

1，使用boot进行运行，目录：vitily-order\vitily-order-service-api\src\main\java\com\vitily\order\api\OrderApiApplication.java
,右键直接运行即可。

2，代码生成器，目录：mybatis-generator\src\main\java\org\mybatis\generator\api\ShellRunner.java
，右键直接运行即可生成。

  2.1，生成器使用的是 mybatis-generator\src\main\resources\generatorConfig-order.xml配置，如果使用自定义配置，可以在ShellRunner.java
  中修改相应文件路径
  2.2，参考generatorConfig-order.xml,将jdbcConnection节点数据库连接改成目标连接和连接用户名、密码、数据库；然后依次修改 "entity class","view class",
  "mapper xml","mapper class","service class","service impl class"等节点信息。
  2.3，table节点中可以配置表名、对应entity class名称、view class 名称、mapperAndServiceFileName 前缀等属性、如果不配置，使用默认的生成。其中schema可以
  识别不同的schema或者库名。
  
  
