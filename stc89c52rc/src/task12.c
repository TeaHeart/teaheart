#ifdef TASK12

#include "reg51.h"
#include "timer.h"
#include "nixie.h"
#include "uart.h"

void uart_isr() __interrupt(SI0_VECTOR) {
    if (RI == 1) {
        uint8_t value = uart_get_byte();
        static uint16_t i = 0;
        static uint8_t buf = 0;
        if (i == 0) {
            buf = value;
        } else if (i == 1) {
            nixie_write_uint16(buf << 8 | value);
        }
        if (++i >= 2) {
            i = 0;
        }
        RI = 0;
    }
}

void timer0_isr() __interrupt(TF0_VECTOR) {
    TL0 = 0x66;
    TH0 = 0xFC;
    nixie_scan();
}

int main() {
    timer0_init();
    uart_init();
    uart_set_bps(BPS_11MHz_2400);
    EA = ES = 1;
    while (true);
}

#endif
