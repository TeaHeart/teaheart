#include "ir.h"
#include "reg51.h"
#include "intrins.h"
#include "util.h"

#define IR P3_2

uint8_t ir_data[4] = {0};

void ir_init() {
    IR = 1;
}

void Delay10us()        //@11.0592MHz
{
    unsigned char __data i;

    i = 2;
    while (--i);
}

void Delay100us()        //@11.0592MHz
{
    unsigned char __data i;

    _nop_();
    i = 43;
    while (--i);
}

#define IR_WAIT(limit, condition)                    \
    for (uint16_t time = limit; condition; --time) { \
        Delay10us();                                 \
        if (time == 0) {                             \
            return;                                  \
        }                                            \
    }

void ir_scan() {
    if (IR != 0) {
        return;
    }
    IR_WAIT(1000, IR == 0);
    if (IR != 1) {
        return;
    }
    IR_WAIT(500, IR == 1);
    for (uint8_t i = 0; i < 4; ++i) {
        for (uint8_t j = 0; j < 8; ++j) {
            IR_WAIT(600, IR == 0);
            uint8_t high = 0;
            while (IR != 0) {
                Delay100us();
                if (++high > 20) {
                    return;
                }
            }
            ir_data[i] >>= 1;
            if (high >= 8) {
                ir_data[i] |= 0x80;
            }
        }
    }
    if (ir_data[0] != (uint8_t) ~ir_data[1] || ir_data[2] != (uint8_t) ~ir_data[3]) {
        fill(ir_data, 0, LENGTH_OF(ir_data));
    }
}

uint8_t ir_get_address() {
    return ir_data[0];
}

uint8_t ir_get_command() {
    return ir_data[2];
}
