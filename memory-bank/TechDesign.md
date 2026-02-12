# TechDesign（最小可执行版本）

## 1. 技术栈（先占位，后续补版本到 ENV.md）
- Java + Spring Boot
- MySQL
- Redis（占位）
- Docker Compose（占位）
- OpenAPI/Swagger（占位）

## 2. 分层与目录约定（必须遵守）
- Controller：只做参数接收与校验，不写业务逻辑
- Service：业务逻辑与流程编排
- Repository：只做数据库访问
- DTO：接口入参/出参对象（先占位）

## 3. 模块划分（第1个月范围）
- Auth（第2周实现）
- User（第2周实现）
- Ticket（第1-2周实现）
- Audit（第2周实现）
- AI Assistant（第3周实现）

## 4. API 草案（先列名字，后续补字段）
- POST /api/tickets            创建工单
- GET  /api/tickets/{id}       查询工单详情
- GET  /api/tickets            列表查询（page,size,status,q,createdAtFrom,createdAtTo；ownerUserId/assigneeUserId 仅 Admin）
- POST /api/tickets/{id}/comments  添加评论
- POST /api/tickets/{id}/transition 状态流转（占位）
- POST /api/tickets/{id}/assign      指派/改派处理人（Admin）
- POST /api/auth/login          登录（用户名+密码，返回 JWT）
- POST /api/auth/logout         登出（JWT jti 写入黑名单，占位）


说明（v0）：
- assign 请求体：{ "assigneeUserId": 123 }
- transition 请求体：{ "toStatus": "IN_PROGRESS" }


## 4.5 授权模型（资源权限：Owner/Assignee/Admin）

主体定义：
- Owner：工单创建人
- Assignee：当前处理人（可为空，未分配）
- Admin：管理员（拥有全局权限）

规则（v0）：
- 查看工单：
  - Owner 可查看自己的工单
  - Assignee 可查看分配给自己的工单
  - Admin 可查看全部工单
- 评论/补充信息：
  - Owner/Assignee/Admin 均可对可见工单发表评论
- 指派处理人（assign）：
  - Admin 可指派/改派 Assignee
- 状态流转（transition）：
  - Assignee/Admin 可流转工单状态
  - Owner 可执行“确认关闭”（从待确认到关闭，细化在状态机章节）
- 权限校验落点：
  - 所有写操作在 Service 层统一做资源权限校验
  - 所有关键操作写入审计日志（谁对哪条 ticket 做了什么）

Admin 判定（v0）：
- 通过 user_role -> role.code 是否包含 "ADMIN" 判定管理员身份（登录后可缓存到 JWT claims，占位）


## 4.6 状态机（Ticket Status v0）

状态枚举：
- NEW：新建/待受理
- IN_PROGRESS：处理中
- WAITING_CONFIRM：待确认
- CLOSED：已关闭

允许的状态流转（v0）：
- NEW -> IN_PROGRESS
- IN_PROGRESS -> WAITING_CONFIRM
- WAITING_CONFIRM -> CLOSED

角色/主体与流转权限（结合资源权限模型）：
- Assignee/Admin：
  - 可执行 NEW -> IN_PROGRESS
  - 可执行 IN_PROGRESS -> WAITING_CONFIRM
- Owner：
  - 可执行 WAITING_CONFIRM -> CLOSED（确认关闭）
- Admin：
  - 拥有所有流转权限（兜底）

审计要求：
- 每次状态流转必须写入审计日志：ticket_id、from_status、to_status、operator、time



## 5. 数据库表草案（先列表名，后续补字段）
- user
- role
- permission
- user_role
- role_permission
- ticket（最小字段占位）
  - id
  - title
  - description
  - status
  - owner_user_id
  - assignee_user_id（nullable）
  - created_at
  - updated_at
  - version（乐观锁）
  - category（枚举：ACCOUNT / NETWORK / SOFTWARE / PERMISSION / HARDWARE / OTHER）
  - priority（枚举：HIGH / MEDIUM / LOW）



- indexes（占位）
  - idx_ticket_status
  - idx_ticket_owner_user_id
  - idx_ticket_assignee_user_id
  - idx_ticket_created_at


- ticket_comment
- audit_log（最小字段占位，严格审计 v0）
  - id
  - biz_type（如 TICKET / AUTH / PERMISSION）
  - biz_id（如 ticket_id）
  - action（如 CREATE_TICKET / ASSIGN / TRANSITION / LOGIN_SUCCESS / LOGIN_FAIL / PERMISSION_CHANGE）
  - operator_user_id
  - operator_ip（占位）
  - trace_id（占位，用于链路追踪）
  - before_json（nullable，占位）
  - after_json（nullable，占位）
  - created_at
- role（最小字段占位）
  - id
  - code（unique，如 ADMIN / AGENT）
  - name
  - status（占位）
  - created_at
  - updated_at

- permission（最小字段占位）
  - id
  - code（unique，如 TICKET_READ / TICKET_ASSIGN / AUDIT_READ）
  - name
  - created_at
  - updated_at

