#ifdef TASK1

#include "reg51.h"
#include "led.h"
#include "buzzer.h"
#include "delay.h"
#include "timer.h"
#include "button.h"
#include "util.h"

void timer0_isr() __interrupt(TF0_VECTOR) {
    TL0 = 0x66;
    TH0 = 0xFC;
    single_button_scan();
}

void flow_led() {
    static uint8_t i = 0x01;
    led_set(~i);
    buzzer_pulse(500);
    led_set(0xFF);
    delay_ms(500);
    i = i == 0x80 ? 0x01 : i << 1;
}

uint8_t __code ANIME[] = {
        // 奇-偶
        0x55, 0xAA,
        0x55, 0xAA,
        0x55, 0xAA,
        // 左右循环
        0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80,
        0x40, 0x20, 0x10, 0x08, 0x04, 0x02,
        0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80,
        // 边-中
        0x81, 0x42, 0x24, 0x18,
        0x81, 0x42, 0x24, 0x18,
        0x81, 0x42, 0x24,
        // 中-边
        0x18, 0x24, 0x42, 0x81,
        0x18, 0x24, 0x42, 0x81,
        0x18, 0x24, 0x42, 0x81,
        // 全闪
        0xFF, 0x00,
        0xFF, 0x00,
        0xFF, 0x00,
};

void anime_led() {
    static uint8_t i = 0;
    led_set(~ANIME[i]);
    delay_ms(300);
    i = (i + 1) % LENGTH_OF(ANIME);
}

int main() {
    timer0_init();
    EA = 1;
    while (true) {
        switch (single_button_get_key()) {
            case KEY_0: {
                flow_led();
                break;
            }
            case KEY_1: {
                anime_led();
                break;
            }
        }
    }
}

#endif //TASK1
