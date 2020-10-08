import axios from 'axios';

let base = 'http://localhost:8088';

export const requestLogin = params => { return axios.post(`${base}/login`, params).then(res => res.data); };

export const getUserList = params => { return axios.get(`${base}/user/list`, { params: params }); };

export const getUserListPage = params => { return axios.get(`${base}/user/listpage`, { params: params }); };

export const removeUser = params => { return axios.delete(`${base}/user/remove`, { params: params }); };

export const batchRemoveUser = params => { return axios.get(`${base}/user/batchremove`, { params: params }); };

export const editUser = params => { return axios.put(`${base}/user/edit`,  params); };

export const addUser = params => { return axios.post(`${base}/user/add`, params ); };

export const getProjectList = params => { return axios.get(`${base}/project/list`, { params: params }); };

export const getProjectListPage = params => { return axios.get(`${base}/project/listpage`, { params: params }); };

export const removeProject = params => { return axios.delete(`${base}/project/remove`, { params: params }); };

export const batchRemoveProject = params => { return axios.get(`${base}/project/batchremove`, { params: params }); };

export const editProject = params => { return axios.put(`${base}/project/edit`,  params); };

export const addProject = params => { return axios.post(`${base}/project/add`, params ); };

export const getCheckList = params => { return axios.get(`${base}/check/list`, { params: params }); };

export const getCheckListPage = params => { return axios.get(`${base}/check/listpage`, { params: params }); };

export const removeCheck = params => { return axios.delete(`${base}/check/remove`, { params: params }); };

export const batchRemoveCheck = params => { return axios.get(`${base}/check/batchremove`, { params: params }); };

export const editCheck = params => { return axios.put(`${base}/check/edit`,  params); };

export const addCheck = params => { return axios.post(`${base}/check/add`, params ); };

export const getResultListPage = params => { return axios.get(`${base}/result/listpage`, { params: params }); };

export const getNoticeListPage = params => { return axios.get(`${base}/notice/listpage`, { params: params }); };