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
