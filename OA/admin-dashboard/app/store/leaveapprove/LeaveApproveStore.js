Ext.define('Admin.store.leaveapprove.LeaveApproveStore', {
    extend: 'Ext.data.Store',
    storeId:'leaveApproveStore',
    alias: 'store.leaveApproveStore',
    model: 'Admin.model.leaveapprove.LeaveApproveModel',
    //pageSize: 25,
    proxy: {
        type: 'rest',
        url: 'leave/approvalTable',			//需要修改
        reader:{
			type:'json',
			rootProperty:'content',//对应后台返回的结果集名称
			totalProperty: 'totalElements'//分页需要知道总记录数
		},
		writer: {
			type: 'json'
		},
		simpleSortMode: true
    },
    remoteSort: true,
	autoSync: true,
	pageSize: 15,
    sorters: [{ property: 'id',direction: 'desc'}],
    autoLoad: true
});	