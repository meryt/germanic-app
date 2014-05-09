/**
 * Collection for pages
 */

var Backbone = require('backbone'),
    sd       = require('sharify').data,
    Page     = require('../models/page');

module.exports = Pages = Backbone.Collection.extend({

    model : Page,

    url: function() {
        return sd.API_URL + '/pages';
    },

    initialize: function(models, options) {
        // this.foo = options.foo;
    }

});
