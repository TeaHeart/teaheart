#ifdef TASK10

#include "reg51.h"
#include "timer.h"
#include "buzzer.h"
#include "uart.h"

int16_t __code mode[] = {400, 200, 100, -1};

int16_t internal = -1;

void uart_isr() __interrupt(SI0_VECTOR) {
    if (RI == 1) {
        uint8_t value = uart_get_byte();
        if (1 <= value && value <= 4) {
            internal = mode[value - 1];
        } else {
            internal = -1;
        }
        RI = 0;
    }
}

void timer0_isr() __interrupt(TF0_VECTOR) {
    TL0 = 0x66;
    TH0 = 0xFC;
    if (internal < 0) {
        return;
    }
    static uint16_t count = 0;
    if (++count < internal) {
        buzzer_toggle();
    } else if (count < internal * 2) {
        // 什么都不做
    } else {
        count = 0;
    }
}

int main() {
    timer0_init();
    uart_init();
    uart_set_bps(BPS_11MHz_2400);
    EA = ES = 1;
    while (true);
}

#endif
