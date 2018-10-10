Ext.define('Admin.view.quit.quitApproval.QuitApprovalPanel', {
    extend: 'Ext.panel.Panel',
    xtype: 'quitApprovalPanel',

    requires: [
        'Ext.grid.Panel',
        'Ext.toolbar.Paging',
		'Ext.form.field.ComboBox',
        'Ext.grid.column.Date'
    ],
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
                {xtype: 'gridcolumn',dataIndex: 'id',text: 'id',hidden:true,},
                {xtype: 'gridcolumn', cls: 'content-column',dataIndex: 'employeeName',text: '员工姓名'},
                {xtype: 'gridcolumn', cls: 'content-column',dataIndex: 'departmentName',text: '部门'},
                
                {xtype: 'datecolumn', cls: 'content-column',width:150, dataIndex: 'applyDate',text: '申请时间',format:'Y/m/d H:i:s'},
                {xtype: 'datecolumn', cls: 'content-column',width:150,dataIndex: 'quitDate',text: '离职时间',format:'Y/m/d H:i:s'},
                
                {xtype: 'gridcolumn', cls: 'content-column',dataIndex: 'reason',text: '离职原因时间'},
                {xtype: 'gridcolumn', cls: 'content-column',dataIndex: 'status',text: '状态',renderer:function (val) {
                        if (val==0){
                            return"申请中";
                        }
                        else if (val==1){
                            return"申请成功";
                        }
                        else if (val==2){
                            return "驳回申请";
                        }
                        else if (val==-1){
                            return "删除";
                        }
                        else if (val<0) {
                            return "被删除"
                        }

                    }},
                //{xtype: 'datecolumn',cls: 'content-column',width: 200,dataIndex: 'orderDate',text: 'orderDate',formatter: 'date("Y/m/d H:i:s")'},
                
                {xtype: 'actioncolumn',cls: 'content-column', width: 120,dataIndex: 'bool',text: 'Actions',tooltip: 'edit ',flex:1,
                    items: [
                        {xtype: 'button', iconCls: 'x-fa fa-pencil',cls:'test' ,handler: 'gridApproval'},
                        {xtype: 'button', iconCls: 'x-fa fa-check' ,handler: 'gridPass',tooltip:'通过'},
                        {xtype: 'button',iconCls: 'x-fa fa-reply'	,handler: 'gridNoPass',tooltip:'驳回'},
                    ]
                },
				/*{
					xtype:"gridcolumn",
                    renderer:function () {
						return "<button>btn</button>"
                    }
					xtype:"gridcolumn",
                    renderer:function(){
					    return  [
                            {xtype:"button",text:"驳回"},
                            {xtype:"button",text:"通过"}
                        ]
                    }

				}*/
				
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
					
					this.down('#removeBtn').setDisabled(records.length === 0);
				}
			},
			selModel: {type: 'checkboxmodel',checkOnly: true},
            dockedItems: [{
                xtype: 'pagingtoolbar',
                dock: 'bottom',
                itemId: 'paginationToolbar',
                displayInfo: true,
                bind: '{quitLists}'
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
				      	{ name: '员工id', value: 'employeeid' },
						{ name: '员工名称', value: 'employeeName' },
						{ name: '离职原因', value: 'reason' },
						{ name: '部门id', value: 'departmentid' },
						{ name: '部门名称', value: 'departmentName' },
						{ name: '申请日期', value: 'preApplyDate' },
						{ name: '离职日期', value: 'preQuitDate' },
						
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
				emptyText: '请选择查询内容',
				width: 135,
				listeners:{
					change:'tbarSelectChange'
				}
			},'-',{
				xtype:'textfield',
				name:'searchTextField',
				reference:'searchTextField'
			},
			{
				xtype:'datefield',
				name:'searchDateField',
				reference:'searchDateField',
				format:"Y/m/d H:i:s",
				hidden:true
			},
            {
                xtype:"departmentcombobox",
                hidden:true,
                reference:'searchComboboxField',
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
				text : '通过审批',
				tooltip: 'Add a new row',
				iconCls: 'fa fa-plus',
				handler:'tbarClickPassBtn'
			},'-',
			{
				xtype: 'button',
				text : '驳回申请',
				tooltip: '申请离职',
		        iconCls: 'fa fa-plus',
				handler:'tbarClickNoPassBtn'
			},
			],
        }]
});
