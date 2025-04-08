#ifdef TASK2

#include "reg51.h"
#include "delay.h"
#include "timer.h"
#include "button.h"
#include "nixie.h"

void timer0_isr() __interrupt(TF0_VECTOR) {
    TL0 = 0x66;
    TH0 = 0xFC;
    single_button_scan();
    nixie_scan();
}

void flow_nixie() {
    static const uint8_t charset[] = "0123456789ABCDEF";
    nixie_clear();
    for (uint8_t i = 0; single_button_get_key() == KEY_0; i = (i + 1) % 16) {
        nixie_write_at(0, nixie_seg(charset[i]));
        delay_ms(100);
    }
}

void show_all() {
    nixie_clear();
    for (uint8_t i = 0; i < 8; ++i) {
        nixie_write_at(i, nixie_seg('0' + i));
    }
    single_button_clear_key();
}

int main() {
    timer0_init();
    EA = 1;
    while (true) {
        switch (single_button_get_key()) {
            case KEY_0: {
                flow_nixie();
                break;
            }
            case KEY_1: {
                show_all();
                break;
            }
        }
    }
}

#endif //TASK2
