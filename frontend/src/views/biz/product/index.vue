<template>
  <div class="app-container product-container">
    <div class="page-header">
      <div class="header-left">
        <div class="page-title">产品库</div>
        <div class="page-desc">管理企业的核心产品矩阵，配置详情页内容、SEO元数据及视觉素材。</div>
      </div>
      <div class="header-right">
        <el-input 
          v-model="queryParams.productName" 
          placeholder="搜索产品名称..." 
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
          v-hasPermi="['biz:product:add']"
        >发布新产品</el-button>
      </div>
    </div>

    <div v-loading="loading" class="product-list">
      <el-empty v-if="!loading && productList.length === 0" description="暂无产品数据"></el-empty>

      <el-row :gutter="24" v-else>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" v-for="item in productList" :key="item.productId" class="mb-24">
          <div class="product-card" :class="{ 'is-offline': item.status === '1' }">
            <div class="card-cover">
              <el-image 
                :src="getImgUrl(item.mainImg)" 
                fit="cover" 
                class="main-img"
                lazy
              >
                <div slot="error" class="image-slot">
                  <i class="el-icon-picture-outline"></i>
                </div>
              </el-image>
              
              <div class="mini-icon" v-if="item.iconImg" title="首页图标">
                <el-image :src="getImgUrl(item.iconImg)" fit="contain" style="width:100%;height:100%"></el-image>
              </div>

              <div class="status-badge" :class="item.status === '0' ? 'success' : 'info'">
                {{ item.status === '0' ? '已上架' : '已下架' }}
              </div>

              <div class="card-mask">
                <el-button circle type="primary" icon="el-icon-edit" @click="handleUpdate(item)" title="编辑详情"></el-button>
                <el-button circle type="danger" icon="el-icon-delete" @click="handleDelete(item)" title="删除产品"></el-button>
              </div>
            </div>

            <div class="card-info">
              <div class="info-header">
                <div class="title" :title="item.productName">{{ item.productName }}</div>
                <!-- 修改点：列表展示时，使用 dict-tag 自动显示中文名称 -->
                <div class="category-tag">
                  <dict-tag :options="dict.type.biz_product_category" :value="item.categoryId"/>
                </div>
              </div>
              <div class="sub-title" :title="item.subTitle">{{ item.subTitle || '暂无副标题' }}</div>
              
              <div class="info-footer">
                <div class="sort-tag" title="排序权重">
                  <i class="el-icon-sort"></i> {{ item.sortOrder }}
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

    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body custom-class="product-dialog" top="5vh">
      <el-form ref="form" :model="form" :rules="rules" label-width="90px" label-position="top">
        <el-tabs v-model="activeTab" class="custom-tabs">
          
          <el-tab-pane label="基础信息 & 视觉" name="basic">
            <span slot="label"><i class="el-icon-s-operation"></i> 基础信息</span>
            
            <el-row :gutter="24">
              <el-col :span="16">
                <el-form-item label="产品名称" prop="productName">
                  <el-input v-model="form.productName" placeholder="请输入核心产品名称" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <!-- 修改点：将 Input 改为 Select 下拉框 -->
                <el-form-item label="所属分类" prop="categoryId">
                  <el-select v-model="form.categoryId" placeholder="请选择产品分类" style="width: 100%" clearable>
                    <el-option
                      v-for="dict in dict.type.biz_product_category"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="副标题 / Slogan" prop="subTitle">
              <el-input v-model="form.subTitle" placeholder="一句话描述产品的核心价值" />
            </el-form-item>

            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="列表主图 (Main Image)" prop="mainImg">
                  <image-upload v-model="form.mainImg" :limit="1"/>
                  <div class="form-tip">建议尺寸 800x600，用于列表卡片</div>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="小图标 (Icon)" prop="iconImg">
                  <image-upload v-model="form.iconImg" :limit="1"/>
                  <div class="form-tip">建议透明PNG，64x64，用于首页</div>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="技术架构图 (Architecture)" prop="techArchImg">
                  <image-upload v-model="form.techArchImg" :limit="1"/>
                  <div class="form-tip">用于详情页展示技术原理</div>
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="演示视频 (可选)" prop="demoVideoUrl">
              <file-upload 
                v-model="form.demoVideoUrl" 
                :limit="1" 
                :fileType="['mp4']" 
                :fileSize="100"
                tip="仅支持 MP4 格式，建议 100MB 以内"
              />
            </el-form-item>
          </el-tab-pane>

          <el-tab-pane label="详细描述 & 方案" name="detail">
            <span slot="label"><i class="el-icon-document"></i> 详细内容</span>
            
            <el-form-item label="产品简介 (Summary)" prop="summary">
              <el-input v-model="form.summary" type="textarea" :rows="3" placeholder="用于列表页展示的简短介绍..." />
            </el-form-item>

            <el-form-item label="客户痛点 (Pain Points)" prop="painPoints">
              <el-input v-model="form.painPoints" type="textarea" :rows="3" placeholder="多个痛点请用分号 ; 隔开，如：效率低;成本高;难以维护" />
            </el-form-item>

            <el-form-item label="解决方案详情 (Rich Text)" prop="solutions">
              <editor v-model="form.solutions" :min-height="300" placeholder="请输入详细的图文解决方案"/>
            </el-form-item>

            <el-form-item label="技术参数规格 (Specs)" prop="techSpecs">
              <el-input v-model="form.techSpecs" type="textarea" :rows="4" placeholder="支持HTML标签或纯文本描述参数..." />
            </el-form-item>
          </el-tab-pane>

          <el-tab-pane label="SEO & 高级配置" name="advanced">
            <span slot="label"><i class="el-icon-setting"></i> 高级配置</span>
            
            <el-divider content-position="left">SEO 优化 (搜索引擎)</el-divider>
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="SEO 标题" prop="seoTitle">
                  <el-input v-model="form.seoTitle" placeholder="如果不填则默认使用产品名称" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="SEO 关键词" prop="seoKeywords">
                  <el-input v-model="form.seoKeywords" placeholder="多个关键词用英文逗号隔开" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="SEO 描述" prop="seoDescription">
              <el-input v-model="form.seoDescription" type="textarea" :rows="2" />
            </el-form-item>

            <el-divider content-position="left">其他设置</el-divider>
            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="排序权重" prop="sortOrder">
                  <el-input-number v-model="form.sortOrder" :min="0" controls-position="right" style="width: 100%"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="上下架状态" prop="status">
                  <el-radio-group v-model="form.status">
                    <el-radio label="0">上架</el-radio>
                    <el-radio label="1">下架</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="关联产品 ID" prop="relatedIds">
                  <el-input v-model="form.relatedIds" placeholder="如: 1,2,3" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="内部备注" prop="remark">
              <el-input v-model="form.remark" placeholder="仅后台可见" />
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
import { listProduct, getProduct, delProduct, addProduct, updateProduct } from "@/api/biz/product"

