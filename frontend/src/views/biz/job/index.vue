<template>
  <div class="app-container job-container">
    <div class="page-header">
      <div class="header-left">
        <div class="page-title">职位招聘管理</div>
        <div class="page-desc">发布和管理企业的招聘岗位，配置薪资范围、任职要求及招聘状态。</div>
      </div>
      <div class="header-right">
        <el-input 
          v-model="queryParams.jobTitle" 
          placeholder="搜索职位名称..." 
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
          v-hasPermi="['biz:job:add']"
        >发布新职位</el-button>
      </div>
    </div>

    <div class="job-tabs">
      <el-tabs v-model="activeType" @tab-click="handleTabClick">
        <el-tab-pane label="全部职位" name="all"></el-tab-pane>
        <el-tab-pane v-for="dict in dict.type.biz_job_type" :key="dict.value" :label="dict.label" :name="dict.value"></el-tab-pane>
      </el-tabs>
    </div>

    <div v-loading="loading" class="job-list">
      <el-empty v-if="!loading && jobList.length === 0" description="暂无招聘职位"></el-empty>

      <el-row :gutter="24" v-else>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="6" v-for="item in jobList" :key="item.jobId" class="mb-24">
          <div class="job-card" :class="{ 'is-closed': item.status === '1' }">
            
            <div class="urgent-ribbon" v-if="item.isUrgent === '1'">
              <span>急聘</span>
            </div>

            <div class="card-header">
              <div class="header-top">
                <div class="title-area">
                  <div class="job-title" :title="item.jobTitle">{{ item.jobTitle }}</div>
                  <div class="job-dept">{{ item.department }}</div>
                </div>
                <div class="salary">{{ item.salaryRange }}</div>
              </div>
              <div class="tags-row">
                <el-tag size="mini" effect="plain" type="info"><i class="el-icon-location-outline"></i> {{ item.workLocation }}</el-tag>
                <el-tag size="mini" effect="plain" type="info"><i class="el-icon-suitcase"></i> {{ item.experience }}</el-tag>
                <el-tag size="mini" effect="plain" type="info"><i class="el-icon-school"></i> {{ item.education }}</el-tag>
              </div>
            </div>

            <div class="card-body">
              <div class="desc-preview">
                <div class="label">岗位职责：</div>
                <div class="text">{{ item.jobDesc || '暂无描述...' }}</div>
              </div>
            </div>

            <div class="card-footer">
              <div class="status-switch">
                <span class="label">{{ item.status === '0' ? '招聘中' : '已停止' }}</span>
                <el-switch
                  v-model="item.status"
                  active-value="0"
                  inactive-value="1"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  @change="handleStatusChange(item)"
                ></el-switch>
              </div>
              <div class="actions">
                <el-button type="text" icon="el-icon-edit" @click="handleUpdate(item)">编辑</el-button>
                <el-divider direction="vertical"></el-divider>
                <el-button type="text" icon="el-icon-delete" class="delete-btn" @click="handleDelete(item)">删除</el-button>
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

    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body custom-class="job-dialog" top="5vh">
      <el-form ref="form" :model="form" :rules="rules" label-width="90px" label-position="top">
        <el-tabs v-model="activeTab" class="custom-tabs">
          
          <el-tab-pane label="基本信息" name="basic">
            <span slot="label"><i class="el-icon-user"></i> 基本信息</span>
            
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="职位名称" prop="jobTitle">
                  <el-input v-model="form.jobTitle" placeholder="如: 高级Java开发工程师" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="所属部门" prop="department">
                  <el-input v-model="form.department" placeholder="如: 研发中心 / 市场部" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="职位类型" prop="jobType">
                  <el-select v-model="form.jobType" placeholder="请选择" style="width: 100%">
                    <el-option
                      v-for="dict in dict.type.biz_job_type"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="工作地点" prop="workLocation">
                  <el-input v-model="form.workLocation" placeholder="如: 北京 / 上海 (可远程)" prefix-icon="el-icon-location-outline" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="薪资范围" prop="salaryRange">
                  <el-input v-model="form.salaryRange" placeholder="如: 20k-35k·14薪" prefix-icon="el-icon-money" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="招聘属性" prop="isUrgent">
                  <el-checkbox v-model="form.isUrgent" true-label="1" false-label="0" border>
                    <span class="text-danger font-bold"><i class="el-icon-s-flag"></i> 标记为急聘岗位</span>
                  </el-checkbox>
                </el-form-item>
              </el-col>
            </el-row>
          </el-tab-pane>

          <el-tab-pane label="任职资格" name="require">
            <span slot="label"><i class="el-icon-medal"></i> 任职资格</span>
            
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="经验要求" prop="experience">
                  <el-input v-model="form.experience" placeholder="如: 3-5年 / 经验不限" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="学历要求" prop="education">
                  <el-input v-model="form.education" placeholder="如: 本科及以上" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="任职要求详情 (Requirements)" prop="jobRequire">
              <el-input 
                v-model="form.jobRequire" 
                type="textarea" 
                :rows="8" 
                placeholder="1. 熟练掌握...&#10;2. 具备...能力&#10;3. 有...经验优先" 
              />
            </el-form-item>
          </el-tab-pane>

          <el-tab-pane label="岗位职责" name="desc">
            <span slot="label"><i class="el-icon-document"></i> 岗位职责</span>
            
            <el-form-item label="岗位职责详情 (Responsibilities)" prop="jobDesc">
              <el-input 
                v-model="form.jobDesc" 
                type="textarea" 
                :rows="10" 
                placeholder="1. 负责...&#10;2. 参与...&#10;3. 配合..." 
              />
            </el-form-item>

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
import { listJob, getJob, delJob, addJob, updateJob } from "@/api/biz/job"

