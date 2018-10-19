

Ext.define('Admin.view.membership.MemberShipController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.membershipcontroller',
    onTooltip: function (component, tooltip, node, element, event) {
        var record = node.data,
            name = record.get('name'),
            //title = record.get('title'),
            title = record.get('position'),
            html = '<span style="font-weight: bold">' + name + '</span><br>' + title;
        tooltip.setHtml(html);
    }

});