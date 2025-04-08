//
// Created by Administrator on 24-11-19.
//

// You may need to build the project (run Qt uic code generator) to get "ui_MainWindow.h" resolved

#include "mainwindow.h"
#include "ui_mainwindow.h"

#include <map>
#include <vector>
#include <QThread>

using namespace std;

const map<QString, QString> option_map = {
    {"开灯", "1"},
    {"关灯", "2"},
    {"开风扇", "3"},
    {"关风扇", "4"},
    {"启动", "5"},
    {"关闭", "6"},
    {"手动", "M"}
};

const vector<int> baud_list = {1200, 2400, 4800, 9600, 19200, 38400, 57600, 115200};

MainWindow::MainWindow(QWidget *parent)
    : QWidget(parent),
      ui(new Ui::MainWindow),
      serialPort(new QSerialPort()),
      audio(new MediaAudio()),
      speech(new SpeechIdentify()),
      tts(new QTextToSpeech()) {
    ui->setupUi(this);
    auto port_list = QSerialPortInfo::availablePorts();
    for (auto &port: port_list) {
        ui->cbPortName->addItem(port.portName());
    }
    ui->cbPortName->setCurrentIndex(port_list.size() - 1);
    for (auto baud: baud_list) {
        ui->cbPortBaud->addItem(QString::number(baud));
    }
    ui->cbPortBaud->setCurrentIndex(baud_list.size() - 1);
    connect(serialPort, QSerialPort::readyRead, this, dataReceived);
}

MainWindow::~MainWindow() {
    delete ui;
    delete serialPort;
    delete audio;
    delete speech;
    delete tts;
}

void MainWindow::on_pbSwitch_clicked() {
    if (ui->pbSwitch->text().compare("打开") == 0) {
        serialPort->setPort(QSerialPortInfo(ui->cbPortName->currentText()));
        // TODO 其他可选择
        serialPort->setBaudRate(ui->cbPortBaud->currentText().toInt());
        serialPort->setDataBits(QSerialPort::Data8);
        serialPort->setStopBits(QSerialPort::OneStop);
        serialPort->setParity(QSerialPort::NoParity);
        serialPort->setFlowControl(QSerialPort::NoFlowControl);
        serialPort->open(QIODevice::ReadWrite);
        ui->pbSwitch->setText("关闭");
    } else {
        serialPort->close();
        ui->pbSwitch->setText("打开");
    }
}

void MainWindow::on_pbSend_clicked() {
    if (serialPort->isOpen()) {
        serialPort->write(ui->leSend->text().toUtf8());
    }
}

void MainWindow::on_pbRecord_clicked() {
    if (ui->pbRecord->text().compare("录音") == 0) {
        audio->startAudio("record.mp3");
        ui->pbRecord->setText("结束");
    } else {
        audio->stopAudio();
        QString data;
        speech->speechIdentify("record.mp3", data);
        ui->teLog->append(data);
        tts->say(data);
        qDebug() << data;
        for (auto &[key, value]: option_map) {
            if (data.contains(key)) {
                if (serialPort->isOpen()) {
                    serialPort->write(value.toUtf8());
                    break;
                }
            }
        }
        ui->pbRecord->setText("录音");
    }
}

void MainWindow::dataReceived() {
    // TODO 接受处理
    ui->teLog->append(serialPort->readAll());
}
