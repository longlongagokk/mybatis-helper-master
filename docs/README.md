# mybatis-helper
一款完全无侵入的mybatis插件
mybatis-helper是一款非常轻量级的mybatis插件。它基于mybatis动态查询sql来开发和实现，代码简单易懂，上手非常顺，
且与项目基本无任何瓜葛，如果不使用内置的分页器，耦合度甚至可以为0，对项目完全无侵入。 
mybatis-helper 工具提供丰富的自定义查询条件，可以自定义各种组合的查询条件类型（如原型查询、属性转换查询、参数化查询）。 
mybatis-helper 提供了各种连表、多表查询、更新、删除等功能。 未来，框架 将持续新增各种方便开发的功能，如：分组、联合查询等。 
使用 mybatis-helper，与mybatis无缝接入，不再需要繁琐的xml编写，一切的业务逻辑以对象为基准，以后数据库增删改字段，
只需要将model属性做相应修改即可，不再需要再从各个xml里去做处理，大大降低项目bug。 
使用 mybatis-helper，无需再编写繁琐的条件构造器和各种增删查改，可以释放大部分精力放在业务、性能、流量等其他更有价值的地方，工作效率大幅度提升。 
目前暂时支持mysql

测试方式

> 使用boot进行运行，目录：`mybatis-helper-demo\src\main\java\com\mybatishelper\demo\`,在文件`OrderApiApplication.java`右键直接运行即可。

> _配置文件在`mybatis-helper-demo\src\main\resources\config\`目录下。_