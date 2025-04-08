#ifndef UART0_H
#define UART0_H

#include "starter.h"

void uart0_send_byte(uint8_t b) {
    U0DBUF = b;
    while (UTX0IF == 0);
    UTX0IF = 0;
    delay_ms(1);
}

void uart0_send_bytes(uint8_t *bytes, uint8_t length) {
    for (uint8_t i = 0; i < length; i++) {
        uart0_send_byte(bytes[i]);
    }
}

void uart0_init() {
    PERCFG = 0x00;              // 位置1 P0口
    P0SEL |= 0x0C;              // P0 用作串口
    P2DIR &= ~0XC0;             // P0 优先作为 UART0

    U0CSR |= 0x80;              // 串口设置为UART方式
    U0GCR |= 11;
    U0BAUD |= 216;              // 波特率设为 115200
    UTX0IF = 0;                 // UART0 TX 中断标志初始置位
    U0CSR |= 0X40;              // 允许接收
    IEN0 |= 0x84;               // 开总中断 接收中断
}

// void uart0_rx_isr() __interrupt(URX0_VECTOR) {
//    uint8_t buf = U0DBUF;
//    {
//        ...
//    }
//    URX0IF = 0;
// }

// int putchar(int c) {
//    uart0_send_byte(c);
//    return c;
// }

#endif //UART0_H
