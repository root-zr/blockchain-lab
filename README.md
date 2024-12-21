# blockchain_lab

#### 介绍
这是一个 fabric 项目系统，系统是来自于 qubing 老师的 gitlab 项目，在原项目的 readme.md 文件里有相关的环境配置文件，您可以点击[这里](https://gitlab.com/qubing/blockchain_lab_v2.git)查阅相关内容。或者您可以点击[这里](https://zhuanlan.zhihu.com/p/370252654)参考我写的搭建过程的博客。

在 qubing 老师的原项目结构的 app/ 目录下有转账相关的 fabric 应用实例，在 chaincode/ 目录下有转账相关的智能合约，它将会很好地帮助您掌握 fabric 的特点。

这个项目所作的工作是在原有的实验平台的基础上做了一个新的应用，基于  Hyperledger Fabric 的钻石订购系统。做这个项目的初衷是想做一个**官宣平台**，借助区块链信息不可篡改和去中心化的特点，可以给用户心理上增加一定的神圣性。这个项目定位的主体用户应该是年轻的群体，我个人认为以下几点可以保证该项目是可以具备用户群体的。

* 如果一对情侣确定了恋爱关系，可能某一方为了验证自己的另一半对自己是忠贞不渝的，所以会要求对方将恋情公布到区块链平台；
* 一方为了表示对另一方的忠诚，可能会自己主动公布恋情。

为了使项目更加完整，由于钻戒本身的特点，本项目以钻石订购作为载体，将订单的订购信息发布上链，所有人可见，借此来宣扬[DR钻戒](https://www.darryring.com/market/config/2109675904.html?utm_source=baidu&utm_medium=pinpai&utm_term=BD-an&utm_content=ZC1&utm_campaign=BD-P&key=BD-an)所提倡一生只爱一个人的观点。

如果您想将**官宣平台**这个想法付诸于商业，我的建议是可以去掉"钻石订购 "这个载体，简洁的东西往往更能吸引用户。如果您考虑扩展用户量的问题，我觉得添加留言或者评论功能，推广该平台的社区属性是一个不错的方案。

#### 软件架构
**前端**：vue  \
**关系型数据库**：Mysql \
**服务器部分**： 服务器采用Ubuntu 18.04；区块链相关服务使用Dokcer容器技术，持久化使用counchdb，order节点采用kafka消息队列实现；后端使用SpringBoot  \
**仓库地址**：    [[前端](https://github.com/root-zr/soul-diamonds)]              [[服务器端](https://github.com/root-zr/blockchain-lab)]\
**项目架构**：

​							![逻辑架构](/img/逻辑架构.PNG)

说明：由于本人精力的关系，架构中提到的安全相关的服务并没有完全实现，在智能合约的代码中也没有考虑到可能会由并发引起的一系列问题。

#### 参考

1.  https://gitlab.com/qubing/blockchain_lab_v2.git
