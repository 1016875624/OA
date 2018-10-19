Ext.define('Admin.view.membership.MemberShipTree', {
    extend: 'Ext.panel.Panel',
    xtype: 'membershipTree',
    controller: 'membershipcontroller',

    requires: [
       // 'Ext.d3.chart.Org',
        'Admin.store.MemberShipStore',
        //'Ext.d3.hierarchy.tree.HorizontalTree'
    ],

    width: 1200,
    height: 700,

    layout: 'fit',

    items: {
        xtype: 'panel',
        layout: 'fit',

        title: 'Sencha Org Chart',

        items: {
            xtype: 'membershipChart',

            interactions: {
                type: 'panzoom',
                zoom: {
                    extent: [0.5, 2],
                    doubleTap: false
                }
            },

            tooltip: {
                renderer: 'onTooltip'
            },

            nodeSize: [200, 100],

            imagePath: 'http://localhost:8080/images/employee/',

            store: {
                type: 'membershipstore'
            }
        }
    }

});