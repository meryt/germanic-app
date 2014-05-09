/**
 * Routes file that exports routes handlers for ease of testing.
 */

var Pages = require('../../collections/pages');

exports.index = function(req, res, next) {
    var pages = new Pages(null, {

    });

    pages.fetch({
        success: function() {
            res.locals.sd.PAGES = pages.toJSON();
            res.render('index', { pages: pages.models });
        },
        error: function(m, err) {
            console.log("Error in fetch");
            next(err.text);
        }
    });
};
