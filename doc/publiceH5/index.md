# 目录
1. [php版-已经绑定手机设置支付密码](#1)
2. [php版-未绑定手机设置支付密码或忘记密码重置密码](#2)
3. [php版-修改绑定手机号](#3)
4. [java版-忘记登录密码](#4)
5. [php版-修改登录密码](#5)
6. [php版-修改支付密码](#6)
7. [java版-登录](#7)
8. [php版-个人中心](#8)
9. [php版-我的消息](#9)
10. [php版-帮助中心](#10)
11. [php版-应用列表首页](#11)


<h4 id="1">已经绑定手机设置支付密码</h4>

```
手机h5:
https://mobile.guanaitong.com/index.php?wxA=PasswordSetting.changePayPassword&callBackUrl=XXX
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
https://mobile.guanaitong.com/index.php?wxA=MyMobile.index&&callBackUrl=XXX

修改手机PC:
https://secure.guanaitong.com/index.php?wxA=Person.index

```

<h4 id="4">忘记登录密码</h4>

```
员工手机:
https://passport.guanaitong.com/mobileForgetPwd/step1

员工PC:
https://passport.guanaitong.com/forgetPwd/step1

企业PC:
https://admin.guanaitong.com/forgetPwd/step1

都不支持跳回url的传入
```

<h4 id="5">修改登录密码</h4>

```
企业PC:
https://my.guanaitong.com/index.php?m=security&a=changePassword

不支持跳回url的传入
```

<h4 id="6">修改支付密码</h4>

```
企业PC:
https://my.guanaitong.com/index.php?m=security&a=changePayPassword

手机h5：
https://mobile.guanaitong.com/index.php?wxA=PasswordSetting.changePayPassword&callBackUrl=XXX
```

<h4 id="7">登录</h4>

```
员工（手机/PC）：
https://passport.guanaitong.com?redirect_url=xxxxx

企业（手机/PC）：
https://admin.guanaitong.com?redirect_url=xxxxx
注：redirect_url是返回页面地址
```

<h4 id="8">个人中心</h4>

```
员工PC：
https://mycenter.guanaitong.com/index.php?wxA=Person.index

企业PC：
https://ecenter.guanaitong.com/index.php?wxA=Message.index
```

<h4 id="9">我的消息</h4>

```
企业PC:
https://ecenter.guanaitong.com/index.php?wxA=Message.index

员工PC:
https://mycenter.guanaitong.com/index.php?wxA=Message.messageList
```

<h4 id="10">帮助中心</h4>

```
企业PC:
帮助中心-常见问题:
https://ecenter.guanaitong.com/index.php?wxA=Help.problem&id=3

帮助中心-操作培训:
https://ecenter.guanaitong.com/index.php?wxA=Help.problem&id=4
```

<h4 id="11">应用列表首页</h4>

```
员工手机:
https://mobile.guanaitong.com/index.php?wxA=Enterprise.home
```