- user_role（最小字段占位）
  - id
  - user_id
  - role_id
  - created_at

- role_permission（最小字段占位）
  - id
  - role_id
  - permission_id
  - created_at





## 6. 非功能约定（先写规则）
- 所有关键操作必须写审计日志（第2周落地）
- 所有 API 需要权限控制（第2周落地）
- 分页策略（v0）：
  - 列表查询采用 offset 分页：page + size
  - 深分页性能风险已知：二期升级为游标分页（cursor 基于 created_at + id）
- 幂等（v0，工单创建）：
  - 客户端在创建工单时传 Idempotency-Key（header）
  - 服务端使用 Redis 记录 (operator_user_id + Idempotency-Key) -> ticket_id，TTL 10 分钟（占位）
  - 若重复请求命中缓存：直接返回已创建的 ticket_id，避免重复工单
- 统一响应与错误码（v0）：
  - 成功响应：{ "code": 0, "msg": "ok", "data": ... }
  - 失败响应：{ "code": "MODULE_XXXX", "msg": "错误信息", "data": null }
  - HTTP Status 约定：
    - 400：参数校验失败
    - 401：未登录/Token 无效
    - 403：无权限（资源权限校验失败）
    - 404：资源不存在（ticket 不存在）
    - 409：状态冲突（非法状态流转/并发冲突占位）
    - 500：服务端异常
  - 错误码命名：
    - AUTH_xxxx：认证鉴权
    - TICKET_xxxx：工单业务
    - AUDIT_xxxx：审计相关
- 并发更新冲突（v0）：
  - ticket 的关键写操作（assign/transition/update）使用乐观锁 version
  - 冲突返回 HTTP 409 + 错误码 TICKET_CONFLICT（占位）
- 审计写入策略（v0）：
  - v0 采用同步写库（audit_log 与业务操作同请求完成）
  - 二期演进：事件驱动异步审计（队列/事件总线，占位），支持失败重试与幂等
- Redis 使用范围（v0）：
  - 幂等：Idempotency-Key -> ticket_id（TTL 10 分钟，占位）
  - JWT 黑名单（登出失效，占位）：
    - JWT 生成包含 jti
    - 登出时写入 redis：blacklist:{jti} = 1，TTL = token 剩余有效期
    - 请求鉴权时若命中黑名单则视为无效 token（401）

- JWT Claims（v0）：
  - 登录时查询用户角色（role.code），写入 JWT claims（roles）
  - 角色变更一致性（占位）：
    - v0 通过较短 token TTL + Redis 黑名单（强制登出/失效）降低风险
- 密码存储（v0）：
  - user.password_hash 使用 BCrypt 哈希（不存明文）
  - 登录校验使用 Spring Security 的 BCryptPasswordEncoder（占位）
- 初始化数据（dev，v0）：
  - 提供种子数据：ADMIN 用户、普通用户、role/user_role 基础数据、示例 ticket
  - 方式占位：SQL 脚本（resources/db/init.sql）或启动时初始化（仅 dev profile）
  - 默认账号仅用于本地演示，生产禁用（占位）
- 日志规范（v0）：
  - 每个请求生成/透传 traceId（header 优先，缺失则生成），写入 MDC
  - 应用日志统一输出 traceId，便于排查与与审计表关联（audit_log.trace_id）
  - 关键业务事件采用结构化字段记录（如 operator_user_id、ticket_id、action）
- 运维与健康检查（v0）：
  - 引入 Spring Boot Actuator
  - 暴露端点（v0）：/actuator/health、/actuator/info、/actuator/metrics
  - 安全策略（占位）：生产环境限制访问（内网/鉴权），避免敏感信息泄露
- 部署方式（v0）：
  - 使用 Docker Compose 一键启动：MySQL、Redis、应用服务
  - 应用提供 Dockerfile（占位）
  - 配置分环境（占位）：dev/prod，敏感信息通过环境变量注入
- 配置管理（v0）：
  - 使用 Spring Boot profiles：application.yml + application-dev.yml + application-prod.yml（占位）
  - Docker Compose 通过环境变量注入敏感配置（如 DB/Redis 密码）（占位）
  - 生产禁用默认账号与调试配置（占位）
- 测试策略（v0）：
  - 单元测试（Service）：覆盖资源权限校验、状态机非法流转、乐观锁冲突（占位）
  - 集成测试（MockMvc）：覆盖未登录 401、无权限 403、创建工单成功返回（占位）
- 接口文档与演示（v0）：
  - 使用 OpenAPI/Swagger 生成接口文档
  - README 提供最小可复现演示脚本（curl）：
    - login -> create ticket -> assign -> transition -> query audit_log（占位）

## 7. 开工前关键决策（v0）
- DB：MyBatis-Plus
- Security：Spring Security Filter Chain + JWT
- 事务：create/assign/transition 与 audit_log 同事务（同步写库）
- 脱敏（v0 最小集合）：password/token 不写日志；疑似手机号/身份证字段打码（占位规则）
- 工程结构：单体分层（controller/service/repository），业务按模块分包（auth/user/ticket/audit/infra）
