Ext.define('Admin.view.quit.quitManager.QuitPanel', {
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


    items: [
        {
            xtype: 'gridpanel',
            // cls: ['quit-grid', 'panel'],
            cls: 'quit-grid',
            //baseCls:'panel',
            itemId: 'quitGridPanel',
            title: 'quit results',
            //routeId: 'user',
            bind: '{quitLists}',
            scrollable: false,
            columns: [
                {xtype: 'gridcolumn', dataIndex: 'id', text: 'id', hidden: true,},
                {xtype: 'gridcolumn', cls: 'content-column', dataIndex: 'employeeName', text: '员工姓名'},
                {xtype: 'gridcolumn', cls: 'content-column', dataIndex: 'departmentName', text: '部门'},

                {
                    xtype: 'datecolumn',
                    cls: 'content-column',
                    width: 150,
                    dataIndex: 'applyDate',
                    text: '申请时间',
                    format: 'Y/m/d H:i:s'
                },
                {
                    xtype: 'datecolumn',
                    cls: 'content-column',
                    width: 150,
                    dataIndex: 'quitDate',
                    text: '离职时间',
                    format: 'Y/m/d H:i:s'
                },

                {xtype: 'gridcolumn', cls: 'content-column', dataIndex: 'reason', text: '离职原因时间'},
                {
                    xtype: 'gridcolumn',
                    cls: 'content-column',
                    dataIndex: 'status',
                    text: '状态',
                    renderer: function (val) {
                        if (val == 0) {
                            return "申请中";
                        }
                        else if (val == 1) {
                            return "申请成功";
                        }
                        else if (val == 2) {
                            return "驳回申请";
                        }
                        else if (val == -1) {
                            return "删除";
                        }
                        else if (val < 0) {
                            return "被删除"
                        }

                    }
                },
                //{xtype: 'datecolumn',cls: 'content-column',width: 200,dataIndex: 'orderDate',text: 'orderDate',formatter: 'date("Y/m/d H:i:s")'},

                {
                    xtype: 'actioncolumn',
                    cls: 'content-column',
                    width: 120,
                    dataIndex: 'bool',
                    text: 'Actions',
                    tooltip: 'edit ',
                    flex: 1,
                    items: [
                        {xtype: 'button', cls: 'pencil', iconCls: 'x-fa fa-pencil', handler: 'gridModify'},
                        {xtype: 'button', iconCls: 'x-fa fa-close', handler: 'gridDelete'},
                        //{xtype: 'button',iconCls: 'x-fa fa-ban'	 	,handler: 'gridDisable'}
                    ]
                }, /*{
                    text: '%Capacity',
                    width: 150,

                    xtype: 'widgetcolumn',

                    // This is the widget definition for each cell.
                    // The Progress widget class's defaultBindProperty is 'value'
                    // so its "value" setting is taken from the ViewModel's record "capacityUsed" field
                    // Note that a row ViewModel will automatically be injected due to the existence of
                    // the bind property in the widget configuration.
                    widget: {
                        xtype: 'button',
                        text:"aaa",
                        //bind: '{record.emp}',
                        // bind:{
                        //   rec:'{record}'
                        // },
                        // textTpl: [
                        //     '{percent:number("0")}% capacity'
                        // ]
                        handler:function (btn) {
                            console.log(btn.lookupViewModel().get("record"));
                            console.log(btn.getWidgetRecord());
                            console.log("{record}")
                            //console.log(rec);
                            console.log(btn.dataIndex)
                            console.log(btn.up("widgetcolumn").dataIndex)
                        }
                    }
                }*/

            ],
            // 监听grid事件：
            listeners: {
                selectionchange: function (view, records, selection, eOpts) {
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
                    Ext.m_data = records[0];

                    this.down('#removeBtn').setDisabled(records.length === 0);
                }
            },
            selModel: {type: 'checkboxmodel', checkOnly: true},
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
                    hideLable: true,
                    reference: 'searchFieldName',
                    store: Ext.create("Ext.data.Store", {
                        fields: ["name", "value"],
                        data: [
                            {name: 'id', value: 'id'},
                            {name: '员工id', value: 'employeeid'},
                            {name: '员工名称', value: 'employeeName'},
                            {name: '离职原因', value: 'reason'},
                            {name: '部门id', value: 'departmentid'},
                            {name: '部门名称', value: 'departmentName'},
                            {name: '申请日期', value: 'preApplyDate'},
                            {name: '离职日期', value: 'preQuitDate'},

                        ]
                    }),
                    //label:'查询类型',
                    displayField: 'name',
                    valueField: 'value',
                    value: 'id',
                    //value:'订单编号',
                    editable: false,
                    queryMode: 'local',
                    triggerAction: 'all',
                    emptyText: '请选择查询内容',
                    width: 135,
                    listeners: {
                        change: 'tbarSelectChange'
                    }
                }, '-', {
                    xtype: 'textfield',
                    name: 'searchTextField',
                    reference: 'searchTextField'
                },
                {
                    xtype: 'datefield',
                    name: 'searchDateField',
                    reference: 'searchDateField',
                    format: "Y/m/d H:i:s",
                    hidden: true
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
                    xtype: "departmentcombobox",
                    hidden: true,
                    reference: 'searchComboboxField',
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
                },*/

                '-', {
                    text: 'Search',
                    iconCls: 'fa fa-search',
                    handler: 'quickSearch'
                }, '-', {
                    text: 'Search More',
                    iconCls: 'fa fa-search-plus',
                    handler: 'openSearchWindow'
                }, '->',
                {
                    xtype: 'button',
                    text: 'Add',
                    tooltip: 'Add a new row',
                    iconCls: 'fa fa-plus',
                    handler: 'tbarClickAddBtn'
                }, '-',
                {
                    xtype: 'button',
                    text: '申请离职',
                    tooltip: '申请离职',
                    iconCls: 'fa fa-plus',
                    handler: 'tbarClickApplyQuitBtn'
                }, '-', {
                    xtype: 'button',
                    text: 'Removes',
                    iconCls: 'fa fa-trash',
                    disabled: true,
                    itemId: 'removeBtn',
                    handler: 'tbarClickDeleteMore'
                },
            ],
        }]
});
