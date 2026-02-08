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
- GET  /api/tickets            列表查询（分页/筛选占位）
- POST /api/tickets/{id}/comments  添加评论
- POST /api/tickets/{id}/transition 状态流转（占位）

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

- ticket_comment
- audit_log

## 6. 非功能约定（先写规则）
- 所有关键操作必须写审计日志（第2周落地）
- 所有 API 需要权限控制（第2周落地）
