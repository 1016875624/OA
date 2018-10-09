Ext.define('Admin.view.leaveapprove.LeaveApproveGrid', {
    extend: 'Ext.grid.Panel',
	xtype:'leaveApproveGrid',
	title: '职员请假审批表',		//需要修改
	iconCls: 'fa-arrow-circle-o-up',
	bind: '{leaveApproveStore}',//调用组件4
	columns: [{
			xtype: 'actioncolumn',
			items: [{
				xtype: 'button',
				iconCls: 'x-fa fa-edit',
				tooltip: '审批任务',
				handler: 'onClickLeaveApproveCompleteWindowButton'	//taskDefinitionKey 动态表单
			},{
				xtype: 'button',
				iconCls: 'x-fa fa-close',
				tooltip: '驳回',
				handler: 'onClickRejectWindowButton'	//流程跟踪
			},{
				xtype: 'button',iconCls: 'x-fa fa-ban',
				tooltip: '销假',
				getClass: function(v, meta, rec) {
					if (rec.get('status')!=2) {
						return 'x-hidden';
					}
					return 'x-fa fa-ban';
				},
				handler: 'endLeaveProcess'
		    }],
			cls: 'content-column',
			width: 120,
			dataIndex: 'bool',
			text: '操作',
			tooltip: 'edit '
		}
		,{header: 'id' 			,dataIndex: 'id',width: 60,sortable: true	,hidden:true}
		,{header: '状态',dataIndex: 'status',width: 80,sortable: true,
            renderer: function(val) {
		            if (val =='0') {
			            return '<span style="color:blue;">待申请</span>';
			        }else if (val =='1') {
			            return '<span style="color:orange;">待审批</span>';
			        }else if (val =='2'){
			        	return '<span style="color:green;">审批通过</span>';
			        }else if (val =='3'){
			        	return '<span style="color:red;">已销假</span>';
			        }
			        return val;
	            }
		}
		,{header: '职员ID'  		,dataIndex: 'employeeId',width: 80,sortable: true}
		,{header: '职员名称'  		,dataIndex: 'employeeName',width: 80,sortable: true}
		,{header: '拟开始时间' 	,dataIndex: 'startTime',width: 160,sortable: true,renderer: Ext.util.Format.dateRenderer('Y/m/d H:i:s')}
		,{header: '拟结束时间' 			,dataIndex: 'endTime',width: 160,sortable: true,renderer: Ext.util.Format.dateRenderer('Y/m/d H:i:s')}
		,{header: 'realityStartTime' 	,dataIndex: 'realityStartTime',width: 150,hidden:true,sortable: true,renderer: Ext.util.Format.dateRenderer('Y/m/d H:i:s')}
		,{header: 'realityEndTime' 	,dataIndex: 'realityEndTime',width: 150,hidden:true,sortable: true,renderer: Ext.util.Format.dateRenderer('Y/m/d H:i:s')}
		,{header: '创建时间' 	,dataIndex: 'applyTime',width: 160,sortable: true,renderer: Ext.util.Format.dateRenderer('Y/m/d H:i:s')}
		,{header: '请假类型'  	,dataIndex: 'leaveType',width: 80,sortable: true,
            renderer: function(val) {
	            if (val =='A') {
		            return '<span style="color:green;">带薪假期</span>';
		        } else if (val =='B') {
		            return '<span style="color:red;">无薪假期</span>';
		        } else if (val =='C') {
		            return '<span style="color:blue;">病假</span>';
		        }
		        return val;
            }
        }
		,{header: '原因' 		,dataIndex: 'reason',width: 180,sortable: true}
	],tbar: [{
			xtype: 'combobox',
			reference:'searchFieldName',
			hideLabel: true,
			store:Ext.create("Ext.data.Store", {
				fields: ["name", "value"],
				data: [{ name: '请假时间', value: 'leaveTime' }
						,{ name: '状态', value: 'status' }
				]
			}),
			displayField: 'name',
			valueField:'value',
			value:'leaveTime',
			editable: false,
			queryMode: 'local',
			triggerAction: 'all',
			emptyText: 'Select a state...',
			width: 135,
			listeners:{
				select: 'searchComboboxSelectChuang'
			}
		}, '-',{
			xtype: 'combobox',
			hideLabel: true,
			hidden: true,
			store:Ext.create("Ext.data.Store", {
				fields: ["name", "value"],
				data: [{ name: '所有', value: '' }
						,{ name: '待审批', value: '1' }
						,{ name: '审批通过', value: '2' }
						,{ name: '已销假', value: '3' }
				]
			}),
			displayField: 'name',
			valueField:'value',
			value:'',
			reference:'searchFieldValue',
			editable: false,
			queryMode: 'local',
			triggerAction: 'all',
			width: 135,
			fieldLabel: 'status',
			name: 'status'
		},'-',{
			xtype: 'datefield',
			hideLabel: true,
			//hidden:true,
			format: 'Y/m/d H:i:s',
			reference:'searchDataFieldValue',
			fieldLabel: 'From',
			name: 'from_date'
		}, {
			xtype: 'datefield',
			hideLabel: true,
			//hidden:true,
			format: 'Y/m/d H:i:s',
			reference:'searchDataFieldValue2',
			fieldLabel: 'To',
			name: 'to_date'
		},'-',{
			text: 'Search',
			iconCls: 'fa fa-search',
			handler: 'quickSearch'
		}, '-',{
			text: 'Search More',
			iconCls: 'fa fa-search-plus',
			handler: 'openSearchWindow'	
		}],
	dockedItems: [{
	    xtype: 'pagingtoolbar',
	    dock: 'bottom',
		bind: '{leaveApproveStore}',	//调用组件4
	    displayInfo: true
	}]
});
