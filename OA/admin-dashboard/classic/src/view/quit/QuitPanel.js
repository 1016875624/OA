Ext.define('Admin.view.quit.QuitPanel', {
    extend: 'Ext.panel.Panel',
    xtype: 'quitPanel',

    requires: [
        'Ext.grid.Panel',
        'Ext.toolbar.Paging',
		'Ext.form.field.ComboBox',
        'Ext.grid.column.Date'
    ],
    //controller: 'searchresults',
   // viewModel: {type: 'orderViewModel'},
    layout: 'fit',
	
	
	
    items: [{
            xtype: 'gridpanel',
            cls: 'quit-grid',
			itemId:'quitGridPanel',
            title: 'quit results',
            //routeId: 'user',
            bind: '{quitLists}',
            scrollable: false,
            columns: [
                {xtype: 'gridcolumn',width: 40,dataIndex: 'id',text: 'id',hidden:true},
                {xtype: 'gridcolumn', cls: 'content-column',dataIndex: 'name',text: '部门名称',flex: 1},
                //{xtype: 'datecolumn',cls: 'content-column',width: 200,dataIndex: 'orderDate',text: 'orderDate',formatter: 'date("Y/m/d H:i:s")'},
                {xtype: 'actioncolumn',cls: 'content-column', width: 120,dataIndex: 'bool',text: 'Actions',tooltip: 'edit ',
                    items: [
                        {xtype: 'button', iconCls: 'x-fa fa-pencil' ,handler: 'gridModify'},
                        {xtype: 'button',iconCls: 'x-fa fa-close'	,handler: 'gridDelete'},
                        //{xtype: 'button',iconCls: 'x-fa fa-ban'	 	,handler: 'gridDisable'}
                    ]
                }
				
            ],
			// 监听grid事件：
			listeners: {
				selectionchange: function(view , records,selection,eOpts) {
					/*if(records[0]) {    // 加载进form表单中；
						this.up('form').getForm().loadRecord(records[0]);
					}*/
					/*console.log(view);
					console.log(records);
					console.log(selection);
					console.log(eOpts);*/
					//Ext.Msg.alert("title","content");
					/*if(records[0]){
						this.up('form').getForm().loadRecord(records[0]);
					}*/
					//showWindow(records[0]);
					Ext.m_data=records[0];
					
					this.down('#userRemoveBtn').setDisabled(records.length === 0);
				}
			},
			selModel: {type: 'checkboxmodel',checkOnly: true},
            dockedItems: [{
                xtype: 'pagingtoolbar',
                dock: 'bottom',
                itemId: 'userPaginationToolbar',
                displayInfo: true,
                bind: '{userLists}'
            }],
			tbar: [
			{
				xtype: 'combobox',
				hideLable:true,
				reference:'searchFieldName',
				store:Ext.create("Ext.data.Store", {
				    fields: ["name", "value"],
				    data: [
				      	{ name: 'id', value: 'id' },
						{ name: '部门名称', value: 'name' }
				    ]
				}),
				//label:'查询类型',
				displayField:'name',
				valueField:'value',
				value:'id',
				//value:'订单编号',
				editable:false,
				queryMode: 'local',
				triggerAction: 'all',
				emptyText: 'Select a state...',
				width: 135,
				listeners:{
					change:'tbarSelectChange'
				}
			},'-',{
				xtype:'textfield',
				name:'orderPanelSearchField',
				reference:'searchFieldValue'
			},
			/*'-',{
				xtype:'datefield',
				name:'orderPanelSearchDateField',
				reference:'searchDateFieldValue',
				formatter: 'date("Y/m/d H:i:s")',
				hidden:true
			},*/
			
			{
				xtype: 'combobox',
				hideLable:true,
				reference:'test',
				store:Ext.create("Ext.data.Store", {
				    fields: ["id", "name"],
				    /*data: [
				      	{ name: 'id', value: 'id' },
						{ name: '部门名称', value: 'name' }
				    ]*/
				   	proxy: {
				        type: 'ajax',
						//url:"/order",
				        url:'http://localhost:8080/quit/simpleget',
						//url: '~api/search/users'	//mvc url  xxx.json
					    reader:{
					    	type:'json',
					    	//rootProperty:'content',
							//totalProperty:'totalElements'
					    },
						//simpleSortMode: true
				    }
				   	,
				   	autoLoad: 'true',
					autoSync:'true',
				}),
				//label:'查询类型',
				displayField:'name',
				valueField:'id',
				value:'id',
				//value:'订单编号',
				editable:false,
				queryMode: 'local',
				triggerAction: 'all',
				
				emptyText: 'Select a state...',
				width: 135,
				listeners:{
					change:'tbarSelectChange'
				}
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
				handler:'tbarClickAddBtn'
			},'-',{
				xtype: 'button',
				text : 'Removes',
				iconCls:'fa fa-trash',
		        disabled: true,
				itemId:'userRemoveBtn',
				handler:'tbarClickDeleteMore'
			},
			],
        }]
});
