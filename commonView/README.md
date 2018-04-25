##   公用弹框文档

###  清单
  - [选择员工](#person)


###  域名及服务器地址

```
    开发：待定
    测试：待定
    生产：待定
```
###  前置条件

用户登陆
###  通用参数设置
```
1、服务器中获取domain：
"guanaitong."+WorkRegion.getCurrentWorkRegion().getPublicDomainSuffix()

2、页面js设置domain：
    <script>
        document.domain = "$!domain";
    </script>

```
##  页面列表
### **1.<a name="person">选择员工</a>**

#### 请求路径

/commonView/person/choosePage

#### 请求方式
get

#### 请求参数
| 参数名 | 参数类型 | 是否必需| 描述|
| -------- | -------- | -------- |
| selectType | int | Y |选择类型，1单选2复选，默认单选 |
| chooseIds | string | N |已选员工memberId，用逗号分隔 |
| callbackFunc | string | Y |选择回调函数 |
| closeFunc | string | Y |关闭弹框函数 |

#### 返回参数

| 参数名 | 参数类型 |描述|
| -------- | -------- |--------|
| memberId   | int | 员工memberId |
| name   | String | 员工姓名 |
| personId   | int | 员工personId |

#### 示例页面
单选
![](https://i.imgur.com/6yUL0He.png)
复选
![](https://i.imgur.com/r9U6Jlb.png)
#### 单选示例

```
1、通用参数设置

2、设置回调函数和关闭函数

<script src="//cdn.guanaitong.com/s2/pc/V5.0/js/jquery.dialog.js"></script>
<script>
    function show(person) {
        alert(JSON.stringify(person));
    }

    function close() {
        dialog.closeFn();
    }
    function choosePerson(){
        dialog.init({
            'isIfm': true,
            'url': 'http://XXX/commonView/person/choosePage?closeFunc=close&callbackFunc=show',
            'w': 700,
            'h': 600
        })
    }
</script>

3、返回对象
{
    "memberId": "253173",
    "name": "黄圆圆",
    "personId": "249389"
}
```
#### 复选示例

```
1、通用参数设置

2、设置回调函数和关闭函数

<script src="//cdn.guanaitong.com/s2/pc/V5.0/js/jquery.dialog.js"></script>
<script>
	function shows(arr) {
        alert(JSON.stringify(arr));
    }
    function choosePersons(){
        dialog.init({
            'isIfm': true,
            'url': 'http://XXX/commonView/person/choosePage?closeFunc=close&chooseIds=258342,253173&callbackFunc=shows&selectType=2',
            'w': 700,
            'h': 600
        })
    }
</script>

3、返回对象
[
    {
        "memberId": "253172",
        "name": "黄方方",
        "personId": "249388"
    },
    {
        "memberId": "179966",
        "name": "研发a",
        "personId": "176551"
    }
]
```