export default {
  name: "Job",
  dicts: ['biz_job_type'],
  data() {
    return {
      activeType: 'all', // 顶部Tab状态
      activeTab: 'basic', // 弹窗Tab状态
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      total: 0,
      jobList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 12,
        jobTitle: null,
        jobType: null, // 筛选字段
      },
      form: {},
      rules: {
        jobTitle: [
          { required: true, message: "职位名称不能为空", trigger: "blur" }
        ],
        department: [
          { required: true, message: "所属部门不能为空", trigger: "blur" }
        ],
        salaryRange: [
          { required: true, message: "薪资范围不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** Tab 切换 */
    handleTabClick(tab) {
      this.queryParams.pageNum = 1;
      this.queryParams.jobType = tab.name === 'all' ? null : tab.name;
      this.getList();
    },

    /** 查询列表 */
    getList() {
      this.loading = true
      listJob(this.queryParams).then(response => {
        this.jobList = response.rows
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
        jobId: null,
        jobTitle: null,
        department: null,
        jobType: null,
        workLocation: null,
        salaryRange: null,
        experience: null,
        education: null,
        jobDesc: null,
        jobRequire: null,
        isUrgent: "0",
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
      this.title = "发布新职位"
      this.activeTab = 'basic'
    },
    
    handleUpdate(row) {
      this.reset()
      const jobId = row.jobId
      getJob(jobId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "编辑职位"
        this.activeTab = 'basic'
      })
    },

    /** 状态切换 */
    handleStatusChange(row) {
      let text = row.status === "0" ? "开启招聘" : "停止招聘";
      updateJob({ jobId: row.jobId, status: row.status }).then(() => {
        this.$message.success(text + "成功");
      }).catch(() => {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.jobId != null) {
            updateJob(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addJob(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    
    handleDelete(row) {
      const jobIds = row.jobId
      this.$modal.confirm('确认删除该职位？').then(function() {
        return delJob(jobIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>

<style scoped lang="scss">
.job-container {
  padding: 24px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 84px);
}

// 头部
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

// 顶部 Tabs
.job-tabs {
  background: #fff;
  padding: 10px 24px 0;
  border-radius: 8px 8px 0 0;
  border-bottom: 1px solid #e4e7ed;
  
  ::v-deep .el-tabs__header {
    margin-bottom: 0;
  }
  ::v-deep .el-tabs__nav-wrap::after {
    display: none;
  }
}

// 列表区域
.job-list {
  background: #fff;
  padding: 24px;
  border-radius: 0 0 8px 8px;
  min-height: 400px;
}

.mb-24 {
  margin-bottom: 24px;
}

// 职位卡片
.job-card {
  border: 1px solid #ebeef5;
  border-radius: 8px;
  background: #fff;
  padding: 20px;
  position: relative;
  transition: all 0.3s;
  height: 100%;
  display: flex;
  flex-direction: column;
  overflow: hidden;

  // 停止招聘状态
  &.is-closed {
    background-color: #fafafa;
    .salary { color: #909399 !important; }
    .job-title { color: #909399 !important; text-decoration: line-through; }
  }

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 24px rgba(0,0,0,0.08);
    border-color: #dcdfe6;
  }

  // 急聘角标
  .urgent-ribbon {
    position: absolute;
    top: 0;
    right: 0;
    width: 0;
    height: 0;
    border-top: 40px solid #f56c6c;
    border-left: 40px solid transparent; 
    z-index: 2;
    
    span {
      position: absolute;
      top: -36px;
      right: 2px;
      color: #fff;
      font-size: 12px;
      font-weight: bold;
      transform: rotate(45deg);
    }
  }

  // 头部
  .card-header {
    margin-bottom: 16px;

    .header-top {
      display: flex;
      justify-content: space-between;
      align-items: flex-start;
      margin-bottom: 12px;

      .title-area {
        max-width: 65%;
        .job-title {
          font-size: 18px;
          font-weight: bold;
          color: #303133;
          margin-bottom: 4px;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
        }
        .job-dept {
          font-size: 13px;
          color: #909399;
        }
      }

      .salary {
        font-size: 16px;
        font-weight: bold;
        color: #ff9900; // 金色/橙色
        font-family: monospace;
      }
    }

    .tags-row {
      display: flex;
      gap: 8px;
      flex-wrap: wrap;
    }
  }

  // 内容预览
  .card-body {
    flex-grow: 1;
    margin-bottom: 20px;
    background: #f9fafe;
    padding: 12px;
    border-radius: 6px;

    .desc-preview {
      .label {
        font-size: 12px;
        color: #909399;
        margin-bottom: 4px;
      }
      .text {
        font-size: 13px;
        color: #606266;
        line-height: 1.5;
        display: -webkit-box;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical;
        overflow: hidden;
      }
    }
  }

  // 底部
  .card-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-top: 1px dashed #ebeef5;
    padding-top: 16px;

    .status-switch {
      display: flex;
      align-items: center;
      gap: 8px;
      .label {
        font-size: 12px;
        color: #909399;
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

// 弹窗
.text-danger { color: #f56c6c; }
.font-bold { font-weight: bold; }
</style>