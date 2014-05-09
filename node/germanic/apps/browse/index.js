/**
 * The express app for the "browse" app.
 *
 * Simply exports the express instance to be mounted in the project,
 * and loads the routes.
 */

var express = require('express'),
    routes  = require('./routes');

var app = module.exports = express();

app.set('views', __dirname + '/templates');
app.set('view engine', 'jade');

app.get('/', routes.index);
app.get('/pages', routes.pages);
app.get('/pages/:page_id', routes.entries);
