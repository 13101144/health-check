<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.name" placeholder="检查项名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getChecks">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="checks" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column type="id" width="60">
			</el-table-column>
			<el-table-column prop="name" label="检查项名" width="120" sortable>
			</el-table-column>
			<el-table-column prop="code" label="检查项代码" width="300" sortable>
			</el-table-column>
			<el-table-column prop="tags" label="标签" width="120" sortable>
			</el-table-column>
			<el-table-column prop="description" label="项目描述" min-width="180" sortable>
			</el-table-column>
            <el-table-column prop="projectName" label="所属项目" min-width="180" sortable>
			</el-table-column>
            <el-table-column prop="grace" label="最大延迟时间(单位：/min)" min-width="180" sortable>
			</el-table-column>
			<el-table-column prop="maxExecTime" label="最大执行时间(单位：/min)" min-width="180" sortable>
			</el-table-column>
            <el-table-column prop="schedule" label="调度周期(cron表达式)" min-width="180" sortable>
			</el-table-column>
            <el-table-column prop="timeZone" label="时区" min-width="120" sortable>
			</el-table-column>
            <el-table-column prop="subject" label="主题" min-width="120" sortable>
			</el-table-column>
			<el-table-column label="操作" width="150">
				<template scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
					<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total" style="float:right;">
			</el-pagination>
		</el-col>

		<!--编辑界面-->
		<el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
				<el-form-item label="检查项名" prop="name">
					<el-input v-model="editForm.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="代码">
					<el-input type="textarea" v-model="editForm.code" :min="0" :max="200"></el-input>
				</el-form-item>
				<el-form-item label="标签">
					<el-input type="textarea" v-model="editForm.tags" :min="0" :max="200"></el-input>
				</el-form-item>
				<el-form-item label="描述">
					<el-input type="textarea" v-model="editForm.description"></el-input>
				</el-form-item>
                <el-form-item label="所属项目名">
					<el-input type="textarea" v-model="editForm.projectName"></el-input>
				</el-form-item>
                <el-form-item label="最大延迟时间(单位：/min)">
					<el-input-number v-model="editForm.grace" :min="0" :max="60"></el-input-number>
				</el-form-item>
				<el-form-item label="最大执行时间(单位：/min)">
					<el-input-number v-model="editForm.maxExecTime" :min="0" :max="60"></el-input-number>
				</el-form-item>
                <el-form-item label="调度周期(cron表达式)">
					<el-input type="textarea" v-model="editForm.schedule"></el-input>
				</el-form-item>
                <el-form-item label="时区">
					<el-input type="textarea" v-model="editForm.timeZone"></el-input>
				</el-form-item>
                <el-form-item label="主题">
					<el-input type="textarea" v-model="editForm.subject"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>

		<!--新增界面-->
		<el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
			<el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
				<el-form-item label="检查项名" prop="name">
					<el-input v-model="addForm.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="标签">
					<el-input type="textarea" v-model="addForm.tags" :min="0" :max="200"></el-input>
				</el-form-item>
				<el-form-item label="描述">
					<el-input type="textarea" v-model="addForm.description"></el-input>
				</el-form-item>
                <el-form-item label="所属项目名">
					<el-input type="textarea" v-model="addForm.projectName"></el-input>
				</el-form-item>
                <el-form-item label="最大延迟时间(单位：/min)">
					<el-input-number v-model="addForm.grace" :min="0" :max="60"></el-input-number>
				</el-form-item>
				<el-form-item label="最大执行时间(单位：/min)">
					<el-input-number v-model="addForm.maxExecTime" :min="0" :max="60"></el-input-number>
				</el-form-item>
                <el-form-item label="调度周期(cron表达式)">
					<el-input type="textarea" v-model="addForm.schedule"></el-input>
				</el-form-item>
                <el-form-item label="时区">
					<el-input type="textarea" v-model="addForm.timeZone"></el-input>
				</el-form-item>
                <el-form-item label="主题">
					<el-input type="textarea" v-model="addForm.subject"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
			</div>
		</el-dialog>
	</section>
</template>

<script>
	import util from '../../common/js/util'
	//import NProgress from 'nprogress'
	import { getCheckListPage, removeCheck, batchRemoveCheck, editCheck, addCheck } from '../../api/api';

	export default {
		data() {
			return {
				filters: {
					name: ''
				},
				checks: [],
				total: 0,
				page: 1,
				listLoading: false,
				sels: [],//列表选中列

				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					name: [
						{ required: true, message: '请输入姓名', trigger: 'blur' }
					]
				},
				//编辑界面数据
				editForm: {
					id: 0,
					name: '',
					code: '',
					tags: '',
                    description: '',
                    projectName: '',
					grace:0,
					maxExecTime:0,
                    schedule:'',
                    timeZone:'',
                    subject:''
				},

				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				addFormRules: {
					name: [
						{ required: true, message: '请输入工程名', trigger: 'blur' }
					]
				},
				//新增界面数据
				addForm: {
					name: '',
					code: '',
					tags: '',
                    description: '',
                    projectName: '',
					grace:0,
					maxExecTime:0,
                    schedule:'',
                    timeZone:'',
                    subject:''
				}

			}
		},
		methods: {
			handleCurrentChange(val) {
				this.page = val;
				this.getChecks();
			},
			//获取用户列表
			getChecks() {
				let para = {
					page: this.page,
					name: this.filters.name
				};
				this.listLoading = true;
				//NProgress.start();
				getCheckListPage(para).then((res) => {
					this.total = res.data.total;
					this.checks = res.data.records;
					this.listLoading = false;
					//NProgress.done();
				});
			},
			//删除
			handleDel: function (index, row) {
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					let para = { id: row.id };
					removeCheck(para).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getChecks();
					});
				}).catch(() => {

				});
			},
			//显示编辑界面
			handleEdit: function (index, row) {
				this.editFormVisible = true;
				this.editForm = Object.assign({}, row);
			},
			//显示新增界面
			handleAdd: function () {
				this.addFormVisible = true;
				this.addForm = {
					name: '',
					code: '',
					tags: '',
                    description: '',
                    projectName: '',
					grace:0,
					maxExecTime:0,
                    schedule:'',
                    timeZone:'',
                    subject:''
				};
			},
			//编辑
			editSubmit: function () {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.editLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.editForm);
							
							editCheck(para).then((res) => {
								this.editLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['editForm'].resetFields();
								this.editFormVisible = false;
								this.getChecks();
							});
						});
					}
				});
			},
			//新增
			addSubmit: function () {
				this.$refs.addForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.addLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.addForm);
							addCheck(para).then((res) => {
								this.addLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['addForm'].resetFields();
								this.addFormVisible = false;
								this.getChecks();
							});
						});
					}
				});
			},
			selsChange: function (sels) {
				this.sels = sels;
			},
			//批量删除
			batchRemove: function () {
				var ids = this.sels.map(item => item.id).toString();
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					let para = { ids: ids };
					batchRemoveCheck(para).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getChecks();
					});
				}).catch(() => {

				});
			}
		},
		mounted() {
			this.getChecks();
		}
	}

</script>

<style scoped>

</style>