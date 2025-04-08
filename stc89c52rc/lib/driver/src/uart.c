#include "uart.h"
#include "reg51.h"

void uart_init()            //57600bps@11.0592MHz
{
    PCON |= 0x80;           //使能波特率倍速位SMOD
    SCON = 0x50;            //8位数据,可变波特率
    // AUXR &= 0xBF;		//定时器时钟12T模式
    // AUXR &= 0xFE;		//串口1选择定时器1为波特率发生器
    TMOD &= 0x0F;           //设置定时器模式
    TMOD |= 0x20;           //设置定时器模式
    // TL1 = 0xFF;          //设置定时初始值
    // TH1 = 0xFF;          //设置定时重载值
    TL1 = TH1 = BPS_11MHz_57600;
    ET1 = 0;                //禁止定时器中断
    TR1 = 1;                //定时器1开始计时
    // ES = 1;              //使能接收中断
}

void uart_set_bps(BPS bps) {
    TL1 = TH1 = bps;
}

void uart_send_byte(uint8_t value) {
    SBUF = value;
    while (TI == 0);
    TI = 0;
}

void uart_send_string(uint8_t *str) {
    for (uint8_t i = 0; str[i] != '\0'; ++i) {
        uart_send_byte(str[i]);
    }
}

uint8_t uart_get_byte() {
    return SBUF;
}
