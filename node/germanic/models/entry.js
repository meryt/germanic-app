/**
 * Model for an entry
 */

var Backbone = require('backbone'),
    sd       = require('sharify').data;

module.exports = Entry = Backbone.Model.extend({
    url: function() {
        return sd.API_URL + '/pages/' + this.collection.page_id;
    }
});
