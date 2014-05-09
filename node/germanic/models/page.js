/**
 * Model for a page
 */

var Backbone = require('backbone'),
    sd       = require('sharify').data;

module.exports = Page = Backbone.Model.extend({
    url: function() {
        // TODO this is wrong - the pages endpoint leads to a list of entries
        return sd.API_URL + '/pages/' + this.get('page_id');
    }
});
