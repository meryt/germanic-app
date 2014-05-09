#!/bin/env python

from termcolor import colored
from twisted.web.server import Site
from twisted.web.resource import Resource
from twisted.internet import reactor
import datetime
import sqlite3
import json

class Simple(Resource):

    numRequests = 0

    def logRequest(self, request):
        self.numRequests += 1
        print colored("[%s] - %s" % (str(datetime.datetime.utcnow()), str(request)), 'cyan', attrs=["bold"])
        print "%s %s from %s for %s" % (
            colored(" Request # %d" % (self.numRequests), 'red'),
            colored(request.method, 'white'),
            colored(request.getClientIP(), 'white', attrs=["bold"]), colored(request.getAllHeaders()['host'], 'yellow', attrs=["bold"]))
        headers = request.getAllHeaders()
        for key in headers:
            if key == 'via' or key == 'x-forwarded-for' or key == 'content-length' or key == 'content-type' or key == 'accept':
                print colored(' ' + key + ": " + headers[key], 'blue')
        print colored(request.content.read(), "yellow")

class EntryPage(Simple):
    def __init__(self, headword):
        Resource.__init__(self)
        self.headword = headword

    def render_GET(self, request):
        return "<html><body>%s</body></html>" % (self.headword,)

class EntryBrowser(Simple):
    def getChild(self, name, request):
        return EntryPage(name)


class PagePage(Simple):
    '''
    View an individual page

    URL:   /page/<page_id>
    '''
    def __init__(self, page_id):
        Simple.__init__(self)
        self.page_id = page_id

    # /pages/<page_id>
    def render_GET(self, request):
        self.logRequest(request)

        request.setHeader('content-type', 'application/json')

        responses = []

        db = sqlite3.connect('./oi_cleasvig.sqlite')
        with db:
            cur = db.cursor()
            cur.execute("SELECT headword, entry FROM entries WHERE page_id = ? ORDER BY line_num", (self.page_id,))
            rows = cur.fetchall()
            for row in rows:
                responses.append({
                    "headword" : row[0],
                    "entry"  : row[1]
                })

        return json.dumps(responses)

class PageBrowser(Simple):
    '''
    Allows listing or viewing Page objects

    URL:   /page[/<page_id>]

    '''
    # /pages/
    def render_GET(self, request):
        self.logRequest(request)
        request.setHeader('content-type', 'application/json')

        responses = []

        db = sqlite3.connect('./oi_cleasvig.sqlite')
        with db:
            cur = db.cursor()
            cur.execute('SELECT id, header FROM pages ORDER BY id')
            rows = cur.fetchall()
            for row in rows:
                responses.append({
                    "page_id" : row[0],
                    "header"  : row[1]
                })

        return json.dumps(responses)

    def getChild(self, name, request):
        if (name.strip() == ''):
            # /pages
            return self
        else:
            return PagePage(name)


class IndexPage(Simple):
    '''
    This is the home page of the site.

    URL:   /
    '''
    isLeaf = True

    def render_GET(self, request):
        return "Hello and welcome"



root = Simple()
root.putChild("", IndexPage())
root.putChild("entries", EntryBrowser())
root.putChild("pages", PageBrowser())
factory = Site(root)
reactor.listenTCP(8880, factory)
reactor.run()
