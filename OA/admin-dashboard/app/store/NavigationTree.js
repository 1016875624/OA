Ext.define('Admin.store.NavigationTree', {
    extend: 'Ext.data.TreeStore',

    storeId: 'NavigationTree',

    fields: [{
        name: 'text'
    }],

    root: {
        expanded: true,
        children: [
            {
                text: 'Dashboard',
                iconCls: 'x-fa fa-desktop',
                rowCls: 'nav-tree-badge nav-tree-badge-new',
                viewType: 'admindashboard',
                routeId: 'dashboard', // routeId defaults to viewType
                leaf: true
            },{
                text: '流程定义模块',
                iconCls: 'x-fa fa-address-card',
                viewType: 'processDefinitionCenterPanel',
                leaf: true
            },
            {
                text: '部门',
                iconCls: 'x-fa fa-building-o',
                //rowCls: 'nav-tree-badge nav-tree-badge-new',
                viewType: 'department',
                leaf: true
            },
            {
                text: '员工管理模块',
                iconCls: 'x-fa fa-user',
                //rowCls: 'nav-tree-badge nav-tree-badge-new',
                viewType: 'employeeCenterPanel',
                leaf: true
            },{
                text: '订单管理模块',
                iconCls: 'x-fa fa-address-card',
                //rowCls: 'nav-tree-badge nav-tree-badge-new',
                viewType: 'orderCenterPanel',
                leaf: true
            },{
				text: '请假模块',
                iconCls: 'x-fa fa-leanpub',
				expanded: false,
                selectable: false,
				children:[
					{
						text: '请假管理模块',
						iconCls: 'x-fa fa-address-card',
						viewType: 'leaveCenterPanel',
						leaf: true
					},{
						text: '请假审批模块',
						iconCls: 'x-fa fa-address-card',
						viewType: 'leaveApproveCenterPanel',
						leaf: true
					}
				]
			},{
				text: '办公资源模块',
                iconCls: 'x-fa fa-address-card',
                expanded: false,
                selectable: false,
                children:[
					{
						text: '办公资源',
						iconCls: 'x-fa fa-address-card',
						viewType: 'officeResourceCenterPanel',
						leaf: true
					},{
						text: '员工资源',
						iconCls: 'x-fa fa-address-card',
						viewType: 'employeeResourceCenterPanel',
						leaf: true
					},{
						text: '员工交易资源',
						iconCls: 'x-fa fa-address-card',
						viewType: 'employeeResourceTradeCenterPanel',
						leaf: true
					}
				]
			},{
                text: 'Login',
                iconCls: 'x-fa fa-check',
                viewType: 'login',
                //hidden:true,
                //style:'display:none',
                leaf: true
           },{
               text: '工时',
               iconCls: 'x-fa fa-address-card',
               viewType: 'workTimeCenterPanel',
               leaf: true
           },{
               text: '审批工时',
               iconCls: 'x-fa fa-address-card',
               viewType: 'workTimeApprovalCenterPanel',
               leaf: true
           },{
               text: '题库管理',
               iconCls: 'x-fa fa-address-card',
               //viewType: 'questionCenterPanel',
               children:[
                   {
                       text: '题库',
                       iconCls: 'x-fa fa-address-card',
                       viewType: 'questionCenterPanel',
                       leaf: true
                   },
                   {
                       text:'考试',
                       iconCls: 'x-fa fa-address-card',
                       viewType: 'testPaperCenterPanel',
                       leaf: true
                   }
               ]
           },{
               text: '离职',
               iconCls: 'x-fa fa-address-card',
               //viewType: 'quit',
               //leaf: true
                children:[
                    {
                        text: '离职管理',
                        iconCls: 'x-fa fa-address-card',
                        viewType: 'quit',
                        leaf: true
                    },
                    {
                        text:'离职审批',
                        iconCls: 'x-fa fa-address-card',
                        viewType: 'quitApproval',
                        leaf: true
                    }
                ]
           }
            ,{
                text: '薪资发放',
                iconCls: 'x-fa fa-address-card',
                viewType: 'salarypay',
                leaf: true
            }
            ,{
                text: '薪资',
                iconCls: 'x-fa fa-address-card',
                viewType: 'salary',
                leaf: true
            },
            {
                text: '员工关系',
                iconCls: 'x-fa fa-address-card',
                viewType: 'membershipTree',
                leaf: true
            },

        ]
    }
});
