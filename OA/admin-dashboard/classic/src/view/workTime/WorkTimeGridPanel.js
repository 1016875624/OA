
﻿Ext.define('Admin.view.workTime.WorkTimeGridPanel', {
    extend: 'Ext.panel.Panel',
    xtype: 'workTimeGridPanel',
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
            title: 'workTimeGrid Results',
            //routeId: 'user',
            bind: '{workTimeLists}',
            scrollable: false,
            selModel: {type: 'checkboxmodel'},
            columns: [
                {xtype: 'gridcolumn',width: 40,dataIndex: 'id',text: '#',hidden:true},
                {xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'employeeid',text: '员工编号',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'employeeName',text: '员工姓名',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'departmentName',text: '部门',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'hour',text: '当天的上班时间',flex: 1},
				{xtype: 'datecolumn',cls: 'content-column',width: 200,dataIndex: 'date',text: '日期',formatter: 'date("Y/m/d")'},
                
                {xtype: 'actioncolumn',cls: 'content-column', width: 120,text: '操作',tooltip: 'edit ',flex: 1,
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
				      	{ name: '员工编号', value: 'employeeid' },
						{ name: '日期'   , value:'date'}
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
				xtype: 'combobox',
				hideLabel: true,
				hidden:true,
				reference:'employeeIdBox'
			}, '-',{
				xtype: 'datefield',
				hideLabel: true,
				hidden:true,
				format: 'Y/m/d',
				reference:'searchDataFieldValue',
				fieldLabel: 'From',
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
		        itemId: 'workTimeGridPanelRemove',
		        disabled: true,
		        handler: 'deleteMoreRows'	
		    }],			
            dockedItems: [{
                xtype: 'pagingtoolbar',
                dock: 'bottom',
                //itemId: 'userPaginationToolbar',
                displayInfo: true,
                bind: '{workTimeLists}'
            }],
            listeners: {
				selectionchange: function(selModel, selections){
					this.down('#workTimeGridPanelRemove').setDisabled(selections.length === 0);
				}
			}		
        }]
});