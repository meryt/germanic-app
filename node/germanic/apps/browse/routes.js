/**
 * Routes file that exports routes handlers for ease of testing.
 */

var Pages = require('../../collections/pages');

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
