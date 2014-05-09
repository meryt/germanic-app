/**
 * Routes file that exports routes handlers for ease of testing.
 */

var Pages = require('../../collections/pages');
var Entries = require('../../collections/entries');

exports.index = function(req, res, next) {
    var pages = new Pages(null, {

    });

    pages.fetch({
        success: function() {
            res.render('index');
        },
        error: function(m, err) {
            next(err.text);
        }
    });
};


exports.pages = function(req, res, next) {
    var pages = new Pages(null, {

    });

    pages.fetch({
        success: function() {
            res.locals.sd.PAGES = pages.toJSON();
            res.render('pages', { pages: pages.models });
        },
        error: function(m, err) {
            console.log("Error in fetch");
            next(err.text);
        }
    });
};

exports.entries = function(req, res, next) {
    var entries = new Entries(null, {
        page_id: req.params.page_id
    });

    entries.fetch({
        success: function() {
            res.locals.sd.ENTRIES = entries.toJSON();
            res.render('entries', { entries: entries.models });
        },
        error: function(m, err) {
            next(err.text);
        }
    });
};
