Ext.define('Admin.view.main.Main', {
    extend: 'Ext.container.Viewport',

    requires: [
        'Ext.button.Segmented',
        'Ext.list.Tree'
    ],

    controller: 'main',
    viewModel: 'main',

    cls: 'sencha-dash-viewport',
    itemId: 'mainView',

    layout: {
        type: 'vbox',
        align: 'stretch'
    },

    listeners: {
        render: 'onMainViewRender'
    },

    items: [
        {
            xtype: 'toolbar',
            cls: 'sencha-dash-dash-headerbar shadow',
            height: 64,
            itemId: 'headerBar',
            items: [
                {
                    xtype: 'component',
                    reference: 'senchaLogo',
                    cls: 'sencha-logo',
                    html: '<div class="main-logo"><img src="resources/images/company-logo.png">OA办公自动化系统</div>',
                    width: 250
                },
                {
                    margin: '0 0 0 8',
                    ui: 'header',
                    iconCls:'x-fa fa-navicon',
                    id: 'main-navigation-btn',
                    handler: 'onToggleNavigationSize'
                },
                '->',
                {
                	//职位
                    xtype: 'tbtext',
                    text: '（职位）',
                    id:'userPositon',
                    hidden:true,
                    cls: 'top-user-name'
                },
                {
                	//用户名称
                    xtype: 'tbtext',
                    text: '用户名 :Admin',
                    id:'loginUserName',
                    cls: 'top-user-name'
                    //reference: 'nameChange'
                },
                {
                	//用户头像
                	xtype:'image',
                    id:'head_Icon',
                    cls: 'header-right-profile-image',
                    width:35,
                    height:35,
                    alt:'点击上传',
                    src:'http://localhost:8080/images/employee/tips.jpg',
                    //reference:"headButton",
                    style:{
                    	cursor: 'pointer',
                        borderRadius: '50%'//显示圆形图片
                    },
                    listeners:{
                    	//监听click事件
                        el:{
                            click:'onClickGridUpload' //imgClick方法写在了controller中，在这里也可以直接替换成function（）{console.log('click')}
                        }
                    }         
                },
                {
                    iconCls:'x-fa fa-sign-out',
                    ui: 'header',
                    tooltip: 'Logout',
                    handler: 'logoutButton'
                }
            ]
        },
        {
            xtype: 'maincontainerwrap',
            id: 'main-view-detail-wrap',
            reference: 'mainContainerWrap',
            flex: 1,
            items: [
                {
                    xtype: 'treelist',
                    reference: 'navigationTreeList',
                    itemId: 'navigationTreeList',
                    ui: 'nav',
                    store: 'NavigationTree',
                    width: 250,
                    expanderFirst: false,
                    expanderOnly: false,
                    listeners: {
                        selectionchange: 'onNavigationTreeSelectionChange'
                    }
                },
                {
                    xtype: 'container',
                    flex: 1,
                    reference: 'mainCardPanel',
                    cls: 'sencha-dash-right-main-container',
                    itemId: 'contentPanel',
                    layout: {
                        type: 'card',
                        anchor: '100%'
                    }
                }
            ]
        }
    ]
});
