#ifndef LCD1602_H
#define LCD1602_H

#include "stdint.h"

typedef enum LCDCommand {
    LCD_CLEAR = 0x01,
    LCD_CURSOR_RESET = 0x02,

    LCD_AUTO_SHIFT = 0x04,
    LCD_SHIFT_LEFT = 0x00,
    LCD_SHIFT_RIGHT = 0x02,

    LCD_DISPLAY_CONFIG = 0x08,
    LCD_DISPLAY_ON = 0x04,
    LCD_DISPLAY_OFF = 0x00,
    LCD_CURSOR_ON = 0x02,
    LCD_CURSOR_OFF = 0x00,
    LCD_BLINK_ON = 0x01,
    LCD_BLINK_OFF = 0x00,

    LCD_CURSOR_CONFIG = 0x10,
    LCD_CURSOR_SHIFT_LEFT = 0x00,
    LCD_CURSOR_SHIFT_RIGHT = 0x04,
    LCD_CURSOR_CHAR_SHIFT_LEFT = 0x08 | LCD_CURSOR_SHIFT_LEFT,
    LCD_CURSOR_CHAR_SHIFT_RIGHT = 0x80 | LCD_CURSOR_SHIFT_RIGHT,

    LCD_FUNCTION_CONFIG = 0x20,
    LCD_DATA_LENGTH_4 = 0x00,
    LCD_DATA_LENGTH_8 = 0x10,
    LCD_ENABLE_ONE_ROW = 0x00,
    LCD_ENABLE_TWO_ROW = 0x08,
    LCD_CHAR_SIZE_5_7 = 0x00,
    LCD_CHAR_SIZE_5_10 = 0x04,

    LCD_CGROM_CONFIG = 0x40,

    LCD_DDRAM_CONFIG = 0x80,
    LCD_BUSY = 0x40,
    LCD_FREE = 0x00,

    LCD_ROW_0_OFFSET = 0x00,
    LCD_ROW_1_OFFSET = 0x40,
} LCDCommand;

void lcd_init();

void lcd_execute(LCDCommand cmd);

void lcd_locate_to(int8_t row, int8_t column);

void lcd_write_char(uint8_t ch);

void lcd_write_char_at(int8_t row, int8_t column, uint8_t ch);

void lcd_write_string(uint8_t *str);

void lcd_write_string_at(int8_t row, int8_t column, uint8_t *str);

void lcd_write_int16(uint8_t width, int16_t number);

void lcd_write_int16_at(int8_t row, int8_t column, uint8_t width, int16_t number);

void lcd_write_uint16(uint8_t radix, uint8_t width, uint16_t number);

void lcd_write_uint16_at(int8_t row, int8_t column, uint8_t radix, uint8_t width, uint16_t number);

void lcd_clear();

#endif //LCD1602_H
