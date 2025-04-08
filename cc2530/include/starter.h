#ifndef STARTER_H
#define STARTER_H

#ifndef SDCC
#define SDCC
#include "lint.h"
#endif //SDCC

#include "iocc2530.h"

#include "stdint.h"
#include "stdbool.h"

void set_32MHz(){
    CLKCONCMD &= ~0x40;         // 设置系统时钟源为32MHZ晶振
    while (CLKCONSTA & 0x40);   // 等待晶振稳定
    CLKCONCMD &= ~0x47;         // 设置系统主时钟频率为32MHZ
}

void delay_ms(uint16_t ms) {
    while (ms-- != 0) {
        for (uint8_t i = 0; i < 10; ++i) {
            for (uint8_t j = 0; j < 160; ++j) {
                __asm nop __endasm;
            }
        }
    }
}

#endif //STARTER_H
