Ext.define('Admin.view.workTime.WorkTimeGridPanel', {
    extend: 'Ext.panel.Panel',
    xtype: 'workTimeGridPanel',
    requires: [
    	//'Admin.view.AdvancedVType',
        'Ext.grid.Panel',
        'Ext.toolbar.Paging',
        'Ext.form.field.ComboBox',
        'Ext.selection.CheckboxModel',
        'Ext.form.field.Date',
        'Ext.grid.column.Date',
        'Ext.grid.plugin.Exporter',
        "Ext.ux.ProgressBarPager",
        "Ext.ux.SlidingPager"
        
    ],
   
   
    resizable: true,
    loadMask: true,
    //frame: true,
    //iconCls: 'framing-buttons-grid',
    layout: 'fit',
    
    items: [{
            xtype: 'gridpanel',
            cls: 'user-grid',
            title: '工时列表',
            //routeId: 'user',
            plugins: {
                gridexporter: true
            },
            bind: '{workTimeLists}',
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
						}else if(val=='3'){
							return '<span style="color:green;">审批通过</span>';
						}else if(val=='4'){
							return '<span style="color:red;">审批不通过</span>';
						}
					},
					exportRenderer: true
				},
                {xtype: 'actioncolumn',cls: 'content-column', width: 120,text: '操作',tooltip: 'edit ',flex: 1,
                    items: [
                        {xtype: 'button', iconCls: 'x-fa fa-pencil' ,handler: 'openEditWindow',tooltip: '修改申请'},
                        {xtype: 'button',iconCls: 'x-fa fa-close'	,handler: 'deleteOneRow',tooltip:'取消申请'},
                        //{xtype: 'button',iconCls: 'x-fa fa-star'	,handler: 'starWorktimeProcess',tooltip: '发起申请'}
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
						{ name: '部门'   , value:'departmentName'},
						{ name: '状态'   , value:'status'},
						{ name:'工作/节假日/请假',value:'ifholiday'}
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
		    }, {
		    	xtype:"combobox",
		    	name:"status",
		    	store:Ext.create('Ext.data.Store',{
		    		fields:["name","value"],
		    		data:[{name:"待审核",value:"2"},{name:"审核通过",value:"3"},{name:"审核不通过",value:"4"}]
		    	}),
		    	displayField:"name",
		    	valueField:"value",
		    	reference:"statusCombo",
		    	emptyText: '请选择状态',
		    	editable: false,
		    	hidden:true,
		    	queryMode:'local',
		    },{
		    	xtype:"combobox",
		    	name:"ifholiday",
		    	store:Ext.create('Ext.data.Store',{
		    		fields:["name","value"],
		    		data:[{name:"工作日",value:"0"},{name:"周六日",value:"1"},{name:"节假日",value:"3"},{name:"请假",value:"3"}]
		    	}),
		    	displayField:"name",
		    	valueField:"value",
		    	reference:"ifholidayCombo",
		    	emptyText: '请选择是否节假日',
		    	editable: false,
		    	hidden:true,
		    	queryMode:'local',
		    },{
		    	xtype:"departmentcombobox",
		    	reference:'departmentBox',
		    	name:'departmentid',
		    	hidden:true,
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
			},{
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
		    }, '-',{
		    	xtype: 'button',
		        text: 'Search More',
		        iconCls: 'fa fa-search-plus',
		        handler: 'openSearchWindow'	
			}, '->',{
				xtype: 'button',
		        text: '添加工时',
		        iconCls: 'fa fa-plus',
		        //iconCls: 'framing-buttons-add',
		        handler: 'openAddWindow'	
		    },'-',{
		        text: 'Removes',
		        tooltip: '删除所选的多条数据',
		        iconCls:'fa fa-trash',
		        //iconCls:'framing-buttons-remove',
		        itemId: 'workTimeGridPanelRemove',
		        disabled: true,
		        handler: 'deleteMoreRows'	
		    },'-',{
		    	ui: 'default-toolbar',
	            xtype: 'button',
	            text: '选择导出数据的类型',
	            menu: {
	                defaults: {
	                    handler: 'exportTo'
	                },
	                items: [{
	                    text:   'Excel xlsx',
	                    cfg: {
	                        type: 'excel07',
	                        ext: 'xlsx'
	                    }
	                },
//	                {
//	                    text:   'Excel xlsx (include groups)',
//	                    cfg: {
//	                        type: 'excel07',
//	                        ext: 'xlsx',
//	                        includeGroups: true,
//	                        includeSummary: true
//	                    }
//	                },
	                {
	                    text: 'Excel xml',
	                    cfg: {
	                        type: 'excel03',
	                        ext: 'xml'
	                    }
	                },
//	                {
//	                    text: 'Excel xml (include groups)',
//	                    cfg: {
//	                        includeGroups: true,
//	                        includeSummary: true
//	                    }
//	                },
	                {
	                    text:   'CSV',
	                    cfg: {
	                        type: 'csv'
	                    }
	                },{
	                    text:   'TSV',
	                    cfg: {
	                        type: 'tsv',
	                        ext: 'csv'
	                    }
	                },{
	                    text:   'HTML',
	                    cfg: {
	                        type: 'html'
	                    }
	                },
//	                {
//	                    text:   'HTML (include groups)',
//	                    cfg: {
//	                        type: 'html',
//	                        includeGroups: true,
//	                        includeSummary: true
//	                    }
//	                }
	                ]
	            },
	            listeners:{
	            	//documentsave: 'onDocumentSave',
	                beforedocumentsave: 'onBeforeDocumentSave',
	                //dataready: 'onDataReady'
	            },
		    }],			
            dockedItems: [{
                xtype: 'pagingtoolbar',
                dock: 'bottom',
                //itemId: 'userPaginationToolbar',
                displayInfo: true,
                plugins: {
                    'ux-progressbarpager': true,
                    'ux-slidingpager': true
                },
                bind: '{workTimeLists}'
            }],
            listeners: {
				selectionchange: function(selModel, selections){
					this.down('#workTimeGridPanelRemove').setDisabled(selections.length === 0);
				},
				
			}		
        }]
});