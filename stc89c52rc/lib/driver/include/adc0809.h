#ifndef ADC0809_H
#define ADC0809_H

#include "stdint.h"

void adc0809_toggle_clock();

uint8_t adc0809_read_ad(uint8_t channel);

#endif //ADC0809_H
