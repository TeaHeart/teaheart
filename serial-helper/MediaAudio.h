#ifndef MEDIAAUDIO_H
#define MEDIAAUDIO_H

#include <QObject>
#include <QAudioFormat>
#include <QAudioDeviceInfo>
#include <QAudioInput>
#include <QFile>

class MediaAudio : public QObject
{
    Q_OBJECT
public:
    explicit MediaAudio(QObject *parent = nullptr);

    bool startAudio(const QString &filename);
    void stopAudio();

signals:

public slots:

private:
    QAudioInput *audio;
    QFile *file;
};

#endif // MEDIAAUDIO_H
