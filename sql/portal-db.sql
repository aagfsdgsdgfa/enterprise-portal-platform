-- ----------------------------
-- 1. 轮播图管理表 (用于首页Banner、广告位)
-- ----------------------------
DROP TABLE IF EXISTS biz_banner;
CREATE TABLE biz_banner (
  banner_id         BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT 'BannerID',
  title             VARCHAR(100)    DEFAULT ''      COMMENT '标题',
  image_url         VARCHAR(255)    NOT NULL        COMMENT '图片地址',
  link_url          VARCHAR(255)    DEFAULT '#'     COMMENT '跳转链接',
  target            VARCHAR(20)     DEFAULT '_self' COMMENT '跳转方式(_self/_blank)',
  sort_order        INT(4)          DEFAULT 0       COMMENT '排序(越大越前)',
  device_type       CHAR(1)         DEFAULT '0'     COMMENT '设备类型(0PC端 1移动端)',
  status            CHAR(1)         DEFAULT '0'     COMMENT '状态(0正常 1停用)',
  create_by         VARCHAR(64)     DEFAULT ''      COMMENT '创建者',
  create_time       DATETIME        COMMENT '创建时间',
  update_by         VARCHAR(64)     DEFAULT ''      COMMENT '更新者',
  update_time       DATETIME        COMMENT '更新时间',
  remark            VARCHAR(500)    DEFAULT NULL    COMMENT '备注',
  PRIMARY KEY (banner_id)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT='轮播图管理';

-- ----------------------------
-- 2. 文章资讯表 (新闻、公告、企业文化)
-- ----------------------------
DROP TABLE IF EXISTS biz_article;
CREATE TABLE biz_article (
  article_id        BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  category_type     VARCHAR(20)     DEFAULT 'news'  COMMENT '分类(news新闻/notice公告/culture文化)',
  title             VARCHAR(200)    NOT NULL        COMMENT '文章标题',
  summary           VARCHAR(500)    DEFAULT ''      COMMENT '文章摘要',
  cover_img         VARCHAR(255)    DEFAULT ''      COMMENT '封面图片',
  content           LONGTEXT        COMMENT '文章内容(富文本)',
  author            VARCHAR(50)     DEFAULT '星曜AI' COMMENT '作者',
  is_top            CHAR(1)         DEFAULT '0'     COMMENT '是否置顶(1是 0否)',
  view_count        BIGINT(20)      DEFAULT 0       COMMENT '浏览量',
  
  -- SEO 字段
  seo_title         VARCHAR(200)    DEFAULT ''      COMMENT 'SEO标题',
  seo_keywords      VARCHAR(200)    DEFAULT ''      COMMENT 'SEO关键词',
  seo_description   VARCHAR(500)    DEFAULT ''      COMMENT 'SEO描述',

  status            CHAR(1)         DEFAULT '0'     COMMENT '状态(0发布 1草稿)',
  publish_time      DATETIME        COMMENT '发布时间(支持定时)',
  create_by         VARCHAR(64)     DEFAULT ''      COMMENT '创建者',
  create_time       DATETIME        COMMENT '创建时间',
  update_by         VARCHAR(64)     DEFAULT ''      COMMENT '更新者',
  update_time       DATETIME        COMMENT '更新时间',
  remark            VARCHAR(500)    DEFAULT NULL    COMMENT '备注',
  PRIMARY KEY (article_id)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT='文章资讯表';

-- ----------------------------
-- 3. 产品与解决方案表 (核心业务)
-- ----------------------------
DROP TABLE IF EXISTS biz_product;
CREATE TABLE biz_product (
  product_id        BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '产品ID',
  product_name      VARCHAR(100)    NOT NULL        COMMENT '产品名称',
  category_id       BIGINT(20)      DEFAULT NULL    COMMENT '所属分类ID',
  sub_title         VARCHAR(200)    DEFAULT ''      COMMENT '副标题/Slogan',
  
  -- 展示相关
  main_img          VARCHAR(255)    DEFAULT ''      COMMENT '主图',
  icon_img          VARCHAR(255)    DEFAULT ''      COMMENT '图标(用于首页列表)',
  tech_arch_img     VARCHAR(255)    DEFAULT ''      COMMENT '技术架构图(用于放大查看)',
  demo_video_url    VARCHAR(255)    DEFAULT ''      COMMENT '演示视频链接',
  
  -- 内容详情
  summary           VARCHAR(500)    DEFAULT ''      COMMENT '产品简介',
  pain_points       TEXT            COMMENT '客户痛点(JSON格式存储)',
  solutions         LONGTEXT        COMMENT '解决方案详情(富文本)',
  tech_specs        LONGTEXT        COMMENT '技术参数',
  
  -- 关联推荐
  related_ids       VARCHAR(200)    DEFAULT ''      COMMENT '关联产品ID集合(1,2,3)',
  
  -- SEO 字段
  seo_title         VARCHAR(200)    DEFAULT ''      COMMENT 'SEO标题',
  seo_keywords      VARCHAR(200)    DEFAULT ''      COMMENT 'SEO关键词',
  seo_description   VARCHAR(500)    DEFAULT ''      COMMENT 'SEO描述',

  sort_order        INT(4)          DEFAULT 0       COMMENT '排序',
  status            CHAR(1)         DEFAULT '0'     COMMENT '状态(0上架 1下架)',
  create_by         VARCHAR(64)     DEFAULT ''      COMMENT '创建者',
  create_time       DATETIME        COMMENT '创建时间',
  update_by         VARCHAR(64)     DEFAULT ''      COMMENT '更新者',
  update_time       DATETIME        COMMENT '更新时间',
  remark            VARCHAR(500)    DEFAULT NULL    COMMENT '备注',
  PRIMARY KEY (product_id)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT='产品解决方案表';

-- ----------------------------
-- 4. 招商线索表 (核心转化功能 - 只有管理员可看)
-- ----------------------------
DROP TABLE IF EXISTS biz_lead;
CREATE TABLE biz_lead (
  lead_id           BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '线索ID',
  type              VARCHAR(20)     DEFAULT 'contact' COMMENT '类型(contact留言/download下载/agent招商)',
  
  -- 客户信息
  contact_name      VARCHAR(50)     NOT NULL        COMMENT '联系人姓名',
  phone             VARCHAR(20)     NOT NULL        COMMENT '联系电话',
  company_name      VARCHAR(100)    DEFAULT ''      COMMENT '企业名称',
  email             VARCHAR(100)    DEFAULT ''      COMMENT '电子邮箱',
  
  -- 意向信息
  intent_product    VARCHAR(100)    DEFAULT ''      COMMENT '意向产品/下载资料名',
  message           VARCHAR(1000)   DEFAULT ''      COMMENT '留言内容',
  budget_range      VARCHAR(50)     DEFAULT ''      COMMENT '预算范围(用于计算器结果)',
  
  -- 追踪信息
  ip_address        VARCHAR(50)     DEFAULT ''      COMMENT '客户IP',
  source_url        VARCHAR(500)    DEFAULT ''      COMMENT '来源页面',
  
  -- 处理状态
  status            CHAR(1)         DEFAULT '0'     COMMENT '状态(0待处理 1跟进中 2已转化 3无效)',
  handle_result     VARCHAR(500)    DEFAULT ''      COMMENT '处理结果/备注',
  
  create_time       DATETIME        COMMENT '提交时间',
  update_by         VARCHAR(64)     DEFAULT ''      COMMENT '处理人',
  update_time       DATETIME        COMMENT '处理时间',
  PRIMARY KEY (lead_id)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT='营销线索表';

-- ----------------------------
-- 5. 职位招聘表
-- ----------------------------
DROP TABLE IF EXISTS biz_job;
CREATE TABLE biz_job (
  job_id            BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '职位ID',
  job_title         VARCHAR(100)    NOT NULL        COMMENT '职位名称',
  department        VARCHAR(50)     DEFAULT ''      COMMENT '所属部门',
  job_type          CHAR(1)         DEFAULT '1'     COMMENT '类型(1研发 2市场 3职能)',
  work_location     VARCHAR(50)     DEFAULT '云南昆明' COMMENT '工作地点',
  salary_range      VARCHAR(50)     DEFAULT '面议'   COMMENT '薪资范围',
  experience        VARCHAR(50)     DEFAULT '不限'   COMMENT '经验要求',
  education         VARCHAR(50)     DEFAULT '本科'   COMMENT '学历要求',
  
  job_desc          TEXT            COMMENT '岗位职责',
  job_require       TEXT            COMMENT '任职要求',
  
  is_urgent         CHAR(1)         DEFAULT '0'     COMMENT '是否急聘(1是 0否)',
  status            CHAR(1)         DEFAULT '0'     COMMENT '状态(0招聘中 1已停止)',
  
  create_by         VARCHAR(64)     DEFAULT ''      COMMENT '创建者',
  create_time       DATETIME        COMMENT '创建时间',
  update_by         VARCHAR(64)     DEFAULT ''      COMMENT '更新者',
  update_time       DATETIME        COMMENT '更新时间',
  remark            VARCHAR(500)    DEFAULT NULL    COMMENT '备注',
  PRIMARY KEY (job_id)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT='职位招聘表';

-- ----------------------------
-- 6. 资料下载中心表 (用于招商手册、白皮书)
-- ----------------------------
DROP TABLE IF EXISTS biz_resource;
CREATE TABLE biz_resource (
  resource_id       BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '资料ID',
  title             VARCHAR(100)    NOT NULL        COMMENT '资料标题',
  file_path         VARCHAR(500)    NOT NULL        COMMENT '文件路径(OSS/本地)',
  file_size         VARCHAR(20)     DEFAULT ''      COMMENT '文件大小',
  file_type         VARCHAR(20)     DEFAULT 'pdf'   COMMENT '文件格式',
  
  need_login        CHAR(1)         DEFAULT '1'     COMMENT '需留资下载(1是 0否)',
  download_count    INT(8)          DEFAULT 0       COMMENT '下载次数',
  
  status            CHAR(1)         DEFAULT '0'     COMMENT '状态(0正常 1停用)',
  create_by         VARCHAR(64)     DEFAULT ''      COMMENT '创建者',
  create_time       DATETIME        COMMENT '创建时间',
  update_by         VARCHAR(64)     DEFAULT ''      COMMENT '更新者',
  update_time       DATETIME        COMMENT '更新时间',
  remark            VARCHAR(500)    DEFAULT NULL    COMMENT '备注',
  PRIMARY KEY (resource_id)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT='资料下载中心';


-- ----------------------------
-- 7. 企业基础信息配置表 (用于网站底部、联系我们页面的数据源)
-- ----------------------------
-- 说明：这张表通常只有1条数据，管理员在后台点“修改”即可，不用点“新增”。
DROP TABLE IF EXISTS biz_company_info;
CREATE TABLE biz_company_info (
  info_id           BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT 'ID',
  company_name      VARCHAR(100)    DEFAULT '星曜智能科技有限公司' COMMENT '公司名称',
  
  -- 联系信息
  address           VARCHAR(255)    DEFAULT ''      COMMENT '公司地址',
  phone_hotline     VARCHAR(20)     DEFAULT ''      COMMENT '官方热线',
  phone_sales       VARCHAR(20)     DEFAULT ''      COMMENT '招商电话',
  email             VARCHAR(100)    DEFAULT ''      COMMENT '联系邮箱',
  work_time         VARCHAR(100)    DEFAULT ''      COMMENT '工作时间',
  
  -- 网站标识
  logo_dark         VARCHAR(255)    DEFAULT ''      COMMENT 'Logo(深色模式)',
  logo_light        VARCHAR(255)    DEFAULT ''      COMMENT 'Logo(浅色模式)',
  qr_code           VARCHAR(255)    DEFAULT ''      COMMENT '公众号二维码',
  
  -- 备案与版权
  icp_beian         VARCHAR(50)     DEFAULT ''      COMMENT 'ICP备案号',
  copyright         VARCHAR(100)    DEFAULT ''      COMMENT '版权信息',
  
  -- 地图坐标 (用于前端展示高德/百度地图)
  latitude          VARCHAR(20)     DEFAULT ''      COMMENT '纬度',
  longitude         VARCHAR(20)     DEFAULT ''      COMMENT '经度',

  update_by         VARCHAR(64)     DEFAULT ''      COMMENT '更新者',
  update_time       DATETIME        COMMENT '更新时间',
  remark            VARCHAR(500)    DEFAULT NULL    COMMENT '备注',
  PRIMARY KEY (info_id)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT='企业基础信息表';

-- ----------------------------
-- 8. 企业文化表 (用于“关于我们”页面的卡片式展示)
-- ----------------------------
-- 说明：每一条数据代表一个文化点，例如“创新”、“诚信”、“共赢”。
DROP TABLE IF EXISTS biz_culture;
CREATE TABLE biz_culture (
  culture_id        BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT 'ID',
  title             VARCHAR(50)     NOT NULL        COMMENT '标题(如:企业使命)',
  sub_title         VARCHAR(200)    DEFAULT ''      COMMENT '短描述(如:让AI造福人类)',
  icon              VARCHAR(100)    DEFAULT ''      COMMENT '图标(ElementUI图标类名或图片)',
  description       VARCHAR(500)    DEFAULT ''      COMMENT '详细阐述',
  sort_order        INT(4)          DEFAULT 0       COMMENT '排序',
  
  -- 区分类型：0=核心价值观(多条), 1=使命(单条), 2=愿景(单条)
  type              CHAR(1)         DEFAULT '0'     COMMENT '类型',
  
  create_by         VARCHAR(64)     DEFAULT ''      COMMENT '创建者',
  create_time       DATETIME        COMMENT '创建时间',
  update_by         VARCHAR(64)     DEFAULT ''      COMMENT '更新者',
  update_time       DATETIME        COMMENT '更新时间',
  PRIMARY KEY (culture_id)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT='企业文化表';

-- ----------------------------
-- 9. 项目案例表 (Success Cases) - 区别于“产品”
-- ----------------------------
-- 说明：产品是“卖什么的(软件/硬件)”，项目是“做过什么(成功案例)”。
DROP TABLE IF EXISTS biz_project;
CREATE TABLE biz_project (
  project_id        BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '项目ID',
  project_name      VARCHAR(200)    NOT NULL        COMMENT '项目名称(如:某市智慧交通系统)',
  client_name       VARCHAR(100)    DEFAULT ''      COMMENT '客户名称(如:某市政府)',
  industry_type     VARCHAR(50)     DEFAULT ''      COMMENT '所属行业(智慧城市/工业/教育)',
  
  -- 展示图
  cover_img         VARCHAR(255)    DEFAULT ''      COMMENT '列表封面图',
  detail_banner     VARCHAR(255)    DEFAULT ''      COMMENT '详情页Banner大图',
  
  -- 项目详情
  background        TEXT            COMMENT '项目背景',
  solution_desc     TEXT            COMMENT '解决方案描述',
  achievement       TEXT            COMMENT '建设成果/价值',
  
  -- 关键数据 (用于前端展示“提升了50%效率”这种亮点)
  key_data_val      VARCHAR(50)     DEFAULT ''      COMMENT '关键数据值(如:50%)',
  key_data_desc     VARCHAR(50)     DEFAULT ''      COMMENT '数据说明(如:通行效率提升)',
  
  completion_date   DATE            DEFAULT NULL    COMMENT '完工日期',
  
  -- 关联产品 (这个项目用了哪些自家产品)
  related_product_ids VARCHAR(200)  DEFAULT ''      COMMENT '关联产品ID',

  sort_order        INT(4)          DEFAULT 0       COMMENT '排序',
  create_by         VARCHAR(64)     DEFAULT ''      COMMENT '创建者',
  create_time       DATETIME        COMMENT '创建时间',
  update_by         VARCHAR(64)     DEFAULT ''      COMMENT '更新者',
  update_time       DATETIME        COMMENT '更新时间',
  PRIMARY KEY (project_id)
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT='项目案例表';

-- ----------------------------
-- 1. 创建解决方案表结构
-- ----------------------------
DROP TABLE IF EXISTS `biz_solution`;
CREATE TABLE `biz_solution` (
  `solution_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '方案ID',
  `title` varchar(128) NOT NULL COMMENT '方案标题',
  `sub_title` varchar(255) DEFAULT '' COMMENT '副标题(Slogan)',
  `summary` varchar(500) DEFAULT '' COMMENT '方案摘要(列表页显示)',
  `icon` varchar(255) DEFAULT '' COMMENT '列表图标(ElementPlus图标名或图片URL)',
  `banner_img` varchar(255) DEFAULT '' COMMENT '详情页顶部大图',
  `content` longtext COMMENT '方案详情(富文本)',
  
  -- 关键业务字段 --
  `pain_points` varchar(1000) DEFAULT '' COMMENT '客户痛点(分号隔开)',
  `value_props` varchar(1000) DEFAULT '' COMMENT '方案价值/优势(分号隔开)',
  `related_product_ids` varchar(255) DEFAULT '' COMMENT '关联产品ID集合(如: 1,2,5)',
  
  -- 标准字段 --
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `sort_order` int(4) DEFAULT 0 COMMENT '显示顺序',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`solution_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='行业解决方案表';