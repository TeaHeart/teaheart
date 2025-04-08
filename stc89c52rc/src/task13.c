#ifdef TASK13

#include "reg51.h"
#include "delay.h"
#include "timer.h"
#include "nixie.h"
#include "adc0809.h"
#include "uart.h"

void timer0_isr() __interrupt(TF0_VECTOR) { // 1ms
    TL0 = 0x66;
    TH0 = 0xFC;
    adc0809_toggle_clock();
    nixie_scan();
}

// 如 "128 2.50V"
uint8_t buf[] = "000 0.00V\r\n";

void format_volts(uint8_t value) {
    static uint8_t charset[] = "0123456789";
    uint16_t volts = (uint16_t) ((uint32_t) value * 500 / 255);
    buf[0] = charset[value / 100 % 10];
    buf[1] = charset[value / 10 % 10];
    buf[2] = charset[value / 1 % 10];
    // buf[3] = ' ';
    buf[0 + 4] = charset[volts / 100 % 10];
    // buf[1 + 4] = '.';
    buf[2 + 4] = charset[volts / 10 % 10];
    buf[3 + 4] = charset[volts / 1 % 10];
    // buf[4 + 4] = 'V';
}

void show_volts() {
    nixie_clear();
    nixie_write_at(1, nixie_seg(buf[0]));
    nixie_write_at(2, nixie_seg(buf[1]));
    nixie_write_at(3, nixie_seg(buf[2]));
    nixie_write_at(-3, nixie_seg(buf[0 + 4]) | nixie_seg('.'));
    nixie_write_at(-2, nixie_seg(buf[2 + 4]));
    nixie_write_at(-1, nixie_seg(buf[3 + 4]));
}

int main() {
    timer0_init();
    uart_init();
    uart_set_bps(BPS_11MHz_1200);
    EA = 1;
    while (true) {
        uint8_t value = adc0809_read_ad(0);
        format_volts(value);
        uart_send_string(buf);
        show_volts();
        delay_ms(1000);
    }
}

#endif
