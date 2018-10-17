Ext.define('Admin.view.salarypay.SalarypayWorkOverTimePanel', {
    extend: 'Ext.panel.Panel',
    xtype: 'salarypayWorkOverTimePanel',

    requires: [
        'Ext.grid.Panel',
        'Ext.toolbar.Paging',
        'Ext.form.field.ComboBox',
        'Ext.grid.column.Date'
    ],
    //controller: 'searchresults',
    // viewModel: {type: 'orderViewModel'},
    layout: 'fit',

    //viewModel: {type: 'salarypayViewModel'},
    items: [
        {
            xtype: 'gridpanel',
            cls: 'workOverTime-grid',
            itemId: 'workOverTimePannel',
            reference:'workOverTimePannel',
            //bind:"{workOverTimeList}",
            //store:"{workOverTimeList}",
            //title: '加班排行榜',
            scrollable: true,
            //model:true,
            columns: [
                {xtype: 'gridcolumn', cls: 'content-column', dataIndex: 'employeeName', text: '员工姓名', flex: 1},
                {xtype: 'gridcolumn', cls: 'content-column', dataIndex: 'departmentName', text: '部门', flex: 1},
                {xtype: 'gridcolumn', cls: 'content-column', dataIndex: 'overHours', text: '加班时间', flex: 1},
            ],
            // 监听grid事件：
            listeners: {
                // selectionchange: function (view, records, selection, eOpts) {
                //     Ext.m_data = records[0];
                //     this.down('#removeBtn').setDisabled(records.length === 0);
                // }
            },
            selModel: {type: 'checkboxmodel', checkOnly: true},
            dockedItems: [{
                xtype: 'pagingtoolbar',
                reference: 'pageBar',
                dock: 'bottom',
                itemId: 'paginationToolbar',
                displayInfo: true,
                //bind: '{workOverTimeList}'
            }],
            tbar: [
                /*{
                    xtype: 'combobox',
                    hideLable: true,
                    reference: 'searchFieldName',
                    store: Ext.create("Ext.data.Store", {
                        fields: ["name", "value"],
                        data: [
                            {name: 'id', value: 'id'},
                            {name: '员工id', value: 'employeeid'},
                            {name: '员工名称', value: 'employeeName'},
                            {name: '部门', value: 'departmentid'},
                            {name: '实际工资', value: 'money'},
                            {name: '实际工作时间', value: 'realWorktime'},
                            {name: '工作时间', value: 'worktime'},
                            {name: '出勤率', value: 'attendRate'},
                            {name: '发放日期', value: 'startDate'},
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
                },
                '-', {
                    xtype: 'textfield',
                    name: 'searchTextField',
                    reference: 'searchTextField'
                },
                {
                    xtype:'datefield',
                    name:'searchDateField',
                    reference:'searchDateField',
                    format:"Y/m/d H:i:s",
                    hidden:true
                },
                {
                    xtype: 'datefield',
                    name: 'searchDateField',
                    reference: 'searchDateField',
                    format: "Y/m/d",
                    hidden: true
                },*/
                {
                    xtype: "departmentcombobox",
                    width:200,
                    //hidden: true,
                    reference: 'workOverTimeDepartmentCombobox',
                    label:"请选择部门",
                    fieldLabel:"请选择部门",
                    listeners:{
                        change:"departmentChange"
                    }
                },'-',
                {
                    xtype:'datefield',
                    fieldLabel:"时间范围",
                    reference: 'workOverTimeStartDate',
                    //reference:'searchDateFieldValue',
                    //formatter: 'date("Y/m/d H:i:s")',
                    formatter: 'date("Y/m/d")',
                    //bind:"{startDate}"
                    //hidden:true
                },'-',
                {
                    xtype:'datefield',
                    reference: 'workOverTimeEndDate',
                    //reference:'searchDateFieldValue',
                    //formatter: 'date("Y/m/d H:i:s")',
                    formatter: 'date("Y/m/d")',
                    //bind:"{endDate}"
                    //formBind:true
                },'->',
                {
                    xtype:"button",
                    text:"查找",
                    /*handler:function () {
                        Ext.toast("查找中....");
                    }*/
                    handler:"workOverTimeSearchBtn"
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

            ],
        }]
});
