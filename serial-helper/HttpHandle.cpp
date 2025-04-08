#include "HttpHandle.h"

HttpHandle::HttpHandle(QObject *parent) : QObject(parent),
    manager(new QNetworkAccessManager(this))
{

}

bool HttpHandle::postSpeechData(const QString &url, const QMap<QString, QString> &headers,
                                const QByteArray &body, QByteArray &replyData)
{
    QNetworkRequest request(url);
    auto it = headers.begin();
    for (; it != headers.end(); ++it) {
        request.setRawHeader(it.key().toUtf8(), it.value().toUtf8());
    }

    QNetworkReply *reply = manager->post(request, body);
    QEventLoop loop;

    connect(reply, &QNetworkReply::finished, &loop, &QEventLoop::quit);
    loop.exec();

    if ((reply != nullptr) && (reply->error() == QNetworkReply::NoError)) {
        //读取服务器返回的数据
        replyData = reply->readAll();
        //qDebug() << replyData;
        return true;
    } else {
        qDebug() << "请求失败";
        return false;
    }
}
