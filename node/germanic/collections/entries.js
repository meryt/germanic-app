/**
 * Collection for entries
 */

var Backbone = require('backbone'),
    sd       = require('sharify').data,
    Entry    = require('../models/entry');

module.exports = Entries = Backbone.Collection.extend({

    model : Entry,

    url: function() {
        return sd.API_URL + '/pages/' + this.page_id;
    },

    initialize: function(models, options) {
        this.page_id = options.page_id;
        this.page = options.page;
    }

});
