## Gconf使用说明示例代码 ##
*说明:目录下有两个演示文件Gconf源码分享.pptx和Gconf流程图.vsd；ppt演示了本次的讲解内容，流程图演示了Gconf的执行流程；示例代码主要补充ppt上的原理部分。*
####  1、反射和注解示例
PropsToBeanUtils和PropsAnnotation类演示了反射和注解
     
    DynamicProxyTest测试类
    @Test
    public void test(){
	    TestBean testBean = PropsToBeanUtils.toBean("test.properties", TestBean.class,true);
	    System.out.println(testBean);
    }
    
####  2、JDK动态代理类示例
DynamicProxy和ISay及SayImp类演示了JDK的动态代理

    PropsToBeanTest测试类
    @Test
    public void test(){
	    ISay iSay = (ISay) Proxy.newProxyInstance(SayImp.class.getClassLoader(), SayImp.class.getInterfaces(), new DynamicProxy(new SayImp()));
	    iSay.say();
	    Assert.assertEquals(iSay.say2(),"say2");
    }
####  3、Atomic类使用示例

通过i++多线程不安全示例来说明AtomicInteger的使用以及TestAtomicReference如何使用
      
     TestCounter测试类
      public static void main(String[] args) {
	    Num num = new Num();
	    go(10000, () -> num.inc());
	    System.out.println(num.get());
    }

#### 4、Gconf使用示例以及Springboot集成
Gconf的一般使用示例：

    public class GconfTest {
    @Test
    public void test(){
	    ConfigCollectionFactory configCollectionFactory = RemoteConfigCollectionFactoryBuilder.getInstance();
	    ConfigCollection configCollection = configCollectionFactory.getConfigCollection("contactlist-provider", "1.0.0");
	    ConfigBean bean = configCollection.getBean("contactlist.properties", ConfigBean.class);
	    IConfig iConfig = configCollection.getBean("contactlist.properties", IConfig.class);
	    Assert.assertEquals(iConfig.getName(),"contactlist");
      }
    }

Gconf结合springboot启动示例：

    @SpringBootApplication
    public class GconfApplication {
    	public static void main(String[] args) {
    		SpringApplication springApplication = new SpringApplication(GconfApplication.class);
    		springApplication.addInitializers(new GConfContextInitializer("contactlist-provider","1.0.0"));
    		springApplication.run(args);
    	}
    }
