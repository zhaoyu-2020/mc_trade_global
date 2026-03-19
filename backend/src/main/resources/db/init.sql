-- mc_trade_global 数据库初始化脚本
-- 创建数据库
CREATE DATABASE IF NOT EXISTS mc_trade_global DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE mc_trade_global;

-- 用户表
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    nickname VARCHAR(50) COMMENT '昵称',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '电话',
    role_id INT NOT NULL DEFAULT 2 COMMENT '角色ID 1:超级管理员 2:普通管理员',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0:禁用 1:启用',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除状态 0:未删除 1:已删除',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_username (username),
    INDEX idx_role_id (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

-- 角色表
CREATE TABLE sys_role (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID',
    role_name VARCHAR(50) NOT NULL COMMENT '角色名称',
    role_key VARCHAR(50) NOT NULL UNIQUE COMMENT '角色标识',
    description VARCHAR(255) COMMENT '角色描述',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0:禁用 1:启用',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- 导航菜单表
CREATE TABLE sys_menu (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '菜单ID',
    menu_name VARCHAR(50) NOT NULL COMMENT '菜单名称(中文)',
    menu_name_en VARCHAR(100) NOT NULL COMMENT '菜单名称(英文)',
    menu_type TINYINT NOT NULL DEFAULT 1 COMMENT '菜单类型 1:一级菜单 2:二级菜单',
    parent_id BIGINT DEFAULT 0 COMMENT '上级菜单ID',
    path VARCHAR(255) COMMENT '跳转链接',
    sort INT DEFAULT 0 COMMENT '排序号',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0:禁用 1:启用',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_parent_id (parent_id),
    INDEX idx_sort (sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='导航菜单表';

-- 轮播图表
CREATE TABLE sys_banner (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '轮播图ID',
    image_url VARCHAR(500) NOT NULL COMMENT '图片URL',
    title VARCHAR(100) COMMENT '标题(中文)',
    title_en VARCHAR(200) COMMENT '标题(英文)',
    description VARCHAR(500) COMMENT '描述(中文)',
    description_en VARCHAR(1000) COMMENT '描述(英文)',
    link_url VARCHAR(500) COMMENT '跳转链接',
    link_target VARCHAR(20) DEFAULT '_blank' COMMENT '跳转方式 _self:_blank',
    sort INT DEFAULT 0 COMMENT '排序号',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0:禁用 1:启用',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_sort (sort),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='轮播图表';

-- 产品分类表
CREATE TABLE sys_product_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    category_name VARCHAR(100) NOT NULL COMMENT '分类名称(中文)',
    category_name_en VARCHAR(200) NOT NULL COMMENT '分类名称(英文)',
    parent_id BIGINT DEFAULT 0 COMMENT '上级分类ID',
    sort INT DEFAULT 0 COMMENT '排序号',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0:禁用 1:启用',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_parent_id (parent_id),
    INDEX idx_sort (sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品分类表';

-- 产品表
CREATE TABLE sys_product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '产品ID',
    category_id BIGINT NOT NULL COMMENT '分类ID',
    product_name VARCHAR(200) NOT NULL COMMENT '产品名称(中文)',
    product_name_en VARCHAR(400) NOT NULL COMMENT '产品名称(英文)',
    model VARCHAR(100) COMMENT '型号(中文)',
    model_en VARCHAR(200) COMMENT '型号(英文)',
    short_description VARCHAR(500) COMMENT '简短描述(中文)',
    short_description_en VARCHAR(1000) COMMENT '简短描述(英文)',
    description TEXT COMMENT '详细描述(中文)',
    description_en TEXT COMMENT '详细描述(英文)',
    price VARCHAR(50) COMMENT '价格',
    stock INT DEFAULT 0 COMMENT '库存',
    main_image VARCHAR(500) COMMENT '主图URL',
    images TEXT COMMENT '图片列表(JSON)',
    parameters TEXT COMMENT '参数列表(JSON)',
    is_recommend TINYINT DEFAULT 0 COMMENT '是否推荐 0:否 1:是',
    is_top TINYINT DEFAULT 0 COMMENT '是否置顶 0:否 1:是',
    sort INT DEFAULT 0 COMMENT '排序号',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0:下架 1:上架',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_category_id (category_id),
    INDEX idx_is_recommend (is_recommend),
    INDEX idx_sort (sort),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品表';

-- 文章分类表
CREATE TABLE sys_article_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    category_name VARCHAR(100) NOT NULL COMMENT '分类名称(中文)',
    category_name_en VARCHAR(200) NOT NULL COMMENT '分类名称(英文)',
    parent_id BIGINT DEFAULT 0 COMMENT '上级分类ID',
    sort INT DEFAULT 0 COMMENT '排序号',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0:禁用 1:启用',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_parent_id (parent_id),
    INDEX idx_sort (sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章分类表';

-- 文章表
CREATE TABLE sys_article (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '文章ID',
    category_id BIGINT COMMENT '分类ID',
    title VARCHAR(200) NOT NULL COMMENT '标题(中文)',
    title_en VARCHAR(400) NOT NULL COMMENT '标题(英文)',
    author VARCHAR(50) COMMENT '作者',
    cover_image VARCHAR(500) COMMENT '封面图URL',
    summary VARCHAR(500) COMMENT '摘要(中文)',
    summary_en VARCHAR(1000) COMMENT '摘要(英文)',
    content TEXT COMMENT '正文(中文)',
    content_en TEXT COMMENT '正文(英文)',
    view_count INT DEFAULT 0 COMMENT '阅读量',
    is_top TINYINT DEFAULT 0 COMMENT '是否置顶 0:否 1:是',
    is_publish TINYINT DEFAULT 1 COMMENT '发布状态 0:草稿 1:已发布',
    sort INT DEFAULT 0 COMMENT '排序号',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_category_id (category_id),
    INDEX idx_is_top (is_top),
    INDEX idx_is_publish (is_publish),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章表';

-- 留言表
CREATE TABLE sys_message (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '留言ID',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    email VARCHAR(100) NOT NULL COMMENT '邮箱',
    phone VARCHAR(30) COMMENT '电话',
    content VARCHAR(2000) NOT NULL COMMENT '留言内容',
    is_read TINYINT DEFAULT 0 COMMENT '是否已读 0:未读 1:已读',
    reply_by BIGINT COMMENT '回复人ID',
    reply_content VARCHAR(2000) COMMENT '回复内容',
    reply_time DATETIME COMMENT '回复时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_is_read (is_read),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='留言表';

-- 系统配置表
CREATE TABLE sys_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '配置ID',
    config_key VARCHAR(100) NOT NULL UNIQUE COMMENT '配置键',
    config_value TEXT COMMENT '配置值(中文)',
    config_value_en TEXT COMMENT '配置值(英文)',
    config_type VARCHAR(50) COMMENT '配置类型',
    description VARCHAR(255) COMMENT '描述',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';

-- 企业优势表
CREATE TABLE sys_advantage (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '优势ID',
    icon VARCHAR(255) COMMENT '图标URL',
    title VARCHAR(100) NOT NULL COMMENT '标题(中文)',
    title_en VARCHAR(200) NOT NULL COMMENT '标题(英文)',
    description VARCHAR(500) COMMENT '描述(中文)',
    description_en VARCHAR(1000) COMMENT '描述(英文)',
    sort INT DEFAULT 0 COMMENT '排序号',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0:禁用 1:启用',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_sort (sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='企业优势表';

-- 发展历程表
CREATE TABLE sys_development (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '历程ID',
    year VARCHAR(10) NOT NULL COMMENT '年份',
    title VARCHAR(100) NOT NULL COMMENT '标题(中文)',
    title_en VARCHAR(200) NOT NULL COMMENT '标题(英文)',
    description VARCHAR(1000) COMMENT '描述(中文)',
    description_en VARCHAR(2000) COMMENT '描述(英文)',
    image_url VARCHAR(500) COMMENT '图片URL',
    sort INT DEFAULT 0 COMMENT '排序号',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_year (year),
    INDEX idx_sort (sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='发展历程表';

-- 插入初始数据
-- 初始化角色
INSERT INTO sys_role (role_name, role_key, description) VALUES
('超级管理员', 'super_admin', '拥有所有权限'),
('普通管理员', 'normal_admin', '仅拥有内容管理权限');

-- 初始化管理员用户 (密码: admin123，使用BCrypt加密)
INSERT INTO sys_user (username, password, nickname, email, role_id) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '超级管理员', 'admin@trade.com', 1);

-- 初始化默认导航菜单
INSERT INTO sys_menu (menu_name, menu_name_en, menu_type, parent_id, path, sort, status) VALUES
('首页', 'Home', 1, 0, '/', 1, 1),
('关于我们', 'About Us', 1, 0, '/about', 2, 1),
('产品中心', 'Products', 1, 0, '/product', 3, 1),
('新闻资讯', 'News', 1, 0, '/article', 4, 1),
('联系我们', 'Contact Us', 1, 0, '/contact', 5, 1);

-- 初始化默认系统配置
INSERT INTO sys_config (config_key, config_value, config_value_en, config_type, description) VALUES
('website_name', 'MC Trade Global', 'MC Trade Global', 'basic', '网站名称'),
('website_logo', '/uploads/logo.png', '/uploads/logo.png', 'basic', '网站LOGO'),
('company_name', 'MC Trade Global Co., Ltd.', 'MC Trade Global Co., Ltd.', 'basic', '公司名称'),
('company_address', '中国广东省深圳市南山区科技园', 'Tech Park, Nanshan District, Shenzhen, Guangdong, China', 'basic', '公司地址'),
('company_phone', '+86 755 12345678', '+86 755 12345678', 'basic', '联系电话'),
('company_email', 'info@mctradeglobal.com', 'info@mctradeglobal.com', 'basic', '联系邮箱'),
('icp_number', '粤ICP备12345678号', 'ICP No. 12345678', 'basic', '备案号'),
('facebook_url', 'https://facebook.com/mctradeglobal', 'https://facebook.com/mctradeglobal', 'social', 'Facebook链接'),
('linkedin_url', 'https://linkedin.com/company/mctradeglobal', 'https://linkedin.com/company/mctradeglobal', 'social', 'LinkedIn链接');

-- 初始化产品分类
INSERT INTO sys_product_category (category_name, category_name_en, parent_id, sort, status) VALUES
('电子产品', 'Electronic Products', 0, 1, 1),
('机械设备', 'Machinery Equipment', 0, 2, 1),
('化工产品', 'Chemical Products', 0, 3, 1),
('纺织品', 'Textile Products', 0, 4, 1);

-- 初始化文章分类
INSERT INTO sys_article_category (category_name, category_name_en, parent_id, sort, status) VALUES
('企业新闻', 'Company News', 0, 1, 1),
('行业资讯', 'Industry News', 0, 2, 1),
('产品知识', 'Product Knowledge', 0, 3, 1);

-- 初始化企业优势
INSERT INTO sys_advantage (icon, title, title_en, description, description_en, sort, status) VALUES
('/uploads/icons/quality.png', '品质保证', 'Quality Assurance', '严格的质量控制体系，确保产品品质', 'Strict quality control system to ensure product quality', 1, 1),
('/uploads/icons/delivery.png', '快速交付', 'Fast Delivery', '高效的物流体系，确保准时交付', 'Efficient logistics system to ensure timely delivery', 2, 1),
('/uploads/icons/service.png', '优质服务', 'Premium Service', '专业的客服团队，提供全方位的服务', 'Professional customer service team providing comprehensive service', 3, 1),
('/uploads/icons/price.png', '价格优势', 'Competitive Price', '源头厂家直销，价格优惠', 'Direct from manufacturer with competitive prices', 4, 1),
('/uploads/icons/custom.png', '定制服务', 'Customization', '支持OEM/ODM定制服务', 'Support OEM/ODM customization services', 5, 1),
('/uploads/icons/global.png', '全球销售', 'Global Sales', '产品远销全球100多个国家和地区', 'Products sold to more than 100 countries and regions worldwide', 6, 1);