export default {
  name: "Product",
  dicts: ['biz_product_category'],
  data() {
    return {
      activeTab: 'basic',
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      total: 0,
      productList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 12, 
        productName: null,
      },
      form: {},
      rules: {
        productName: [
          { required: true, message: "产品名称不能为空", trigger: "blur" }
        ],
        // 修改点：添加分类的验证规则，trigger改为change
        categoryId: [
          { required: true, message: "请选择产品分类", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getImgUrl(path) {
      if (!path) return '';
      if (path.startsWith('http') || path.startsWith('https')) return path;
      return process.env.VUE_APP_BASE_API + path; 
    },

    getList() {
      this.loading = true
      listProduct(this.queryParams).then(response => {
        this.productList = response.rows
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
        productId: null,
        productName: null,
        categoryId: null, // 这个字段现在绑定Select
        subTitle: null,
        mainImg: null,
        iconImg: null,
        techArchImg: null,
        demoVideoUrl: null,
        summary: null,
        painPoints: null,
        solutions: null,
        techSpecs: null,
        relatedIds: null,
        seoTitle: null,
        seoKeywords: null,
        seoDescription: null,
        sortOrder: 0,
        status: "0",
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
      this.title = "发布新产品"
      this.activeTab = 'basic' 
    },
    
    handleUpdate(row) {
      this.reset()
      const productId = row.productId
      getProduct(productId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "编辑产品详情"
        this.activeTab = 'basic'
      })
    },

    handleStatusChange(row) {
      let text = row.status === "0" ? "上架" : "下架";
      updateProduct({ productId: row.productId, status: row.status }).then(() => {
        this.$message.success(text + "成功");
      }).catch(() => {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.productId != null) {
            updateProduct(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addProduct(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    
    handleDelete(row) {
      const productIds = row.productId
      this.$modal.confirm('是否确认删除该产品？').then(function() {
        return delProduct(productIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>

<style scoped lang="scss">
.product-container {
  padding: 24px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 84px);
}

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

.product-list {
  background: transparent;
}

.mb-24 {
  margin-bottom: 24px;
}

.product-card {
  background: #fff;
  border-radius: 12px;
  border: 1px solid #ebeef5;
  overflow: hidden;
  transition: all 0.3s;
  
  &.is-offline {
    filter: grayscale(1); 
    opacity: 0.8;
  }

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 12px 32px rgba(0,0,0,0.08);
    .card-cover .card-mask { opacity: 1; }
  }

  .card-cover {
    position: relative;
    height: 180px;
    background: #f5f7fa;
    overflow: hidden;

    .main-img {
      width: 100%;
      height: 100%;
    }

    .mini-icon {
      position: absolute;
      top: 12px;
      right: 12px;
      width: 36px;
      height: 36px;
      background: #fff;
      border-radius: 8px;
      padding: 4px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.1);
      z-index: 2;
    }

    .status-badge {
      position: absolute;
      top: 12px;
      left: 12px;
      padding: 4px 8px;
      border-radius: 4px;
      font-size: 12px;
      font-weight: bold;
      color: #fff;
      z-index: 2;
      
      &.success { background: #67c23a; }
      &.info { background: #909399; }
    }

    .card-mask {
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

  .card-info {
    padding: 16px;

    .info-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 6px;

      .title {
        font-size: 16px;
        font-weight: 600;
        color: #303133;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        max-width: 70%;
      }
      .category-tag {
        font-size: 12px;
        color: #409EFF;
        background: #ecf5ff;
        padding: 2px 6px;
        border-radius: 4px;
        // 强制不换行
        white-space: nowrap;
      }
    }

    .sub-title {
      font-size: 13px;
      color: #909399;
      height: 20px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      margin-bottom: 12px;
    }

    .info-footer {
      border-top: 1px dashed #ebeef5;
      padding-top: 12px;
      display: flex;
      justify-content: space-between;
      align-items: center;

      .sort-tag {
        font-size: 12px;
        color: #909399;
        i { margin-right: 2px; }
      }
    }
  }
}

.form-tip {
  font-size: 12px;
  color: #909399;
  line-height: 1.4;
  margin-top: 4px;
}
</style>