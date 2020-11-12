# quick-transform

#### 介绍
这是一款基于SpringBoot的返回值数据处理组件,主要目的是用于数据脱敏等返回数据额外处理操作
1.该组件提供默认的注解和对应的处理策略利用AOP,可以便捷的对一些常见敏感数据进行数据修改操作

2.允许用户添加自定义的处理策略对方法返回值进行便携加工

3.项目地址：https://gitee.com/silwings/quick-transform.git


#### 安装教程

1.  将代码install到本地仓库
2.  在项目中引入依赖
    ```
    <dependency>
       <groupId>com.silwings</groupId>
       <artifactId>quick-transform</artifactId>
       <version>1.0-SNAPSHOT</version>
    </dependency>
    ```

#### 使用说明

##### 1.核心注解说明

1. @MethodTransform
   1. 添加了该注解的方法会被AOP操作类检测到,并通过该注解指定的`strategy`的实例进行数据操作
   2. `strategy`的值必须是实现了`TransformStrategy`接口的java类,组件中提供了一些常用的实现,用户可以自己定义.
   3. @MethodTransform注解中提供了一个默认值`TransformStrategy.class`,即`strategy`需要实现的接口的class,表示不指定任何操作策略.这种情况下会检查方法返回值是否包含一个`@Transform`注解.如果包含,则按照类中属性上定义的具体注解进行操作
   4. 如果自己指定`strategy`的值,将不理会返回值上可能存在的`@Transform`注解,而是按照指定的值的实例进行数据处理.可以理解为实体类上的`@Transform`及相关注解配置的策略是默认策略.当不通过`@MethodTransform`注解指定其他处理策略是会使用默认配置,否则使用指定策略
   5. 可以通过`execute`指定是否需要执行,默认为true表示需要执行
2. @Transform
   1. 该注解用于在标记当前实体类存在需要处理的属性
   2. 仅仅是作为一个是否需要遍历类属性列表的标识
   3. 添加该注解的实体类的属性上可以添加`@DataTransform`及其语义化注解,当该注解被应用时,管理器会对类中属性进行遍历寻找属性上包含的`@DataTransform`注解.
3. @DataTransform
   1. 该注解标记需要进行数据处理的属性或方法返回值
   2. 和`@MethodTransform`一样,需要且必须指定一个`strategy`策略来明确数据处理逻辑.
   3. 组件中提供了一些常用的该注解的语义化注解,如有需要可以直接使用语义化注解,而不用去自定义处理策略并指定
   4. 可以通过`execute`指定是否需要执行,默认为true表示需要执行



##### 2.@DataTransform的语义化注解

1. @BankCardTransform: 银行卡处理策略,显示前 6 位 + *(实际位数) + 后 4 位，如：622575******1496
2. @EmailTransform: 邮箱处理策略,展示首个字符和@符号后面的值
3. @FixedPhoneTransform: 固定电话处理策略,固定电话展示后四位
4. @IdCardTransform: 身份证处理策略,隐藏出生日期及其前三位
5. @NameTransform: 名称处理策略,展示姓,隐藏其他
6. @PasswordTransform: 密码处理策略,8位数星号
7. @PhoneTransform: 手机号处理策略,隐藏第4-8位,例:138*****245



##### 3.自定义策略

1. 自定义策略需要实现`com.silwings.transform.strategy.TransformStrategy`接口

2. 该接口包含一个`T transform(T t);`抽象方法,其就是真正执行数据处理的方法.自定义实现类时务必保证该方法的返回值与被指定策略类为当前实现类的方法的返回值类型一致,否则可能发生类型转换异常.

3. 实现类需要添加`@Component`注解注入Spring容器才能正常生效.

4. 使用时直接指定`strategy`为实现类class即可

5. 示例:

   1. 自定义实现

      ```java
      import com.silwings.transform.strategy.TransformStrategy;
      import org.springframework.stereotype.Component;
      
      /**
       * @author CuiYiXiang
       * @Classname MyStrategy
       * @Description
       * @Date 2020/11/8
       */
      @Component
      public class MyStrategy implements TransformStrategy<String> {
          @Override
          public String transform(String s) {
              return "自定义字符串" + s;
          }
      }
      ```

      

   2. 使用自定义策略

      ```java
      import com.silwings.goods.strategy.MyStrategy;
      import com.silwings.transform.annotation.DataTransform;
      import com.silwings.transform.annotation.MethodTransform;
      import org.springframework.web.bind.annotation.GetMapping;
      import org.springframework.web.bind.annotation.RestController;
      
      /**
       * @author CuiYiXiang
       * @Classname Demo
       * @Description
       * @Date 2020/11/8
       */
      @RestController
      public class Demo {
      
          @GetMapping("/demo")
          @MethodTransform(strategy = MyStrategy.class)
      //    @DataTransform(strategy = MyStrategy.class)
          public String demo() {
              return "Hello Word";
          }
      
      }
      ```

      

   3. 结果参考

      ```
      原结果:Hello Word
      现结果:自定义字符串Hello Word
      ```

##### 

##### 4.自定义模糊字符

1. 组件默认的模糊字符是`*`,需要时使用等长度的星号字符串对原数据替换
2. 该组件支持对该默认字符进行自定义修改,在配置文件配置transform.replaceSymbol即可.接收一个字符串,但只要首个字符会被使用
3. 注意:部分字符在Spring中不支持,如&



#### 后记:

​		非常感谢你能看到这里,希望这个小组件对你有所帮助.非常期待您的评论与反馈

