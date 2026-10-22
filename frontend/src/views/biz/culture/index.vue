<template>
  <div class="app-container culture-container">
    <div class="page-header">
      <div class="header-left">
        <div class="page-title">企业文化墙配置</div>
        <div class="page-desc">定义企业的使命、愿景、价值观及核心理念，这些内容将展示在“关于我们”页面。</div>
      </div>
      <div class="header-right">
        <el-input 
          v-model="queryParams.title" 
          placeholder="搜索标题..." 
          prefix-icon="el-icon-search"
          size="medium"
          clearable
          @keyup.enter.native="handleQuery"
          @clear="handleQuery"
          class="search-input"
        ></el-input>
        <el-button 
          type="primary" 
          icon="el-icon-plus" 
          size="medium" 
          @click="handleAdd"
          v-hasPermi="['biz:culture:add']"
        >新增文化条目</el-button>
      </div>
    </div>

    <div v-loading="loading" class="culture-content">
      
      <el-empty v-if="!loading && cultureList.length === 0" description="暂无企业文化数据，请点击右上角新增"></el-empty>

      <el-row :gutter="24" v-else>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" v-for="item in cultureList" :key="item.cultureId" class="mb-24">
          <div class="culture-card">
            <div class="card-header">
              <div class="icon-wrapper">
                <el-image 
                  v-if="isImageUrl(item.icon)" 
                  :src="getImgUrl(item.icon)" 
                  fit="cover" 
                  class="custom-img"
                ></el-image>
                <i v-else :class="['custom-icon', item.icon || 'el-icon-star-on']"></i>
              </div>
              <div class="title-group">
                <div class="main-title">{{ item.title }}</div>
                <div class="sub-title">{{ item.subTitle || '暂无副标题' }}</div>
              </div>
              <div class="sort-badge" title="排序权重">#{{ item.sortOrder }}</div>
            </div>

            <div class="card-body">
              <p class="description">{{ item.description || '暂无详细描述...' }}</p>
            </div>

            <div class="card-actions">
              <span class="update-time">
                <i class="el-icon-time"></i> {{ parseTime(item.updateTime, '{y}-{m}-{d}') }}
              </span>
              <div class="btn-group">
                <el-button 
                  type="text" 
                  icon="el-icon-edit" 
                  class="action-btn edit"
                  @click="handleUpdate(item)"
                  v-hasPermi="['biz:culture:edit']"
                >编辑</el-button>
                <el-button 
                  type="text" 
                  icon="el-icon-delete" 
                  class="action-btn delete"
                  @click="handleDelete(item)"
                  v-hasPermi="['biz:culture:remove']"
                >删除</el-button>
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

    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body custom-class="culture-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" label-position="top">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="核心标题" prop="title">
              <el-input v-model="form.title" placeholder="如: 我们的使命" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="简短副标题" prop="subTitle">
              <el-input v-model="form.subTitle" placeholder="如: 让科技更有温度" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="排序权重 (越小越靠前)" prop="sortOrder">
              <el-input-number v-model="form.sortOrder" :min="0" :max="999" controls-position="right" style="width: 100%"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
             </el-col>
        </el-row>

        <el-form-item label="视觉图标 (建议上传透明PNG图标或SVG)" prop="icon">
          <image-upload v-model="form.icon" :limit="1" />
          <div class="form-tip">建议尺寸 64x64，透明背景效果最佳</div>
        </el-form-item>

        <el-form-item label="详细阐述" prop="description">
          <el-input 
            v-model="form.description" 
            type="textarea" 
            :rows="4" 
            placeholder="请详细描述该文化理念的内涵..." 
            resize="none"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定 保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCulture, getCulture, delCulture, addCulture, updateCulture } from "@/api/biz/culture"

