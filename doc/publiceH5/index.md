# 目录
1. [php版-已经绑定手机设置支付密码](#1)
2. [php版-未绑定手机设置支付密码或忘记密码重置密码](#2)
3. [php版-修改绑定手机号](#3)
4. [java版-忘记登录密码](#4)
5. [php版-修改登录密码(企业)](#5)
6. [php版-修改支付密码(企业)](#6)
7. [php版-我的消息](#7)
8. [php版-帮助中心](#8)

<h4 id="1">已经绑定手机设置支付密码</h4>

```
无
```

<h4 id="2">未绑定手机设置支付密码或忘记密码重置密码</h4>

```
忘记支付密码 H5:
https://mobile.guanaitong.com/index.php?wxA=PasswordSetting.forgetPayPassword
&retryPayUrl=http%3A%2F%2Fwww.baidu.com
注：retryPayUrl是返回页面地址

忘记支付密码 PC:
https://secure.guanaitong.com/index.php?wxA=Person.index
```

<h4 id="3">修改绑定手机号</h4>

```
修改手机H5:
https://mobile.guanaitong.com/index.php?wxA=MyMobile.index

修改手机PC:
https://secure.guanaitong.com/index.php?wxA=Person.index

都不支持跳回url的传入
```

<h4 id="4">忘记登录密码</h4>
```
手机:
https://passport.guanaitong.com/mobileForgetPwd/step1

PC:
https://passport.guanaitong.com/forgetPwd/step1

都不支持跳回url的传入
```

<h4 id="5">修改登录密码(企业)</h4>

```
PC:
https://my.guanaitong.com/index.php?m=security&a=changePassword

不支持跳回url的传入
```

<h4 id="6">修改支付密码(企业)</h4>

```
PC:
https://my.guanaitong.com/index.php?m=security&a=changePayPassword

不支持跳回url的传入
```

<h4 id="7">我的消息</h4>

```
PC:
https://ecenter.guanaitong.com/index.php?wxA=Message.index
```

<h4 id="8">帮助中心</h4>

```
PC:
帮助中心-常见问题:
https://ecenter.guanaitong.com/index.php?wxA=Help.problem&id=3

帮助中心-操作培训:
https://ecenter.guanaitong.com/index.php?wxA=Help.problem&id=4
```