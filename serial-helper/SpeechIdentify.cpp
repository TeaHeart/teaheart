#include "SpeechIdentify.h"

SpeechIdentify::SpeechIdentify(QObject *parent) : QObject(parent),
    http(new HttpHandle(this))
{
    header.insert(QString("Content-Type"), QString("audio/pcm;rate=16000"));
    //获取access_token
    tokenUrl = QString(baiduTokenUrl.arg(app_key).arg(secert_key));;
}

bool SpeechIdentify::speechIdentify(const QString &filename, QString &replyData)
{
    //发起第一次请求
    QString accessToken("");
    QString key = "access_token";
    bool result = http->postSpeechData(tokenUrl, header, userSendData, serverReplyData);
    if (!result) {
        return false;
    }
    result = getJsonValue(serverReplyData, key, accessToken);
    if (!result) {
        return false;
    }

    //组合Url
    QString speechUrl = QString(baiduSpeechUrl.arg(QHostInfo::localHostName())
                                .arg(accessToken));
    //把文件转换成QByteArray
    QFile file(filename);
    file.open(QIODevice::ReadOnly);
    userSendData = file.readAll();
    file.close();
    qDebug() << "url" << speechUrl;

    //再次发起请求
    serverReplyData.clear();
    result = http->postSpeechData(speechUrl, header, userSendData, serverReplyData);
    if (!result) {
        return false;
    }
    key = "result";
    result = getJsonValue(serverReplyData, key, replyData);
    if (!result) {
        return false;
    }

    //qDebug() << replyData;
    userSendData.clear();
    serverReplyData.clear();

    return true;
}

bool SpeechIdentify::getJsonValue(const QByteArray &data,
                                  const QString &key, QString &replyData)
{
    QJsonParseError jsonParseError;
    QJsonDocument jsonDocument = QJsonDocument::fromJson(data, &jsonParseError);
    if (jsonParseError.error != QJsonParseError::NoError) {
        return false;
    }

    if (!jsonDocument.isObject()) {
        return false;
    }

    //jsonDocument转换成json对象
    QJsonObject jsonObject = jsonDocument.object();
    if (!jsonObject.contains(key)) {
        return false;
    }

    QJsonValue jsonValue = jsonObject.value(key);
    if (jsonValue.isString()) {
        replyData = jsonValue.toString();
        return true;
    } else if (jsonValue.isArray()) {
        QJsonArray arr = jsonValue.toArray(); //先转换成jsonArray
        QJsonValue val = arr.at(0);           //获取第一个元素
        replyData = val.toString();
        return true;
    }

    return false;
}
