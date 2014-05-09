/**
 * Model for a page
 */

var Backbone = require('backbone'),
    sd       = require('sharify').data;

module.exports = Page = Backbone.Model.extend({
    url: function() {
        return sd.API_URL + '/pages/' + this.get('page_id');
    }
});
