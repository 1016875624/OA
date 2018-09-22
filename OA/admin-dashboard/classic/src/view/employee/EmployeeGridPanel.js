Ext.define('Admin.view.employee.EmployeeGridPanel', {
    extend: 'Ext.panel.Panel',
    xtype: 'employeeGridPanel',
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
            title: 'employeeGrid Results',
            //routeId: 'user',
            bind: '{employeeLists}',
            scrollable: false,
            selModel: {type: 'checkboxmodel'},
            columns: [
                //{xtype: 'gridcolumn',width: 40,dataIndex: 'id',text: 'Key',hidden:true},
                {xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'id',text: '员工编号',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'name',text: '员工姓名',flex: 1},
                {xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'department',text: '所属部门',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'position',text: '职位',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'email',text: '邮箱',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'status',text: '在职状态',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'leader',text: '上级领导',flex: 1},
				{xtype: 'datecolumn',cls: 'content-column',width: 200,dataIndex: 'entryTime',text: '入职时间',formatter: 'date("Y/m/d H:i:s")'},
                
                {xtype: 'actioncolumn',cls: 'content-column', width: 120,text: '操作',tooltip: 'edit ',
                    items: [
                        {xtype: 'button', iconCls: 'x-fa fa-pencil' ,handler: 'openEditWindow'},
                        {xtype: 'button',iconCls: 'x-fa fa-close'	,handler: 'deleteOneRow'},
                        {xtype: 'button',iconCls: 'x-fa fa-eye'	 	,handler: 'onCheckButton'}
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
				      	{ name: '员工编号', value: 'id' },
						{ name: '员工姓名', value: 'name' },
						{ name: '入职时间', value: 'entryTime' }
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
	        },'-',{
            	xtype:'textfield',
            	reference:'searchFieldValue',
            	name:'orderPanelSearchField'
		    }, '-',{
				xtype: 'datefield',
				hideLabel: true,
				hidden:true,
				format: 'Y/m/d H:i:s',
				reference:'searchDataFieldValue',
				fieldLabel: 'From',
				name: 'from_date'
				//,id:'from_date',
				//vtype: 'daterange',
				//endDateField: 'to_date'
			}, '-',{
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
		        itemId: 'employeeGridPanelRemove',
		        disabled: true,
		        handler: 'deleteMoreRows'	
		    }],			
            dockedItems: [{
                xtype: 'pagingtoolbar',
                dock: 'bottom',
                //itemId: 'userPaginationToolbar',
                displayInfo: true,
                bind: '{employeeLists}'
            }],
            listeners: {
				selectionchange: function(selModel, selections){
					this.down('#employeeGridPanelRemove').setDisabled(selections.length === 0);
				}
			}		
        }]
});