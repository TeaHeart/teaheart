#ifndef UTIL_H
#define UTIL_H

#include "stdint.h"

#define LENGTH_OF(array) (sizeof(array) / sizeof(array[0]))

#define BIT_OF(type) (sizeof (type) * 8)

int8_t lowest_bit_at(uint16_t n);

uint8_t *copy(uint8_t *dest, uint8_t *src, uint8_t count);

uint8_t *fill(uint8_t *dest, uint8_t value, uint8_t count);

uint8_t *uint16_to_string(uint8_t *buffer, uint8_t radix, uint8_t width, uint16_t number);

uint8_t *int16_to_string(uint8_t *buffer, uint8_t width, int16_t number);

uint8_t uint16_string_size(uint8_t radix, uint16_t number);

uint8_t int16_string_size(uint8_t radix, int16_t number);

uint8_t uint8_reverse_bit(uint8_t value);

void insert_sort(uint8_t *array, uint8_t length);

uint8_t average(uint8_t *array, uint8_t length);

uint8_t bcd_to_dec(uint8_t value);

uint8_t dec_to_bcd(uint8_t value);

#endif //UTIL_H
