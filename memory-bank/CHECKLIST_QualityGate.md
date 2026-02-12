# CHECKLIST_QualityGate（PR 合并门禁清单）

## 使用规则
1. 每个 PR 必须在合并前完成本清单勾选。
2. 清单证据必须可复现：写出 Windows CMD 命令 / Swagger 入口 / 截图（可选）。
3. 若某项不适用，必须标注 N/A（Not Applicable，不适用）并说明原因。
4. 本清单与 memory-bank/SOP_Workflow.md 一致；若冲突，以 SOP 为准并更新本清单。

---

## 0) Scope（范围控制）
- [ ] 本 PR 只做一个主题（功能/配置/文档/测试之一），无顺手重构
- [ ] 改动仅发生在白名单目录内（src/、memory-bank/、.github/、scripts/、docker/ 等）
- [ ] refs/（本地参考目录）未被提交（若存在）

证据（可选）：`git status` / 文件列表截图

---

## 1) Build（构建）
- [ ] 能在本机成功构建（至少一次）
- [ ] 若引入新依赖/插件，已在文档中记录（ENV.md/README，按需）

证据（填写命令与结果）：
- Command：
  - `...`
- Result：PASS/FAIL

---

## 2) Test（测试）
- [ ] 至少新增/更新 1 条测试或明确 N/A（原因：纯文档/纯配置）
- [ ] 关键路径（成功路径）有测试覆盖（单元测试/集成测试二选一）

证据：
- Command：
  - `...`
- Result：PASS/FAIL/N/A（原因：...）

---

## 3) Run（可运行/可演示）
- [ ] 服务可启动（或可在本 PR 的范围内被验证）
- [ ] 若提供接口：Swagger UI 可看到并能调用（或用 curl 验证）
- [ ] 若有鉴权：已验证携带 Authorization: Bearer <JWT> 可调用受保护接口（如适用）

证据：
- Entry / Command：
  - `...`（Swagger URL 或 CMD）
- Result：PASS/FAIL/N/A（原因：...）

---

## 4) Observability（可观测：日志/审计/错误）
- [ ] 日志具备最小可用信息（至少包含：请求/关键动作/错误）
- [ ] 异常路径可定位（有错误码/错误信息，不吞异常）
- [ ] 若本 PR 涉及关键操作（登录/创建/状态变更等）：审计日志策略已考虑或明确 N/A

证据：
- 现象（简述）：...
- Result：PASS/FAIL/N/A（原因：...）

---

## 5) Data & Config（数据与配置变更）
- [ ] 若涉及数据库表/字段：TechDesign.md 已同步（或在本 PR 描述中说明）
- [ ] 若涉及环境/版本：ENV.md 已同步
- [ ] 若新增配置项：有默认值/说明/示例（README 或 ENV）

证据：
- 变更点：...
- Result：PASS/FAIL/N/A（原因：...）

---

## 6) Docs（文档与证据链）
- [ ] PR 描述包含：What / Why / How to verify / Risk & rollback
- [ ] DAILY_LOG.md 已追加今日条目（只写事实：Done/Verified/Result）
- [ ] 与本 PR 相关的 memory-bank 文档已同步（PRD/TechDesign/Architecture/AGENTS/SOP/ENV 等按需）

证据：
- 链接/说明：...

---

## 7) Release Safety（回滚与风险控制）
- [ ] 已写明风险点（至少 1 条）
- [ ] 已写明回滚方式（至少 1 条：恢复文件/回退提交/关闭配置开关等）
- [ ] 若引入破坏性变更：已明确迁移策略或分阶段方案

证据：
- Risk：...
- Rollback：...

---

## 8) Final Decision（最终结论）
- [ ] 允许合并（以上关键项 PASS 或合理 N/A）
- [ ] 不允许合并（列出阻塞项）：
  - ...
