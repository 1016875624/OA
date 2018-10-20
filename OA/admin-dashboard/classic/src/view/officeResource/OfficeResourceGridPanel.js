Ext.define('Admin.view.officeResource.OfficeResourceGridPanel', {
	extend: 'Ext.panel.Panel',
	xtype: 'officeResourceGridPanel',
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
		bind: '{officeResourceLists}',
		scrollable: false,
		selModel: {type: 'checkboxmodel'},
		columns: [
			 {header: 'id',dataIndex:'id',width: 60,sortable: true,hidden:true}
			,{header: '状态',dataIndex: 'status',width: 80,sortable: true,
	            renderer: function(val) {
		            if (val =='0') {
			            return '<span style="color:blue;">待发起</span>';
			        }else if (val =='1') {
			            return '<span style="color:green;">可抢</span>';
			        }else if (val =='2'){
			        	return '<span style="color:red;">已抢完</span>';
			        }else if (val =='3'){
			        	return '<span style="color:green;">可抽奖</span>';
			        }else if (val =='4'){
			        	return '<span style="color:red;">已抽完</span>';
			        }
			        return val;
	            }
			}
			,{header: '创建时间',dataIndex: 'applyTime',width: 165,sortable: true,renderer: Ext.util.Format.dateRenderer('Y/m/d H:i:s')}
			,{header: '用途',dataIndex: 'remark',width: 95,sortable: true,
				 renderer: function(val) {
					if (val =='0') {
						return '<span style="color:blue;">抽奖用</span>';
					}else if (val =='1') {
						return '<span style="color:orange;">抢夺用</span>';
					}
					return val;
				}
			}
			,{header: '资源名称',dataIndex: 'resourceName',width: 120,sortable: true}
			,{header: '剩余数量',dataIndex: 'leftCount',width: 100,sortable: true}
			,{header: '职员ID',dataIndex: 'employeeId',width: 120,sortable: true, hidden:true}
			,{header: '开始时间',dataIndex: 'startTime',width: 165,sortable: true,renderer:Ext.util.Format.dateRenderer('Y/m/d H:i:s')}
			,{header: '结束时间',dataIndex: 'endTime',width: 165,sortable: true,renderer: Ext.util.Format.dateRenderer('Y/m/d H:i:s')}
			
			,{xtype: 'actioncolumn',cls: 'content-column', width: 180,text: '操作',tooltip: 'edit ',
				items: [
					{xtype: 'button', iconCls: 'x-fa fa-pencil',
						getClass: function(v, meta, rec) {
		                    if (rec.get('status')==1||rec.get('status')==3) {
		                        return 'x-hidden';
		                    }
		                    return 'x-fa fa-pencil';
		                },
					handler: 'openEditWindow'},
					{xtype: 'button',iconCls: 'x-fa fa-close',
						getClass: function(v, meta, rec) {
		                    if (rec.get('status')==1||rec.get('status')==3) {
		                        return 'x-hidden';
		                    }
		                    return 'x-fa fa-close';
		                },
					handler: 'deleteOneRow'},
					{
		                xtype: 'button',iconCls: 'x-fa fa-star',tooltip: '开始抽奖',
		                getClass: function(v, meta, rec) {
		                    if (rec.get('status')!=0) {
		                        return 'x-hidden';
		                    }
		                    return 'x-fa fa-star';
		                },
		                handler: 'startLuckyDraw'
		            },{
		                xtype: 'button',iconCls: 'x-fa fa-hand-lizard-o',tooltip: '开始抢资源',
		                getClass: function(v, meta, rec) {
		                    if (rec.get('status')!=0) {
		                        return 'x-hidden';
		                    }
		                    return 'x-fa fa-hand-lizard-o';
		                },
		                handler: 'startGrabResource'
		            },{
		                xtype: 'button',iconCls: 'x-fa fa-hand-lizard-o',tooltip: '抢资源',
		                getClass: function(v, meta, rec) {
		                    if (rec.get('status')!=1) {
		                        return 'x-hidden';
		                    }
		                    return 'x-fa fa-hand-lizard-o';
		                },
		                handler: 'grabResource'
		            },{
		                xtype: 'button',iconCls: 'x-fa fa-star',tooltip: '抽奖',
		                getClass: function(v, meta, rec) {
		                    if (rec.get('status')!=3) {
		                        return 'x-hidden';
		                    }
		                    return 'x-fa fa-star';
		                },
		                handler: 'luckyDraw'
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
				data: [{ name: '抽奖时间', value: 'luckyTime' }
						,{ name: '状态', value: 'status' }
						,{ name: '用途', value: 'remark' }
						,{ name: '资源名称', value: 'resourceName' }
				]
			}),
			displayField: 'name',
			valueField:'value',
			value:'luckyTime',
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
						,{ name: '待发起', value: '0' }
						,{ name: '可抢', value: '1' }
						,{ name: '可抽奖', value: '3' }
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
			xtype: 'combobox',
			hideLabel: true,
			hidden: true,
			store:Ext.create("Ext.data.Store", {
				fields: ["name", "value"],
				data: [{ name: '所有', value: '' }
						,{ name: '抽奖用', value: '0' }
						,{ name: '抢资源用', value: '1' }
				]
			}),
			displayField: 'name',
			valueField:'value',
			value:'',
			reference:'searchFieldValue2',
			editable: false,
			queryMode: 'local',
			triggerAction: 'all',
			width: 135,
			fieldLabel: 'remark',
			name: 'remark'
		},'-',{
			xtype: 'datefield',
			hideLabel: true,
			format: 'Y/m/d H:i:s',
			reference:'searchDataFieldValue',
			fieldLabel: 'From',
			name: 'from_date'
		}, {
			xtype: 'datefield',
			hideLabel: true,
			format: 'Y/m/d H:i:s',
			reference:'searchDataFieldValue2',
			fieldLabel: 'To',
			name: 'to_date'
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
		}, '->',{
			text: 'Add',
			tooltip: 'Add a new row',
			iconCls: 'fa fa-plus',
			handler: 'openAddWindow'	
		},'-',{
			text: 'Allocate',
			tooltip: 'Allocate resources to employees',
			iconCls: 'fa fa-support',
			handler: 'openAllocateWindow'
		},'-',{
			text: 'Removes',
			tooltip: 'Remove the selected item',
			iconCls:'fa fa-trash',
			itemId: 'officeResourceGridPanelRemove',
			disabled: true,
			handler: 'deleteMoreRows'	
		}],			
		dockedItems: [{
			xtype: 'pagingtoolbar',
			dock: 'bottom',
			displayInfo: true,
			bind: '{officeResourceLists}'
		}],
		listeners: {
			selectionchange: function(selModel, selections){
				this.down('#officeResourceGridPanelRemove').setDisabled(selections.length === 0);
			}
		}		
	}]
});