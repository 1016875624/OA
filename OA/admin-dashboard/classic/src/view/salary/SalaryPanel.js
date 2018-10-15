Ext.define('Admin.view.salary.SalaryPanel', {
    extend: 'Ext.panel.Panel',
    xtype: 'salaryPanel',
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
            cls: 'user-grid',
            title: 'SalaryGrid Results',
            //routeId: 'user',
            bind: '{salaryLists}',
            scrollable: false,
            selModel: {type: 'checkboxmodel'},
            columns: [
                {xtype: 'gridcolumn',width: 40,dataIndex: 'id',text: 'Key',hidden:true},
                {xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'employeeid',text: '员工工号',flex: 1},
                {xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'employeeName',text: '员工姓名',flex: 1},
                {xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'sal',text: '基本工资',flex: 1},
                {xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'bonus',text: '奖金',flex: 1},
                {xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'workMonth',text: '工龄',flex: 1},
                {xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'worktimeMoney',text: '工龄工资',flex: 1},
                {xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'subsidy',text: '补贴',flex: 1},
                //{xtype: 'datecolumn',cls: 'content-column',width: 200,dataIndex: 'createTime',text: 'Create Time',formatter: 'date("Y/m/d H:i:s")'},
                
                {xtype: 'actioncolumn',cls: 'content-column', width: 120,text: 'Actions',tooltip: 'edit ',
                    items: [
                        {xtype: 'button', iconCls: 'x-fa fa-pencil' ,handler: 'openEditWindow'},
                        {xtype: 'button',iconCls: 'x-fa fa-close'	,handler: 'deleteOneRow'},
                        {xtype: 'button',iconCls: 'x-fa fa-ban'	 	,handler: 'onDisableButton'}
                    ]
                }
            ],
            tbar: [{
	            xtype: 'combobox',
	            reference:'searchFieldName',
	            hideLabel: true,
	            store:Ext.create("Ext.data.Store", {
				    fields: ["name", "value"],
				    data: [
                        { name: '工资id', value: 'id' },
				      	{ name: '员工工号', value: 'employeeid' },
						{ name: '员工姓名', value: 'employeeName' },
                        { name: '工资范围', value: 'preSal' },
                        { name: '奖金范围', value: 'preBonus' },
                        { name: '工龄范围', value: 'preWorkMonth' },
                        { name: '工龄工资范围', value: 'preWorktimeMoney' },
                        { name: '补贴范围', value: 'preSubsidy' },
				    ]
				}),
	            displayField: 'name',
	            valueField:'value',
	            value:'id',
	            editable: false,
	            queryMode: 'local',
	            triggerAction: 'all',
	            emptyText: 'Select a state...',
	            width: 135,
	            listeners:{
					select: 'searchComboboxSelectChuang'
				}
	        }, '-',{
            	xtype:'textfield',
            	reference:'searchFieldValue',
            	name:'salaryPanelSearchField',
            	//hidden:true,
            	width:135,
            	hideLable:true
		    }, '-',{
		        text: '查找',
		        iconCls: 'fa fa-search',
		        handler: 'quickSearch'
		    }, '-',{
		        text: '查找更多',
		        iconCls: 'fa fa-search-plus',
		        handler: 'openSearchWindow'	
			}, '->',{
		        text: '添加',
		        tooltip: 'Add a new row',
		        iconCls: 'fa fa-plus',
		        handler: 'openAddWindow'	
		    },'-',{
		        text: '批量删除',
		        tooltip: 'Remove the selected item',
		        iconCls:'fa fa-trash',
		        itemId: 'salaryGridPanelRemove',
		        disabled: true,
		        handler: 'deleteMoreRows'	
		    }],			
            dockedItems: [{
                xtype: 'pagingtoolbar',
                dock: 'bottom',
                //itemId: 'userPaginationToolbar',
                displayInfo: true,
                bind: '{salaryLists}'
            }],
            listeners: {
				selectionchange: function(selModel, selections){
					this.down('#salaryGridPanelRemove').setDisabled(selections.length === 0);
				}
			}
        }]
});
