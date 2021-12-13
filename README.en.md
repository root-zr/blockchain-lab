# blockchain_lab

#### Description
fabric项目平台

#### Software Architecture
Software architecture description

传统的钻石订购平台往往只具备单一的订购功能，而且因为这种产品可以被使用的场景有限，所以往往这类平台的用户量都不会太高，数据也一般都采用关系型数据库进行存储，这种存储方式数据既容易被篡改，同时数据也不向外界公开，没法使信息得到有效的利用。为了提高销售量，有的商家提出每位用户一生只能凭身份证采购一份钻戒。这种营销方式虽然能起到一定的作用，但是一方面因为关系型数据库的数据容易被后台篡改，另一方面外界很难查到某一位用户真实的订购信息，除非他有内部的权限。

本文针对以上情况设计了一种基于Hyperledger Fabric的钻石订购平台，在保证实现一个订购平台应有的功能基础上，该系统还基于区块链不可篡改以及信息公开的特点，将客户的采购信息公开在区块链网络中，所有人员都可以查到全部的订单交易，这样能有效地改善信息不透明。

本系统的另一个亮点在于数据的存储并不是全部放在区块链中，因为Fabric的数据库是一种冗余存储的方式，将数据存在区块链中会造成资源的浪费，同时和分布式的系统进行交互效率也会相对低一点。所以本系统只将关键的订单信息存在区块链中，其他的信息，如用户信息，商品信息等则是存放在关系型数据库MySQL中，实现了资源利用的最大化。

#### Installation

1.  xxxx
2.  xxxx
3.  xxxx

#### Instructions

1.  xxxx
2.  xxxx
3.  xxxx

#### Contribution

1.  Fork the repository
2.  Create Feat_xxx branch
3.  Commit your code
4.  Create Pull Request


#### Gitee Feature

1.  You can use Readme\_XXX.md to support different languages, such as Readme\_en.md, Readme\_zh.md
2.  Gitee blog [blog.gitee.com](https://blog.gitee.com)
3.  Explore open source project [https://gitee.com/explore](https://gitee.com/explore)
4.  The most valuable open source project [GVP](https://gitee.com/gvp)
5.  The manual of Gitee [https://gitee.com/help](https://gitee.com/help)
6.  The most popular members  [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
