<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.name" placeholder="检查项名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getResults">查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<template>
			<el-table :data="results" highlight-current-row v-loading="loading" style="width: 100%;">
				<el-table-column type="selection" width="55">
			    </el-table-column>
			    <el-table-column type="id" width="60">
			    </el-table-column>
				<el-table-column prop="beatId" label="心跳ID" width="120" sortable>
				</el-table-column>
				<el-table-column prop="projectName" label="项目" width="100"  sortable>
				</el-table-column>
				<el-table-column prop="checkName" label="检查项" width="100" sortable>
				</el-table-column>
				<el-table-column prop="beatCreated" label="心跳创建时间" width="120" sortable>
				</el-table-column>
				<el-table-column prop="address" label="心跳服务器地址" min-width="180" sortable>
				</el-table-column>
                <el-table-column prop="status" label="判断结果" min-width="180" :formatter="formatStatus" sortable>
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
	import { getResultListPage } from '../../api/api';
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
				results: [
				]
			}
		},
		methods: {
            handleCurrentChange(val) {
				this.page = val;
				this.getResults();
			},
			//状态显示转换
			formatStatus: function (row, column) {
				return row.status == "Y" ? '成功' : '失败';
			},
			//获取判断结果列表
			getResults: function () {
                let para = {
					page: this.page,
					name: this.filters.name
				};
				this.listLoading = true;
				//NProgress.start();
				getResultListPage(para).then((res) => {
					this.total = res.data.total;
					this.results = res.data.records;
					this.listLoading = false;
					//NProgress.done();
				});
		
			}
		},
		mounted() {
			this.getResults();
		}
	};

</script>

<style scoped>

</style>