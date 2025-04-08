# 用户管理系统

---

## 环境

- python3.9
- django3.2.5
- docker1.13.1
- centos7

## 运行

```bash
pip install -i https://pypi.tuna.tsinghua.edu.cn/simple -r requirements.txt
# python manage.py makemigrations domain
# python manage.py migrate
python manage.py runserver 8080
# insert into domain_admin (username, password) values ('sa', 'sa') # sql
```

## docker

```bash
sudo yum install -y docker
sudo systemctl enable docker
sudo systemctl start docker
...
sudo docker build -t ums:0.1 .
# sudo docker images
sudo docker run --name ums -d -p 8080:8080 ums:0.1 
# sudo docker ps
```

## 管理员

| ID | 用户名 | 密码 |
|:--:|:---:|:--:|
| 1  | sa  | sa |
