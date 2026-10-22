<template>
  <div class="app-container banner-container">
    <div class="page-header">
      <div class="header-left">
        <div class="page-title">轮播图配置</div>
        <div class="page-desc">管理门户网站首页的 Banner 广告图，支持区分 PC 端与移动端展示。</div>
      </div>
      <div class="header-right">
        <el-button 
          type="primary" 
          icon="el-icon-plus" 
          size="medium" 
          @click="handleAdd"
          v-hasPermi="['biz:banner:add']"
        >新增 Banner</el-button>
      </div>
    </div>

    <div class="device-tabs">
      <el-tabs v-model="activeDevice" @tab-click="handleTabClick">
        <el-tab-pane label="PC 端 (电脑版)" name="0">
          <span slot="label"><i class="el-icon-monitor"></i> PC 端 (电脑版)</span>
        </el-tab-pane>
        <el-tab-pane label="移动端 (手机版)" name="1">
          <span slot="label"><i class="el-icon-mobile-phone"></i> 移动端 (手机版)</span>
        </el-tab-pane>
      </el-tabs>
    </div>

    <div v-loading="loading" class="banner-list">
      <el-empty v-if="!loading && bannerList.length === 0" description="该设备端暂无轮播图数据"></el-empty>

      <el-row :gutter="24" v-else>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" v-for="item in bannerList" :key="item.bannerId" class="mb-24">
          <div class="banner-card">
            <div class="card-image">
              <el-image 
                :src="getImgUrl(item.imageUrl)" 
                fit="cover" 
                class="banner-img"
                :preview-src-list="[getImgUrl(item.imageUrl)]"
              >
                <div slot="error" class="image-slot">
                  <i class="el-icon-picture-outline"></i>
                </div>
              </el-image>
              <div class="status-tag" :class="item.status === '0' ? 'active' : 'inactive'">
                {{ item.status === '0' ? '展示中' : '已停用' }}
              </div>
              <div class="card-mask">
                <el-button circle icon="el-icon-edit" type="primary" @click="handleUpdate(item)" title="编辑"></el-button>
                <el-button circle icon="el-icon-delete" type="danger" @click="handleDelete(item)" title="删除"></el-button>
              </div>
            </div>

            <div class="card-info">
              <div class="info-row main">
                <span class="title" :title="item.title">{{ item.title }}</span>
                <span class="sort-badge">排序: {{ item.sortOrder }}</span>
              </div>
              <div class="info-row sub">
                <div class="link-url" :title="item.linkUrl">
                  <i class="el-icon-link"></i> {{ item.linkUrl || '无跳转链接' }}
                </div>
              </div>
              <div class="info-row actions">
                <span class="target-tag">{{ item.target === '_blank' ? '新窗口打开' : '本页跳转' }}</span>
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

    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body custom-class="banner-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" label-position="top">
        <el-row :gutter="24">
          <el-col :span="10">
            <el-form-item label="轮播图片" prop="imageUrl">
              <div class="upload-area">
                <image-upload v-model="form.imageUrl" :limit="1" />
                <div class="upload-tip">
                  <span v-if="form.deviceType === '0'">建议尺寸: 1920 x 600</span>
                  <span v-else>建议尺寸: 750 x 400</span>
                  <br>支持 JPG/PNG，文件小于 2MB
                </div>
              </div>
            </el-form-item>
          </el-col>

          <el-col :span="14">
            <el-form-item label="Banner 标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入标题 (用于alt描述)" />
            </el-form-item>
            
            <el-form-item label="跳转链接" prop="linkUrl">
              <el-input v-model="form.linkUrl" placeholder="http://..." prefix-icon="el-icon-link"/>
            </el-form-item>

            <el-row :gutter="12">
              <el-col :span="12">
                <el-form-item label="排序权重" prop="sortOrder">
                  <el-input-number v-model="form.sortOrder" :min="0" controls-position="right" style="width: 100%"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="设备类型" prop="deviceType">
                  <el-select v-model="form.deviceType" placeholder="请选择" style="width: 100%">
                    <el-option label="PC 端" value="0" />
                    <el-option label="移动端" value="1" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="12">
              <el-col :span="12">
                <el-form-item label="打开方式" prop="target">
                  <el-radio-group v-model="form.target" size="small">
                    <el-radio-button label="_self">本页</el-radio-button>
                    <el-radio-button label="_blank">新窗</el-radio-button>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="状态" prop="status">
                  <el-radio-group v-model="form.status" size="small">
                    <el-radio-button label="0">显示</el-radio-button>
                    <el-radio-button label="1">隐藏</el-radio-button>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
        
        <el-form-item label="备注说明" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="2" placeholder="仅内部可见的备注信息" resize="none"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listBanner, getBanner, delBanner, addBanner, updateBanner } from "@/api/biz/banner"

