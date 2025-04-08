#ifndef SPEECHIDENTIFY_H
#define SPEECHIDENTIFY_H

#include "HttpHandle.h"
#include <QJsonDocument>
#include <QJsonParseError>
#include <QJsonObject>
#include <QJsonArray>
#include <QHostInfo>
#include <QFile>

//获取access_token相关
const QString baiduTokenUrl = "https://aip.baidubce.com/oauth/2.0/token?"
                              "grant_type=client_credentials&client_id=%1&client_secret=%2&";
const QString app_key = "9ePJPR6T7FL0BOQ5S55rRUnO";
const QString secert_key = "EJzmPqizCe8F9BGIpY6v95BSN71aY18q";

//语音识别相关
const QString baiduSpeechUrl = "http://vop.baidu.com/server_api?dev_pid=1537&cuid=%1&token=%2";


class SpeechIdentify : public QObject
{
    Q_OBJECT
public:
    explicit SpeechIdentify(QObject *parent = nullptr);

    //根据filename中的语音，转化成replyData中的数据
    bool speechIdentify(const QString &filename, QString &replyData);
    bool getJsonValue(const QByteArray &data,
                      const QString &key, QString &replyData);

signals:

public slots:

private:
    HttpHandle *http;
    QMap<QString, QString> header;
    QString tokenUrl;
    //用户发送的内容
    QByteArray userSendData;
    //服务器返回内容
    QByteArray serverReplyData;
};

#endif // SPEECHIDENTIFY_H
