package com.xyss.aiticketassistant.interfaces.api;

/*
 * package 逐行解释：
 * 1) com.xyss.aiticketassistant 是项目基础包名（通常用公司/组织域名反写 + 项目名）。
 * 2) interfaces 是“对外输入边界”层（输入适配器），不是 Java interface 关键字的意思。
 * 3) api 表示这里放 HTTP API 相关入口代码。
 *
 * 架构角度：
 * - 这个类属于 Controller 层（接口层）。
 * - 放在 interfaces 包，是为了表达“它是外部请求进入系统的入口”，
 *   业务逻辑应继续下沉到 application/domain 层，而不是堆在 Controller 里。
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @RestController = @Controller + @ResponseBody
 * 含义：这个类是 REST 控制器，方法返回值会直接写入 HTTP 响应体（通常是 JSON），
 * 而不是返回一个页面模板。
 */
@RestController

/*
 * @RequestMapping("/api")：类级别路由前缀。
 * 本类所有接口都会自动带上 /api 前缀，便于统一 API 命名空间。
 * GET/POST/PUT/DELETE 是 Request 里的“Method（方法）”字段。
 * @RequestMapping 是通用映射注解，可映射路径、方法、参数等。
 */
@RequestMapping("/api")
public class HealthController {

    /*
     * 函数意义（这个“函数”就是一个接口处理方法）：
     * - 对应 GET /api/health 健康检查端点。
     * - 用于最小可用探活：告诉调用方“服务进程是活的”。
     * - 设计上保持无副作用、无入参、固定输出，便于测试与 CI 稳定验证。
     *
     * 语法说明：
     * - @GetMapping("/health")：只匹配 HTTP GET + 子路径 /health。
     * - public：对 Spring 框架可见，允许被路由调用。
     * - HealthResponse：返回类型是一个结构化对象，不是裸字符串，便于扩展字段。
     */
    @GetMapping("/health")
    public HealthResponse health() {
        // 返回状态 "UP"（常见语义：服务可用）。
        return new HealthResponse("UP");
    }

    /*
     * record 是 Java 的轻量数据载体类型（不可变）。
     * 这里的 HealthResponse(String status) 表示响应只有一个字段 status。
     * Spring 会自动序列化为 JSON：{"status":"UP"}
     *
     * 为什么不用普通 class：
     * - 这个对象只承载数据，没有复杂行为。
     * - record 更简洁，减少样板代码（构造器/getter/equals/hashCode/toString）。
     */
    public record HealthResponse(String status) {
    }
}
