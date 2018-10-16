Ext.define('Aria.view.salary.SalarySearchWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.salarySearchWindow',
    //height: 200,
    minHeight: 100,
    minWidth: 300,
    width: 500,
    scrollable: true,
    title: 'Salary Search Window',
    closable: true,
    constrain: true,
    defaultFocus: 'textfield',
    modal: true,
    autoShow:true,
    layout: 'fit',
    items: [{
        xtype: 'form',
        layout: 'form',
        padding: '10px',
        ariaLabel: 'Enter your name',
        items: [
            {
                xtype: 'numberfield',
                fieldLabel: 'id',
                name: 'id'
            },
            {
                xtype: 'textfield',
                fieldLabel: '员工工号',
                name: 'employeeid'
            }
            , {
                xtype: 'textfield',
                fieldLabel: '员工姓名',
                name: 'employeeName'
            }
            , {
                xtype: 'numberfield',
                fieldLabel: '工资范围',
                name: 'preSal'
            }
            , {
                xtype: 'numberfield',
                fieldLabel: '工资范围',
                name: 'sufSal'
            }
            , {
                xtype: 'numberfield',
                fieldLabel: '奖金范围',
                name: 'preBonus'
            }
            , {
                xtype: 'numberfield',
                fieldLabel: '奖金范围',
                name: 'sufBonus'
            }
            , {
                xtype: 'numberfield',
                fieldLabel: '工龄范围',
                name: 'preWorkMonth'
            }
            , {
                xtype: 'numberfield',
                fieldLabel: '工龄范围',
                name: 'sufWorkMonth',
                //禁止小数
                allowDecimals:false
            }
           /* , {
                xtype: 'numberfield',
                fieldLabel: '工龄范围',
                name: 'sufWorkMonth',
                //禁止小数
                allowDecimals:false
            }*/
            , {
                xtype: 'numberfield',
                fieldLabel: '工龄工资范围',
                name: 'preWorktimeMoney',
            }
            , {
                xtype: 'numberfield',
                fieldLabel: '工龄工资范围',
                name: 'sufWorktimeMoney',
            }
            , {
                xtype: 'numberfield',
                fieldLabel: '补贴范围',
                name: 'preSubsidy',
            }
            , {
                xtype: 'numberfield',
                fieldLabel: '补贴范围',
                name: 'sufSubsidy',
            }


        ]
    }],
    buttons: ['->', {
        xtype: 'button',
        text: 'Submit',
        handler: 'submitSearchForm'
    }, {
        xtype: 'button',
        text: 'Close',
        handler: function (btn) {
            btn.up('window').close();
        }
    }, '->']
});
