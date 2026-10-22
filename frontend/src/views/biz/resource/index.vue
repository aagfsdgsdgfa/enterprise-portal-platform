<template>
  <div class="app-container resource-container">
    <div class="filter-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
        <el-form-item label="资料标题" prop="title">
          <el-input
            v-model="queryParams.title"
            placeholder="搜索资料名称..."
            clearable
            prefix-icon="el-icon-search"
            style="width: 220px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="权限要求" prop="needLogin">
          <el-select v-model="queryParams.needLogin" placeholder="全部权限" clearable style="width: 140px">
            <el-option label="公开下载" value="0" />
            <el-option label="需登录" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="全部状态" clearable style="width: 140px">
            <el-option label="正常" value="0" />
            <el-option label="停用" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-row :gutter="10" class="mb8 toolbar-row">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-upload"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['biz:resource:add']"
        >上传新资料</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['biz:resource:edit']"
        >修改信息</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['biz:resource:remove']"
        >批量删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table 
      v-loading="loading" 
      :data="resourceList" 
      @selection-change="handleSelectionChange"
      :header-cell-style="{background:'#f8f9fa',color:'#606266'}"
    >
      <el-table-column type="selection" width="50" align="center" />
      
      <el-table-column label="资料文件" min-width="250">
        <template slot-scope="scope">
          <div class="file-info-cell">
            <div class="file-icon">
              <i :class="getFileIcon(scope.row.filePath)"></i>
            </div>
            <div class="file-meta">
              <div class="file-title" :title="scope.row.title">{{ scope.row.title }}</div>
              <div class="file-desc">
                <span v-if="scope.row.fileSize">{{ scope.row.fileSize }}</span>
                <span v-if="scope.row.fileType"> · {{ scope.row.fileType.toUpperCase() }}</span>
              </div>
            </div>
          </div>
        </template>
      </el-table-column>

      <el-table-column label="访问权限" align="center" width="120">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.needLogin == '1'" type="warning" size="mini" effect="dark"><i class="el-icon-lock"></i> 需登录</el-tag>
          <el-tag v-else type="success" size="mini" effect="plain"><i class="el-icon-unlock"></i> 公开</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="下载热度" align="center" width="120" prop="downloadCount">
        <template slot-scope="scope">
          <div class="download-badge">
            <i class="el-icon-download"></i> {{ scope.row.downloadCount || 0 }}
          </div>
        </template>
      </el-table-column>

      <el-table-column label="状态" align="center" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document-copy"
            @click="handleCopyLink(scope.row)"
          >复制链接</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['biz:resource:edit']"
          >编辑</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            class="delete-btn"
            @click="handleDelete(scope.row)"
            v-hasPermi="['biz:resource:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body custom-class="resource-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" label-position="top">
        
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="上传文件" prop="filePath" class="upload-form-item">
              <div class="upload-wrapper">
                <file-upload 
                  v-model="form.filePath" 
                  :limit="1" 
                  :fileSize="50" 
                  :fileType="['pdf', 'doc', 'docx', 'xls', 'xlsx', 'ppt', 'pptx', 'zip', 'rar']"
                  @input="handleUploadSuccess"
                />
                <div class="upload-tip">
                  支持 PDF, Word, Excel, PPT, Zip 等格式<br>
                  单个文件不超过 50MB
                </div>
              </div>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="资料标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入直观的资料名称" />
            </el-form-item>

            <el-row :gutter="12">
              <el-col :span="12">
                <el-form-item label="文件大小" prop="fileSize">
                  <el-input v-model="form.fileSize" placeholder="例: 2.5MB" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="文件格式" prop="fileType">
                  <el-input v-model="form.fileType" placeholder="例: PDF" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="12">
              <el-col :span="12">
                <el-form-item label="下载权限" prop="needLogin">
                  <el-select v-model="form.needLogin" placeholder="请选择">
                    <el-option label="公开下载" value="0" />
                    <el-option label="需登录" value="1" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="初始热度" prop="downloadCount">
                  <el-input-number v-model="form.downloadCount" :min="0" controls-position="right" style="width: 100%" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="内部备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" :rows="2" placeholder="仅后台可见的备注信息" />
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定 上 传</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listResource, getResource, delResource, addResource, updateResource } from "@/api/biz/resource"

