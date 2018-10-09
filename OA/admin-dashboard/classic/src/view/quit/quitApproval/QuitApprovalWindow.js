Ext.define('Admin.view.quit.quitApproval.QuitApprovalWindow', {
    extend: 'Ext.window.Window',
    //alias:'widget.orderWindow',

    requires: [
        'Ext.grid.Panel',
        'Ext.toolbar.Paging',
        'Ext.form.field.ComboBox',
        'Ext.grid.column.Date'
    ],
    xtype: 'quitApprovalWindow',
    autoShow: true,
    modal: true,
    layout: 'fit',
    width: 600,
    height: 450,
    title: '修改部门信息',
    items: [{
        xtype: 'form',
        layout: {type: 'vbox', align: 'stretch'},
        bodyPadding: 20,
        scrollable: true,
        default: {
            labelWidth: 100,
            labelSeparator: ''
        },
        defaultType: 'textfield',
        items: [
            {fieldLabel: 'id', name: 'id', hidden: true},
            {fieldLabel: '员工id', name: 'employeeid', disable: true},
            {fieldLabel: '员工姓名', name: 'employeeName', disable: true},
            {fieldLabel: '申请日期', name: 'applyDate', xtype: "datefield", format: 'Y/m/d H:i:s', disable: true},
            {fieldLabel: '离职原因', name: 'reason', disable: true},
            {fieldLabel: '离职日期', name: 'quitDate', xtype: "datefield", format: 'Y/m/d H:i:s'},
            //{fieldLabel: '状态', name: 'status',xtype:'numberfield',allowDecimals:false},
        ],


    }],

    buttons: ['->', {
        id: 'quitWindowSave',
        text: '通过',
        handler: function () {
            this.up('window').close();
        }
    }, {
        text: '驳回',
        handler: function () {
            this.up('window').close();
        }
    }, {
        text: 'Cancel',
        handler: function () {
            this.up('window').close();
        }
    }, '->'
    ]
});
