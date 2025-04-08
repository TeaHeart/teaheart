#include "matrix_led.h"
#include "reg51.h"
#include "74hc595.h"
#include "util.h"

#define SEG_DATA P0

uint8_t seg_data[8] = {0};

void matrix_led_row_scan() {
    static uint8_t row = 0;
    SEG_DATA = 0xFF;
    _74hc595_send_byte(1 << row);
    SEG_DATA = ~seg_data[8 - 1 - row];
    if (++row >= 8) {
        row = 0;
    }
}

void matrix_led_column_scan() {
    static uint8_t column = 0;
    SEG_DATA = 0xFF;
    _74hc595_send_byte(seg_data[8 - 1 - column]);
    SEG_DATA = ~(1 << column);
    if (++column >= 8) {
        column = 0;
    }
}

void matrix_led_write_at(int8_t index, uint8_t value) {
    seg_data[(index + 8) % 8] = value;
}

void matrix_led_set_seg(uint8_t seg[8]) {
    copy(seg_data, seg, LENGTH_OF(seg_data));
}

void matrix_led_clear() {
    fill(seg_data, 0, LENGTH_OF(seg_data));
}
