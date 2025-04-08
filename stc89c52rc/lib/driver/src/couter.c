#include "counter.h"
#include "reg51.h"

void counter0_init() {
    TMOD &= 0xF0;
    TMOD |= 0x01;
    TL0 = 0;
    TH0 = 0;
    TF0 = 0;
    TR0 = 0;
}

void counter0_set(uint16_t value) {
    TH0 = value / 256;
    TL0 = value % 256;
}

uint16_t counter0_get() {
    return ((uint16_t) TH0 << 8) | TL0;
}

void counter0_set_status(bool is_run) {
    TR0 = is_run;
}
