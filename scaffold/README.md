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
contract和controller定义了系统所提供的内部服务
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
### 3.1. 缓存 共享和本地
### 3.1. 路径规范
### 3.1. 参数记忆功能
### 3.1. 异常统一处理
### 3.1. 功能和业务分离





