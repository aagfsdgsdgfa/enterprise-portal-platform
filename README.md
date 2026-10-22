# NovaTech 企业门户平台（enterprise-portal-platform）

![Java](https://img.shields.io/badge/Java-8-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.5-green) ![Vue](https://img.shields.io/badge/Vue-2%20%2F%203-brightgreen) ![TailwindCSS](https://img.shields.io/badge/Tailwind-3-06b6d4) ![uni-app](https://img.shields.io/badge/uni--app-3-blue) ![MySQL](https://img.shields.io/badge/MySQL-teal)

企业数字化门户平台：**官网门户站点 + 内容管理后台 + 移动端 App** 三端一体的 monorepo。管理端基于 RuoYi-Vue 二次开发，门户站点为 Vue3 + Vite + TailwindCSS 独立实现，支持企业信息、新闻资讯、产品、招聘、成功案例、资料下载、留言线索等全流程内容运营。

## 项目结构

```
├── backend/     # Spring Boot 后端（RuoYi-Vue 3.9.0：内容管理、线索收集、账号体系）
├── frontend/    # 管理后台（Vue2 + Element UI，基于 ruoyi-ui 改造）
├── portal/      # 官网门户站点（Vue3.5 + Vite + TailwindCSS + Vue Router）
├── app/         # 移动端（uni-app 多端）
└── sql/         # 数据库脚本（portal-db.sql 表结构 + portal-data.sql 演示种子数据）
```

## 功能亮点

- **三端一体**：一套后端支撑管理后台、响应式官网、移动 App，统一内容模型
- **官网门户**：首页轮播/产品矩阵/新闻资讯/招聘岗位/成功案例/资料下载/在线留言，SEO 友好（TDK/关键词由后台配置）
- **内容运营闭环**：企业信息、联系方式、ICP 备案等站点基础信息后台可配，前台 Footer/About 实时渲染
- **线索收集**：资料下载留资、留言咨询自动入库，后台可跟进
- **地图与坐标**：企业坐标后台维护，前台地图组件展示

## 技术栈

| 模块 | 技术 |
|---|---|
| backend | Spring Boot + MyBatis + Druid + Redis + JWT（RuoYi-Vue 3.9.0 基线二次开发） |
| frontend | Vue2 + Element UI + Axios |
| portal | Vue3.5 + Vite + TailwindCSS + Vue Router |
| app | uni-app（H5 / 小程序 / App 多端） |

## 快速开始

### 1. 数据库
导入 `sql/ry_20250522.sql`（基础表）→ `sql/portal-db.sql`（门户表结构）→ `sql/portal-data.sql`（演示数据）。

### 2. 后端（端口 8080）
```bash
cd backend
mvn spring-boot:run
```
配置通过环境变量注入（`application-druid.yml`）：`MYSQL_HOST` / `MYSQL_PORT` / `MYSQL_USERNAME` / `MYSQL_PASSWORD` / `TOKEN_SECRET` / `DRUID_PASSWORD`，均无明文密钥。

### 3. 管理后台（端口 80）
```bash
cd frontend
npm install
npm run dev
```

### 4. 门户站点
```bash
cd portal
npm install
npm run dev
```

### 5. 移动端
HBuilderX 打开 `app/` 目录运行。

## 我做了什么

- 独立完成需求分析、数据库设计、后端接口、管理后台、官网门户与移动端开发
- 完成生产环境部署（Nginx 反向代理、SSL 证书、域名解析），系统稳定运行
- 基于 RuoYi-Vue（MIT）二次开发，保留上游版权声明

## License

MIT