export default {
  name: "Banner",
  dicts: ['biz_device_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 轮播图列表
      bannerList: [],
      // 默认选中的Tab（设备类型：0-PC, 1-Mobile）
      activeDevice: '0', 
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 12, // 卡片模式每页显示12个比较合适
        deviceType: '0', // 默认查PC端
        title: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [{ required: true, message: "标题不能为空", trigger: "blur" }],
        imageUrl: [{ required: true, message: "请上传图片", trigger: "blur" }],
        deviceType: [{ required: true, message: "请选择设备类型", trigger: "change" }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 图片路径处理
    getImgUrl(path) {
      if (!path) return '';
      if (path.startsWith('http') || path.startsWith('https')) return path;
      return process.env.VUE_APP_BASE_API + path;
    },
    
    /** 切换Tab事件 */
    handleTabClick(tab) {
      this.queryParams.pageNum = 1;
      this.queryParams.deviceType = tab.name;
      this.getList();
    },

    /** 查询轮播图列表 */
    getList() {
      this.loading = true
      listBanner(this.queryParams).then(response => {
        this.bannerList = response.rows
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
        bannerId: null,
        title: null,
        imageUrl: null,
        linkUrl: null,
        target: '_blank', // 默认新窗口
        sortOrder: 0,
        deviceType: this.activeDevice, // 默认新增当前Tab选中的设备类型
        status: "0",
        remark: null
      }
      this.resetForm("form")
    },
    
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "新增 Banner"
    },
    
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const bannerId = row.bannerId
      getBanner(bannerId).then(response => {
        this.form = response.data
        // 确保单选框回显正确
        this.form.target = this.form.target || '_blank';
        this.open = true
        this.title = "编辑 Banner"
      })
    },

    /** 列表上的开关状态修改 */
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要' + text + ' "' + row.title + '" 吗？').then(function() {
        return updateBanner({ bannerId: row.bannerId, status: row.status });
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        // 如果取消，状态回滚
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.bannerId != null) {
            updateBanner(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addBanner(this.form).then(response => {
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
      const bannerIds = row.bannerId
      this.$modal.confirm('是否确认删除该 Banner？').then(function() {
        return delBanner(bannerIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>

<style scoped lang="scss">
.banner-container {
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
}

// Tabs 样式微调
.device-tabs {
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
.banner-list {
  background: #fff;
  padding: 24px;
  border-radius: 0 0 8px 8px;
  min-height: 400px;
}

.mb-24 {
  margin-bottom: 24px;
}

// 卡片样式
.banner-card {
  border: 1px solid #ebeef5;
  border-radius: 12px;
  overflow: hidden;
  transition: all 0.3s;
  background: #fff;

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 12px 32px rgba(0,0,0,0.08);
    
    .card-image {
      .card-mask {
        opacity: 1;
      }
    }
  }

  // 上半部分：图片
  .card-image {
    position: relative;
    height: 160px;
    background: #f5f7fa;
    overflow: hidden;

    .banner-img {
      width: 100%;
      height: 100%;
      display: block;
    }

    // 状态标签
    .status-tag {
      position: absolute;
      top: 10px;
      left: 10px;
      padding: 4px 8px;
      border-radius: 4px;
      font-size: 12px;
      font-weight: bold;
      color: #fff;
      z-index: 2;
      box-shadow: 0 2px 6px rgba(0,0,0,0.2);

      &.active {
        background: #67c23a;
      }
      &.inactive {
        background: #909399;
      }
    }

    // 悬浮遮罩
    .card-mask {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
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

  // 下半部分：信息
  .card-info {
    padding: 16px;

    .info-row {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 8px;

      &.main {
        .title {
          font-weight: 600;
          color: #303133;
          font-size: 16px;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
          max-width: 70%;
        }
        .sort-badge {
          background: #f0f2f5;
          color: #909399;
          font-size: 12px;
          padding: 2px 6px;
          border-radius: 4px;
        }
      }

      &.sub {
        .link-url {
          font-size: 12px;
          color: #909399;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
          width: 100%;
          
          i { margin-right: 4px; }
        }
      }

      &.actions {
        margin-top: 12px;
        margin-bottom: 0;
        padding-top: 12px;
        border-top: 1px solid #f5f7fa;

        .target-tag {
          font-size: 12px;
          color: #606266;
          background: #fdf6ec;
          color: #e6a23c;
          padding: 2px 6px;
          border-radius: 4px;
        }
      }
    }
  }
}

// 弹窗样式调整
.upload-area {
  background: #fbfdff;
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