package com.xyss.aiticketassistant.interfaces.api;

/*
 * 这是 Web 层测试类，目标是只验证 HealthController 的 HTTP 行为。
 * 不测数据库、不测第三方依赖，只测“请求进来 -> 响应出去”是否正确。
 */

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * @WebMvcTest(HealthController.class) 选型说明：
 * 1) 这是“测试切片（slice test）”，只加载 Spring MVC 相关组件。
 * 2) 参数 HealthController.class 表示本测试只关注这个 Controller。
 * 3) 优点：启动快、失败定位清晰、CI 执行成本低。
 * 4) 局限：不会加载完整业务上下文（例如数据库、完整 Service 链路）。
 *
 * 常见替代：
 * - @SpringBootTest：加载整个应用上下文，更接近真实环境，但更慢。
 * - 这里选 @WebMvcTest，是因为当前需求仅验证 /api/health 接口返回。
 */
@WebMvcTest(HealthController.class)
class HealthControllerTest {

    /*
     * @Autowired：让 Spring 把 MockMvc 实例注入进来。
     * MockMvc：模拟 HTTP 请求/响应的测试工具，不需要真实启动端口。
     *
     * 为什么选 MockMvc：
     * 1) 快：不走真实网络栈。
     * 2) 稳：不依赖端口占用。
     * 3) 易断言：可直接断言状态码、响应头、响应体 JSON。
     */
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnHealthStatusUp() throws Exception {
        /*
         * 下面逐行按“实际类型”解释，不抽象：
         *
         * 1) get("/api/health")
         *    - 调用的是 MockMvcRequestBuilders.get(...)
         *    - 返回类型：MockHttpServletRequestBuilder
         *    - 含义：先造一个“GET /api/health”的请求对象（还没发出去）。
         *
         * 2) mockMvc.perform(上面的请求对象)
         *    - 调用的是 MockMvc.perform(RequestBuilder requestBuilder)
         *    - 返回类型：ResultActions
         *    - 含义：真正执行这次模拟请求，并拿到“可继续写断言”的结果对象。
         *
         * 3) .andExpect(status().isOk())
         *    - 先看里面：status().isOk() 返回一个 ResultMatcher
         *      含义：这是一个“检查器”，专门检查响应状态码是否为 200。
         *    - 再看外面：ResultActions.andExpect(ResultMatcher matcher)
         *      含义：把这个“状态码检查器”应用到当前响应结果上。
         *
         * 4) .andExpect(content().json("{\"status\":\"UP\"}"))
         *    - content().json(...) 也会返回一个 ResultMatcher
         *      含义：这是另一个“检查器”，检查响应体 JSON 是否等于 {"status":"UP"}。
         *    - 再次调用 andExpect(...)：把“JSON 检查器”应用到同一次响应结果上。
         *
         * 所以这段链式调用不是“魔法”，本质是：
         * - 先执行请求，拿到 ResultActions
         * - 再连续调用 andExpect(...)，每次塞一个 ResultMatcher 做校验
         */
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"UP\"}"));
    }
}
