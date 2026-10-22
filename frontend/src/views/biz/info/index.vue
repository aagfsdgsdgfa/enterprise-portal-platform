<template>
  <div class="app-container setting-container">
    <div class="page-header">
      <div class="header-content">
        <div class="title">企业信息配置</div>
        <div class="sub-title">管理公司的基础信息、品牌标识及联系方式，这些信息将直接展示在门户网站首页及底部。</div>
      </div>
      <div class="header-action">
        <el-button 
          type="primary" 
          icon="el-icon-check" 
          :loading="loading" 
          @click="submitForm"
          size="medium"
        >保存配置</el-button>
      </div>
    </div>

    <el-row :gutter="24">
      <el-col :span="16">
        <el-card shadow="hover" class="setting-card">
          <div slot="header" class="card-header">
            <span class="header-title"><i class="el-icon-office-building"></i> 基础运营信息</span>
          </div>
          <el-form ref="form" :model="form" :rules="rules" label-position="top" size="medium">
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="公司全称" prop="companyName">
                  <el-input v-model="form.companyName" placeholder="请输入工商注册全称" prefix-icon="el-icon-school" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="办公地址" prop="address">
                  <el-input v-model="form.address" placeholder="请输入详细办公地址" prefix-icon="el-icon-location-information" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="对外官方热线" prop="phoneHotline">
                  <el-input v-model="form.phoneHotline" placeholder="例: 400-888-8888" prefix-icon="el-icon-phone-outline" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="商务招商电话" prop="phoneSales">
                  <el-input v-model="form.phoneSales" placeholder="例: 13800138000" prefix-icon="el-icon-mobile-phone" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="企业联系邮箱" prop="email">
                  <el-input v-model="form.email" placeholder="contact@example.com" prefix-icon="el-icon-message" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="工作时间" prop="workTime">
                  <el-input v-model="form.workTime" placeholder="例: 周一至周五 9:00-18:00" prefix-icon="el-icon-time" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="地理位置纬度" prop="latitude">
                  <el-input v-model="form.latitude" placeholder="百度地图Latitude" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="地理位置经度" prop="longitude">
                  <el-input v-model="form.longitude" placeholder="百度地图Longitude" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="公司简介 / 备注" prop="remark">
              <el-input 
                type="textarea" 
                v-model="form.remark" 
                :rows="4" 
                placeholder="请输入简短的公司介绍，可能用于网站Meta描述或底部简介..." 
                resize="none"
              />
            </el-form-item>
          </el-form>
        </el-card>

        <el-card shadow="hover" class="setting-card mt-4">
          <div slot="header" class="card-header">
            <span class="header-title"><i class="el-icon-monitor"></i> 网站底部信息</span>
          </div>
          <el-form :model="form" label-position="top" size="medium">
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="ICP备案号" prop="icpBeian">
                  <el-input v-model="form.icpBeian" placeholder="例: 滇ICP备20260001号" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="版权所有信息" prop="copyright">
                  <el-input v-model="form.copyright" placeholder="例: Copyright © 2025 NovaTech AI. All Rights Reserved." />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card shadow="hover" class="setting-card">
          <div slot="header" class="card-header">
            <span class="header-title"><i class="el-icon-picture-outline"></i> 品牌视觉资产</span>
          </div>
          <el-form :model="form" label-position="top">
            
            <el-form-item label="深色模式 Logo (用于浅色背景)" prop="logoDark">
              <div class="upload-wrapper">
                <image-upload v-model="form.logoDark" :limit="1" />
                <div class="upload-tip">建议透明PNG，高度60px</div>
              </div>
            </el-form-item>

            <el-divider></el-divider>

            <el-form-item label="浅色模式 Logo (用于深色背景)" prop="logoLight">
              <div class="upload-wrapper dark-bg">
                <image-upload v-model="form.logoLight" :limit="1" />
                <div class="upload-tip">建议白色透明PNG，高度60px</div>
              </div>
            </el-form-item>

            <el-divider></el-divider>

            <el-form-item label="官方公众号二维码" prop="qrCode">
              <div class="upload-wrapper">
                <image-upload v-model="form.qrCode" :limit="1" />
                <div class="upload-tip">用于底部关注栏，建议正方形</div>
              </div>
            </el-form-item>

          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// 引入API，确保路径正确
