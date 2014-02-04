#!/bin/env python

from twisted.web.server import Site
from twisted.web.resource import Resource
from twisted.internet import reactor

class EntryPage(Resource):
    def __init__(self, headword):
        Resource.__init__(self)
        self.headword = headword

    def render_GET(self, request):
        return "<html><body>%s</body></html>" % (self.headword,)

class EntryBrowser(Resource):
    def getChild(self, name, request):
        return EntryPage(name)


class PagePage(Resource):
    '''
    View an individual page

    URL:   /page/<page_id>
    '''
    def __init__(self, page_id):
        Resource.__init__(self)
        self.page_id = page_id

    # /pages/<page_id>
    def render_GET(self, request):
        return "<html><body>%s</body></html>" % (self.page_id,)

class PageBrowser(Resource):
    '''
    Allows listing or viewing Page objects

    URL:   /page[/<page_id>]

    '''
    # /pages/
    def render_GET(self, request):
        return "<html><body>%s</body></html>" % ("List of pages here",)

    def getChild(self, name, request):
        if (name.strip() == ''):
            # /pages
            return self
        else:
            return PagePage(name)


class IndexPage(Resource):
    '''
    This is the home page of the site.

    URL:   /
    '''
    isLeaf = True

    def render_GET(self, request):
        return "Hello and welcome"



root = Resource()
root.putChild("", IndexPage())
root.putChild("entries", EntryBrowser())
root.putChild("pages", PageBrowser())
factory = Site(root)
reactor.listenTCP(8880, factory)
reactor.run()
