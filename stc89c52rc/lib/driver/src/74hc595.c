#include "74hc595.h"
#include "reg51.h"

#define RCK P3_5
#define SCK P3_6
#define SER P3_4

void _74hc595_set_bit(bool value) {
    SER = value;
}

void _74hc595_shift() {
    SCK = 1;
    SCK = 0;
}

void _74hc595_send() {
    RCK = 0;
    RCK = 1;
}

void _74hc595_send_byte(uint8_t value) {
    for (uint8_t i = 0x80; i != 0x00; i >>= 1) {
        _74hc595_set_bit((value & i) != 0 ? 1 : 0);
        _74hc595_shift();
    }
    _74hc595_send();
}
