Ext.define('Admin.view.department.DepartmentPanel', {
    extend: 'Ext.panel.Panel',
    xtype: 'departmentPanel',

    requires: [
        'Ext.grid.Panel',
        'Ext.toolbar.Paging',
		'Ext.form.field.ComboBox',
		'Ext.selection.CheckboxModel',
        'Ext.grid.column.Date'
		
    ],
    //controller: 'searchresults',
   // viewModel: {type: 'orderViewModel'},
    layout: 'fit',
	
	
	
    items: [{
            xtype: 'gridpanel',
            //cls: 'department-grid',
            cls: 'user-grid',
			itemId:'departmentGridPanel',
            title: 'department results',
            //routeId: 'user',
            bind: '{departmentLists}',
            scrollable: false,
            selModel: {type: 'checkboxmodel'},
            columns: [
                {xtype: 'gridcolumn',width: 40,dataIndex: 'id',text: 'id',hidden:true},
                {xtype: 'gridcolumn', cls: 'content-column',dataIndex: 'name',text: '部门名称',flex: 1},
                {xtype: 'gridcolumn',dataIndex: 'employeesids',text: '部门人员',flex: 1,hidden:false},
                {xtype: 'actioncolumn',cls: 'content-column', width: 160,text: 'Actions',tooltip: 'edit ',
                    items: [
                        {xtype: 'button', iconCls: 'x-fa fa-pencil' ,handler: 'gridModify'},
                        {xtype: 'button',iconCls: 'x-fa fa-close'	,handler: 'gridDelete'},
                        {xtype: 'button',iconCls: 'x-fa fa-eye'	,handler: 'gridCheck'},
                        {xtype: 'button',iconCls: 'x-fa fa-exchange'	 	,handler: 'gridChange'}
                        
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
				      	{ name: '部门编号', value: 'id' },
						{ name: '部门名称'   , value:'name'}
				    ]
				}),
	            displayField: 'name',
	            valueField:'value',
	            value:'选择查询类型',
	            editable: false,
	            queryMode: 'local',
	            triggerAction: 'all',
	            emptyText: 'Select a state...',
	            width: 135,
	            listeners:{
					change:'tbarSelectChange'
				}
	        },'-',{
            	xtype:'textfield',
            	reference:'searchFieldValue',
            	//name:'orderPanelSearchField',
            	hidden:true,
            	width: 135,
            	hideLabel: true
		    },'-',{
		    	xtype:"departmentcombobox",
		    	reference:'departmentBox',
		    	name:'departmentid',
		    	width: 150,
		    	hidden:true
			},
			
			'-',{
		        text: 'Search',
		        iconCls: 'fa fa-search',
		        handler: 'quickSearch'
		    }, '-',{
		        text: 'Search More',
		        iconCls: 'fa fa-search-plus',
		        handler: 'openSearchWindow'	
			},'->',
			{
				xtype: 'button',
				text : 'Add',
				tooltip: 'Add a new row',
		        iconCls: 'fa fa-plus',
				handler:'openAddWindow'
			},'-',{
				xtype: 'button',
				text : 'Removes',
				iconCls:'fa fa-trash',
		        disabled: true,
				itemId:'departmentRemoveBtn',
				handler:'tbarClickDeleteMore'
			}],
			dockedItems: [{
                xtype: 'pagingtoolbar',
                dock: 'bottom',
                displayInfo: true,
                bind: '{departmentLists}'
            }],
            listeners: {
				selectionchange: function(selModel, selections){
					this.down('#departmentRemoveBtn').setDisabled(selections.length === 0);
				}
			}	
        }]
});
