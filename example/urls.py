"""example URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.urls import path

from domain.views import AdminView, GroupView, UserView

admin_view = AdminView()
group_view = GroupView()
user_view = UserView()

urlpatterns = [
    path('', admin_view.login_in),
    path('admin/login', admin_view.login_in),
    path('admin/logout', admin_view.login_out),

    path('group/list', group_view.list),
    path('group/update', group_view.update),
    path('group/delete', group_view.delete),

    path('user/list', user_view.list),
    path('user/update', user_view.update),
    path('user/delete', user_view.delete),
]
