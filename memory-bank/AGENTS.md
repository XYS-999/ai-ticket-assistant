# AGENTS（协作规则：每次请严格遵守）

## 0. 真相源（Single Source of Truth）
本项目以 memory-bank 内文档为准：
- PRD.md：需求与范围
- TechDesign.md：技术设计与接口/表结构
- Architecture.mmd：架构图（模块与关系）
- ENV.md：环境/版本记录

## 1. 开始任何编码前的必做检查（Always）
- 先确认当前要做的功能属于 PRD 的 MVP
- 先确认 TechDesign 已写清：分层、接口、数据表
- 任何新增/变更：先改文档，再改代码

## 2. 输出与代码约束（防止 AI 失控）
- 一次只做一个“最小可验证改动”
- 任何步骤都必须能运行/能回退（Git 提交）
- 采用分层：Controller / Service / Repository，不写巨石文件
- 先用成熟组件再拼接（胶水编程），不从零造轮子

## 协作硬规则（补充）
- 先只读摸底：任何实现前必须先输出“现状摘要 + 文件级计划 + 验收点”，再开始改动
- 改动白名单：默认只允许改 backend/、memory-bank/、.github/（超出需先在计划中说明原因与风险）
- VS Code 扩展约束：只允许修改当前打开文件；跨文件批量改动优先使用 Codex 并先给计划
- 命令规范：后续任何命令必须带注释，说明用途与要获取的信息
- 相关真相源：
  - 流程：`memory-bank/SOP_Workflow.md`
  - 门禁：`memory-bank/CHECKLIST_QualityGate.md`
  - 环境：`memory-bank/ENV.md`
