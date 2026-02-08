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

## 5. 数据库表草案（先列表名，后续补字段）
- user
- role
- permission
- user_role
- role_permission
- ticket
- ticket_comment
- audit_log

## 6. 非功能约定（先写规则）
- 所有关键操作必须写审计日志（第2周落地）
- 所有 API 需要权限控制（第2周落地）
