from abc import ABCMeta

from django.http import HttpResponseRedirect
from django.utils.deprecation import MiddlewareMixin


class MiddlewareMixinBase(MiddlewareMixin, metaclass=ABCMeta):
    def process_request(self, request):
        ...

    def process_response(self, request, response):
        return response


class AdminAuth(MiddlewareMixinBase):
    def process_request(self, request):
        path = request.path_info
        info = request.session.get("info")
        if path != "/admin/login" and not info:
            return HttpResponseRedirect("/admin/login")
        elif path == "/admin/login" and info:
            return HttpResponseRedirect("/group/list")
