<template>
  <div class="app-container solution-container">
    <div class="page-header">
      <div class="header-left">
        <div class="page-title">行业解决方案管理</div>
        <div class="page-desc">配置面向不同行业的解决方案，包含痛点分析、核心价值及详情页视觉素材。</div>
      </div>
      <div class="header-right">
        <el-input 
          v-model="queryParams.title" 
          placeholder="搜索方案标题..." 
          prefix-icon="el-icon-search"
          size="medium"
          clearable
          @keyup.enter.native="handleQuery"
          class="search-input"
        ></el-input>
        <el-button 
          type="primary" 
          icon="el-icon-plus" 
          size="medium" 
          @click="handleAdd"
          v-hasPermi="['biz:solution:add']"
        >创建新方案</el-button>
      </div>
    </div>

    <div v-loading="loading" class="solution-list">
      <el-empty v-if="!loading && solutionList.length === 0" description="暂无解决方案数据"></el-empty>

      <el-row :gutter="24" v-else>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="6" v-for="item in solutionList" :key="item.solutionId" class="mb-24">
          <div class="solution-card" :class="{ 'is-hidden': item.status === '1' }">
            <div class="card-visual">
              <el-image 
                :src="getImgUrl(item.bannerImg)" 
                fit="cover" 
                class="banner-img"
                lazy
              >
                <div slot="error" class="image-slot">
                  <i class="el-icon-picture-outline"></i> 暂无Banner
                </div>
              </el-image>
              
              <div class="solution-icon">
                <el-image :src="getImgUrl(item.icon)" fit="cover" style="width:100%;height:100%">
                  <div slot="error" class="icon-slot"><i class="el-icon-menu"></i></div>
                </el-image>
              </div>

              <div class="status-tag" :class="item.status === '0' ? 'active' : ''">
                {{ item.status === '0' ? '已发布' : '草稿/下架' }}
              </div>

              <div class="visual-mask">
                <el-button type="primary" icon="el-icon-edit" circle @click="handleUpdate(item)" title="编辑方案"></el-button>
                <el-button type="danger" icon="el-icon-delete" circle @click="handleDelete(item)" title="删除方案"></el-button>
              </div>
            </div>

            <div class="card-content">
              <div class="header-row">
                <div class="title" :title="item.title">{{ item.title }}</div>
                <div class="sort">#{{ item.sortOrder }}</div>
              </div>
              
              <div class="slogan" :title="item.subTitle">
                <i class="el-icon-collection-tag"></i> {{ item.subTitle || '暂无Slogan' }}
              </div>
              
              <div class="summary">
                {{ item.summary || '暂无摘要描述...' }}
              </div>

              <div class="footer-row">
                <div class="meta">
                  <span title="痛点数量" v-if="item.painPoints"><i class="el-icon-aim"></i> {{ item.painPoints.split(/[,;，；]/).length }}</span>
                  <!-- 修改展示逻辑：如果有关联产品，显示数量 -->
                  <span title="关联产品" v-if="item.relatedProductIds"><i class="el-icon-connection"></i> {{ item.relatedProductIds.split(',').length }}</span>
                </div>
                <el-switch
                  v-model="item.status"
                  active-value="0"
                  inactive-value="1"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  @change="handleStatusChange(item)"
                ></el-switch>
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

    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body custom-class="solution-dialog" top="5vh">
      <el-form ref="form" :model="form" :rules="rules" label-width="90px" label-position="top">
        <el-tabs v-model="activeTab" class="custom-tabs">
          
          <el-tab-pane label="基础概览" name="basic">
            <span slot="label"><i class="el-icon-document"></i> 基础概览</span>
            
            <el-row :gutter="24">
              <el-col :span="16">
                <el-form-item label="方案标题" prop="title">
                  <el-input v-model="form.title" placeholder="如: 智慧校园整体解决方案" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="显示顺序" prop="sortOrder">
                  <el-input-number v-model="form.sortOrder" :min="0" controls-position="right" style="width: 100%"/>
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="副标题 (Slogan)" prop="subTitle">
              <el-input v-model="form.subTitle" placeholder="一句话描述方案的核心价值" />
            </el-form-item>

            <el-form-item label="方案摘要 (用于列表页卡片展示)" prop="summary">
              <el-input v-model="form.summary" type="textarea" :rows="4" placeholder="简要介绍方案背景和目标..." />
            </el-form-item>

            <el-form-item label="发布状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio label="0">立即发布</el-radio>
                <el-radio label="1">存为草稿</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-tab-pane>

          <el-tab-pane label="视觉素材" name="visual">
            <span slot="label"><i class="el-icon-picture-outline"></i> 视觉素材</span>
            
            <el-row :gutter="40">
              <el-col :span="12">
                <el-form-item label="列表图标 (Icon)" prop="icon">
                  <div class="upload-box">
                    <image-upload v-model="form.icon" :limit="1"/>
                    <div class="tip">建议尺寸: 64x64 或 128x128<br>透明背景 PNG/SVG</div>
                  </div>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="详情页顶部大图 (Banner)" prop="bannerImg">
                  <div class="upload-box">
                    <image-upload v-model="form.bannerImg" :limit="1"/>
                    <div class="tip">建议尺寸: 1920x600<br>高清大图 JPG/PNG</div>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
          </el-tab-pane>

          <el-tab-pane label="深度内容" name="content">
            <span slot="label"><i class="el-icon-reading"></i> 深度内容</span>
            
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="客户痛点 (Pain Points)" prop="painPoints">
                  <el-input 
                    v-model="form.painPoints" 
                    type="textarea" 
                    :rows="4" 
                    placeholder="多个痛点请用分号 ; 隔开&#10;例如：数据孤岛严重;人工成本高;监管难度大" 
                  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="方案核心价值 (Value Props)" prop="valueProps">
                  <el-input 
                    v-model="form.valueProps" 
                    type="textarea" 
                    :rows="4" 
                    placeholder="多个价值点请用分号 ; 隔开&#10;例如：全流程自动化;降本增效30%;实时风控" 
                  />
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="方案详细图文介绍" prop="content">
              <editor v-model="form.content" :min-height="300" placeholder="请编写详细的方案介绍..."/>
            </el-form-item>
          </el-tab-pane>

          <el-tab-pane label="关联配置" name="relation">
            <span slot="label"><i class="el-icon-connection"></i> 关联配置</span>
            
            <!-- 修改重点：将Input改为Select -->
            <el-form-item label="关联产品 (Related Products)" prop="relatedProductIds">
              <el-select 
                v-model="form.relatedProductIds" 
                multiple 
                filterable 
                placeholder="请选择关联的产品（支持搜索）"
                style="width: 100%"
              >
                <el-option
                  v-for="product in productOptions"
                  :key="product.productId"
                  :label="product.productName"
                  :value="product.productId"
                >
                  <span style="float: left">{{ product.productName }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">ID: {{ product.productId }}</span>
                </el-option>
              </el-select>
              <div class="form-tip">在方案详情页侧边栏推荐显示的相关产品。</div>
            </el-form-item>

            <el-form-item label="内部备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="仅后台可见的备注说明..." />
            </el-form-item>
          </el-tab-pane>

        </el-tabs>
      </el-form>
      
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定 发 布</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSolution, getSolution, delSolution, addSolution, updateSolution } from "@/api/biz/solution"
// 修改重点1：引入你的API
import { listProducts } from "@/api/website"

