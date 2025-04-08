from django.forms import Form, ModelForm, CharField, PasswordInput, DateInput

from domain.models import Group, User


class FormBase(Form):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        for name, field in self.fields.items():
            field.widget.attrs["class"] = "form-control"
            field.widget.attrs["placeholder"] = field.label


class ModelFormBase(ModelForm):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        for name, field in self.fields.items():
            field.widget.attrs["class"] = "form-control"
            field.widget.attrs["placeholder"] = field.label


class AdminLoginForm(FormBase):
    username = CharField(label="用户名")
    password = CharField(label="密码", widget=PasswordInput(render_value=True))


class GroupModelForm(ModelFormBase):
    class Meta:
        model = Group
        fields = "__all__"


class UserModelForm(ModelFormBase):
    class Meta:
        model = User
        fields = "__all__"
        widgets = {
            "create_time": DateInput(attrs={"type": "date"}, format="%Y-%m-%d")
        }
