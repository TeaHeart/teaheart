#ifdef TASK4

#include "reg51.h"
#include "buzzer.h"
#include "delay.h"
#include "timer.h"
#include "button.h"
#include "nixie.h"
#include "util.h"

uint16_t __code MUSIC[] = {
        64285,
        64424,
        64535,
        64626,
        64702,
        64766,
        64821,
        64868,
        64910,
        64947,
        64979,
        65009,
        65035,
        65059,
        65080,
        65100,
        65118,
};

uint8_t index = 0;

void timer0_isr() __interrupt(TF0_VECTOR) {
    TL0 = 0x66;
    TH0 = 0xFC;
    single_button_scan();
    nixie_scan();
}

void timer1_isr() __interrupt(TF1_VECTOR) {
    TH1 = MUSIC[index] / 0x100;
    TL1 = MUSIC[index] % 0x100;
    buzzer_toggle();
}

enum CountDownType {
    _24_Second = 2400,
    _12_Second = 1200
} type = _24_Second;

enum CountDownMode {
    Running,
    Pause,
    End
} mode = Pause;

uint16_t time = (uint16_t) _24_Second;

void show_time(uint8_t start) {
    nixie_write_at(start++, nixie_seg('0' + time / 1000 % 10));
    nixie_write_at(start++, nixie_seg('0' + time / 100 % 10) | nixie_seg('.'));
    nixie_write_at(start++, nixie_seg('0' + time / 10 % 10));
    nixie_write_at(start, nixie_seg('0' + time / 1 % 10));
}

void nixie_fill_char(uint8_t start, uint8_t c) {
    nixie_write_at(start++, nixie_seg(c));
    nixie_write_at(start++, nixie_seg(c));
    nixie_write_at(start++, nixie_seg(c));
    nixie_write_at(start, nixie_seg(c));
}

void warning(uint16_t duration) {
    int8_t dir = 1;
    bool is_show = false;
    ET1 = 1;
    for (uint16_t i = 0; i < duration; i += 10) {
        index += dir;
        if (index == 0 || index == LENGTH_OF(MUSIC) - 1) {
            dir = -dir;
        }
        delay_ms(10);
        if (i % 200 == 0) {
            if (is_show) {
                nixie_fill_char(4, '0');
            } else {
                nixie_fill_char(4, 0);
            }
            is_show = !is_show;
        }
    }
    nixie_fill_char(4, '0');
    ET1 = 0;
    buzzer_set(1);
}

void count_down() {
    --time;
    show_time(4);
    if (time == 0) {
        mode = End;
        warning(5000);
    }
    delay_ms(10);
}

void reset() {
    time = (uint16_t) type;
    mode = Pause;
    nixie_clear();
    show_time(4);
}

int main() {
    timer0_init();
    timer1_init();
    ET1 = 0;
    EA = 1;
    while (true) {
        switch (single_button_clear_key()) {
            case KEY_0: {
                reset();
                break;
            }
            case KEY_1: {
                if (mode == Running) {
                    mode = Pause;
                } else if (mode == Pause) {
                    mode = Running;
                }
                break;
            }
            case KEY_2: {
                show_time(0);
                break;
            }
            case KEY_3: {
                type = type != _24_Second ? _24_Second : _12_Second;
                reset();
                break;
            }
        }
        if (mode == Running) {
            count_down();
        }
    }
}

#endif //TASK4
