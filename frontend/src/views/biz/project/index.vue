<template>
  <div class="app-container project-container">
    <div class="page-header">
      <div class="header-left">
        <div class="page-title">成功案例库</div>
        <div class="page-desc">管理企业过往的标杆项目，展示客户背景、解决方案及核心商业价值数据。</div>
      </div>
      <div class="header-right">
        <el-input 
          v-model="queryParams.projectName" 
          placeholder="搜索项目名称..." 
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
          v-hasPermi="['biz:project:add']"
        >新建案例</el-button>
      </div>
    </div>
    <div class="filter-bar">
      <el-radio-group v-model="queryParams.industryType" size="small" @change="handleQuery">
        <el-radio-button :label="null">全部行业</el-radio-button>
        <el-radio-button 
          v-for="dict in dict.type.biz_industry_type" 
          :key="dict.value" 
          :label="dict.value"
        >{{ dict.label }}</el-radio-button>
      </el-radio-group>
    </div>

    <div v-loading="loading" class="project-list">
      <el-empty v-if="!loading && projectList.length === 0" description="暂无项目案例"></el-empty>

      <el-row :gutter="24" v-else>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" v-for="item in projectList" :key="item.projectId" class="mb-24">
          <div class="project-card">
            <div class="card-cover">
              <el-image 
                :src="getImgUrl(item.coverImg)" 
                fit="cover" 
                class="cover-img"
                lazy
              >
                <div slot="error" class="image-slot">
                  <i class="el-icon-picture-outline"></i>
                </div>
              </el-image>
              
              <!-- 修改点2：列表展示使用 dict-tag 显示中文 -->
              <div class="industry-tag" v-if="item.industryType">
                <dict-tag :options="dict.type.biz_industry_type" :value="item.industryType"/>
              </div>

              <div class="key-data-badge" v-if="item.keyDataVal">
                <span class="val">{{ item.keyDataVal }}</span>
                <span class="desc">{{ item.keyDataDesc || '提升' }}</span>
              </div>

              <div class="card-mask">
                <el-button circle icon="el-icon-edit" type="primary" @click="handleUpdate(item)" title="编辑案例"></el-button>
                <el-button circle icon="el-icon-delete" type="danger" @click="handleDelete(item)" title="删除案例"></el-button>
              </div>
            </div>

            <div class="card-content">
              <div class="project-title" :title="item.projectName">{{ item.projectName }}</div>
              
              <div class="client-info">
                <i class="el-icon-user"></i> 客户：{{ item.clientName || '未公开' }}
              </div>

              <div class="project-desc" :title="item.background">
                {{ item.background || '暂无项目背景描述...' }}
              </div>

              <div class="card-footer">
                <div class="date">
                  <i class="el-icon-date"></i> {{ parseTime(item.completionDate, '{y}-{m}') || '进行中' }}
                </div>
                <div class="sort">#{{ item.sortOrder }}</div>
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

    <el-dialog :title="title" :visible.sync="open" width="850px" append-to-body custom-class="project-dialog" top="5vh">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" label-position="top">
        <el-tabs v-model="activeTab" class="custom-tabs">
          
          <el-tab-pane label="项目概况" name="basic">
            <span slot="label"><i class="el-icon-document-copy"></i> 项目概况</span>
            
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="项目名称" prop="projectName">
                  <el-input v-model="form.projectName" placeholder="如: XX市智慧交通指挥中心项目" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="客户名称" prop="clientName">
                  <el-input v-model="form.clientName" placeholder="如: XX市交通局" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="24">
              <el-col :span="12">
                <!-- 修改点3：行业选择改为字典下拉框 -->
                <el-form-item label="所属行业" prop="industryType">
                  <el-select v-model="form.industryType" placeholder="请选择行业分类" style="width: 100%" clearable>
                    <el-option
                      v-for="dict in dict.type.biz_industry_type"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="完工日期" prop="completionDate">
                  <el-date-picker 
                    v-model="form.completionDate" 
                    type="date" 
                    value-format="yyyy-MM-dd" 
                    placeholder="选择日期" 
                    style="width: 100%">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="项目背景 (Background)" prop="background">
              <el-input 
                type="textarea" 
                v-model="form.background" 
                :rows="4" 
                placeholder="简述项目实施前的痛点、难点及客户需求..."
              />
            </el-form-item>
          </el-tab-pane>

          <el-tab-pane label="详情与视觉" name="visual">
            <span slot="label"><i class="el-icon-picture-outline"></i> 详情与视觉</span>
            
            <el-row :gutter="40">
              <el-col :span="12">
                <el-form-item label="列表封面图 (Cover)" prop="coverImg">
                  <div class="upload-box">
                    <image-upload v-model="form.coverImg" :limit="1"/>
                    <div class="tip">建议尺寸: 600x400 (3:2)</div>
                  </div>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="详情页 Banner 大图" prop="detailBanner">
                  <div class="upload-box">
                    <image-upload v-model="form.detailBanner" :limit="1"/>
                    <div class="tip">建议尺寸: 1920x500</div>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="解决方案描述 (Solution)" prop="solutionDesc">
              <el-input 
                type="textarea" 
                v-model="form.solutionDesc" 
                :rows="6" 
                placeholder="详细描述我们在该项目中提供的技术手段、产品组合及实施策略..."
              />
            </el-form-item>
          </el-tab-pane>

          <el-tab-pane label="价值与成果" name="value">
            <span slot="label"><i class="el-icon-data-line"></i> 价值与成果</span>
            
            <div class="section-title">核心成效指标</div>
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="关键数据值" prop="keyDataVal">
                  <el-input v-model="form.keyDataVal" placeholder="如: 50%, 3000万, 10倍" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="数据说明" prop="keyDataDesc">
                  <el-input v-model="form.keyDataDesc" placeholder="如: 效率提升, 节省成本, 覆盖人群" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="建设成果详细描述 (Achievement)" prop="achievement">
              <el-input 
                type="textarea" 
                v-model="form.achievement" 
                :rows="4" 
                placeholder="描述项目上线后带来的社会效益、经济效益或客户评价..."
              />
            </el-form-item>

            <el-divider></el-divider>

            <el-row :gutter="24">
              <el-col :span="12">
                <!-- 修改点4：关联产品改为多选下拉框 -->
                <el-form-item label="关联产品" prop="relatedProductIds">
                   <el-select 
                    v-model="form.relatedProductIds" 
                    multiple 
                    filterable 
                    placeholder="请选择用到的产品"
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
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="排序权重" prop="sortOrder">
                  <el-input-number v-model="form.sortOrder" :min="0" controls-position="right" style="width: 100%"/>
                </el-form-item>
              </el-col>
            </el-row>
          </el-tab-pane>

        </el-tabs>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定 保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProject, getProject, delProject, addProject, updateProject } from "@/api/biz/project"
