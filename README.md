# mc_trade_global 外贸企业官网

## 项目介绍

外贸企业宣传官网，采用前后端分离架构，支持多语言（中文/英文），提供可视化后台管理系统。

## 技术栈

### 前端
- Vue 3
- Vue Router
- Pinia (状态管理)
- Element Plus (UI组件库)
- Axios (HTTP客户端)
- Vue I18n (国际化)

### 后端
- Spring Boot 3.2
- MyBatis-Plus (ORM框架)
- MySQL 8.0
- JWT (身份认证)

## 项目结构

```
mc_trade_global/
├── backend/                 # 后端 Spring Boot 项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/trade/global/
│   │   │   │   ├── config/      # 配置类
│   │   │   │   ├── controller/  # 控制器
│   │   │   │   ├── service/     # 服务层
│   │   │   │   ├── mapper/      # 数据访问层
│   │   │   │   ├── entity/      # 实体类
│   │   │   │   ├── common/      # 通用类
│   │   │   │   └── util/        # 工具类
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       └── mapper/      # MyBatis XML
│   │   └── test/
│   └── pom.xml
│
├── frontend/                # 前端 Vue3 项目
│   ├── src/
│   │   ├── api/            # API 接口
│   │   ├── assets/         # 静态资源
│   │   ├── components/      # 公共组件
│   │   ├── layouts/        # 布局组件
│   │   ├── locales/        # 国际化文件
│   │   ├── pages/          # 页面组件
│   │   │   ├── home/       # 首页
│   │   │   ├── about/      # 关于我们
│   │   │   ├── product/    # 产品中心
│   │   │   ├── article/    # 新闻资讯
│   │   │   ├── contact/    # 联系我们
│   │   │   └── admin/      # 管理后台
│   │   ├── router/         # 路由配置
│   │   ├── store/          # 状态管理
│   │   └── utils/          # 工具函数
│   ├── index.html
│   ├── vite.config.js
│   └── package.json
│
├── docs/                   # 文档
│   └── 需求文档.md
│
├── plan.md                 # 开发计划
└── README.md
```

## 快速开始

### 环境要求

- JDK 17+
- Node.js 16+
- MySQL 8.0+

### 1. 初始化数据库

```bash
# 登录 MySQL
mysql -u root -p

# 执行初始化脚本
source backend/src/main/resources/db/init.sql
```

### 2. 启动后端服务

```bash
cd backend

# 使用 Maven 构建
mvn clean package

# 运行项目
mvn spring-boot:run

# 或者运行 JAR 包
java -jar target/mc_trade_global-1.0.0.jar
```

后端服务将运行在 http://localhost:8080/api

### 3. 启动前端服务

```bash
cd frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

前端服务将运行在 http://localhost:3000

### 4. 访问系统

- 前端官网：http://localhost:3000
- 管理后台：http://localhost:3000/admin
- 默认管理员账号：admin / admin123

## 主要功能

### 前端用户端
- 首页（轮播图、企业简介、优势、产品推荐、新闻动态、在线留言）
- 关于我们（企业介绍、发展历程、企业文化）
- 产品中心（分类导航、产品搜索、产品列表、产品详情）
- 新闻资讯（分类导航、文章列表、文章详情）
- 联系我们（联系方式、在线留言）

### 后端管理端
- 仪表盘（数据统计、快捷操作）
- 导航菜单管理（可视化配置）
- 轮播图管理
- 产品管理
- 文章管理
- 留言管理
- 系统设置

## API 接口

### 认证模块
- `POST /api/auth/login` - 用户登录
- `POST /api/auth/logout` - 退出登录
- `GET /api/auth/currentUser` - 获取当前用户

### 内容管理
- `GET/POST/PUT/DELETE /api/menu` - 导航菜单
- `GET/POST/PUT/DELETE /api/banner` - 轮播图
- `GET/POST/PUT/DELETE /api/product` - 产品
- `GET/POST/PUT/DELETE /api/article` - 文章
- `GET/PUT/DELETE /api/message` - 留言

### 前端展示
- `GET /api/home/banner` - 获取轮播图
- `GET /api/home/product/recommend` - 获取推荐产品
- `GET /api/home/article/latest` - 获取最新文章
- `POST /api/home/message` - 提交留言

## 开发规范

### 代码规范
- 遵循 Google Java Style Guide (后端)
- 遵循 Vue 3 官方风格指南
- 使用 ESLint + Prettier 格式化代码

### Git 提交规范
```
feat: 新功能
fix: 修复bug
docs: 文档更新
style: 代码格式调整
refactor: 重构
test: 测试相关
chore: 构建或辅助工具相关
```

## 许可证

MIT License
