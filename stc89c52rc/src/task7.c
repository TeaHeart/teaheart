#ifdef TASK7

#include "reg51.h"
#include "delay.h"
#include "timer.h"
#include "nixie.h"
#include "adc0832.h"
#include "util.h"

#define S0 P2_6
#define S1 P2_7

void timer0_isr() __interrupt(TF0_VECTOR) {
    TL0 = 0x66;
    TH0 = 0xFC;
    nixie_scan();
}

int main() {
    timer0_init();
    EA = 1;
    while (true) {
        uint8_t channel = S0 == 1 ? 0 : 1;
        if (S1 == 0) {
            uint8_t arr[9] = {0};
            uint8_t n = LENGTH_OF(arr);
            for (uint8_t i = 0; i < n; i++) {
                arr[i] = adc0832_read_ad(channel);
                delay_ms(2);
            }
            nixie_write_int16(average(arr, n));
        } else {
            nixie_write_int16(adc0832_read_ad(channel));
        }
        delay_ms(1);
    }
}

#endif //TASK7
