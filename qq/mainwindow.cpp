#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    setWindowFlag(Qt::FramelessWindowHint);

    QIcon icon = QIcon(":/img/QQ.png");
    QAction* action = new QAction(icon, "", ui->le_username);
    ui->le_username->addAction(action, QLineEdit::LeadingPosition);

    icon = QIcon(":/img/down.png");
    action = new QAction(icon, "", ui->le_username);
    ui->le_username->addAction(action, QLineEdit::TrailingPosition);

    icon = QIcon(":/img/lock.png");
    action = new QAction(icon, "", ui->le_password);
    ui->le_password->addAction(action, QLineEdit::LeadingPosition);

    icon = QIcon(":/img/keyborad.png");
    action = new QAction(icon, "", ui->le_password);
    ui->le_password->addAction(action, QLineEdit::TrailingPosition);

    ui->le_password->setEchoMode(QLineEdit::Password);
}

MainWindow::~MainWindow()
{
    delete ui;
}

