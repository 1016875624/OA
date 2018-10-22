Ext.define('Admin.view.workTimeApproval.WorkTimeApprovalGridPanel', {
    extend: 'Ext.panel.Panel',
    xtype: 'workTimeApprovalGridPanel',
    requires: [
    	//'Admin.view.AdvancedVType',
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
            cls: 'user-grid',
            title: '审批工时',
            //routeId: 'user',
            bind: '{workTimeApprovalLists}',
            scrollable: false,
            selModel: {type: 'checkboxmodel'},
            columns: [
            	{xtype: 'gridcolumn',width: 40,dataIndex: 'id',text: '#',hidden:true},
                {xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'employeeid',text: '员工编号',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'employeeName',text: '员工姓名',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'departmentName',text: '部门',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'hour',text: '当天的上班时间 (单位：H)',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'ifholiday',text: '工作/节假日',flex: 1,
					renderer:function(val){
						if(val=='0'){
							return '<span>工作日</span>';
						}else if(val=='1'){
							return '<span style="color:orange">周六日</span>';
						}else if(val=='2'){
							return '<span style="color:orange">节假日</span>';
						}else if(val=='3'){
							return '<span style="color:red">请假</span>';
						}
					},
					exportRenderer: true
				},
				{xtype: 'datecolumn',cls: 'content-column',width: 200,dataIndex: 'date',text: '日期',formatter: 'date("Y/m/d")'},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'status',text: '状态',flex: 1,
					renderer:function(val){
						if(val=='2'){
							return '<span style="color:orange;">待审批</span>';
						}
					}
				},
                {xtype: 'actioncolumn',cls: 'content-column', width: 120,text: '操作',tooltip: 'edit ',flex: 1,
                    items: [
                        {xtype: 'button', iconCls: 'x-fa fa-pencil' ,reference:"passId",handler: 'passApproval',tooltip: '通过'},
                        {xtype: 'button',iconCls: 'x-fa fa-close'	,reference:"failId",handler: 'backApproval',tooltip:'驳回'}
                    ]
                }
            ],
            tbar: [{
	            xtype: 'combobox',
	            reference:'searchFieldName',
	            //hideLabel: true,
	            store:Ext.create("Ext.data.Store", {
				    fields: ["name", "value"],
				    data: [
				      	{ name: '员工编号', value: 'employeeid' },
						{ name: '日期'   , value:'date'},
				    ]
				}),
	            displayField: 'name',
	            valueField:'value',
	            value:'选择查询方式',
	            editable: false,
	            queryMode: 'local',
	            triggerAction: 'all',
	            emptyText: 'Select a state...',
	            width: 135,
	            listeners:{
					//select: 'searchComboboxSelectChuang',
					change:'tbarSelectChange'
				}
	        },{
            	xtype:'textfield',
            	reference:'searchFieldValue',
            	name:'orderPanelSearchField',
            	emptyText: '输入员工编号',
            	hidden:true,
            	hideLabel: true
		    },
		    {
				xtype: 'datefield',
				hideLabel: true,
				hidden:true,
				format: 'Y/m/d',
				reference:'searchDataFieldValue',
				fieldLabel: 'From',
				editable:false,
				emptyText: '起始时间',
				name: 'from_date'
			}, {
				xtype: 'datefield',
				hideLabel: true,
				hidden:true,
				editable:false,
				format: 'Y/m/d',
				reference:'searchDataFieldValue2',
				fieldLabel: 'To',
				emptyText: '末时间',
				name: 'end_date'
			}, '-',{
		        text: 'Search',
		        iconCls: 'fa fa-search',
		        handler: 'quickSearch'
		    }
//			, '-',{
//		        text: '高级查询',
//		        iconCls: 'fa fa-search-plus',
//		        handler: 'openSearchWindow'	
//			}
			, '->',{
		        text: '批量驳回',
		        tooltip: 'back the selected item',
		        iconCls:'fa fa-trash',
		        itemId: 'workTimeGridPanelRemove',
		        disabled: true,
		        handler: 'deleteMoreRows'	
		    },'-',{
		    	text: '批量同意',
		        tooltip: 'pass the selected item',
		        iconCls:'fa fa-search',
		        itemId: 'workTimeGridPanelpass',
		        disabled: true,
		        handler: 'approvalMoreRows'	
		    	
		    }],			
            dockedItems: [{
                xtype: 'pagingtoolbar',
                dock: 'bottom',
                //itemId: 'userPaginationToolbar',
                displayInfo: true,
                bind: '{workTimeApprovalLists}'
            }],
            listeners: {
				selectionchange: function(selModel, selections){
					this.down('#workTimeGridPanelRemove').setDisabled(selections.length === 0);
					this.down('#workTimeGridPanelpass').setDisabled(selections.length === 0);
					
				}
			}		
        }]
});