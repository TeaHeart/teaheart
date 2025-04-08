#ifdef TASK6

#include "reg51.h"
#include "delay.h"
#include "timer.h"
#include "nixie.h"
#include "adc0809.h"
#include "stdbool.h"
#include "util.h"

#define S0 P2_6
#define S1 P2_7

void timer0_isr() __interrupt(TF0_VECTOR) {
    TL0 = 0x66;
    TH0 = 0xFC;
    nixie_scan();
    adc0809_toggle_clock();
}

void show_volts(uint8_t value) {
    uint16_t volts = (uint16_t) ((uint32_t) value * 500 / 255);
    nixie_write_at(-3, nixie_seg('0' + volts / 100 % 10) | nixie_seg('.'));
    nixie_write_at(-2, nixie_seg('0' + volts / 10 % 10));
    nixie_write_at(-1, nixie_seg('0' + volts / 1 % 10));
}

int main() {
    timer0_init();
    EA = 1;
    while (true) {
        uint8_t channel = S0 == 1 ? 0 : 1;
        if (S1 == 0) {
            uint8_t arr[5] = {0};
            uint8_t n = LENGTH_OF(arr);
            for (uint8_t i = 0; i < n; i++) {
                arr[i] = adc0809_read_ad(channel);
                delay_ms(1);
            }
            insert_sort(arr, n);
            show_volts(arr[n / 2]);
        } else {
            show_volts(adc0809_read_ad(channel));
        }
        delay_ms(1);
    }
}

#endif //TASK6
