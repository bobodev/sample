## 巨灵神分享 ##
#### 1、Feign简介 ####
#### 2、巨灵神ciicgat-api原理分享 ####
#### 3、自己写一个简单的Feign框架 ####


----------
#### 1、Feign简介 ####
*Feign使得 Java HTTP 客户端编写更方便。Feign 灵感来源于Retrofit、JAXRS-2.0和WebSocket。Feign 最初是为了降低统一绑定Denominator 到 HTTP API 的复杂度，不区分是否支持 Restful。*

		<dependency>
			<groupId>io.github.openfeign</groupId>
			<artifactId>feign-core</artifactId>
			<version>9.0.0</version>
		</dependency>
Github源码地址：https://github.com/OpenFeign/feign

**为什么选择Feign而不是其他**

*你可以使用 Jersey 和 CXF 这些来写一个 Rest 或 SOAP 服务的java客服端。你也可以直接使用 Apache HttpClient 来实现。但是 Feign 的目的是尽量的减少资源和代码来实现和 HTTP API 的连接。通过自定义的编码解码器以及错误处理，你可以编写任何基于文本的 HTTP API。*

**Feign使用简介**

*基本的使用如下所示:*

public class GitHubExample {

  interface GitHub {

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @RequestLine("POST /api/hello?name={name}")
    ApiResponse hello(@Param("name") String name);

    @RequestLine("GET /api/hello")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    ApiResponse hello2(String name);

    static GitHub connect() {
      Decoder decoder = new GsonDecoder();
      return Feign.builder()
          .encoder(new FormEncoder())
          .decoder(new GsonDecoder())
          .logger(new Slf4jLogger())
          .logLevel(Logger.Level.FULL)
          .target(GitHub.class, "http://localhost:8080");
    }
 

**Feign集成模块**
**Gson**
Gson 包含了一个编码器和一个解码器，这个可以被用于JSON格式的API。 
添加 GsonEncoder 以及 GsonDecoder 到你的 Feign.Builder 中， 如下:
            
                GsonCodec codec = new GsonCodec();
                GitHub github = Feign.builder()
                 .encoder(new GsonEncoder())
                 .decoder(new GsonDecoder())
                 .target(GitHub.class, "https://api.github.com");

maven依赖

    		<dependency>
    			<groupId>io.github.openfeign</groupId>
    			<artifactId>feign-gson</artifactId>
    			<version>9.0.0</version>
    		</dependency>

**feign-form**<br/>
https://github.com/OpenFeign/feign-form<br/>
form表单请求体格式：

    	       <dependency>
    			<groupId>io.github.openfeign.form</groupId>
    			<artifactId>feign-form</artifactId>
    			<version>3.3.0</version>
    		</dependency>

**feign核心类**<br/>

- ReflectiveFeign 构造类，其newInstance方法,创建代理类

        InvocationHandler handler = factory.create(target, methodToHandler);
        T proxy = (T) Proxy.newProxyInstance(target.type().getClassLoader(), new Class<?>[]{target.type()}, handler);

  其 RequestTemplate resolve解析参数
     
           Map<String, Object> formVariables = new LinkedHashMap<String, Object>();
              for (Entry<String, Object> entry : variables.entrySet()) {
                if (metadata.formParams().contains(entry.getKey())) {
                  formVariables.put(entry.getKey(), entry.getValue());
                }
              }
              try {
                encoder.encode(formVariables, Encoder.MAP_STRING_WILDCARD, mutable);
              } catch (EncodeException e) {
                throw e;
              } catch (RuntimeException e) {
                throw new EncodeException(e.getMessage(), e);
              }
              return super.resolve(argv, mutable, variables);
            }


- SynchronousMethodHandler 动态代理类，实际处理类，扩展接口的调用类
  
      private final MethodMetadata metadata;
      private final Target<?> target;
      private final Client client;
      private final Retryer retryer;
      private final List<RequestInterceptor> requestInterceptors;
      private final Logger logger;
      private final Logger.Level logLevel;
      private final RequestTemplate.Factory buildTemplateFromArgs;
      private final Options options;
      private final Decoder decoder;
      private final ErrorDecoder errorDecoder;
      private final boolean decode404;

    实际调用的地方：首先解析参数，然后调用请求

