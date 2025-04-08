from django.db.models import Model, AutoField, CharField, SmallIntegerField, DateField, ForeignKey, CASCADE

gender_choices = (
    (0, '女'),
    (1, '男'),
)


class Admin(Model):
    id = AutoField(verbose_name="编号", primary_key=True)
    username = CharField(verbose_name="用户名", max_length=32, unique=True)
    password = CharField(verbose_name="密码", max_length=32)


class Group(Model):
    id = AutoField(verbose_name="编号", primary_key=True)
    name = CharField(verbose_name="组名", max_length=32)

    def __str__(self):
        return self.name


class User(Model):
    id = AutoField(verbose_name="编号", primary_key=True)
    username = CharField(verbose_name="用户名", max_length=32)
    create_time = DateField(verbose_name="创建时间")
    gender = SmallIntegerField(verbose_name="性别", choices=gender_choices)
    group = ForeignKey(verbose_name="组", to=Group, on_delete=CASCADE)
