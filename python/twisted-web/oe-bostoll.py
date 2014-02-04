#!/bin/env python

from twisted.web.server import Site
from twisted.web.resource import Resource
from twisted.internet import reactor

class EntryPage(Resource):
    def __init__(self, headword):
        Resource.__init__(self)
        self.headword = headword

    def render_GET(self, request):
        return "<html><body>%s</pre></html>" % (self.headword,)

class EntryBrowser(Resource):
    def getChild(self, name, request):
        return EntryPage(name)

root = Resource()
root.putChild("entry", EntryBrowser())
factory = Site(root)
reactor.listenTCP(8880, factory)
reactor.run()