export default {
  name: "Culture",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 表格数据
      cultureList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 12, // 卡片布局每页多展示一点
        title: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          { required: true, message: "标题不能为空", trigger: "blur" }
        ],
        sortOrder: [
          { required: true, message: "排序不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 判断是否为图片路径
    isImageUrl(path) {
      return path && (path.indexOf('/') !== -1 || path.indexOf('.') !== -1);
    },
    // 处理图片回显路径 (复用之前Article页面的逻辑思路)
    getImgUrl(path) {
      if (!path) return '';
      if (path.startsWith('http') || path.startsWith('https')) return path;
      return process.env.VUE_APP_BASE_API + path; 
    },
    /** 查询列表 */
    getList() {
      this.loading = true
      listCulture(this.queryParams).then(response => {
        this.cultureList = response.rows
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
        cultureId: null,
        title: null,
        subTitle: null,
        icon: null,
        description: null,
        sortOrder: 0,
        type: null,
        remark: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "新增企业文化条目"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const cultureId = row.cultureId
      getCulture(cultureId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "编辑企业文化"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.cultureId != null) {
            updateCulture(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addCulture(this.form).then(response => {
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
      const cultureId = row.cultureId
      this.$modal.confirm('是否确认删除该条数据？').then(function() {
        return delCulture(cultureId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>

<style scoped lang="scss">
.culture-container {
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

  .header-right {
    display: flex;
    gap: 16px;
    .search-input {
      width: 240px;
    }
  }
}

// 卡片布局
.mb-24 {
  margin-bottom: 24px;
}

.culture-card {
  background: #fff;
  border-radius: 12px;
  padding: 24px;
  border: 1px solid #ebeef5;
  transition: all 0.3s ease;
  position: relative;
  height: 100%;
  display: flex;
  flex-direction: column;

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 12px 32px rgba(0,0,0,0.08);
    border-color: transparent;

    .card-actions {
      opacity: 1;
    }
  }

  // 卡片头部
  .card-header {
    display: flex;
    align-items: flex-start;
    margin-bottom: 16px;
    position: relative;

    .icon-wrapper {
      width: 56px;
      height: 56px;
      border-radius: 12px;
      background-color: #f0f7ff;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-right: 16px;
      flex-shrink: 0;

      .custom-img {
        width: 100%;
        height: 100%;
        border-radius: 12px;
      }
      .custom-icon {
        font-size: 28px;
        color: #409EFF;
      }
    }

    .title-group {
      flex-grow: 1;
      overflow: hidden;
      
      .main-title {
        font-size: 18px;
        font-weight: 600;
        color: #303133;
        margin-bottom: 6px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
      .sub-title {
        font-size: 13px;
        color: #909399;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }

    .sort-badge {
      position: absolute;
      top: -4px;
      right: -4px;
      background: #f2f6fc;
      color: #909399;
      font-size: 12px;
      padding: 2px 8px;
      border-radius: 10px;
      font-family: monospace;
    }
  }

  // 卡片内容
  .card-body {
    flex-grow: 1;
    margin-bottom: 20px;
    
    .description {
      font-size: 14px;
      color: #606266;
      line-height: 1.6;
      display: -webkit-box;
      -webkit-line-clamp: 3; // 限制显示3行
      -webkit-box-orient: vertical;
      overflow: hidden;
      margin: 0;
    }
  }

  // 卡片底部
  .card-actions {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-top: 1px solid #f5f7fa;
    padding-top: 16px;
    opacity: 0.6; // 默认半透明
    transition: opacity 0.3s;

    .update-time {
      font-size: 12px;
      color: #c0c4cc;
    }

    .btn-group {
      .action-btn {
        padding: 0 8px;
        font-size: 14px;
        
        &.edit { color: #409EFF; }
        &.delete { color: #F56C6C; }
        
        &:hover {
          font-weight: bold;
        }
      }
    }
  }
}

.form-tip {
  font-size: 12px;
  color: #909399;
  margin-top: 8px;
  line-height: 1.2;
}
</style>