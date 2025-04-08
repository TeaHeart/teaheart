#include "starter.h"

void _74HC138_select(uint8_t channel) {
    // P2_7 P2_6 P2_5
    P2 &= ~(0b111 << 5);
    P2 |= channel << 5;
}

static
void Delay1ms()        //@33.000MHz
{
    unsigned char __data i, j;

    i = 6;
    j = 86;
    do {
        while (--j);
    } while (--i);
}

void delay_ms(uint16_t t) {
    while (t--) {
        Delay1ms();
    }
}

void init() {
    _74HC138_select(ULN2003_CHANNEL);
    P0 = 0x00;
    _74HC138_select(LED_CHANNEL);
    P0 = 0xFF;
}


void timer1_isr() __interrupt(TF1_VECTOR) {

}

void uart_isr() __interrupt(SI0_VECTOR) {

}