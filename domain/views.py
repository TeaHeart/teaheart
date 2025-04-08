from abc import ABCMeta

from django.core.paginator import Paginator
from django.http import HttpResponseRedirect
from django.shortcuts import render

from domain.forms import AdminLoginForm, UserModelForm, GroupModelForm
from domain.models import Admin, User, Group


class GeneralView(metaclass=ABCMeta):
    GET = "GET"

    def __init__(self, model, model_form, **kwargs):
        self.model = model
        self.model_form = model_form
        self.limit = kwargs.get("limit", 10)
        self.list_title = kwargs.get("list_title", "列表")
        self.add_title = kwargs.get("add_title", "添加")
        self.edit_title = kwargs.get("edit_title", "修改")

    def list(self, request):
        title = self.list_title
        index = int(request.GET.get("index", 1))
        fields = self._get_fields()
        page = Paginator(self._object_mapper(), self.limit).page(index)
        return render(request, "list.html", locals())

    def update(self, request):
        title = self.add_title
        id_ = request.GET.get("id")
        instance = None
        if id_:  # update
            title = self.edit_title
            instance = self.model.objects.filter(id=id_).first()
        if request.method == GeneralView.GET:
            form = self.model_form(instance=instance)
            return render(request, "update.html", locals())
        form = self.model_form(instance=instance, data=request.POST)
        if not form.is_valid():
            return render(request, "update.html", locals())
        form.save()
        return HttpResponseRedirect("list")

    def delete(self, request):
        id_ = request.GET.get("id")
        if id_:
            self.model.objects.filter(id=id_).delete()
        return HttpResponseRedirect("list")

    def _get_fields(self):
        return [x.verbose_name for x in self.model._meta.fields]

    def _object_mapper(self):
        list_ = []
        for row in self.model.objects.all().order_by():
            data = row.__dict__.copy()
            data.pop("_state")
            list_.append(data)
        return list_


class AdminView:
    def login_in(self, request):
        if request.method == GeneralView.GET:
            form = AdminLoginForm()
            return render(request, "login.html", locals())
        form = AdminLoginForm(data=request.POST)
        if not form.is_valid():
            return render(request, "login.html", locals())
        admin = Admin.objects.filter(**form.cleaned_data).first()
        if not admin:
            form.add_error("username", "用户名或密码错误")
            form.add_error("password", "用户名或密码错误")
            return render(request, "login.html", locals())
        request.session["info"] = form.cleaned_data
        return HttpResponseRedirect("/group/list")

    def login_out(self, request):
        request.session.flush()
        return HttpResponseRedirect("/admin/login")


class GroupView(GeneralView):
    def __init__(self):
        super().__init__(Group, GroupModelForm, **{
            "list_title": "组列表",
            "add_title": "添加组",
            "edit_title": "修改组",
        })


class UserView(GeneralView):
    def __init__(self):
        super().__init__(User, UserModelForm, **{
            "list_title": "用户列表",
            "add_title": "添加用户",
            "edit_title": "修改用户",
        })

    def _object_mapper(self):
        list_ = []
        for row in self.model.objects.all().order_by():
            list_.append({
                "id": row.id,
                "name": row.username,
                "create_time": row.create_time.strftime("%Y-%m-%d"),
                "gender": row.get_gender_display(),
                "group": row.group
            })
        return list_
