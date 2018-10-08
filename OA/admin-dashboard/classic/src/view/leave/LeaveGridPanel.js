Ext.define('Admin.view.leave.LeaveGridPanel', {
	extend: 'Ext.panel.Panel',
	xtype: 'leaveGridPanel',
	requires: [
		'Ext.grid.Panel',
		'Ext.toolbar.Paging',
		'Ext.form.field.ComboBox',
		'Ext.selection.CheckboxModel',
		'Ext.form.field.Date',
		'Ext.grid.column.Date'
	],
	layout: 'fit',
	items: [{
		xtype: 'gridpanel',
		title: 'LeaveGrid Results',
		//routeId: 'user',
		bind: '{leaveLists}',
		scrollable: false,
		selModel: {type: 'checkboxmodel'},
		columns: [
			 {header: 'id',dataIndex:'id',width: 60,sortable: true,hidden:true}
			,{header: '状态',dataIndex: 'status',width: 120,sortable: true,
	            renderer: function(val) {
		            if (val =='-1') {
			            return '<span style="color:green;">已删除</span>';
			        } else if (val =='0') {
			            return '<span style="color:blue;">待申请</span>';
			        } else if (val =='1') {
			            return '<span style="color:orange;">待审批</span>';
			        }else if (val =='2'){
			        	return '<span style="color:red;">审批通过</span>';
			        }else if (val =='3'){
			        	return '<span style="color:red;">已销假</span>';
			        }
			        return val;
	            }
			}
			,{header: '职员ID',dataIndex: 'employeeId',width: 120,sortable: true}
			,{header: '拟开始时间',dataIndex: 'startTime',width: 180,sortable: true,renderer:Ext.util.Format.dateRenderer('Y/m/d H:i:s')}
			,{header: '拟结束时间',dataIndex: 'endTime',width: 180,sortable: true,renderer: Ext.util.Format.dateRenderer('Y/m/d H:i:s')}
			,{header: '请假类型',dataIndex: 'leaveType',width: 120,sortable: true,
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
			,{header: '原因',dataIndex: 'reason',width: 220,sortable: true}
			//,{header: 'realityStartTime',dataIndex: 'realityStartTime',width: 60,sortable: true,renderer: Ext.util.Format.dateRenderer('Y/m/d H:i:s')}
			//,{header: 'realityEndTime',dataIndex: 'realityEndTime',width: 60,sortable: true,renderer: Ext.util.Format.dateRenderer('Y/m/d H:i:s')}
			//,{header: 'applyTime',dataIndex: 'applyTime',width: 180,sortable: true,renderer: Ext.util.Format.dateRenderer('Y/m/d H:i:s')}
			// ,{header: 'processInstanceId' ,dataIndex: 'processInstanceId',width: 180,sortable: true}
			,{xtype: 'actioncolumn',cls: 'content-column', width: 140,text: '操作',tooltip: 'edit ',
				items: [
					{xtype: 'button', iconCls: 'x-fa fa-pencil',handler: 'openEditWindow'},
					{xtype: 'button',iconCls: 'x-fa fa-close',handler: 'deleteOneRow'},
					{
		                xtype: 'button',iconCls: 'x-fa fa-star',tooltip: '发起请假',
		                getClass: function(v, meta, rec) {
		                    if (rec.get('status')!=0) {
		                        return 'x-hidden';
		                    }
		                    return 'x-fa fa-star';
		                },
		                handler: 'starLeaveProcess'
		            },{
		                xtype: 'button',iconCls: 'x-fa fa-ban',tooltip: '销假',
		                getClass: function(v, meta, rec) {
		                    if (rec.get('status')!=2) {
		                        return 'x-hidden';
		                    }
		                    return 'x-fa fa-ban';
		                },
		                handler: 'endLeaveProcess'
		            }
				]
			}
		],
		tbar: [{
			xtype: 'combobox',
			reference:'searchFieldName',
			hideLabel: true,
			store:Ext.create("Ext.data.Store", {
				fields: ["name", "value"],
				data: [{ name: '请假时间', value: 'leaveTime' }]
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
		}, '->',{
			text: 'Add',
			tooltip: 'Add a new row',
			iconCls: 'fa fa-plus',
			handler: 'openAddWindow'	
		},'-',{
			text: 'Removes',
			tooltip: 'Remove the selected item',
			iconCls:'fa fa-trash',
			itemId: 'leaveGridPanelRemove',
			disabled: true,
			handler: 'deleteMoreRows'	
		}],			
		dockedItems: [{
			xtype: 'pagingtoolbar',
			dock: 'bottom',
			displayInfo: true,
			bind: '{leaveLists}'
		}],
		listeners: {
			selectionchange: function(selModel, selections){
				this.down('#leaveGridPanelRemove').setDisabled(selections.length === 0);
			}
		}		
	}]
});