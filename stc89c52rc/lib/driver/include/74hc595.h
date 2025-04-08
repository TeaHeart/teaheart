#ifndef _74HC595_H
#define _74HC595_H

#include "stdint.h"
#include "stdbool.h"

void _74hc595_set_bit(bool b);

void _74hc595_shift();

void _74hc595_send();

void _74hc595_send_byte(uint8_t value);

#endif //_74HC595_H
