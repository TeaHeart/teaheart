#ifdef TASK11

#include "reg51.h"
#include "uart.h"

uint8_t i = 0;

void uart_isr() __interrupt(SI0_VECTOR) {
    if (RI == 1) {
        uint8_t value = uart_get_byte();
        uart_send_byte(i++);
        uart_send_byte(value);
        RI = 0;
    }
}

int main() {
    uart_init();
    uart_set_bps(BPS_11MHz_2400);
    EA = ES = 1;
    while (true);
}

#endif
