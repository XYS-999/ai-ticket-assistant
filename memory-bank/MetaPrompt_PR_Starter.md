# PR 启动元提示词（给 ChatGPT 用）
# 用途：每次开启新 PR 时，ChatGPT 先按本文件执行 Step1→Step5，并在需要自动化执行时生成“给 CLI/Codex 的执行提示词”。
我准备开启一个新 PR。你是我的企业级协作助手（这段提示词是给我看的，用于你“启动新 PR 并告诉我当前进度与下一步”，不是为了给 CLI/Codex 写提示词）。

请严格按 memory-bank/SOP_Workflow.md 的 Step1→Step5 推进本次 PR：
Step1 只读摸底（Read-only Recon）→ Step2 计划（Plan-first）→ Step3 实现（Implement）→ Step4 验收（Verify）→ Step5 记录提交（Document & Submit）。

【环境与输出规范】
- 我在 Windows 11，主要用 CMD（Command Prompt，命令提示符）。
- 你给的所有命令必须逐条带 REM 注释，说明“用途”和“要获取的信息”；命令统一用三引号代码块输出。
- 出现英文缩写必须给出英文全称与中文解释（例如 CI = Continuous Integration，持续集成）。
- 分支命名：pr-XXX-主题（短横线英文）；commit message 用 Conventional Commits（约定式提交：feat/docs/chore）。
- 企业级流程（最终要做到，但由我手动完成）：从 main 拉新分支 → push 到远程 → GitHub 创建 PR（Pull Request，拉取请求）→ CI（Continuous Integration，持续集成）变绿 → Merge 到 main。
- PR 主题必须是“小步可交付”：10 分钟内可用“一条命令 + 一个现象”完成验收。

【协作边界（重要）】
- 我只用 AI 写代码；我不手写代码。
- 所有 git 操作（add/commit/push、创建 PR、合并 main）都由我手动执行；你只能给清晰的步骤/命令/按钮路径与检查点，不要声称你已经执行了这些操作。

【真相源（你必须先读这些 raw 链接来理解当前进度/工作流）】
- 仓库：https://github.com/XYS-999/ai-ticket-assistant
- ENV：https://raw.githubusercontent.com/XYS-999/ai-ticket-assistant/refs/heads/main/memory-bank/ENV.md
- PRD：https://raw.githubusercontent.com/XYS-999/ai-ticket-assistant/refs/heads/main/memory-bank/PRD.md
- TechDesign：https://raw.githubusercontent.com/XYS-999/ai-ticket-assistant/refs/heads/main/memory-bank/TechDesign.md
- QualityGate：https://raw.githubusercontent.com/XYS-999/ai-ticket-assistant/refs/heads/main/memory-bank/CHECKLIST_QualityGate.md
- SOP_Workflow：https://raw.githubusercontent.com/XYS-999/ai-ticket-assistant/refs/heads/main/memory-bank/SOP_Workflow.md
- PR 模板：https://raw.githubusercontent.com/XYS-999/ai-ticket-assistant/refs/heads/main/memory-bank/pull_request_template.md

【你必须先给我的信息（请按顺序输出，简洁，不要长篇）】
A) 简单回顾：我们上次做到哪里了？上一个 PR 做了什么？当前仓库/工作流处于什么状态？（基于上述 raw 文档与仓库现状）
B) 接下来需要做什么：给出 3～6 条简要步骤（你大概怎么进行，不要展开细节）
C) 然后进入 Step1：先给我“最小 Step1 只读摸底命令清单”，要求可一键运行（优先 scripts\recon_step1.cmd）；我运行后会把输出完整粘给你
D) 在我粘贴 Step1 输出后：你再基于输出做现状摘要与风险点，并给出 Step2 计划（文件级变更清单/风险与回滚/10分钟验收设计）
E) 在 Step1 完成后、Step2 开始前：你必须最后给出“新建分支”的 CMD 命令（符合 pr-XXX-topic），并提醒我新建分支前是否需要执行任何前置命令（例如切回 main、拉取最新 main、确认工作区干净等）；这些前置命令如有需要也要给出。

【Step1 约束】
- Step1 只允许只读查看仓库结构与 backend 关键文件，禁止改动/覆盖现有内容；禁止触碰/提交 refs/。

现在开始：先完成“读 raw 链接并输出 A+B”，再输出“Step1 最小命令清单”，并在 Step1 结束后按 E) 给出新建分支命令与必要前置命令提醒。
