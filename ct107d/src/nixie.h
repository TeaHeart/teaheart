#ifndef NIXIE_H
#define NIXIE_H

#include "starter.h"

void nixie_fill(uint8_t seg);

void nixie_show(uint8_t i, uint8_t seg);

void nixie_scan(uint16_t t);

void nixie_write_at(uint8_t index, uint8_t seg);

void nixie_write_number_at(uint8_t end, uint16_t number, uint8_t width);

void nixie_write_string(uint8_t *str);

#endif //NIXIE_H
