<template>
  <div class="app-container home">
    <el-row :gutter="40" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-blue">
            <i class="el-icon-s-shop card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">产品总数</div>
            <div class="card-panel-num">{{ stats.productCount }}</div>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-green">
            <i class="el-icon-document-copy card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">新闻资讯</div>
            <div class="card-panel-num">{{ stats.articleCount }}</div>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-red">
            <i class="el-icon-user-solid card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">招聘职位</div>
            <div class="card-panel-num">{{ stats.jobCount }}</div>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-cyan">
            <i class="el-icon-s-data card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">成功案例</div>
            <div class="card-panel-num">{{ stats.projectCount }}</div>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="12">
        <div class="chart-wrapper">
          <div class="chart-title">产品分类统计</div>
          <div ref="barChart" style="height: 350px;"></div>
        </div>
      </el-col>
      
      <el-col :xs="24" :sm="24" :lg="12">
        <div class="chart-wrapper">
          <div class="chart-title">招聘部门分布</div>
          <div ref="pieChart" style="height: 350px;"></div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="12">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>系统信息</span>
          </div>
          <div class="body">
            <p><i class="el-icon-info"></i> 当前版本：v{{ version }}</p>
            <p><i class="el-icon-s-platform"></i> 后端框架：SpringBoot</p>
            <p><i class="el-icon-menu"></i> 前端框架：Vue 2 + Element UI</p>
          </div>
        </el-card>
      </el-col>

      <el-col :xs="24" :sm="24" :md="12" :lg="12">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>快速入口</span>
          </div>
          <div class="body">
            <el-button type="primary" icon="el-icon-plus" @click="handleJump('/ywgl/Product')">发布产品</el-button>
            <el-button type="success" icon="el-icon-edit" @click="handleJump('/nrzx/Article')">发布新闻</el-button>
            <el-button type="warning" icon="el-icon-user" @click="handleJump('/khzp/Job')">发布职位</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// 引入 ECharts (需要先 npm install echarts --save)
import * as echarts from 'echarts';
// 引入 API (假设你已经把 website.js 放到了 src/api 目录下)
// 如果路径不对，请修改为实际路径
import { listProducts, listArticles, listJobs, listProjects } from '@/api/website';

export default {
  name: "Index",
  data() {
    return {
      version: "3.8.7",
      // 统计数据
      stats: {
        productCount: 0,
        articleCount: 0,
        jobCount: 0,
        projectCount: 0
      },
      // 图表实例
      barChart: null,
      pieChart: null
    };
  },
  mounted() {
    this.getData();
    // 监听窗口大小变化，自适应图表
    window.addEventListener('resize', this.resizeCharts);
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.resizeCharts);
    if (this.barChart) this.barChart.dispose();
    if (this.pieChart) this.pieChart.dispose();
  },
  methods: {
    // 获取数据
    async getData() {
      try {
        // 并行请求数据，page=1, size=100 用于简单统计分布，total 用于显示总数
        const [resProd, resArt, resJob, resProj] = await Promise.all([
          listProducts({ pageNum: 1, pageSize: 100 }),
          listArticles({ pageNum: 1, pageSize: 1 }),
          listJobs({ pageNum: 1, pageSize: 100 }),
          listProjects({ pageNum: 1, pageSize: 1 })
        ]);

        // 1. 设置统计卡片数字
        this.stats.productCount = resProd.total || 0;
        this.stats.articleCount = resArt.total || 0;
        this.stats.jobCount = resJob.total || 0;
        this.stats.projectCount = resProj.total || 0;

        // 2. 初始化图表
        this.$nextTick(() => {
          this.initBarChart(resProd.rows || []);
          this.initPieChart(resJob.rows || []);
        });

      } catch (error) {
        console.error("获取看板数据失败:", error);
      }
    },

    // 初始化柱状图 (产品分类)
    initBarChart(products) {
      this.barChart = echarts.init(this.$refs.barChart);
      
      // 简单模拟分类统计 (根据 categoryId)
      // 你可以根据实际情况修改这里的映射逻辑
      const categoryMap = { '1': '智慧城市', '2': '工业视觉', '3': '智能硬件', '4': '算法中台' };
      const dataMap = {};
      
      products.forEach(item => {
        const name = categoryMap[item.categoryId] || '其他';
        dataMap[name] = (dataMap[name] || 0) + 1;
      });

      const xAxisData = Object.keys(dataMap);
      const seriesData = Object.values(dataMap);

      this.barChart.setOption({
        tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
        grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
        xAxis: [{ type: 'category', data: xAxisData, axisTick: { alignWithLabel: true } }],
        yAxis: [{ type: 'value' }],
        series: [{
          name: '产品数量',
          type: 'bar',
          barWidth: '40%',
          data: seriesData,
          itemStyle: { color: '#409EFF' }
        }]
      });
    },

    // 初始化饼图 (职位部门)
    initPieChart(jobs) {
      this.pieChart = echarts.init(this.$refs.pieChart);
      
      const deptMap = {};
      jobs.forEach(item => {
        const name = item.department || '未分类';
        deptMap[name] = (deptMap[name] || 0) + 1;
      });

      const pieData = Object.keys(deptMap).map(key => ({ value: deptMap[key], name: key }));

      this.pieChart.setOption({
        tooltip: { trigger: 'item' },
        legend: { bottom: '0%' },
        series: [{
          name: '职位分布',
          type: 'pie',
          radius: ['40%', '70%'],
          avoidLabelOverlap: false,
          itemStyle: {
            borderRadius: 10,
            borderColor: '#fff',
            borderWidth: 2
          },
          label: { show: false, position: 'center' },
          emphasis: {
            label: { show: true, fontSize: '20', fontWeight: 'bold' }
          },
          labelLine: { show: false },
          data: pieData
        }]
      });
    },

    resizeCharts() {
      if (this.barChart) this.barChart.resize();
      if (this.pieChart) this.pieChart.resize();
    },

    handleJump(path) {
      this.$router.push(path).catch(() => {});
    },
    
    goTarget(href) {
      window.open(href, "_blank");
    }
  }
};
</script>

<style scoped lang="scss">
.home {
  padding: 32px;
  background-color: #f0f2f5;
  min-height: calc(100vh - 84px);

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
    border-radius: 8px;
    box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
    
    .chart-title {
      font-size: 18px;
      font-weight: bold;
      color: #333;
      margin-bottom: 15px;
      padding-left: 10px;
      border-left: 4px solid #409EFF;
    }
  }
}

// 统计卡片样式
.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);
    border-radius: 8px;

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-blue { background: #36a3f7; }
      .icon-green { background: #34bfa3; }
      .icon-red { background: #f4516c; }
      .icon-cyan { background: #11a983; }
    }

    .icon-blue { color: #36a3f7; }
    .icon-green { color: #34bfa3; }
    .icon-red { color: #f4516c; }
    .icon-cyan { color: #11a983; }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

.update-log {
  margin-bottom: 20px;
  
  .body {
    padding: 10px;
    p {
      margin: 10px 0;
      color: #606266;
      font-size: 14px;
    }
    
    .el-button {
      margin-bottom: 10px;
      margin-left: 0;
      margin-right: 10px;
    }
  }
}
</style>