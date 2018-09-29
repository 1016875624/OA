Ext.define('Admin.view.salarypay.SalarypayPanel', {
    extend: 'Ext.panel.Panel',
    xtype: 'salarypayPanel',

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
            cls: 'salarypay-grid',
			itemId:'salarypayGridPanel',
            title: 'salarypay results',
            //routeId: 'user',
            bind: '{salarypayLists}',
            scrollable: false,
            columns: [
                {xtype: 'gridcolumn',dataIndex: 'id',text: 'id',hidden:true,},
                {xtype: 'gridcolumn', cls: 'content-column',dataIndex: 'employeeName',text: '员工姓名'},
                {xtype: 'gridcolumn', cls: 'content-column',dataIndex: 'departmentName',text: '部门'},
                
                {xtype: 'datecolumn', cls: 'content-column',width:150, dataIndex: 'date',text: '发放时间',format:'Y/m/d H:i:s'},

                {xtype: 'gridcolumn', cls: 'content-column',dataIndex: 'money',text: '实际工资'},
                {xtype: 'gridcolumn', cls: 'content-column',dataIndex: 'realWorktime',text: '实际工作时间'},
                {xtype: 'gridcolumn', cls: 'content-column',dataIndex: 'worktime',text: '工作时间'},
                {xtype: 'gridcolumn', cls: 'content-column',dataIndex: 'attendRate',text: '出勤率'},
                {xtype: 'gridcolumn', cls: 'content-column',dataIndex: 'status',text: '状态'},
                //{xtype: 'datecolumn',cls: 'content-column',width: 200,dataIndex: 'orderDate',text: 'orderDate',formatter: 'date("Y/m/d H:i:s")'},
                
                {xtype: 'actioncolumn',cls: 'content-column', width: 120,text: 'Actions',tooltip: 'edit ',flex:1,
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
					
					this.down('#removeBtn').setDisabled(records.length === 0);
				}
			},
			selModel: {type: 'checkboxmodel',checkOnly: true},
            dockedItems: [{
                xtype: 'pagingtoolbar',
                dock: 'bottom',
                itemId: 'paginationToolbar',
                displayInfo: true,
                bind: '{salarypayLists}'
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
						{ name: '实际工资', value: 'money' },
						{ name: '实际工作时间', value: 'realWorktime' },
						{ name: '工作时间', value: 'worktime' },
						{ name: '出勤率', value: 'attendRate' },
						{ name: '发放日期', value: 'startDate' },
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
			/*{
				xtype: 'combobox',
				hideLable:true,
				reference:'searchComboboxField',
				store:Ext.create("Ext.data.Store", {
					fields: ["id", "name"],
					// data: [
					// 	  { name: 'id', value: 'id' },
					// 	{ name: '部门名称', value: 'name' }
					// ]
					proxy: {
						type: 'ajax',
						//url:"/order",
						url:'http://localhost:8080/department/simpleget',
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
				//value:'id',
				//value:'订单编号',
				editable:false,
				queryMode: 'local',
				triggerAction: 'all',

				emptyText: 'Select a state...',
				width: 135,
				listeners:{
					//change:'tbarSelectChange'
				},
				hidden:true
			},*/
            {
                xtype:"departmentcombobox",
                hidden:true,
                reference:'searchComboboxField',
            },

			/*'-',{
				xtype:'datefield',
				name:'orderPanelSearchDateField',
				reference:'searchDateFieldValue',
				formatter: 'date("Y/m/d H:i:s")',
				hidden:true
			},*/
			
			/*{
				xtype: 'combobox',
				hideLable:true,
				reference:'test',
				store:Ext.create("Ext.data.Store", {
				    fields: ["id", "name"],
//				    data: [
//				      	{ name: 'id', value: 'id' },
//						{ name: '部门名称', value: 'name' }
//				    ]
				   	proxy: {
				        type: 'ajax',
						//url:"/order",
				        url:'http://localhost:8080/salarypay/simpleget',
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
			},*/
			
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
				itemId:'removeBtn',
				handler:'tbarClickDeleteMore'
			},
			],
        }]
});
