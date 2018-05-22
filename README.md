# 诱人的JWT
## 一、新建springboot项目
    主要依赖：
    h2、security、web、jpa
## 二、新建Git项目
    git init .
    git add .
    git commit -m 'first commit'
    git remote add origin https://github.com/wuyanshen/springbootjwt.git
    git push origin master
## 三、运行项目
    cd security_jwt
    mvn spring-boot:run -D maven.test.skip=true
### 1.向内存数据库h2注册用户
    localhost:8088/user/regist
    POST 请求参数
        {
            "username":"jack",
            "password":"123"
        }
### 2.登录，成功后会获取到token
    localhost:8088/login
    POST 请求参数
        {
            "username":"jack",
            "password":"123"
        }
### 3.访问目标api
    localhost:8088/user/hello
    get 请求参数
    Authorization  Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYWNrIiwiZXhwIjoxNTI3MjI1Mjk2fQ.nRWQArkDdsOuXthGkURZMqp4jmbmGNpL_xWcs3b4aSfM1BcqnHUJQ9f79wYND2ZlpETAC7FUNgvCOIhGxBlelw
    获取到结果：
    hello world!

## 四、参考地址
   [用JWT技术为SpringBoot的API增加授权保护](https://blog.csdn.net/haiyan_qi/article/details/77373900)
    