      public Object invoke(Object[] argv) throws Throwable {
		    RequestTemplate template = buildTemplateFromArgs.create(argv);
		    Retryer retryer = this.retryer.clone();
		    while (true) {
		      try {
		         return executeAndDecode(template);
		         } catch (RetryableException e) {
		         retryer.continueOrPropagate(e);
                if (logLevel != Logger.Level.NONE) {
                  logger.logRetry(metadata.configKey(), logLevel);
                }
                continue;
		      }
		    }
      }
 
- Contract 处理feign类的注解，包装解析feign的原始类和方法


	     protected void processAnnotationOnMethod(MethodMetadata data, Annotation methodAnnotation,
	     Method method) {
		  Class<? extends Annotation> annotationType = methodAnnotation.annotationType();
		  if (annotationType == RequestLine.class) {
			String requestLine = RequestLine.class.cast(methodAnnotation).value();
			checkState(emptyToNull(requestLine) != null,
			    "RequestLine annotation was empty on method %s.", method.getName());
			      if (requestLine.indexOf(' ') == -1) {
			    checkState(requestLine.indexOf('/') == -1,
			      "RequestLine annotation didn't start with an HTTP verb on method %s.",
			      method.getName());
				data.template().method(requestLine);
			      return;
	    }



#### 2、巨灵神ciicgat-api原理 ####
**FeignServiceFactory** <br/>
通过此构造工厂首先通过注解类ServiceEndPoint来从Gconf上寻找请求的API地址，获得到地址之后，通过feign工厂来构建具体的实现类。【具体情参考ciicgat-sdk-api】
    
     public static <T> T newInstance(final Class<T> serviceClazz)
     private static <T> ServiceEndPoint getServiceEndPoint(final Class<T>   serviceClazz) 
     private static <T> T create0(final Class<T> serviceClazz)
     
     T serviceInstance = Feign.builder()
        .logger(new Slf4jLogger())
        .contract(new MyContract())
        .logLevel(feign.Logger.Level.NONE)
        .decoder(new OptionalDecoder(new JacksonDecoder(OBJECT_MAPPER)))
        .encoder(new FormEncoder(new JacksonEncoder(OBJECT_MAPPER)))
        .client(new OkHttpClientWrapper(HttpClientSingleton.getOkHttpClient()))
        .retryer(new Retryer.Default(0, 0, -1))//默认不retry
        .target(serviceClazz, baseUrl);
   
*特别注意：* <br/>
   MyContract()-通过注解的形式封装了获取phpapi的统一形式<br/>
   JacksonDecoder-重写了获取response返回的统一解析格式<br/>
   FormEncoder-支持表单的提交形式<br/>


#### 3、自己写一个简单的Feign框架 ####

简单的写一个Feign框架来理解，如何自己控制接口动态的通过注解方式来请求不同的API。

接口类： <br/>

    public interface IHello {
    @MyRequestLine("GET /api/hello")
    ApiResponse hello(@MyParam("name") String name);
    
    @MyRequestLine("GET /api/hello2")
    String hello2(@MyParam("name") String name);
    
使用类： <br/>
   
      IHello instance = MyFeignFactory.build().decoder(MyFeignFactory.defaultDecoder()).target(IHello.class,"http://localhost:8080");
      ApiResponse world = instance.hello("world");
      System.out.println(world);

*核心:* <br/>

   *1、动态的为接口实现其方法*
    
    public<T> T target(Class<T> clazz, String url){
        this.url=url;
        MethodProxy methodProxy = new MethodProxy(this);
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, methodProxy);
        return (T) o;
    }

   *2、利用注解来获取方法参数名称和请求体参数*
    
    for (int i=0;i<args.length;i++){
	    MyParam myParamAnnotation = parameters[i].getAnnotation(MyParam.class);
	    if (myParamAnnotation!=null){
	    String key = myParamAnnotation.value();
	    params.put(key,args[i]);
     }
    }

  *3、使用接口来对接不同的实现方式（如decoder）*

    public static MyDecoder defaultDecoder(){
        return new MyDecoder() {
            @Override
            public <T> T decode(String result, Class<T> clazz) {
                return JSON.parseObject(result,clazz);
            }
        };
    }

