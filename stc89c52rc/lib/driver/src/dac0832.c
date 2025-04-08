#include "dac0832.h"
#include "reg51.h"

#define CS P3_0
#define WR1 P3_1
#define ILE P3_2
#define WR2 P3_3
#define XFER P3_4

#define DI P1

void dac0832_send_da(uint8_t value) {
    CS = 0;
    XFER = 0;
    WR1 = 0;
    WR2 = 0;
    ILE = 1;
    DI = value;
}