export default {
  name: "Resource",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 资料列表
      resourceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        needLogin: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [{ required: true, message: "资料标题不能为空", trigger: "blur" }],
        filePath: [{ required: true, message: "请上传文件", trigger: "change" }],
        needLogin: [{ required: true, message: "请选择权限要求", trigger: "change" }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 辅助函数：根据文件路径获取图标类名
    getFileIcon(path) {
      if (!path) return 'el-icon-document';
      const ext = path.split('.').pop().toLowerCase();
      if (['pdf'].includes(ext)) return 'el-icon-document text-danger'; // 红色PDF
      if (['doc', 'docx'].includes(ext)) return 'el-icon-document text-primary'; // 蓝色Word
      if (['xls', 'xlsx'].includes(ext)) return 'el-icon-s-data text-success'; // 绿色Excel
      if (['ppt', 'pptx'].includes(ext)) return 'el-icon-data-board text-warning'; // 黄色PPT
      if (['zip', 'rar', '7z'].includes(ext)) return 'el-icon-files text-info'; // 灰色压缩包
      return 'el-icon-document';
    },

    /** 查询列表 */
    getList() {
      this.loading = true
      listResource(this.queryParams).then(response => {
        this.resourceList = response.rows
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
        resourceId: null,
        title: null,
        filePath: null,
        fileSize: null,
        fileType: null,
        needLogin: "0", // 默认公开
        downloadCount: 0,
        status: "0",
        remark: null
      }
      this.resetForm("form")
    },

    /** 模拟上传成功回调 (可选) */
    handleUploadSuccess(val) {
      // 若依的 FileUpload 组件 v-model 绑定的是文件路径字符串
      // 这里可以尝试从 val 中解析文件名填入 title，如果 title 为空
      if (val && !this.form.title) {
        // 简单提取文件名逻辑，实际可能需要根据 url 格式调整
        const fileName = val.substring(val.lastIndexOf('/') + 1);
        this.form.title = decodeURIComponent(fileName); 
      }
      // 注意：真实的文件大小和类型通常由后端在接收文件时处理并保存
      // 前端这里若依组件默认只返回 url，无法直接获取 size/type
      // 建议后端在上传接口中解析并入库，或者人工填写
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.resourceId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "上传新资料"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const resourceId = row.resourceId || this.ids
      getResource(resourceId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改资料信息"
      })
    },
    
    /** 快捷修改状态 */
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      updateResource({ resourceId: row.resourceId, status: row.status }).then(() => {
        this.$message.success(text + "成功");
      }).catch(() => {
        row.status = row.status === "0" ? "1" : "0";
      });
    },

    /** 复制链接 */
    handleCopyLink(row) {
      const url = process.env.VUE_APP_BASE_API + row.filePath; // 拼接完整地址
      this.$copyText(url).then(() => {
        this.$message.success("下载链接已复制到剪贴板");
      }).catch(() => {
        this.$message.error("复制失败，请手动复制");
      });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.resourceId != null) {
            updateResource(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addResource(this.form).then(response => {
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
      const resourceIds = row.resourceId || this.ids
      this.$modal.confirm('确认删除该资料文件吗？').then(function() {
        return delResource(resourceIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>

<style scoped lang="scss">
.resource-container {
  padding: 20px;
  background-color: #fff;
}

// 筛选栏美化
.filter-container {
  background-color: #f8f9fa;
  padding: 15px 15px 0 15px;
  border-radius: 6px;
  margin-bottom: 20px;
}

.toolbar-row {
  margin-bottom: 15px;
}

// 文件信息单元格
.file-info-cell {
  display: flex;
  align-items: center;
  padding: 5px 0;

  .file-icon {
    width: 40px;
    height: 40px;
    border-radius: 6px;
    background-color: #f0f2f5;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 12px;
    font-size: 24px;
    color: #909399;
    flex-shrink: 0;
  }

  .file-meta {
    flex-grow: 1;
    overflow: hidden;

    .file-title {
      font-size: 14px;
      font-weight: 600;
      color: #303133;
      margin-bottom: 4px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    .file-desc {
      font-size: 12px;
      color: #909399;
    }
  }
}

// 下载徽标
.download-badge {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  background-color: #ecf5ff;
  color: #409eff;
  padding: 4px 10px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: bold;
}

// 图标颜色类
.text-danger { color: #f56c6c !important; }
.text-primary { color: #409eff !important; }
.text-success { color: #67c23a !important; }
.text-warning { color: #e6a23c !important; }
.text-info { color: #909399 !important; }

// 删除按钮
.delete-btn {
  color: #f56c6c;
  &:hover { color: #f78989; }
}

// 弹窗内部样式
.upload-wrapper {
  background-color: #fbfdff;
  border: 1px dashed #c0ccda;
  border-radius: 6px;
  padding: 20px;
  text-align: center;

  .upload-tip {
    font-size: 12px;
    color: #909399;
    margin-top: 10px;
    line-height: 1.5;
  }
}
</style>