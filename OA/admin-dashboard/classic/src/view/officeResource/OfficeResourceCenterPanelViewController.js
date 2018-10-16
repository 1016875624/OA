Ext.define('Admin.view.officeResource.OfficeResourceCenterPanelViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.OfficeResourceCenterPanelViewController',

    onTabChange: function(tabs, newTab, oldTab) {
        Ext.suspendLayouts();
        Ext.resumeLayouts(true);
    }
});