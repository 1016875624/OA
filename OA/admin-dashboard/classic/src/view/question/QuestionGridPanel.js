Ext.define('Admin.view.question.QuestionGridPanel', {
    extend: 'Ext.panel.Panel',
    xtype: 'questionGridPanel',
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
            title: 'questionGrid Results',
            //routeId: 'user',
            bind: '{questionLists}',
            scrollable: false,
            selModel: {type: 'checkboxmodel'},
            columns: [
                {xtype: 'gridcolumn',width: 40,dataIndex: 'id',text: '#',hidden:true},
                {xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'type',text: '类型',
					renderer:function(val){
						if(val=='0'){
							return '<span>单选题</span>';
						}else if(val=='1'){
							return '<span>多选题</span>';
						}else if(val=='2'){
							return '<span>填空题</span>';
						}
						return val;
						
					}
				
				},
                {xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'textQuestion',text: '题目',flex: 1},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'realanswer',text: '标准答案',flex: 1},
				{xtype: 'widgetcolumn',cls: 'content-column',text: '标准答案',flex: 1,
					widget:{
			            align: 'center',
			            xtype: 'button',
			            width:100,
			            text:"点击查看答案",
			            handler:function(btn){
			            	console.log(Ext.ClassManager.getName(btn));
			            	console.log(111);
			            	var rec = btn.getWidgetRecord();
			            	//或者bind:'{record.realanswer}'
			            	var str=rec.get("realanswer");
			            	console.log(str);
			            	
			            	if(rec.get("type")=="0"){
			            		Ext.Msg.alert("标准答案",str);
			            	}else if(rec.get("type")=="1"){
			            		var strs=str.split("&");
			            		Ext.Msg.alert("标准答案",strs);
			            	}else if(rec.get("type")=="2"){
			            		var strs=str.split("$");
			            		Ext.Msg.alert("标准答案",strs);
			            	}
			            }
			        }
				},
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'answers',text: '选择题选项',flex:1},
				
				{xtype: 'gridcolumn',cls: 'content-column',dataIndex: 'status',text: '状态',
					renderer:function(val){
						if(val=='0'){
							return '<span>正常</span>';
						}
						return val;
						
					}
				},
                {xtype: 'actioncolumn',cls: 'content-column', width: 120,text: '操作',tooltip: 'edit ',flex: 1,
                    items: [
                        {xtype: 'button', iconCls: 'x-fa fa-pencil' ,handler: 'openEditWindow',tooltip:"修改题目"},
                        {xtype: 'button',iconCls: 'x-fa fa-close'	,handler: 'deleteOneRow',tooltip:"删除题目"},
                        {xtype: 'button',iconCls: 'x-fa fa-eye'	 	,handler: 'onCheckButton',tooltip:"查看题目"}
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
				emptyText:'选择查询方式',
	            displayField: 'name',
	            valueField:'value',
	            editable: false,
	            queryMode: 'local',
	            triggerAction: 'all',
	            
	            width: 135,
	            listeners:{
					select: 'searchComboboxSelectChuang'
				}
	        },'-',{
	        	xtype:'textfield',
            	reference:'searchTextQuesValue',
            	emptyText: '输入题目部分字段',
            	hidden:true,
            	hideLabel: true
		    }, '-',{
		    	xtype:'combobox',
		    	name:'type',
		    	editable:false,
		    	reference:'questionType',
		        iconCls: 'fa fa-search',
		    	hidden:true,
		    	hideLabel: true,
		    	emptyText:'选择题目类型',
		        fieldLabel:'题目类型',
				store: Ext.create('Ext.data.Store', {
					fields: ['value', 'name'],
					data : [
						{"value":"0", "name":"单选题"},
						{"value":"1", "name":"多选题"},
						{"value":"2", "name":"填空题"}
					]
				}),
				queryMode: 'local',
				displayField: 'name',
				valueField: 'value'
		    }, '-',{
		        text: 'Search',
		        iconCls: 'fa fa-search',
		        handler: 'quickSearch'
		    }, '-',{
		        text: 'Search More',
		        iconCls: 'fa fa-search-plus',
		        handler: 'openSearchWindow'	
			}, '->',{
		        text: '添加题目',
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
                plugins: {
                    'ux-progressbarpager': true,
                    'ux-slidingpager': true
                },
                bind: '{questionLists}'
            }],
            listeners: {
				selectionchange: function(selModel, selections){
					this.down('#questionGridPanelRemove').setDisabled(selections.length === 0);
				}
			}		
        }]
});