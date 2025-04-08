#ifdef TASK9

#include "reg51.h"
#include "timer.h"
#include "nixie.h"
#include "dac0832.h"

// 最高电压 0~255
uint8_t max_volts = 255;

// 到达最大电压的时间(ms) 500~2500
uint16_t duration = 1000;

// 256 * duration
uint16_t step = 0;

void nixie_write_uint16_at(int8_t start, uint16_t n) {
    bool is_prefix = true;
    for (uint16_t i = 1000; i != 0; i /= 10, ++start) {
        uint8_t mod = n / i % 10;
        if (is_prefix && mod == 0) {
            nixie_write_at(start, 0);
        } else {
            nixie_write_at(start, nixie_seg('0' + mod));
            is_prefix = false;
        }
    }
}

void update_param() {
    step = 1000L * (max_volts + 1) / duration;
    nixie_write_uint16_at(0, duration);
    nixie_write_uint16_at(4, max_volts);
}

#define KEY_PRESS(P__, body) if (P__ == 0) body

void key_scan() {
    static uint8_t prev = 0;
    uint8_t curr = 0;
    KEY_PRESS(P2_6, curr |= 0x02);
    KEY_PRESS(P2_7, curr |= 0x04);
    KEY_PRESS(P3_6, curr |= 0x10);
    KEY_PRESS(P3_7, curr |= 0x20);
    if (curr == 0 && prev != 0) {
        if (prev == 0x02) {
            max_volts = max_volts <= 7 ? 0 : max_volts - 8;
        }
        if (prev == 0x04) {
            max_volts = max_volts >= 248 ? 255 : max_volts + 8;
        }
        if (prev == 0x10) {
            duration = duration <= 100 ? 100 : duration - 100;
        }
        if (prev == 0x20) {
            duration = duration >= 2000 ? 2000 : duration + 100;
        }
        update_param();
    }
    prev = curr;
}

void generate_wave() {
    static uint16_t i = 0;
    if (i < duration) {
        dac0832_send_da((uint32_t) i * step / 1000);
    }
    if (++i >= duration + 1000) {
        i = 0;
    }
}

void timer0_isr() __interrupt(TF0_VECTOR) {
    TL0 = 0x66;
    TH0 = 0xFC;
    key_scan();
    nixie_scan();
    generate_wave();
}

int main() {
    update_param();
    timer0_init();
    EA = 1;
    while (true);
}

#endif
