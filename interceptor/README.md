# Interceptor脚手架


```
1、针对不通URL进行拦截、检验、返回。
2、如果需要调取其他服务，实现了本地缓存，用guava的CacheBuilder实现
3、通过在controller进行getAttribute来获取缓存数据，省去了每个controller都去调用底层接口效率低的问题。

```
