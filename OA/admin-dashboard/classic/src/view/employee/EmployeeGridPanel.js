﻿Ext.define('Admin.view.employee.EmployeeGridPanel', {
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
                {xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'departmentName',text: '所属部门',width: 70,flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'position',text: '职位',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'status',text: '在职状态',width: 80,sortable: true,
		            renderer: function(val) {
			            if (val =='0') {
				            return '<span>正常</span>';
				        } else if (val =='1') {
				            return '<span>离职</span>';
				        } else if (val =='-1') {
				            return '<span>封禁</span>';
				        }
				        return val;
		            }
				},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'email',text: '邮箱',flex: 1},
				//{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'status',text: '在职状态',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'leaderName',text: '上级领导',flex: 1},
				{xtype: 'datecolumn',cls: 'content-column',width: 200,dataIndex: 'entryTime',text: '入职时间',formatter: 'date("Y/m/d")'},
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
						{ name: '入职时间'   , value:'entryTime'},
						{ name: '所属部门'   , value:'departmentName'}
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
            	name:'orderPanelSearchField',
            	hidden:true,
            	width: 135,
            	hideLabel: true
		    },'-',{
		       xtype: 'combobox',
	           fieldLabel: '请选择部门',
	           name:'departmentid',
	           store:Ext.create("Ext.data.Store", {
				    fields: ["id", "name"],
				   	proxy: {
				        type: 'ajax',
				        url:'http://localhost:8080/department/simpleget',
					    reader:{
					    	type:'json',
					    },
				    }
				   	,
				   	autoLoad: 'true',
					autoSync:'true',
				}),
				displayField:'name',
				valueField:'id',
				//value:'',
				editable:false,
				queryMode: 'local',
				triggerAction: 'all',
				emptyText: 'Select a state...',
				width: 135,
				listeners:{
					
				},
				hideLabel: true,
				hidden:true,
				reference:'departmentBox'
			},'-',{
				xtype: 'datefield',
				hideLabel: true,
				hidden:true,
				format: 'Y/m/d',
				reference:'searchDataFieldValue',
				fieldLabel: 'From',
				width: 135,
				name: 'from_date'
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