package com.xyss.aiticketassistant;

/*
 * package 逐行解释：
 * 1) com.xyss.aiticketassistant 是项目基础包名（公司/组织域名反写 + 项目标识）。
 * 2) 启动类放在“根包”下，是 Spring Boot 的常见约定。
 *
 * 为什么放根包：
 * - @SpringBootApplication 默认从当前包开始做组件扫描（Component Scan）。
 * - 放根包可以覆盖子包（例如 interfaces.api），避免扫描不到 Controller/Service。
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * @SpringBootApplication 是一个“组合注解”，核心包含：
 * 1) @SpringBootConfiguration：声明这是 Spring Boot 配置类。
 * 2) @EnableAutoConfiguration：启用自动配置（按依赖和配置自动装配 Bean）。
 * 3) @ComponentScan：扫描当前包及子包里的 Spring 组件。
 *
 * 架构意义：
 * - 这是应用启动入口，不承载业务逻辑。
 * - 业务代码应放到 Controller/Service/Domain 等层，不应写在启动类里。
 */
@SpringBootApplication
public class AiTicketAssistantApplication {

    /*
     * main 是 Java 程序标准入口函数。
     * 语法拆解：
     * - public：JVM（Java Virtual Machine，Java 虚拟机）可从外部访问这个方法。
     * - static：无需创建对象即可调用。
     * - void：无返回值。
     * - String[] args：接收命令行参数（例如 --server.port=8081）。
     *
     * 为什么要这么写：
     * - 这是 JVM 识别应用入口的固定签名。
     * - 交给 SpringApplication.run 后，Spring 容器会完成启动流程。
     */
    public static void main(String[] args) {
        /*
         * SpringApplication.run(当前启动类, 命令行参数) 会做这些事：
         * 1) 创建并刷新 ApplicationContext（应用上下文/IOC 容器）。
         * 2) 执行自动配置，注册 Bean。
         * 3) 启动内嵌 Web 服务器（当前项目是 Tomcat）。
         * 4) 挂载 Controller 路由（如 /api/health）。
         */
        SpringApplication.run(AiTicketAssistantApplication.class, args);
    }
}
