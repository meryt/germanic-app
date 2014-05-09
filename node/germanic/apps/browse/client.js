/**
 * The client-side code for the browse page
 *
 * This module exports the Backbone view and an init function that gets used in
 * /assets/page.js.  Doing this allows us to easily test these components, and
 * makes the code more module and composable in general.
 */

var Backbone = require('backbone'),
    $        = require('jqyery'),
    sd       = require('sharify').data,
    Pages    = require('../../collections/pages.js'),
    listTemplate = function() {
        return require('./templates/list.jade').apply(null, arguments)
    };
Backbone.$ = $;

module.exports.PagesView = PagesView = Backbone.View.extend({

    initialize: function() {
        this.collection.on('sync', this.render, this);
    },

    render: function() {
        this.$("#pages-list").html(listTemplate({ pages: this.collection.models }));
    }

});

module.exports.init = function() {
    new PagesView({
        el: $('body'),
        collection: new Pages(sd.PAGES, { })
    });
};

