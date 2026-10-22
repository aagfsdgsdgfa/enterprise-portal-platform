<template>
  <div class="app-container lead-container">
    <div class="page-header">
      <div class="header-left">
        <div class="page-title">线索商机管理</div>
        <div class="page-desc">
          <i class="el-icon-info"></i> 
          管理来自官网留言、资料下载及招商咨询的潜在客户线索。
        </div>
      </div>
      <div class="header-right">
        <el-button plain icon="el-icon-download" size="medium" @click="handleExport" v-hasPermi="['biz:lead:export']">导出报表</el-button>
        <el-button type="primary" icon="el-icon-plus" size="medium" @click="handleAdd" v-hasPermi="['biz:lead:add']">录入线索</el-button>
      </div>
    </div>

    <div class="status-tabs-wrapper">
      <el-tabs v-model="activeStatus" @tab-click="handleTabClick" class="custom-tabs">
        <el-tab-pane label="全部线索" name="all"></el-tab-pane>
        <el-tab-pane name="0">
          <span slot="label"><i class="el-icon-bell"></i> 待处理 <el-badge :value="pendingCount" v-if="pendingCount > 0" class="tab-badge" /></span>
        </el-tab-pane>
        <el-tab-pane name="1">
          <span slot="label"><i class="el-icon-chat-dot-square"></i> 跟进中</span>
        </el-tab-pane>
        <el-tab-pane name="2">
          <span slot="label"><i class="el-icon-success"></i> 已转化</span>
        </el-tab-pane>
        <el-tab-pane name="3">
          <span slot="label"><i class="el-icon-circle-close"></i> 无效/关闭</span>
        </el-tab-pane>
      </el-tabs>

      <div class="search-box">
        <el-input 
          v-model="queryParams.contactName" 
          placeholder="搜索姓名 / 电话 / 公司..." 
          prefix-icon="el-icon-search" 
          size="small" 
          clearable
          @keyup.enter.native="handleQuery"
          style="width: 260px; margin-right: 10px;"
        />
        <el-select v-model="queryParams.type" placeholder="来源渠道" size="small" clearable style="width: 120px; margin-right: 10px;" @change="handleQuery">
          <el-option label="留言咨询" value="contact" />
          <el-option label="资料下载" value="download" />
          <el-option label="招商加盟" value="agent" />
        </el-select>
        <el-button type="primary" icon="el-icon-search" size="small" @click="handleQuery">查询</el-button>
      </div>
    </div>

    <div class="table-wrapper" v-loading="loading">
      <el-table :data="leadList" style="width: 100%" :header-cell-style="{background:'#f7f8fa',color:'#606266'}">
        
        <el-table-column label="客户信息" min-width="260">
          <template slot-scope="scope">
            <div class="contact-card">
              <div class="avatar" :class="'bg-' + (scope.row.leadId % 4)">
                {{ scope.row.contactName ? scope.row.contactName.substring(0, 1) : '客' }}
              </div>
              <div class="info">
                <div class="name-row">
                  <span class="name">{{ scope.row.contactName }}</span>
                  <el-tag size="mini" effect="light" :type="getTypeInfo(scope.row.type).tagType" class="source-tag">
                    {{ getTypeInfo(scope.row.type).label }}
                  </el-tag>
                </div>
                <div class="company" v-if="scope.row.companyName">
                  <i class="el-icon-office-building"></i> {{ scope.row.companyName }}
                </div>
                <div class="contact-detail">
                  <span class="phone click-copy" @click="handleCopy(scope.row.phone)" title="点击复制">
                    <i class="el-icon-phone"></i> {{ scope.row.phone }}
                  </span>
                  <span class="email" v-if="scope.row.email" :title="scope.row.email">
                    <i class="el-icon-message"></i> {{ scope.row.email }}
                  </span>
                </div>
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="需求意向" min-width="240">
          <template slot-scope="scope">
            <div class="intent-box">
              <div class="intent-item" v-if="scope.row.intentProduct">
                <span class="label">意向:</span> 
                <span class="val highlight">{{ scope.row.intentProduct }}</span>
              </div>
              <div class="intent-item" v-if="scope.row.budgetRange">
                <span class="label">预算:</span> 
                <span class="val">{{ scope.row.budgetRange }}</span>
              </div>
              <div class="message-bubble" v-if="scope.row.message">
                "{{ scope.row.message }}"
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="来源与时间" width="200">
          <template slot-scope="scope">
            <div class="meta-info">
              <div class="time"><i class="el-icon-time"></i> {{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}') }}</div>
              <div class="ip" v-if="scope.row.ipAddress">IP: {{ scope.row.ipAddress }}</div>
              <div class="source" v-if="scope.row.sourceUrl">
                <el-popover placement="top" width="300" trigger="hover" :content="scope.row.sourceUrl">
                  <span slot="reference" class="link-text"><i class="el-icon-link"></i> 来源页面</span>
                </el-popover>
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="跟进状态" width="180" align="center" fixed="right">
          <template slot-scope="scope">
            <div class="action-column">
              <el-select 
                v-model="scope.row.status" 
                size="small" 
                class="status-select" 
                :class="'status-' + scope.row.status"
                @change="handleStatusChange(scope.row)"
              >
                <el-option label="待处理" value="0" />
                <el-option label="跟进中" value="1" />
                <el-option label="已转化" value="2" />
                <el-option label="已关闭" value="3" />
              </el-select>
              
              <div class="btn-group">
                <el-button type="text" size="small" @click="handleUpdate(scope.row)">详情/备注</el-button>
                <el-divider direction="vertical"></el-divider>
                <el-button type="text" size="small" class="text-danger" @click="handleDelete(scope.row)">删除</el-button>
              </div>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-if="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>

    <el-drawer
      :title="title"
      :visible.sync="open"
      direction="rtl"
      size="600px"
      append-to-body
      custom-class="lead-drawer"
    >
      <div class="drawer-body">
        <el-form ref="form" :model="form" :rules="rules" label-width="100px" label-position="top">
          
          <div class="section-block">
            <div class="section-title"><i class="el-icon-user"></i> 客户资料</div>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="联系人姓名" prop="contactName">
                  <el-input v-model="form.contactName" placeholder="请输入姓名" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="联系电话" prop="phone">
                  <el-input v-model="form.phone" placeholder="请输入电话" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="企业名称" prop="companyName">
                  <el-input v-model="form.companyName" placeholder="请输入企业名称" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="电子邮箱" prop="email">
                  <el-input v-model="form.email" placeholder="请输入邮箱" />
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <div class="section-block">
            <div class="section-title"><i class="el-icon-s-order"></i> 需求意向</div>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="意向产品 / 下载资料" prop="intentProduct">
                  <el-input v-model="form.intentProduct" placeholder="客户感兴趣的内容" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="预算范围" prop="budgetRange">
                  <el-input v-model="form.budgetRange" placeholder="如: 10-20万" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="留言内容 / 备注" prop="message">
              <el-input v-model="form.message" type="textarea" :rows="3" readonly class="readonly-textarea" />
            </el-form-item>
          </div>

          <div class="section-block highlight">
            <div class="section-title"><i class="el-icon-edit-outline"></i> 跟进处理</div>
            <el-form-item label="当前状态" prop="status">
              <el-radio-group v-model="form.status" size="small">
                <el-radio-button label="0">待处理</el-radio-button>
                <el-radio-button label="1">跟进中</el-radio-button>
                <el-radio-button label="2">已转化</el-radio-button>
                <el-radio-button label="3">无效/关闭</el-radio-button>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="处理结果 / 内部跟进记录" prop="handleResult">
              <el-input 
                v-model="form.handleResult" 
                type="textarea" 
                :rows="4" 
                placeholder="请记录联系情况、客户反馈或处理结果...." 
              />
            </el-form-item>
          </div>

          <div class="section-block readonly-info">
            <el-row>
              <el-col :span="8">
                <div class="label">来源渠道</div>
                <div class="val">{{ getTypeInfo(form.type).label }}</div>
              </el-col>
              <el-col :span="8">
                <div class="label">提交时间</div>
                <div class="val">{{ form.createTime }}</div>
              </el-col>
              <el-col :span="8">
                <div class="label">客户IP</div>
                <div class="val">{{ form.ipAddress }}</div>
              </el-col>
            </el-row>
            <div class="mt-2" v-if="form.sourceUrl">
              <div class="label">来源链接</div>
              <div class="val text-ellipsis">{{ form.sourceUrl }}</div>
            </div>
          </div>

        </el-form>
      </div>
      <div class="drawer-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">保 存 更 新</el-button>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { listLead, getLead, delLead, addLead, updateLead } from "@/api/biz/lead"

export default {
  name: "Lead",
  data() {
    return {
      activeStatus: 'all', // 顶部Tab状态
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      total: 0,
      leadList: [],
      // 待处理数量（模拟，实际可从后端获取）
      pendingCount: 0,
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        type: null,
        contactName: null,
        status: null, // 这里的 status 会随 Tab 变化
      },
      form: {},
      rules: {
        contactName: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
        phone: [{ required: true, message: "电话不能为空", trigger: "blur" }],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 辅助函数：根据类型获取标签样式
    getTypeInfo(type) {
      const map = {
        'contact': { label: '留言咨询', tagType: 'primary' },
        'download': { label: '资料下载', tagType: 'warning' },
        'agent': { label: '招商加盟', tagType: 'success' }
      };
      return map[type] || { label: '其他', tagType: 'info' };
    },

    /** Tab 切换 */
    handleTabClick(tab) {
      this.queryParams.pageNum = 1;
      this.queryParams.status = tab.name === 'all' ? null : tab.name;
      this.getList();
    },

    /** 查询列表 */
    getList() {
      this.loading = true
      listLead(this.queryParams).then(response => {
        this.leadList = response.rows
        this.total = response.total
        // 简单计算待处理数量（仅当前页，实际应由后端统计接口返回）
        this.pendingCount = this.leadList.filter(i => i.status === '0').length;
        this.loading = false
      })
    },
    
    cancel() {
      this.open = false
      this.reset()
    },
    
    reset() {
      this.form = {
        leadId: null,
        type: 'contact',
        contactName: null,
        phone: null,
        companyName: null,
        email: null,
        intentProduct: null,
        message: null,
        budgetRange: null,
        ipAddress: null,
        sourceUrl: null,
        status: "0",
        handleResult: null,
      }
      this.resetForm("form")
    },
    
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    
    handleResetQuery() {
      this.resetForm("queryForm");
      this.activeStatus = 'all';
      this.queryParams.status = null;
      this.handleQuery();
    },

    handleAdd() {
      this.reset()
      this.open = true
      this.title = "录入新线索"
    },
    
    handleUpdate(row) {
      this.reset()
      const leadId = row.leadId
      getLead(leadId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "线索详情与跟进"
      })
    },

    /** 快捷复制 */
    handleCopy(text) {
      if(!text) return;
      // 使用 Clipboard API 或 VueClipboard 插件
      // 这里为了兼容性，简单模拟
      navigator.clipboard.writeText(text).then(() => {
        this.$message.success("已复制: " + text);
      }).catch(() => {
        this.$message.info(text);
      });
    },

    /** 状态快捷切换 */
    handleStatusChange(row) {
      updateLead({ leadId: row.leadId, status: row.status }).then(() => {
        this.$message.success("状态更新成功");
      });
    },
    
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.leadId != null) {
            updateLead(this.form).then(response => {
              this.$modal.msgSuccess("保存成功")
              this.open = false
              this.getList()
            })
          } else {
            addLead(this.form).then(response => {
              this.$modal.msgSuccess("录入成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    
    handleDelete(row) {
      const leadIds = row.leadId
      this.$modal.confirm('确认删除该条线索吗？').then(function() {
        return delLead(leadIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    
    handleExport() {
      this.download('biz/lead/export', {
        ...this.queryParams
      }, `lead_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style scoped lang="scss">
.lead-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 84px);
}

// 1. 顶部 Header
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  
  .header-left {
    .page-title {
      font-size: 20px;
      font-weight: 600;
      color: #303133;
      margin-bottom: 6px;
    }
    .page-desc {
      font-size: 13px;
      color: #909399;
      i { margin-right: 4px; color: #409EFF; }
    }
  }
}

// 2. Tabs 与 搜索
.status-tabs-wrapper {
  background: #fff;
  padding: 10px 20px;
  border-radius: 8px 8px 0 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #f0f2f5;

  .custom-tabs {
    flex-grow: 1;
    ::v-deep .el-tabs__nav-wrap::after { display: none; }
    ::v-deep .el-tabs__header { margin-bottom: 0; }
  }

  .search-box {
    display: flex;
    align-items: center;
  }
}

// 3. 列表样式
.table-wrapper {
  background: #fff;
  padding: 20px;
  border-radius: 0 0 8px 8px;
}

// 客户信息名片
.contact-card {
  display: flex;
  align-items: flex-start;
  padding: 8px 0;

  .avatar {
    width: 42px;
    height: 42px;
    border-radius: 50%;
    color: #fff;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 16px;
    font-weight: bold;
    margin-right: 12px;
    flex-shrink: 0;
    
    // 随机颜色类
    &.bg-0 { background-color: #409EFF; }
    &.bg-1 { background-color: #67C23A; }
    &.bg-2 { background-color: #E6A23C; }
    &.bg-3 { background-color: #F56C6C; }
  }

  .info {
    flex-grow: 1;
    overflow: hidden;

    .name-row {
      display: flex;
      align-items: center;
      margin-bottom: 4px;
      
      .name {
        font-size: 15px;
        font-weight: 600;
        color: #303133;
        margin-right: 8px;
      }
    }

    .company {
      font-size: 12px;
      color: #909399;
      margin-bottom: 4px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    .contact-detail {
      font-size: 12px;
      color: #606266;
      display: flex;
      flex-wrap: wrap;
      gap: 10px;

      .phone.click-copy {
        color: #409EFF;
        cursor: pointer;
        &:hover { text-decoration: underline; }
      }
    }
  }
}

// 需求信息
.intent-box {
  font-size: 13px;
  .intent-item {
    margin-bottom: 4px;
    .label { color: #909399; margin-right: 4px; }
    .val.highlight { color: #E6A23C; font-weight: bold; }
  }
  .message-bubble {
    background: #f4f4f5;
    color: #606266;
    padding: 6px 10px;
    border-radius: 4px;
    font-size: 12px;
    margin-top: 6px;
    line-height: 1.4;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
  }
}

// 来源信息
.meta-info {
  font-size: 12px;
  color: #909399;
  line-height: 1.6;
  .time { color: #606266; }
  .link-text { color: #409EFF; cursor: pointer; }
}

// 状态选择器颜色
.status-select {
  width: 100px;
  margin-bottom: 8px;
  
  &.status-0 ::v-deep .el-input__inner { color: #F56C6C; border-color: #fde2e2; background-color: #fef0f0; } // 待处理-红
  &.status-1 ::v-deep .el-input__inner { color: #E6A23C; border-color: #faecd8; background-color: #fdf6ec; } // 跟进中-黄
  &.status-2 ::v-deep .el-input__inner { color: #67C23A; border-color: #e1f3d8; background-color: #f0f9eb; } // 已转化-绿
  &.status-3 ::v-deep .el-input__inner { color: #909399; background-color: #f4f4f5; } // 已关闭-灰
}

.action-column {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.text-danger { color: #F56C6C; }

// 4. 抽屉样式
.lead-drawer {
  .drawer-body {
    padding: 20px;
    height: calc(100vh - 60px);
    overflow-y: auto;
  }
  .drawer-footer {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 15px 20px;
    background: #fff;
    border-top: 1px solid #e8e8e8;
    text-align: right;
  }
}

.section-block {
  margin-bottom: 24px;
  padding: 16px;
  background: #fcfcfc;
  border-radius: 8px;
  border: 1px solid #ebeef5;

  &.highlight {
    background: #fdf6ec;
    border-color: #faecd8;
    .section-title { color: #E6A23C; border-left-color: #E6A23C; }
  }

  &.readonly-info {
    background: transparent;
    border: none;
    padding: 0;
    .label { font-size: 12px; color: #909399; margin-bottom: 2px; }
    .val { font-size: 13px; color: #303133; }
  }

  .section-title {
    font-size: 15px;
    font-weight: 600;
    color: #303133;
    margin-bottom: 16px;
    padding-left: 10px;
    border-left: 3px solid #409EFF;
  }
}

.readonly-textarea ::v-deep .el-textarea__inner {
  background-color: #f5f7fa;
  border-color: #e4e7ed;
  color: #606266;
  cursor: default;
}
</style>