#include "delay.h"
#include "lint.h"
#include "intrins.h"

void Delay1ms() //@11.0592MHz
{
    unsigned char __data i, j;

    _nop_();
    i = 2;
    j = 199;
    do {
        while (--j);
    } while (--i);
}

void delay_ms(uint16_t duration) {
    while (duration-- != 0) {
        Delay1ms();
    }
}