import { listInfo, addInfo, updateInfo } from "@/api/biz/info";

export default {
  name: "InfoConfig",
  data() {
    return {
      loading: false,
      // 表单参数
      form: {
        infoId: null,
        companyName: '',
        address: '',
        phoneHotline: '',
        phoneSales: '',
        email: '',
        workTime: '',
        logoDark: '',
        logoLight: '',
        qrCode: '',
        icpBeian: '',
        copyright: '',
        latitude: '',
        longitude: '',
        remark: ''
      },
      // 表单校验规则
      rules: {
        companyName: [
          { required: true, message: "请输入公司名称", trigger: "blur" }
        ],
        address: [
          { required: true, message: "请输入办公地址", trigger: "blur" }
        ],
        phoneHotline: [
          { required: true, message: "请输入官方热线", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.initData();
  },
  methods: {
    /** 初始化数据：获取唯一的公司配置 */
    initData() {
      this.loading = true;
      // 这里的 pageNum: 1, pageSize: 1 只是为了取第一条数据
      listInfo({ pageNum: 1, pageSize: 1 }).then(response => {
        if (response.rows && response.rows.length > 0) {
          // 如果数据库有数据，直接回显第一条
          this.form = response.rows[0];
        } else {
          // 如果没有数据，保持表单为空，infoId 为 null，提交时自动走新增逻辑
          this.resetForm();
        }
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.form.infoId != null) {
            // 有ID，走修改逻辑
            updateInfo(this.form).then(response => {
              this.$modal.msgSuccess("配置已更新");
              this.loading = false;
              this.initData(); // 刷新数据
            }).catch(() => { this.loading = false });
          } else {
            // 无ID，走新增逻辑
            addInfo(this.form).then(response => {
              this.$modal.msgSuccess("配置已创建");
              this.loading = false;
              this.initData();
            }).catch(() => { this.loading = false });
          }
        }
      });
    },
    
    /** 重置表单 */
    resetForm() {
      this.form = {
        infoId: null,
        companyName: '',
        address: '',
        phoneHotline: '',
        phoneSales: '',
        email: '',
        workTime: '',
        logoDark: '',
        logoLight: '',
        qrCode: '',
        icpBeian: '',
        copyright: '',
        latitude: '',
        longitude: '',
        remark: ''
      };
    }
  }
};
</script>

<style scoped lang="scss">
.setting-container {
  padding: 20px 30px;
  background-color: #f5f7fa;
  min-height: 100vh;
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
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05);

  .header-content {
    .title {
      font-size: 20px;
      font-weight: 600;
      color: #303133;
      margin-bottom: 8px;
    }
    .sub-title {
      font-size: 13px;
      color: #909399;
    }
  }
}

// 卡片样式
.setting-card {
  border: none;
  border-radius: 8px;
  margin-bottom: 24px;
  
  // 覆盖 Element UI 默认的卡片头部样式
  ::v-deep .el-card__header {
    padding: 16px 20px;
    border-bottom: 1px solid #f0f2f5;
  }

  .card-header {
    display: flex;
    align-items: center;
    .header-title {
      font-size: 16px;
      font-weight: 600;
      color: #303133;
      display: flex;
      align-items: center;
      gap: 8px;
      
      i {
        color: #409EFF; // 主题色
        font-size: 18px;
      }
    }
  }
}

// 辅助间距类
.mt-4 {
  margin-top: 24px;
}

// 上传组件包装器
.upload-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 15px;
  background-color: #f9f9f9;
  border-radius: 6px;
  border: 1px dashed #e4e7ed;
  transition: all 0.3s;

  &:hover {
    border-color: #409EFF;
    background-color: #fff;
  }

  // 浅色Logo专用的深色背景
  &.dark-bg {
    background-color: #545c64;
    border-color: #666;
    .upload-tip { color: #ccc; }
  }

  .upload-tip {
    font-size: 12px;
    color: #909399;
    margin-top: 8px;
  }
}

// 覆盖输入框样式，使其更显高级
::v-deep .el-input__inner, ::v-deep .el-textarea__inner {
  border-radius: 4px;
  &:focus {
    border-color: #409EFF;
    box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.1);
  }
}
</style>