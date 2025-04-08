#ifndef NIXIE_H
#define NIXIE_H

#include "stdint.h"

uint8_t nixie_seg(uint8_t ch);

void nixie_write_at(int8_t index, uint8_t seg);

void nixie_set_seg(uint8_t seg[8]);

void nixie_clear();

void nixie_scan();

void nixie_write_int16(int16_t number);

void nixie_write_uint16(uint16_t number);

void nixie_write_error();

#endif //NIXIE_H
