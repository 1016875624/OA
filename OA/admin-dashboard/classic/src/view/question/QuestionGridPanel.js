
﻿Ext.define('Admin.view.question.QuestionViewModel', {
    extend: 'Ext.panel.Panel',
    xtype: 'questionGridPanel',
    requires: [
        'Ext.grid.Panel',
        'Ext.toolbar.Paging',
        'Ext.form.field.ComboBox',
        'Ext.selection.CheckboxModel',
        'Ext.form.field.Date',
        'Ext.grid.column.Date',
        'Ext.fx.target.Element'
    ],
    layout: 'fit',
    items: [{
            xtype: 'gridpanel',
            cls: 'user-grid',
            title: 'questionGrid Results',
            //routeId: 'user',
            bind: '{questionLists}',
            scrollable: false,
            selModel: {type: 'checkboxmodel'},
            columns: [
                {xtype: 'gridcolumn',width: 40,dataIndex: 'id',text: '#',hidden:true},
                {xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'textQuestion',text: '题目',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'realanswer',text: '标准答案',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'answers',text: '选择题选项',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'type',text: '类型',
					renderer:function(val){
						if(val=='1'){
							return '<span>选择题</span>';
						}else if(val='2'){
							return '<span>填空题</span>';
						}
						return val;
						
					}
				
				},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'status',text: '状态'},
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
				      	{ name: '题目', value: 'textQuestion' },
						{ name: '题目类型', value: 'type' }
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
		    	xtype:'combobox',
		    	name:'type',
		    	reference:'questionType',
		        iconCls: 'fa fa-search',
		        handler: 'quickSearch',
		        fieldLabel:'题目类型',
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
		        itemId: 'questionGridPanelRemove',
		        disabled: true,
		        handler: 'deleteMoreRows'	
		    }],			
            dockedItems: [{
                xtype: 'pagingtoolbar',
                dock: 'bottom',
                //itemId: 'userPaginationToolbar',
                displayInfo: true,
                bind: '{questionLists}'
            }],
            listeners: {
				selectionchange: function(selModel, selections){
					this.down('#questionGridPanelRemove').setDisabled(selections.length === 0);
				}
			}		
        }]
});