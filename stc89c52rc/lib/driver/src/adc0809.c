#include "adc0809.h"
#include "reg51.h"
#include "util.h"

#ifndef TASK13
#define CLOCK P3_0
#define START P3_1
#else
#define CLOCK P2_0
#define START P2_1
#endif

#define EOC P3_2
#define OE P3_3
// #define ADDA P3_4
// #define ADDB P3_5
// #define ADDC P3_6
#define ALE P3_7
#define OUT P1
#define ADD_ P3

void adc0809_toggle_clock() {
    CLOCK = !CLOCK;
}

uint8_t adc0809_read_ad(uint8_t channel) {
    uint8_t value;
    ALE = 0;
    ADD_ &= ~(0x07 << 4); // ADDA ADDB ADDC
    ADD_ |= channel << 4; // ADDA ADDB ADDC
    ALE = 1;
    START = 1;
    START = 0;
    while (EOC == 0);
    OE = 1;
    value = OUT;
    OE = 0;
    return uint8_reverse_bit(value);
}
