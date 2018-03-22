## scaffold

本文主要描述脚手架项目的相关内容

项目git地址 https://github.com/gatdevelop/sample.git

## 1. 概述
scaffold 基于springboot的一个脚手架项目，便于快速的搭建springboot项目。简化项目搭建的流程，提高工作效率。
## 2. 目录介绍
项目目录整体分为一下几个目录
* config
* contract
* controller
* model
* service
* util

下面详细介绍一下每个目录对应的含义

1.config

config目录主要放置一些配置文件，如数据源、redis配置、mq配置、线程配置等

2.contract

contract意为契约。contract包描述了与外部服务交互的契约。调用方和服务实现遵循契约的规定完成服务的调用和实现。
契约一旦开发完成，即可进行并行开发阶段。契约为主要的文档输出。
contract和controller定义了系统所提供的内部服务。
contact包下面分为dto、request、response三个包和Constant类。
dto包放置了数据传输对象，request包用于放置请求信息模型，response包用于放置相应模型。Constant类放置常量。

3.controller

controller 内部服务的接口，依赖于contract。
controller 下面view包用于描述跳转控制。ApiController用于描述数据接口。controller主要体现业务控制，不处理任何业务逻辑。

4.model

model包放置了底层数据模型。是直接和数据库打交道的模型。

5.service

sevice包下放置了项目中的业务代码，该模块也是主要的书写业务实现的地方。

service包下分为biz、schedule、technology、thirdparty四个包

biz: 主要用来实现业务，由接口和实现组成

schedule: 主要用来放置一些定时任务

technology: 主要用来放置技术组件

thirdparty: 主要用来放置第三方的服务

6.util

util包放置了一些工具类

## 3. 集成示例
### 3.1. 拦截
参考WebMvcConfig

```
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] excludeRequestPath = new String[]{
        };
        String[] includeRequestPath = new String[]{
                "/api/scaffold/**/*"
        };
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns(excludeRequestPath)
                .addPathPatterns(includeRequestPath);
    }

```

### 3.1. 缓存 共享和本地
缓存选用Spring Cache
参考

1.https://aggarwalarpit.wordpress.com/2017/01/25/setting-ttl-for-cacheable-spring/

2.https://zhuanlan.zhihu.com/p/30537673

3.https://zhuanlan.zhihu.com/p/30540686

4.https://www.jianshu.com/p/275cb42080d9

5.https://www.journaldev.com/18141/spring-boot-redis-cache

### 3.1. 路径规范

1.参考包规范

2.前端文件名称下划线方式。如user_list.html、user_add.html,含有业务含义的以文件夹进行区分

3.请求路径命名：驼峰命名。如/api/scaffold/listUser、/api/scaffold/addUser

### 3.1. 参数记忆功能

todo

### 3.1. 异常统一处理
参考参考WebMvcConfig

```
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResponseEntity<ServiceException> jsonErrorHandler(Exception exception) throws Exception {
        ServiceException serviceException;
        if (exception instanceof ServiceException) {
            serviceException = (ServiceException) exception;
        } else {
            serviceException = new ServiceException(exception);
            serviceException.setMessage(exception.getMessage());
        }
        ResponseEntity<ServiceException> responseEntity = ResponseEntity.badRequest().body(serviceException);
        return responseEntity;
    }
```

### 3.1. 功能和业务分离

1.controller只负责跳转控制和数据获取

2.service包负责业务逻辑处理

* biz包下负责大部分的普通业务处理，每个Service都要包含接口和实现。
* schedule包下负责含有定时任务的业务逻辑，建议通过ScheduleFactory管理整个定时任务。
* technology包下负责业务组件的封装。比如可以封装RedisService、MqService。
* thirdparty包下负责第三方Service的清单




