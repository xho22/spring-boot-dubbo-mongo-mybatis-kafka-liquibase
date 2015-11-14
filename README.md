##要开始使用spring boot了，网上例子很杂乱，我一个一个找并集成，踩了很多坑，在这里把我做的一些集成分享一下

##启动方法 
1. 直接执行com.ubankers.userservice.Application即可
2. ./user.sh start

###spring boot 需要解决的问题列表

1 mybatis resolved

mybatis我还是用了xml的配置方式，为了移植方便些

2 dubbo resolved

dubbo 同样的，为了移植方便些，用了xml

3 slf4j resolved


4 kafka resolved


5 liquibase resolved


6 junit resolved


7 package script resolved


8 mongodb resolved


9 graceful shutdown resolved 

curl -X POST http://127.0.0.1:8181/shutdown 