### 一、简介

1、开发语言：Java

2、开发框架：Spring Boot

3、开发工具：Eclipse

4、数据库：MySQL

5、缓存：Redis

6、日志：Logger

### 二、接口说明

1、创建用户接口

(1)URL：/user/create

(2)请求方式：POST

(3)请求参数

| 参数名称 | 参数类型 | 是否必填 |                             备注                             |
| :------: | :------: | :------: | :----------------------------------------------------------: |
| userName |  String  |    是    |                            用户名                            |
| password |  String  |    是    |                             密码                             |
|   type   |  String  |    是    | 用户类型，管理员输入ADMIN，教师输入TEATHER，学生则输入STUDENT |

(4)返回类型：String

|      |         输出         |
| :--: | :------------------: |
|  1   | 用户名和密码不能为空 |
|  2   |  用户名类型不能为空  |
|  3   |   用户名类型不正确   |
|  4   |     用户名已存在     |
|  5   |     用户创建成功     |

2、用户登录接口

(1)URL：/user/login

(2)请求方式：POST

(3)请求参数

| 参数名称 | 参数类型 | 是否必填 |  备注  |
| :------: | :------: | :------: | :----: |
| userName |  String  |    是    | 用户名 |
| password |  String  |    是    |  密码  |

(4)返回类型：String

|      |         输出         |
| :--: | :------------------: |
|  1   | 用户名和密码不能为空 |
|  2   |       登录失败       |
|  3   |       登录成功       |

3、新增图书接口

(1)URL：/book/add

(2)请求方式：POST

(3)请求参数

| 参数名称 | 参数类型 | 是否必填 |                             备注                             |
| :------: | :------: | :------: | :----------------------------------------------------------: |
|   name   |  String  |    是    |                            图书名                            |
|  author  |  String  |    是    |                             作者                             |
| category |  String  |    是    | 图书类别，小说输入NOVEL，科学输入SCIENCE，历史输入HISTORY，其他则输入OTHER |

| 参数名称 | 参数类型 | 是否必填 |  备注  |
| :------: | :------: | :------: | :----: |
| userName |  String  |    是    | 用户名 |

(4)返回类型：String

|      |        输出        |
| :--: | :----------------: |
|  1   |  没有新增图书权限  |
|  2   | 书名和作者不能为空 |
|  3   |  图书类别不能为空  |
|  4   |     图书已存在     |
|  5   |    图书新增成功    |

4、删除图书接口

(1)URL：/book/delete

(2)请求方式：POST

(3)请求参数

| 参数名称 | 参数类型 | 是否必填 |  备注  |
| :------: | :------: | :------: | :----: |
|  bookId  |   int    |    是    | 图书id |
| userName |  String  |    是    | 用户名 |

(4)返回类型：String

|      |       输出       |
| :--: | :--------------: |
|  1   | 没有删除图书权限 |
|  2   |   图书删除成功   |

5、更新图书接口

(1)URL：/book/update

(2)请求方式：POST

(3)请求参数

| 参数名称 | 参数类型 | 是否必填 |                             备注                             |
| :------: | :------: | :------: | :----------------------------------------------------------: |
|  bookId  |   int    |    是    |                            图书id                            |
|   name   |  String  |    否    |                            图书名                            |
|  author  |  String  |    否    |                             作者                             |
| category |  String  |    否    | 图书类别，小说输入NOVEL，科学输入SCIENCE，历史输入HISTORY，其他则输入OTHER |

| 参数名称 | 参数类型 | 是否必填 |  备注  |
| :------: | :------: | :------: | :----: |
| userName |  String  |    是    | 用户名 |

(4)返回类型：String

|      |       输出       |
| :--: | :--------------: |
|  1   | 没有更新图书权限 |
|  2   |   书id不能为空   |
|  3   |   该图书不存在   |
|  4   |     更新成功     |

6、查询图书接口

(1)URL：/book/query

(2)请求方式：POST

(3)请求参数

| 参数名称 | 参数类型 | 是否必填 |                             备注                             |
| :------: | :------: | :------: | :----------------------------------------------------------: |
|   name   |  String  |    是    |                            图书名                            |
|  author  |  String  |    是    |                             作者                             |
| category |  String  |    是    | 图书类别，小说输入NOVEL，科学输入SCIENCE，历史输入HISTORY，其他则输入OTHER |

(4)返回类型：List，List中对象的参数为

| 参数名称 | 参数类型 |                             备注                             |
| :------: | :------: | :----------------------------------------------------------: |
|  bookId  |   int    |                            图书id                            |
|   name   |  String  |                            图书名                            |
|  author  |  String  |                             作者                             |
| category |  String  | 图书类别，小说输入NOVEL，科学输入SCIENCE，历史输入HISTORY，其他则输入OTHER |

### 三、环境准备

1、MySQL

(1)执行两条建表语句，分别建立用户信息表user_info和图书表library_book

CREATE TABLE `user_info` (
  `user_id` varchar(255) NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `type` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `name` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `library_book` (
  `book_id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `author` varchar(100) NOT NULL,
  `category` varchar(100) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

(2)修改配置文件application.properties

![image-20230810130940205](/Users/dian/Library/Application Support/typora-user-images/image-20230810130940205.png)

2、Redis

(1)下载并安装redis

(2)修改配置文件application.properties，默认ip为127.0.0.1，默认端口号为6379。如不需要使用redis，可在UserServicerImpl.class和BookServicerImpl.class中注释掉相关使用到的缓存语句。

### 四、运行

Eclipse中run ProjectApplication.java

### 五、Swagger2

(1)URL：http://localhost:8080/swagger-ui.html

(2)UI

![image](https://github.com/wuyudian/szhn_subject1/blob/main/imgForReadme/image-20230810132110425.png)
