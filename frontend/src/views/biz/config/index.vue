<template>
  <div class="app-container config-container">
    <div class="page-header">
      <div class="header-left">
        <div class="page-title">首页通用配置</div>
        <div class="page-desc">集中管理门户首页的统计数据、核心优势、版块标题及其他动态文案元素。</div>
      </div>
      <div class="header-right">
        <el-button 
          type="primary" 
          icon="el-icon-plus" 
          size="medium" 
          @click="handleAdd"
          v-hasPermi="['biz:config:add']"
        >新增配置项</el-button>
      </div>
    </div>

    <div class="config-tabs">
      <el-tabs v-model="activeTab" @tab-click="handleTabClick">
        <el-tab-pane label="数据统计 (Stats)" name="stats">
          <span slot="label"><i class="el-icon-data-line"></i> 数据统计</span>
        </el-tab-pane>
        <el-tab-pane label="核心优势 (Why Us)" name="why_us">
          <span slot="label"><i class="el-icon-trophy"></i> 核心优势</span>
        </el-tab-pane>
        <el-tab-pane label="版块标题 (Titles)" name="section_title">
          <span slot="label"><i class="el-icon-collection-tag"></i> 版块标题</span>
        </el-tab-pane>
        <el-tab-pane label="招聘文案 (Recruit)" name="recruit">
          <span slot="label"><i class="el-icon-suitcase"></i> 招聘文案</span>
        </el-tab-pane>
      </el-tabs>
    </div>

    <div v-loading="loading" class="config-list-area">
      <el-empty v-if="!loading && configList.length === 0" description="该分组下暂无配置，请点击右上角新增"></el-empty>

      <el-row :gutter="24" v-else>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" v-for="item in configList" :key="item.configId" class="mb-24">
          <div class="config-card">
            <div class="card-content">
              <div class="card-top">
                <div class="icon-wrapper" :class="{ 'has-img': item.imageUrl }">
                  <image-preview v-if="item.imageUrl" :src="item.imageUrl" :width="48" :height="48" />
                  <i v-else :class="['custom-icon', item.icon || 'el-icon-setting']"></i>
                </div>
                <div class="meta-info">
                  <div class="key-tag" v-if="item.configKey" title="前端定位标识">
                    <i class="el-icon-key"></i> {{ item.configKey }}
                  </div>
                  <div class="sort-tag">#{{ item.sortOrder }}</div>
                </div>
              </div>

              <div class="card-main">
                <div class="main-title" :title="item.title">{{ item.title || '未命名配置' }}</div>
                <div class="sub-title" :title="item.subTitle">{{ item.subTitle || '暂无描述信息...' }}</div>
              </div>

              <div class="card-footer">
                <div class="link-status" v-if="item.linkUrl" title="已配置跳转链接">
                  <i class="el-icon-link"></i> <span>已配置链接</span>
                </div>
                <div class="link-status disabled" v-else>
                  <i class="el-icon-link"></i> <span>无链接</span>
                </div>
                
                <div class="actions">
                  <el-button type="text" icon="el-icon-edit" @click="handleUpdate(item)">编辑</el-button>
                  <el-divider direction="vertical"></el-divider>
                  <el-button type="text" icon="el-icon-delete" class="delete-btn" @click="handleDelete(item)">删除</el-button>
                </div>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>

      <pagination
        v-if="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>

    <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body custom-class="config-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="90px" label-position="top">
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="配置分组 (Group)" prop="configGroup">
              <el-select v-model="form.configGroup" placeholder="请选择分组" style="width: 100%" :disabled="!!form.configId">
                <el-option label="数据统计 (stats)" value="stats"></el-option>
                <el-option label="核心优势 (why_us)" value="why_us"></el-option>
                <el-option label="版块标题 (section_title)" value="section_title"></el-option>
                <el-option label="招聘文案 (recruit)" value="recruit"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="唯一标识 (Key - 选填)" prop="configKey">
              <el-input v-model="form.configKey" placeholder="如: product_section, banner_1" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="主标题 / 数值" prop="title">
              <el-input v-model="form.title" placeholder="如: 99.9% 或 产品中心" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序权重" prop="sortOrder">
              <el-input-number v-model="form.sortOrder" :min="0" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="副标题 / 简述" prop="subTitle">
          <el-input v-model="form.subTitle" type="textarea" :rows="2" placeholder="请输入补充描述信息..." resize="none"/>
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="图标 (Icon类名)" prop="icon">
              <el-input v-model="form.icon" placeholder="如: el-icon-data-line" prefix-icon="el-icon-picture-outline-round" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="跳转链接 (Link)" prop="linkUrl">
              <el-input v-model="form.linkUrl" placeholder="http://..." prefix-icon="el-icon-link" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="配图 (若上传图片优先展示图片)" prop="imageUrl">
          <image-upload v-model="form.imageUrl" :limit="1"/>
        </el-form-item>

        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="仅后台可见的备注" />
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定 保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listConfig, getConfig, delConfig, addConfig, updateConfig } from "@/api/biz/config"

