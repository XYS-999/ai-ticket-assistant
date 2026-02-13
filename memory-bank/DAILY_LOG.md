# DAILY_LOG（事实账本：给 AI/协作者看的）

## 规则
- 只追加（append-only）
- 只写两类条目：PR / 技术栈学习
- 每条必须有“验证证据”（命令/入口/结果）；没有验证就写“未验证”
- 不写过程感想、不写长解释

---

## YYYY-MM-DD（周X）

### PR（有就写，没有就省略）
- PR-?? <标题>
  - Done：<一句话说明做成了什么>
  - Verified：<如何验证：命令/入口>
  - Result：PASS/FAIL（或未验证）
  - Notes：<1句风险/遗留（可选）>

### 技术栈学习（有就写，没有就省略）
- <主题1>：<掌握到可执行层面的一句话>｜Verified：<命令/入口>｜Result：PASS/未验证
- <主题2>：...

## 2026-02-12（周四）

### 技术栈学习
- Git 忽略目录（.gitignore）：对 untracked 的 refs/ 用 `refs/` 忽略规则即可｜Verified：`git status`｜Result：PASS
- Node.js + npm：已安装并确认版本｜Verified：`node -v` / `npm -v`｜Result：PASS
- VS Code 提交推送（中文界面）：可用“源代码管理→暂存→提交→同步更改”完成推送｜Verified：一次提交并同步更改（手动）｜Result：PASS

## 2026-02-13 （周五）
- PR-001：backend Spring Boot 骨架 + /api/health + CI（GitHub Actions）
- Toolchain：安装/配置 Java 21（Temurin），补齐 JAVA_HOME 与 PATH（详见 memory-bank/ENV.md）
- Verified：
  - `cd backend` # 用途：进入后端工程目录；获取信息：确保后续 mvnw/pom 解析在正确目录
  - `mvnw.cmd -B test` # 用途：执行到 test 阶段并运行单元测试；获取信息：BUILD SUCCESS/测试通过
  - `mvnw.cmd spring-boot:run` # 用途：启动 Spring Boot 应用；获取信息：Started AiTicketAssistantApplication... 日志
  - `curl http://localhost:8080/api/health` # 用途：验证健康检查接口；获取信息：应返回 {"status":"UP"}
- Result：PASS
