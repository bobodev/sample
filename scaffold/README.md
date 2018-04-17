
```
1. 概述
2. 目录介绍
3. 集成示例
3.1. 拦截器
3.2. 缓存
3.3. 路径规范
3.4. 前端页面参数自动存储记忆
3.5. 异常统一处理
3.6. 功能和业务分离
3.7. 指定包方法返回值的JSON序列化为下划线
3.8. 定时任务的分布式锁机制，避免多台机器同时运行。同时支持动态干预任务的执行
3.9. 增加DB支持
3.10. 参数验证
3.11. Controller层接收下划线自动转驼峰
3.12. 启用https支持
3.13. https重定向到http
3.14. 防重复提交

```

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

2.contract（简单业务可以忽略request和response）

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
### 3.1. 拦截器
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

### 3.2. 缓存

主要是DefaultCacheManager类。目前继承了redisCache和simpleCacheManager，可以实现指定缓存实现

使用步骤：

1、启动类上加入@EnableCaching，启用缓存

2、配置 DefaultCacheManager 相关缓存参数

3、使用@CacheConfig、@Cacheable、@CacheEvict、@CachePut进行缓存使用

4、参照UserService基本用法

参考网址

1.https://aggarwalarpit.wordpress.com/2017/01/25/setting-ttl-for-cacheable-spring/

2.https://zhuanlan.zhihu.com/p/30537673

3.https://zhuanlan.zhihu.com/p/30540686

4.https://www.jianshu.com/p/275cb42080d9

5.https://www.journaldev.com/18141/spring-boot-redis-cache

### 3.3. 路径规范

1.参考包规范

2.前端文件名称下划线方式。如user_list.html、user_add.html,含有业务含义的以文件夹进行区分

3.请求路径命名：前端请求路径蛇形,如/api/scaffold/list_user;巨灵神路径驼峰命名。如/api/scaffold/listUser、/api/scaffold/addUser

### 3.4. 前端页面参数自动存储记忆

1、采用store.js,参考网址 https://github.com/marcuswestin/store.js/blob/master/README.md

2、兼容性

store.js 兼容IE6+、iOS 8+、Android 4+、Firefox 4+、Chrome 27+、Safari 5+、Opera 11+

3、存储大小限制

参考 Storages limits

4、API示例

```
// Store current user
store.set('user', { name:'Marcus' })

// Get current user
store.get('user')

// Remove current user
store.remove('user')

// Clear all keys
store.clearAll()

// Loop over all stored values
store.each(function(value, key) {
	console.log(key, '==', value)
})
```

5、示例

参照public下a.html和b.html

### 3.5. 异常统一处理
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

### 3.6. 功能和业务分离

1.controller只负责跳转控制和数据获取

2.service包负责业务逻辑处理

* biz包下负责大部分的普通业务处理，每个Service都要包含接口和实现。
* schedule包下负责含有定时任务的业务逻辑，建议通过ScheduleFactory管理整个定时任务。
* technology包下负责业务组件的封装。比如可以封装RedisService、MqService。
* thirdparty包下负责第三方Service的清单


### 3.7. 指定包方法返回值的JSON序列化为下划线

1、参考类 ConverterInterceptor

2、指定影响的包名

```
    @ControllerAdvice(basePackages = "com.sample.scaffold.controller")

```

### 3.8. 定时任务的分布式锁机制，避免多台机器同时运行。同时支持动态干预任务的执行
参考 RedisLockAnno和RedisLockAnnoProcessor
使用步骤：

1、在定时任务上指定@RedisLockAnno，指定相关key和expired，通过execKey指定是否要执行，默认执行

2、目前尚未集成任务的动态干预，参考gitlab上jd-job项目已经实现结合gconf动态干预任务。

3、相关例子程序有空补上

### 3.9. 增加DB支持

1、JPA(已支持)

2、mybatis(已支持)

3、spring jdbcTemplate(待集成)

### 3.10. 参数验证

1、Validate注解校验

2、支持级联校验

3、支持快速失败校验

4、支持方法中基本参数校验，但是如果用到了接口，接口中需要指定校验的内容，否则会报错。如：

```
    SingleDto findOneSingle(@NotNull(message = "id不允许为空") Long id) throws Exception;

```

### 3.11. Controller层接收下划线自动转驼峰

注:如果接收方式为application/json则无需配置。

1、WebMvcConfig加上代码

```
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        // 注册JsonPathArgumentResolver的参数分解器
        argumentResolvers.add(new MyArgumentResolver());
    }
```

2、在需要转化的方法参数前加上 RequestConverterAnno 注解


### 3.12. 启用https支持

1、确认项目根目录下含有证书文件，如果没有生成一个

```
keytool -genkey -alias tomcat  -storetype PKCS12 -keyalg RSA -keysize 2048  -keystore keystore.p12 -validity 3650

```

2、application.properties 打开注释，开启https支持

```
#https支持
#server.ssl.key-store=keystore.p12
#server.ssl.key-store-password=123456
#server.ssl.keyStoreType=PKCS12
#server.ssl.keyAlias:tomcat

```
### 3.13. https重定向到http

在本地测试了下什么不配置也是ok的。

贴一下解决方案：

1、解决redirect，重写InternalResourceViewResolver

```
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setRedirectHttp10Compatible(false);
        registry.viewResolver(resolver);
    }


```
或者

```
    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setRedirectHttp10Compatible(false);
        return resolver;
    }
```

2、解决response.sendRedirect

```
    response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
    response.setHeader("Location", "/a.html");
        
```

### 3.14. 防止重复提交

源码参见 TokenManager

1、通用解决方案

* 产生token
* 验证token
* 移除token

2、mvc项目解决方案

```
    @Autowired
    private TokenManager tokenManager;

    /**
     * 产生token
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/productToken")
    public String productToken(ModelMap model) throws Exception {
        model.addAttribute("token",tokenManager.productToken());
        return "xxx";
    }

   
    /**
     * 表单防重复提交（token参数可以防止在header）
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping("/submit")
    public String submit(String token) throws Exception {
        tokenManager.validateToken(token);
        //业务方法开始
        //业务方法结束
        return "xxx";
    }
```

3、前后端分离项目解决方案

```
    /**
     * 产生token fox ajax
     * @return
     * @throws Exception
     */
    @RequestMapping("/productTokenFoxAjax")
    @ResponseBody
    public String productTokenFoxAjax() throws Exception {
        return tokenManager.productToken();
    }

    /**
     * 表单防重复提交（token参数可以防止在header）
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping("/submit")
    public String submit(String token) throws Exception {
        tokenManager.validateToken(token);
        //业务方法开始
        //业务方法结束
        return "xxx";
    }
    
```