export default {
  name: "Solution",
  data() {
    return {
      activeTab: 'basic',
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      total: 0,
      solutionList: [],
      // 修改重点2：定义一个数组存放所有产品
      productOptions: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 12, 
        title: null,
      },
      form: {},
      rules: {
        title: [
          { required: true, message: "方案标题不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
    // 修改重点3：页面创建时获取产品列表
    this.getProductList();
  },
  methods: {
    // 修改重点4：获取产品列表的方法
    getProductList() {
      // 传递 pageSize: 999 确保获取所有产品用于下拉框
      listProducts({ pageNum: 1, pageSize: 999 }).then(response => {
        // 根据若依标准，列表数据通常在 rows 中
        this.productOptions = response.rows; 
      });
    },

    getImgUrl(path) {
      if (!path) return '';
      if (path.startsWith('http') || path.startsWith('https')) return path;
      return process.env.VUE_APP_BASE_API + path; 
    },

    getList() {
      this.loading = true
      listSolution(this.queryParams).then(response => {
        this.solutionList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    
    cancel() {
      this.open = false
      this.reset()
    },
    
    reset() {
      this.form = {
        solutionId: null,
        title: null,
        subTitle: null,
        summary: null,
        icon: null,
        bannerImg: null,
        content: null,
        painPoints: null,
        valueProps: null,
        // 修改重点5：这里初始化为数组，而不是 null，配合 multiple select 使用
        relatedProductIds: [], 
        status: "0",
        sortOrder: 0,
        remark: null
      }
      this.resetForm("form")
    },
    
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "创建新方案"
      this.activeTab = 'basic'
    },
    
    handleUpdate(row) {
      this.reset()
      const solutionId = row.solutionId
      getSolution(solutionId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "编辑方案详情"
        this.activeTab = 'basic'

        // 修改重点6：数据转换 (String -> Array)
        // 后端存的是 "1,2,3" 这种字符串，ElementUI 多选下拉框需要 [1, 2, 3] 这种数组
        if (this.form.relatedProductIds && typeof this.form.relatedProductIds === 'string') {
          // split分割字符串，并map(Number)转为数字类型，以匹配 productOptions 中的 value 类型
          this.form.relatedProductIds = this.form.relatedProductIds.split(',').map(Number);
        } else {
           this.form.relatedProductIds = [];
        }
      })
    },

    handleStatusChange(row) {
      let text = row.status === "0" ? "发布" : "下架";
      updateSolution({ solutionId: row.solutionId, status: row.status }).then(() => {
        this.$message.success(text + "成功");
      }).catch(() => {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 修改重点7：数据转换 (Array -> String)
          // 提交前，把数组 [1, 2, 3] 转回字符串 "1,2,3" 传给后端
          // 使用深拷贝或新对象，避免直接修改 this.form 导致页面下拉框瞬间报错
          let data = { ...this.form };
          if (Array.isArray(data.relatedProductIds)) {
            data.relatedProductIds = data.relatedProductIds.join(',');
          }

          if (data.solutionId != null) {
            updateSolution(data).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addSolution(data).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    
    handleDelete(row) {
      const solutionIds = row.solutionId
      this.$modal.confirm('是否确认删除该解决方案？').then(function() {
        return delSolution(solutionIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>

<style scoped lang="scss">
.solution-container {
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
    gap: 12px;
    .search-input { width: 220px; }
  }
}

// 列表区
.solution-list {
  background: transparent;
}

.mb-24 {
  margin-bottom: 24px;
}

// 方案卡片
.solution-card {
  background: #fff;
  border-radius: 12px;
  border: 1px solid #ebeef5;
  overflow: hidden;
  transition: all 0.3s;
  height: 100%;
  display: flex;
  flex-direction: column;

  &.is-hidden {
    .card-visual { filter: grayscale(0.8); }
    .status-tag { background: #909399; }
  }

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 12px 32px rgba(0,0,0,0.08);
    
    .card-visual .visual-mask { opacity: 1; }
  }

  // 视觉区 (Banner + Icon)
  .card-visual {
    position: relative;
    height: 160px;
    background: #f5f7fa;
    overflow: hidden;

    .banner-img {
      width: 100%;
      height: 100%;
    }

    // 悬浮图标
    .solution-icon {
      position: absolute;
      bottom: -20px; 
      left: 20px;
      width: 64px;
      height: 64px;
      border-radius: 12px;
      background: #fff;
      padding: 4px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.1);
      z-index: 2;
      display: flex;
      align-items: center;
      justify-content: center;

      .icon-slot {
        font-size: 32px;
        color: #409EFF;
      }
    }

    // 状态标签
    .status-tag {
      position: absolute;
      top: 12px;
      right: 12px;
      padding: 4px 10px;
      border-radius: 12px;
      background: rgba(0,0,0,0.5);
      color: #fff;
      font-size: 12px;
      backdrop-filter: blur(4px);
      z-index: 2;

      &.active {
        background: #67c23a;
      }
    }

    // 遮罩
    .visual-mask {
      position: absolute;
      top: 0; left: 0; right: 0; bottom: 0;
      background: rgba(0,0,0,0.6);
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 16px;
      opacity: 0;
      transition: opacity 0.3s;
      z-index: 3;
    }
  }

  // 内容区
  .card-content {
    padding: 36px 20px 20px; 
    flex-grow: 1;
    display: flex;
    flex-direction: column;

    .header-row {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 8px;

      .title {
        font-size: 18px;
        font-weight: 600;
        color: #303133;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
      .sort {
        font-size: 12px;
        color: #909399;
        font-family: monospace;
      }
    }

    .slogan {
      font-size: 13px;
      color: #409EFF;
      margin-bottom: 12px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      
      i { margin-right: 4px; }
    }

    .summary {
      font-size: 14px;
      color: #606266;
      line-height: 1.5;
      margin-bottom: 20px;
      height: 42px; 
      overflow: hidden;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
    }

    .footer-row {
      margin-top: auto;
      padding-top: 16px;
      border-top: 1px dashed #ebeef5;
      display: flex;
      justify-content: space-between;
      align-items: center;

      .meta {
        display: flex;
        gap: 12px;
        font-size: 12px;
        color: #909399;
        
        span {
          display: flex;
          align-items: center;
          gap: 4px;
        }
      }
    }
  }
}

// 弹窗样式
.upload-box {
  background: #fbfdff;
  border: 1px dashed #c0ccda;
  border-radius: 6px;
  padding: 16px;
  text-align: center;
  
  .tip {
    margin-top: 8px;
    font-size: 12px;
    color: #909399;
    line-height: 1.4;
  }
}

.form-tip {
  font-size: 12px;
  color: #909399;
  margin-top: 4px;
}
</style>