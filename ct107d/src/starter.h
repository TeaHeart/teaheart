#ifndef STARTER_H
#define STARTER_H

#include "reg52.h"
#include "stdint.h"

#define LED_CHANNEL 4
#define ULN2003_CHANNEL 5
#define NIXIE_BIT 6
#define NIXIE_SEG 7

void _74HC138_select(uint8_t channel);

void delay_ms(uint16_t t);

void init();

#endif //STARTER_H