export default {
  name: "Config",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      total: 0,
      configList: [],
      // 默认选中第一个Tab
      activeTab: 'stats',
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 12, // 卡片布局每页多一点
        configGroup: 'stats', // 默认查询分组
        configKey: null,
        title: null,
      },
      form: {},
      rules: {
        configGroup: [
          { required: true, message: "配置分组不能为空", trigger: "change" }
        ],
        title: [
          { required: true, message: "标题/数值不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 切换Tab */
    handleTabClick(tab) {
      this.queryParams.pageNum = 1;
      this.queryParams.configGroup = tab.name;
      this.getList();
    },
    /** 查询列表 */
    getList() {
      this.loading = true
      listConfig(this.queryParams).then(response => {
        this.configList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        configId: null,
        configGroup: this.activeTab, // 新增时默认选中当前Tab的分组
        configKey: null,
        title: null,
        subTitle: null,
        icon: null,
        imageUrl: null,
        linkUrl: null,
        sortOrder: 0,
        status: "0",
        remark: null
      }
      this.resetForm("form")
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "新增配置项"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const configId = row.configId || this.ids
      getConfig(configId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改配置项"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.configId != null) {
            updateConfig(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addConfig(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const configIds = row.configId || this.ids
      this.$modal.confirm('确认删除该配置项？删除后首页对应内容将消失。').then(function() {
        return delConfig(configIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>

<style scoped lang="scss">
.config-container {
  padding: 24px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 84px);
}

// 头部样式
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  background: #fff;
  padding: 20px 24px;
  border-radius: 8px;
  box-shadow: 0 1px 2px 0 rgba(0,0,0,0.05);

  .header-left {
    .page-title {
      font-size: 20px;
      font-weight: 600;
      color: #303133;
      margin-bottom: 8px;
    }
    .page-desc {
      font-size: 13px;
      color: #909399;
    }
  }
}

// Tabs 样式
.config-tabs {
  background: #fff;
  padding: 12px 24px 0;
  border-radius: 8px 8px 0 0;
  border-bottom: 1px solid #e4e7ed;
  
  ::v-deep .el-tabs__header {
    margin-bottom: 0;
  }
  ::v-deep .el-tabs__nav-wrap::after {
    display: none; // 去掉那根灰线
  }
}

// 列表区域
.config-list-area {
  background: #fff;
  padding: 24px;
  border-radius: 0 0 8px 8px;
  min-height: 400px;
}

.mb-24 {
  margin-bottom: 24px;
}

// 卡片样式
.config-card {
  border: 1px solid #ebeef5;
  border-radius: 12px;
  transition: all 0.3s ease;
  background: #fff;
  height: 100%;
  position: relative;

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 24px rgba(0,0,0,0.08);
    border-color: #dcdfe6;
  }

  .card-content {
    padding: 20px;
    display: flex;
    flex-direction: column;
    height: 100%;
  }

  // 顶部：图标与Meta
  .card-top {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin-bottom: 16px;

    .icon-wrapper {
      width: 48px;
      height: 48px;
      border-radius: 10px;
      background-color: #f2f6fc;
      display: flex;
      align-items: center;
      justify-content: center;
      color: #409EFF;
      font-size: 24px;
      border: 1px solid #f2f6fc;

      &.has-img {
        padding: 2px;
        background: #fff;
        border-color: #e4e7ed;
      }
    }

    .meta-info {
      text-align: right;
      .key-tag {
        font-size: 12px;
        color: #e6a23c;
        background: #fdf6ec;
        padding: 2px 6px;
        border-radius: 4px;
        margin-bottom: 4px;
        display: inline-block;
      }
      .sort-tag {
        font-size: 12px;
        color: #909399;
        font-family: monospace;
        display: block;
      }
    }
  }

  // 中部：标题
  .card-main {
    flex-grow: 1;
    margin-bottom: 20px;

    .main-title {
      font-size: 18px;
      font-weight: 600;
      color: #303133;
      margin-bottom: 8px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    .sub-title {
      font-size: 13px;
      color: #909399;
      line-height: 1.5;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      overflow: hidden;
      height: 38px; // 固定高度，对齐
    }
  }

  // 底部：操作
  .card-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-top: 1px dashed #ebeef5;
    padding-top: 15px;

    .link-status {
      font-size: 12px;
      color: #67c23a;
      display: flex;
      align-items: center;
      gap: 4px;

      &.disabled {
        color: #c0c4cc;
      }
    }

    .actions {
      .delete-btn {
        color: #f56c6c;
        &:hover { color: #f78989; }
      }
    }
  }
}
</style>