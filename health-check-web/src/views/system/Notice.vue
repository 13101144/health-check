<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.name" placeholder="检查项名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getNotices">查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<template>
			<el-table :data="notices" highlight-current-row v-loading="loading" style="width: 100%;">
				<el-table-column type="selection" width="55">
			    </el-table-column>
			    <el-table-column type="id" width="60">
			    </el-table-column>
				<el-table-column prop="name" label="用户名" width="120" sortable>
				</el-table-column>
				<el-table-column prop="projectName" label="项目名" width="100"  sortable>
				</el-table-column>
				<el-table-column prop="checkName" label="检查项" width="100" sortable>
				</el-table-column>
				<el-table-column prop="method" label="发送方式" width="120" sortable>
				</el-table-column>
				<el-table-column prop="to" label="发送账号" min-width="180" sortable>
				</el-table-column>
                <el-table-column prop="error" label="错误信息" min-width="180" :formatter="formatStatus" sortable>
				</el-table-column>
				<el-table-column prop="status" label="是否已发送" min-width="180" :formatter="formatStatus" sortable>
				</el-table-column>
			</el-table>
		</template>

        <!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total" style="float:right;">
			</el-pagination>
		</el-col>

	</section>
</template>
<script>
	import { getNoticeListPage } from '../../api/api';
	//import NProgress from 'nprogress'
	export default {
		data() {
			return {
				filters: {
					name: ''
                },
                total: 0,
				page: 1,
				loading: false,
				notices: [
				]
			}
		},
		methods: {
            handleCurrentChange(val) {
				this.page = val;
				this.getNotices();
			},
			//性别显示转换
			formatStatus: function (row, column) {
				return row.status == "Y" ? '已成功' : '未发送';
			},
			//获取判断结果列表
			getNotices: function () {
                let para = {
					page: this.page,
					name: this.filters.name
				};
				this.listLoading = true;
				//NProgress.start();
				getNoticeListPage(para).then((res) => {
					this.total = res.data.total;
					this.notices = res.data.records;
					this.listLoading = false;
					//NProgress.done();
				});
		
			}
		},
		mounted() {
			this.getNotices();
		}
	};

</script>

<style scoped>

</style>