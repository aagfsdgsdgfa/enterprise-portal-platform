<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px" class="search-form">
      <el-form-item label="文章分类" prop="categoryType">
        <el-select v-model="queryParams.categoryType" placeholder="请选择分类" clearable style="width: 200px">
          <el-option
            v-for="dict in dict.type.biz_article_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="文章标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入关键词"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input
          v-model="queryParams.author"
          placeholder="请输入作者"
          clearable
          style="width: 150px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="发布状态" clearable style="width: 120px">
          <el-option label="已发布" value="0" />
          <el-option label="草稿" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['biz:article:add']"
        >新增文章</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['biz:article:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['biz:article:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['biz:article:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="articleList" @selection-change="handleSelectionChange" border stripe>
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="articleId" width="60" />
      
      <el-table-column label="封面" align="center" prop="coverImg" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.coverImg" :width="50" :height="50"/>
        </template>
      </el-table-column>

      <el-table-column label="标题" align="left" prop="title" min-width="200" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span class="font-bold">{{ scope.row.title }}</span>
          <el-tag size="mini" effect="dark" type="danger" v-if="scope.row.isTop == '1'" style="margin-left: 5px">顶</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="分类" align="center" prop="categoryType" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.biz_article_type" :value="scope.row.categoryType"/>
        </template>
      </el-table-column>

      <el-table-column label="作者" align="center" prop="author" width="100" />
      
      <el-table-column label="数据统计" align="center" width="120">
        <template slot-scope="scope">
          <div style="font-size: 12px; color: #666;">
            <i class="el-icon-view"></i> {{ scope.row.viewCount || 0 }}
          </div>
        </template>
      </el-table-column>

      <el-table-column label="状态" align="center" prop="status" width="80">
        <template slot-scope="scope">
           <el-tag v-if="scope.row.status == '0'" type="success" size="mini">已发布</el-tag>
           <el-tag v-else type="info" size="mini">草稿</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="发布时间" align="center" prop="publishTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.publishTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['biz:article:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            class="text-danger"
            @click="handleDelete(scope.row)"
            v-hasPermi="['biz:article:remove']"
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

    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body top="5vh">
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        
        <el-row :gutter="20">
          <el-col :span="16">
            <el-form-item label="文章标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入引人注目的标题" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
             <el-form-item label="文章分类" prop="categoryType">
              <el-select v-model="form.categoryType" placeholder="请选择" style="width: 100%">
                <el-option
                  v-for="dict in dict.type.biz_article_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
           <el-col :span="16">
              <el-form-item label="文章摘要" prop="summary">
                <el-input v-model="form.summary" type="textarea" :rows="3" placeholder="请输入简短摘要，用于列表展示" />
              </el-form-item>
           </el-col>
           <el-col :span="8">
             <el-form-item label="作者/来源" prop="author">
                <el-input v-model="form.author" placeholder="文章作者" />
              </el-form-item>
              <el-form-item label="发布时间" prop="publishTime">
                <el-date-picker 
                  v-model="form.publishTime" 
                  type="datetime" 
                  value-format="yyyy-MM-dd HH:mm:ss"
                  placeholder="选择发布时间"
                  style="width: 100%">
                </el-date-picker>
              </el-form-item>
           </el-col>
        </el-row>

        <el-form-item label="封面图片" prop="coverImg">
          <image-upload v-model="form.coverImg" :limit="1"/>
          <div class="el-upload__tip">建议尺寸：800x600，格式 jpg/png</div>
        </el-form-item>

        <el-form-item label="文章内容" prop="content">
          <editor v-model="form.content" :min-height="300"/>
        </el-form-item>

        <el-collapse v-model="activeNames" style="margin-bottom: 20px; border: 1px solid #ebeef5; padding: 0 10px; border-radius: 4px;">
           <el-collapse-item title="高级设置 & SEO优化 (点击展开)" name="1">
              <el-row :gutter="20">
                <el-col :span="8">
                  <el-form-item label="是否置顶" prop="isTop">
                    <el-radio-group v-model="form.isTop">
                      <el-radio label="1">是</el-radio>
                      <el-radio label="0">否</el-radio>
                    </el-radio-group>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="状态" prop="status">
                    <el-radio-group v-model="form.status">
                      <el-radio label="0">发布</el-radio>
                      <el-radio label="1">草稿</el-radio>
                    </el-radio-group>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                   <el-form-item label="浏览量" prop="viewCount">
                    <el-input-number v-model="form.viewCount" :min="0" controls-position="right" />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row :gutter="20">
                <el-col :span="12">
                   <el-form-item label="SEO标题" prop="seoTitle">
                    <el-input v-model="form.seoTitle" placeholder="优化搜索引擎标题" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                   <el-form-item label="SEO关键词" prop="seoKeywords">
                    <el-input v-model="form.seoKeywords" placeholder="多个关键词用逗号分隔" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-form-item label="SEO描述" prop="seoDescription">
                <el-input v-model="form.seoDescription" type="textarea" placeholder="搜索引擎显示的描述信息" />
              </el-form-item>
              <el-form-item label="备注" prop="remark">
                <el-input v-model="form.remark" placeholder="内部备注信息" />
              </el-form-item>
           </el-collapse-item>
        </el-collapse>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listArticle, getArticle, delArticle, addArticle, updateArticle } from "@/api/biz/article"

export default {
  name: "Article",
  dicts: ['biz_article_type'],
  data() {
    return {
      activeNames: [],
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      articleList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryType: null,
        title: null,
        author: null,
        status: null, 
      },
      form: {},
      rules: {
        title: [
          { required: true, message: "文章标题不能为空", trigger: "blur" }
        ],
        categoryType: [
          { required: true, message: "请选择分类", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询文章资讯列表 */
    getList() {
      this.loading = true
      listArticle(this.queryParams).then(response => {
        this.articleList = response.rows
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
        articleId: null,
        categoryType: null,
        title: null,
        summary: null,
        coverImg: null,
        content: null,
        author: this.$store.state.user.nickName || 'Admin', 
        isTop: "0", 
        viewCount: 0,
        seoTitle: null,
        seoKeywords: null,
        seoDescription: null,
        status: "0", 
        publishTime: new Date(), 
        remark: null
      }
      this.resetForm("form")
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
      this.ids = selection.map(item => item.articleId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "撰写新文章"
      this.activeNames = [] 
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const articleId = row.articleId || this.ids
      getArticle(articleId).then(response => {
        this.form = response.data
        this.form.isTop = this.form.isTop ? this.form.isTop.toString() : "0";
        this.form.status = this.form.status ? this.form.status.toString() : "0";
        this.open = true
        this.title = "编辑文章"
        this.activeNames = [] 
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.articleId != null) {
            updateArticle(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addArticle(this.form).then(response => {
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
      const articleIds = row.articleId || this.ids
      this.$modal.confirm('是否确认删除所选文章？').then(function() {
        return delArticle(articleIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('biz/article/export', {
        ...this.queryParams
      }, `article_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style scoped lang="scss">
.text-danger {
  color: #f56c6c;
  &:hover {
    color: #f78989;
  }
}
.font-bold {
  font-weight: 600;
  color: #303133;
}
</style>