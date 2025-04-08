#ifndef MATRIX_LED_H
#define MATRIX_LED_H

#include "stdint.h"

void matrix_led_row_scan();

void matrix_led_column_scan();

void matrix_led_write_at(int8_t index, uint8_t value);

void matrix_led_set_seg(uint8_t seg[8]);

void matrix_led_clear();

#endif //MATRIX_LED_H
