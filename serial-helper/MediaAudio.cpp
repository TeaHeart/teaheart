#include "MediaAudio.h"

MediaAudio::MediaAudio(QObject *parent) : QObject(parent)
{

}

bool MediaAudio::startAudio(const QString &filename)
{
    //判断你的电脑是否存在录音设备，不存在则警告，存在则打开设备开始录音
    QAudioDeviceInfo device = QAudioDeviceInfo::defaultInputDevice();
    if (device.isNull()) {
        return false;
    }

    //设置音频编码要求(16000、16bit、单通道、audio/pcm)
    QAudioFormat format;
    //设置采样频率
    format.setSampleRate(16000);
    //设置位深
    format.setSampleSize(16);
    //设置通道数
    format.setChannelCount(1);
    //设置编码
    format.setCodec("audio/pcm");
    //判断设备是否支持该格式，若是不支持则匹配近似的格式
    if (!device.isFormatSupported(format)) {
        format = device.nearestFormat(format);
    }

    //打开文件
    file = new QFile(filename);
    file->open(QIODevice::Truncate | QIODevice::WriteOnly); //重写只写方式打开

    //创建录音对象，开始录音
    //state函数返回QAudio::IdleState
    //error函数返回QAudio::NoError
    audio = new QAudioInput(format, this);
    audio->start(file);

    return true;
}

void MediaAudio::stopAudio()
{
    //停止录音
    audio->stop();
    //关闭文件
    file->close();
    //删除文件对象
    delete file;
    file = nullptr;
}
