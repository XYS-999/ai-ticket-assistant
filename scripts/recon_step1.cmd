@echo off
setlocal
REM ============================================================
REM Step1 Read-only Recon（只读摸底）快照脚本（CMD 可直接运行）
REM 目标：每个 PR 开始前确认：目录/分支/工作区/远端/结构/CI/health 线索
REM 注意：本脚本不修改业务代码；git fetch 只更新远端引用（不改工作区文件）
REM ============================================================

REM [0] 设置控制台为 UTF-8 代码页：减少中文/UTF-8 输出乱码风险
chcp 65001 >nul

echo.
echo ========= [A] ENVIRONMENT SNAPSHOT =========
REM 输出当前目录：确认在仓库根目录执行
echo [PWD]
cd
REM 输出系统版本：确认 Windows 版本信息
ver
REM 输出当前用户：排查权限差异
whoami

echo.
echo ========= [B] GIT STATUS =========
REM 输出 git 版本：确认 git 可用
git --version
REM 输出工作区状态：必须无未提交修改，才能开始新 PR
git status

echo.
echo ========= [C] GIT REMOTE and BRANCH =========
REM 输出远端地址：确认 origin 指向正确仓库
git remote -v
REM 输出当前分支与跟踪关系：确认是否在 main 且跟踪 origin/main
git branch -vv

echo.
echo ========= [D] GIT FETCH (READ-ONLY) =========
REM 拉取远端引用并清理失效引用：不改工作区文件，只更新引用
git fetch --prune

echo.
echo ========= [E] LAST COMMIT =========
REM 输出最近 1 条提交：确认当前 HEAD
git log -1 --oneline

echo.
echo ========= [F] REPO STRUCTURE =========
REM 列出仓库根目录内容：确认 backend/.github/memory-bank/scripts 等目录存在
dir

echo.
echo ---- [backend] ----
REM 列出 backend 目录：定位 pom.xml / mvnw.cmd / src 等
if exist backend (
  dir backend
) else (
  echo ERROR: backend directory NOT found
)

echo.
echo ---- [.github\workflows] ----
REM 列出 workflow 文件名
if exist .github\workflows (
  dir .github\workflows
) else (
  echo ERROR: .github\workflows NOT found
)

echo.
echo ---- [memory-bank] ----
REM 列出真相源文件：确认 PRD/TechDesign/SOP/QualityGate/ENV 存在
if exist memory-bank (
  dir memory-bank
) else (
  echo ERROR: memory-bank directory NOT found
)

echo.
echo ========= [G] CI WORKFLOW CONTENT (READ-ONLY) =========
REM 输出 CI yml 内容：确认 CI（Continuous Integration，持续集成）具体执行命令
if exist .github\workflows\*.yml (
  type .github\workflows\*.yml
) else (
  echo WARN: no workflow yml found
)

echo.
echo ========= [H] FIND EXISTING HEALTH ENDPOINT =========
REM 使用 PowerShell 搜索 /api/health：比 findstr 更稳（支持 UTF-8/中文），只读输出定位信息
REM 预期：打印出包含 "/api/health" 的文件路径与行号
if exist backend\src\main\java (
  powershell -NoProfile -Command ^
    "Get-ChildItem -Recurse -Filter *.java backend\src\main\java | Select-String -SimpleMatch '/api/health' | ForEach-Object { '{0}:{1}:{2}' -f $_.Path,$_.LineNumber,$_.Line.Trim() }"
) else (
  echo WARN: backend\src\main\java NOT found, skip search
)

echo.
echo ========= [I] BUILD FILES EXISTS? =========
REM 检查 pom.xml 是否存在：确认 Maven 构建入口
if exist backend\pom.xml (
  echo OK: backend\pom.xml exists
) else (
  echo ERROR: backend\pom.xml NOT found
)
REM 检查 Maven Wrapper 是否存在：后续验收建议用 mvnw.cmd 保证一致性
if exist backend\mvnw.cmd (
  echo OK: backend\mvnw.cmd exists
) else (
  echo WARN: backend\mvnw.cmd NOT found (may rely on global mvn)
)

echo.
echo ========= DONE: Step1 Read-only Recon =========
endlocal
exit /b 0
