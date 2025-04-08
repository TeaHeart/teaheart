#include "adc0832.h"
#include "reg51.h"

#define CLK P3_0
#define DI P3_1
#define DO P3_2
#define CS P3_3

void adc0832_send_bit(bool b) {
    DI = b;
    CLK = 1;
    CLK = 0;
}

uint8_t adc0832_read_ad(uint8_t channel) {
    CS = 0;
    CLK = 0;
    adc0832_send_bit(1);
    adc0832_send_bit(1);
    adc0832_send_bit(channel);
    uint8_t value = 0x00;
    // MSB
    for (uint8_t i = 0x80; i != 0x00; i >>= 1) {
        CLK = 1;
        CLK = 0;
        if (DO == 1) {
            value |= i;
        }
    }
    // LSB 不读取
    CS = 1;
    return value;
}
