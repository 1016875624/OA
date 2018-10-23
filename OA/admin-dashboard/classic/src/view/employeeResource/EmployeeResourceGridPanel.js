Ext.define('Admin.view.employeeResource.EmployeeResourceGridPanel', {
	extend: 'Ext.panel.Panel',
	xtype: 'employeeResourceGridPanel',
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
		bind: '{employeeResourceLists}',
		scrollable: false,
		selModel: {type: 'checkboxmodel'},
		columns: [
			 {header: 'id',dataIndex:'id',width: 60,sortable: true,hidden:true}
			,{header: '状态',dataIndex: 'status',width: 100,sortable: true,
	            renderer: function(val) {
		            if (val =='0') {
			            return '<span style="color:blue;">交易中</span>';
			        }else if (val =='1') {
			            return '<span style="color:green;">已拥有</span>';
			        }else if (val =='2') {
			            return '<span style="color:red;">兑奖中</span>';
			        }else if (val =='3') {
			            return '<span style="color:yellow;">已兑奖</span>';
			        }
			        return val;
	            }
			}
			,{header: '资源名称',dataIndex: 'resourceName',width: 150,sortable: true}
			,{header: '剩余数量',dataIndex: 'count',width: 100,sortable: true}
			,{header: '最近改变时间',dataIndex: 'recentChangeTime',width: 185,sortable: true,renderer: Ext.util.Format.dateRenderer('Y/m/d H:i:s')}
			,{header: '备注',dataIndex: 'remark',width: 300,sortable: true}
			,{header: '交易走的资源',dataIndex: 'loseResourceName',width: 120,sortable: true, hidden:true}
			,{header: '交易走的数量',dataIndex: 'loseCount',width: 100,sortable: true, hidden:true}
			,{header: '职员ID',dataIndex: 'employeeId',width: 120,sortable: true, hidden:true}
			,{xtype: 'actioncolumn',cls: 'content-column', width: 180,text: '操作',tooltip: 'edit',
				items: [
					{xtype: 'button', iconCls: 'x-fa fa-check',tooltip: '同意交易',
						getClass: function(v, meta, rec) {
		                    if (rec.get('status')!=0) {
		                        return 'x-hidden';
		                    }
		                    return 'x-fa fa-check';
		                },
					handler: 'sureTrade'},
					{xtype: 'button',iconCls: 'x-fa fa-close',tooltip: '拒绝交易',
						getClass: function(v, meta, rec) {
		                    if (rec.get('status')!=0) {
		                        return 'x-hidden';
		                    }
		                    return 'x-fa fa-close';
		                },
					handler: 'cancelTrade'},
					{xtype: 'button',iconCls: 'x-fa fa-handshake-o',tooltip: '兑换资源',
						getClass: function(v, meta, rec) {
		                    if (rec.get('status')!=1) {
		                        return 'x-hidden';
		                    }
		                    return 'x-fa fa-handshake-o';
		                },
					handler: 'openExchangeWindow'},
					{xtype: 'button',iconCls: 'x-fa fa-check-square-o',tooltip: '确认兑换',
						getClass: function(v, meta, rec) {
		                    if (rec.get('status')!=2) {
		                        return 'x-hidden';
		                    }
		                    return 'x-fa fa-check-square-o';
		                },
					handler: 'finishExchange'},
					{xtype: 'button',iconCls: 'x-fa fa-trash',tooltip: '删除兑换完成的资源',
						getClass: function(v, meta, rec) {
		                    if (rec.get('status')!=3) {
		                        return 'x-hidden';
		                    }
		                    return 'x-fa fa-trash';
		                },
					handler: 'deleteFinishExchange'}
				]
			}
		],
		tbar: [{
			xtype: 'combobox',
			reference:'searchFieldName',
			hideLabel: true,
			store:Ext.create("Ext.data.Store", {
				fields: ["name", "value"],
				data: [{ name: '最近修改时间', value: 'recentChangeTime' }
						,{ name: '状态', value: 'status' }
						,{ name: '资源名称', value: 'resourceName' }
				]
			}),
			displayField: 'name',
			valueField:'value',
			value:'recentChangeTime',
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
						,{ name: '交易中', value: '0' }
						,{ name: '已拥有', value: '1' }
						,{ name: '兑奖中', value: '2' }
						,{ name: '已兑奖', value: '3' }
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
			format: 'Y/m/d H:i:s',
			reference:'searchDataFieldValue',
			fieldLabel: 'From',
			name: 'from_date'
		},'-',{
			xtype: 'textfield',
			hideLabel: true,
			hidden: true,
			reference:'searchFieldValue3',
			fieldLabel: 'resourceName',
			name: 'resourceName'
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
			displayInfo: true,
			bind: '{employeeResourceLists}'
		}]		
	}]
});