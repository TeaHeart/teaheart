//
// Created by Administrator on 24-11-19.
//

#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include "MediaAudio.h"
#include "SpeechIdentify.h"

#include <QWidget>
#include <QSerialPort>
#include <QSerialPortInfo>
#include <QTextToSpeech>

QT_BEGIN_NAMESPACE

namespace Ui {
    class MainWindow;
}

QT_END_NAMESPACE

class MainWindow : public QWidget {
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = nullptr);

    ~MainWindow() override;

public slots:
    void on_pbSwitch_clicked();

    void on_pbSend_clicked();

    void on_pbRecord_clicked();

    void dataReceived();

private:
    Ui::MainWindow *ui;

    QSerialPort *serialPort;

    MediaAudio *audio;

    SpeechIdentify *speech;

    QTextToSpeech *tts;
};

#endif //MAINWINDOW_H
