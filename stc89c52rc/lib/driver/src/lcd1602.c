#include "lcd1602.h"
#include "reg51.h"
#include "delay.h"
#include "util.h"

#define RS P2_6
#define RW P2_5
#define EN P2_7
#define DATA P0

void lcd_init() {
    lcd_execute(LCD_FUNCTION_CONFIG | LCD_DATA_LENGTH_8 | LCD_ENABLE_TWO_ROW | LCD_CHAR_SIZE_5_7);
    lcd_execute(LCD_DISPLAY_CONFIG | LCD_DISPLAY_ON | LCD_CURSOR_OFF | LCD_BLINK_OFF);
    lcd_execute(LCD_AUTO_SHIFT | LCD_SHIFT_RIGHT);
    lcd_execute(LCD_CLEAR);
}

void lcd_execute(LCDCommand cmd) {
    RS = 0;
    RW = 0;
    EN = 0;
    DATA = cmd;
    delay_ms(1);
    EN = 1;
    delay_ms(1);
    EN = 0;
}

void lcd_locate_to(int8_t row, int8_t column) {
    row = (row + 2) % 2;
    column = (column + 16) % 16;
    if (row == 0) {
        lcd_execute(LCD_DDRAM_CONFIG | LCD_ROW_0_OFFSET | column);
    } else {
        lcd_execute(LCD_DDRAM_CONFIG | LCD_ROW_1_OFFSET | column);
    }
}

void lcd_write_char(uint8_t ch) {
    RS = 1;
    RW = 0;
    EN = 0;
    DATA = ch;
    delay_ms(1);
    EN = 1;
    delay_ms(1);
    EN = 0;
}

void lcd_write_char_at(int8_t row, int8_t column, uint8_t ch) {
    lcd_locate_to(row, column);
    lcd_write_char(ch);
}

void lcd_write_string(uint8_t *str) {
    for (uint8_t i = 0; i < 16 && str[i] != '\0'; ++i) {
        lcd_write_char(str[i]);
    }
}

void lcd_write_string_at(int8_t row, int8_t column, uint8_t *str) {
    lcd_locate_to(row, column);
    lcd_write_string(str);
}

void lcd_write_int16(uint8_t width, int16_t number) {
    uint8_t buffer[5 + 1 + 1] = {0};
    int16_to_string(buffer, width, number);
    lcd_write_string(buffer);
}

void lcd_write_int16_at(int8_t row, int8_t column, uint8_t width, int16_t number) {
    lcd_locate_to(row, column);
    lcd_write_int16(width, number);
}

void lcd_write_uint16(uint8_t radix, uint8_t width, uint16_t number) {
    uint8_t buffer[16 + 1] = {0};
    uint16_to_string(buffer, radix, width, number);
    lcd_write_string(buffer);
}

void lcd_write_uint16_at(int8_t row, int8_t column, uint8_t radix, uint8_t width, uint16_t number) {
    lcd_locate_to(row, column);
    lcd_write_uint16(radix, width, number);
}

void lcd_clear() {
    lcd_execute(LCD_CLEAR);
}
