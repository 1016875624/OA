Ext.define('Ext.locale.fr.pivot.Aggregators', {
    override: 'Ext.pivot.Aggregators',

    customText:                 'Custom',
    sumText:                    'Somme',
    avgText:                    'Moyenne',
    countText:                  'Nombre',
    minText:                    'Min',
    maxText:                    'Max',
    groupSumPercentageText:     'Pourcentage somme cumulée',
    groupCountPercentageText:   'Pourcentage nombre cumulé',
    varianceText:               'Var',
    variancePText:              'Varp',
    stdDevText:                 'StdDev',
    stdDevPText:                'StdDevp'
});
/**
 * French translation by Daniel Grana
 *
 */

Ext.define('Ext.locale.fr.pivot.Grid', {
    override: 'Ext.pivot.Grid',

    textTotalTpl:       'Total ({name})',
    textGrandTotalTpl:  'Total général'
});
Ext.define('Ext.locale.fr.pivot.plugin.RangeEditor', {
    override: 'Ext.pivot.plugin.RangeEditor',

    textWindowTitle:    'Editeur de plage',
    textFieldValue:     'Valeur',
    textFieldEdit:      'Champ',
    textFieldType:      'Type',
    textButtonOk:       'Ok',
    textButtonCancel:   'Annuler',

    updaters: [
        ['percentage', 'Pourcentage'],
        ['increment', 'Incrément'],
        ['overwrite', 'Ecraser'],
        ['uniform', 'Uniformément']
    ]
});Ext.define('Ext.locale.fr.pivot.plugin.configurator.Column', {
    override: 'Ext.pivot.plugin.configurator.Column',

    sortAscText:                'Trier A to Z',
    sortDescText:               'Trier Z to A',
    sortClearText:              'Desactiver triage',
    clearFilterText:            'Vider filtres de "{0}"',
    labelFiltersText:           'Filtres label',
    valueFiltersText:           'Filtres valeurs',
    equalsText:                 'Egal...',
    doesNotEqualText:           'Différent de...',
    beginsWithText:             'Commence avec...',
    doesNotBeginWithText:       'Ne commence pas avec...',
    endsWithText:               'Termine avec...',
    doesNotEndWithText:         'Ne termine pas avec...',
    containsText:               'Contient...',
    doesNotContainText:         'Ne contient pas...',
    greaterThanText:            'Supérieur à...',
    greaterThanOrEqualToText:   'Supérieur ou égal à...',
    lessThanText:               'Inférieur à...',
    lessThanOrEqualToText:      'Inférieur ou égal à...',
    betweenText:                'Entre...',
    notBetweenText:             'Pas entre...',
    top10Text:                  'Top 10...',

    equalsLText:                'égal à',
    doesNotEqualLText:          'pas égal à',
    beginsWithLText:            'commence avec',
    doesNotBeginWithLText:      'ne commence pas avec',
    endsWithLText:              'termine avec',
    doesNotEndWithLText:        'ne termine pas avec',
    containsLText:              'contient',
    doesNotContainLText:        'ne contient pas',
    greaterThanLText:           'est supérieur à',
    greaterThanOrEqualToLText:  'est supérieur ou égal à',
    lessThanLText:              'est inférieur à',
    lessThanOrEqualToLText:     'est inférieur ou égal à',
    betweenLText:               'est entre',
    notBetweenLText:            'n\'est pas entre',
    top10LText:                 'Top 10...',
    topOrderTopText:            'Top',
    topOrderBottomText:         'Dessous',
    topTypeItemsText:           'Entrées',
    topTypePercentText:         'Pourcentage',
    topTypeSumText:             'Somme'

});Ext.define('Ext.locale.fr.pivot.plugin.configurator.Panel', {
    override: 'Ext.pivot.plugin.configurator.Panel',

    panelAllFieldsText:     'Placez les champs inutilisés ici',
    panelTopFieldsText:     'Placez les champs colonnes ici',
    panelLeftFieldsText:    'Placez les champs lignes ici',
    panelAggFieldsText:     'Placez les champs aggrégés ici',
    panelAllFieldsTitle:    'All fields',
    panelTopFieldsTitle:    'Column labels',
    panelLeftFieldsTitle:   'Row labels',
    panelAggFieldsTitle:    'Values',
    addToText:              'Add to {0}',
    moveToText:             'Move to {0}',
    removeFieldText:        'Remove field',
    moveUpText:             'Move up',
    moveDownText:           'Move down',
    moveBeginText:          'Move to beginning',
    moveEndText:            'Move to end',
    formatText:             'Format as',
    fieldSettingsText:      'Field settings'
});Ext.define('Ext.locale.fr.pivot.plugin.configurator.window.FieldSettings', {
    override: 'Ext.pivot.plugin.configurator.window.FieldSettings',

    title:              'Field settings',
    formatText:         'Format as',
    summarizeByText:    'Summarize by',
    customNameText:     'Custom name',
    sourceNameText:     'Source name',
    alignText:          'Align',
    alignLeftText:      'Left',
    alignCenterText:    'Center',
    alignRightText:     'Right'
});
Ext.define('Ext.locale.fr.pivot.plugin.configurator.window.FilterLabel',{
    override: 'Ext.pivot.plugin.configurator.window.FilterLabel',

    titleText:          'Filtre label ({0})',
    fieldText:          'Afficher entrées avec label',
    caseSensitiveText:  'Sensibilité à la casse'
});Ext.define('Ext.locale.fr.pivot.plugin.configurator.window.FilterTop',{
    override: 'Ext.pivot.plugin.configurator.window.FilterTop',

    titleText:      'Filtre Top 10 ({0})',
    fieldText:      'Afficher',
    sortResultsText:'Trier les résultats'
});Ext.define('Ext.locale.fr.pivot.plugin.configurator.window.FilterValue',{
    override: 'Ext.pivot.plugin.configurator.window.FilterValue',

    titleText:      'Filtre valeurs ({0})',
    fieldText:      'Afficher entrées avec'
});