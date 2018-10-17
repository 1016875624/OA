Ext.define('Admin.view.employeeResource.EmployeeResourceTradeGridPanel', {
	extend: 'Ext.panel.Panel',
	xtype: 'employeeResourceTradeGridPanel',
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
		bind: '{employeeResourceTradeLists}',
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
			        }
			        return val;
	            }
			}
			,{header: '资源名称',dataIndex: 'resourceName',width: 150,sortable: true}
			,{header: '剩余数量',dataIndex: 'count',width: 100,sortable: true}
			,{header: '最近改变时间',dataIndex: 'recentChangeTime',width: 185,sortable: true,renderer: Ext.util.Format.dateRenderer('Y/m/d H:i:s')}
			,{header: '备注',dataIndex: 'remark',width: 300,sortable: true, hidden:true}
			,{header: '交易走的资源',dataIndex: 'loseResourceName',width: 120,sortable: true, hidden:true}
			,{header: '交易走的数量',dataIndex: 'loseCount',width: 100,sortable: true, hidden:true}
			,{header: '拥有者',dataIndex: 'employeeId',width: 160,sortable: true}
			,{xtype: 'actioncolumn',cls: 'content-column', width: 180,text: '操作',tooltip: 'edit',
				items: [
					{xtype: 'button', iconCls: 'x-fa fa-commenting-o',
						getClass: function(v, meta, rec) {
		                    if (rec.get('status')!=1) {
		                        return 'x-hidden';
		                    }
		                    return 'x-fa fa-commenting-o';
		                },
					handler: 'openTradeWindow'},
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
			bind: '{employeeResourceTradeLists}'
		}]		
	}]
});