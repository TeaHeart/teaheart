#ifndef HTTPHANDLE_H
#define HTTPHANDLE_H

#include <QObject>
#include <QMap>
#include <QNetworkAccessManager>
#include <QNetworkRequest>
#include <QNetworkReply>
#include <QEventLoop>
#include <QDebug>

class HttpHandle : public QObject
{
    Q_OBJECT
public:
    explicit HttpHandle(QObject *parent = nullptr);

    //通过POST方法获取数据到replyData中
    bool postSpeechData(const QString &url, const QMap<QString, QString> &headers,
                        const QByteArray &body, QByteArray &replyData);

signals:

public slots:

private:
    QNetworkAccessManager *manager;
};

#endif // HTTPHANDLE_H
