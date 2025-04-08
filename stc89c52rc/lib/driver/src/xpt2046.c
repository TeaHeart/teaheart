#include "xpt2046.h"
#include "reg51.h"

#define CS P3_5
#define DCLK P3_6
#define DIN P3_4
#define DOUT P3_7

uint16_t xpt2046_read_ad(XPT2046Command cmd) {
    DCLK = 0;
    CS = 0;
    for (uint8_t i = 0x80; i != 0x00; i >>= 1) {
        DIN = cmd & i;
        DCLK = 1;
        DCLK = 0;
    }
    uint16_t value = 0x00;
    for (uint16_t i = 0x8000; i != 0x0000; i >>= 1) {
        DCLK = 1;
        DCLK = 0;
        if (DOUT == 1) {
            value |= i;
        }
    }
    CS = 1;
    return value >> ((cmd & XPT_MODE_8_BIT) != 0 ? 8 : 4);
}