// 修改点5：引入产品API
import { listProducts } from "@/api/website"

export default {
  name: "Project",
  // 修改点6：引入字典配置
  dicts: ['biz_industry_type'],
  data() {
    return {
      activeTab: 'basic',
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      total: 0,
      projectList: [],
      // 修改点7：定义产品选项数组
      productOptions: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 12, 
        projectName: null,
        industryType: null, 
      },
      form: {},
      rules: {
        projectName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" }
        ],
        industryType: [
          { required: true, message: "请选择所属行业", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
    // 修改点8：加载产品列表
    this.getProductList()
  },
  methods: {
    // 修改点9：获取产品列表方法
    getProductList() {
      listProducts({ pageNum: 1, pageSize: 999 }).then(response => {
        this.productOptions = response.rows; 
      });
    },

    getImgUrl(path) {
      if (!path) return '';
      if (path.startsWith('http') || path.startsWith('https')) return path;
      return process.env.VUE_APP_BASE_API + path; 
    },

    /** 查询列表 */
    getList() {
      this.loading = true
      listProject(this.queryParams).then(response => {
        this.projectList = response.rows
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
        projectId: null,
        projectName: null,
        clientName: null,
        industryType: null,
        coverImg: null,
        detailBanner: null,
        background: null,
        solutionDesc: null,
        achievement: null,
        keyDataVal: null,
        keyDataDesc: null,
        completionDate: null,
        // 修改点10：初始化为空数组
        relatedProductIds: [],
        sortOrder: 0,
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
      this.title = "新建项目案例"
      this.activeTab = 'basic'
    },
    
    handleUpdate(row) {
      this.reset()
      const projectId = row.projectId
      getProject(projectId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "编辑项目案例"
        this.activeTab = 'basic'

        // 修改点11：字符串转数组 (String -> Array)
        if (this.form.relatedProductIds && typeof this.form.relatedProductIds === 'string') {
          this.form.relatedProductIds = this.form.relatedProductIds.split(',').map(Number);
        } else {
           this.form.relatedProductIds = [];
        }
      })
    },
    
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 修改点12：数组转字符串提交 (Array -> String)
          let data = { ...this.form };
          if (Array.isArray(data.relatedProductIds)) {
            data.relatedProductIds = data.relatedProductIds.join(',');
          }

          if (data.projectId != null) {
            updateProject(data).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addProject(data).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    
    handleDelete(row) {
      const projectIds = row.projectId
      this.$modal.confirm('确认删除该项目案例？').then(function() {
        return delProject(projectIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>

<style scoped lang="scss">
.project-container {
  padding: 24px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 84px);
}

// 头部
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
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

// 筛选栏
.filter-bar {
  margin-bottom: 20px;
  text-align: center;
}

// 列表区
.project-list {
  background: transparent;
}

.mb-24 {
  margin-bottom: 24px;
}

// 案例卡片
.project-card {
  background: #fff;
  border-radius: 12px;
  border: 1px solid #ebeef5;
  overflow: hidden;
  transition: all 0.3s;
  height: 100%;
  display: flex;
  flex-direction: column;

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 12px 32px rgba(0,0,0,0.08);
    .card-cover .card-mask { opacity: 1; }
  }

  // 封面图
  .card-cover {
    position: relative;
    height: 180px;
    background: #f5f7fa;
    overflow: hidden;

    .cover-img {
      width: 100%;
      height: 100%;
    }

    // 行业标签
    .industry-tag {
      position: absolute;
      top: 12px;
      left: 12px;
      background: rgba(0,0,0,0.6);
      color: #fff;
      font-size: 12px;
      padding: 4px 10px;
      border-radius: 20px;
      backdrop-filter: blur(4px);
      z-index: 2;
      
      // 强制让dict-tag生成的span继承颜色
      ::v-deep .el-tag {
        background: transparent;
        border: none;
        color: #fff;
        padding: 0;
        margin: 0;
      }
    }

    // 关键数据徽标
    .key-data-badge {
      position: absolute;
      bottom: 12px;
      right: 12px;
      background: #fff;
      padding: 6px 12px;
      border-radius: 8px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.1);
      display: flex;
      flex-direction: column;
      align-items: center;
      line-height: 1;
      z-index: 2;

      .val {
        font-size: 18px;
        font-weight: bold;
        color: #409EFF;
        margin-bottom: 2px;
      }
      .desc {
        font-size: 10px;
        color: #909399;
        text-transform: uppercase;
      }
    }

    // 遮罩
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

  // 内容区
  .card-content {
    padding: 16px;
    flex-grow: 1;
    display: flex;
    flex-direction: column;

    .project-title {
      font-size: 16px;
      font-weight: 600;
      color: #303133;
      margin-bottom: 8px;
      line-height: 1.4;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      overflow: hidden;
      height: 44px; // 固定两行高度
    }

    .client-info {
      font-size: 12px;
      color: #909399;
      margin-bottom: 12px;
      display: flex;
      align-items: center;
      i { margin-right: 4px; }
    }

    .project-desc {
      font-size: 13px;
      color: #606266;
      line-height: 1.5;
      margin-bottom: 16px;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      overflow: hidden;
      flex-grow: 1;
    }

    .card-footer {
      border-top: 1px dashed #ebeef5;
      padding-top: 12px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-size: 12px;
      color: #909399;

      .sort {
        font-family: monospace;
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
  }
}

.section-title {
  font-size: 14px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 16px;
  border-left: 3px solid #409EFF;
  padding-left: 8px;
}
</style>