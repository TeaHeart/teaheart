#ifndef OLED_H
#define OLED_H

#include "starter.h"
#include "oled_font.h"

#define SCL P1_2
#define SDA P1_3
#define RST P1_7
#define DC  P0_0

static const char *charset = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

void i2c_send_byte(uint8_t byte) {
    for (uint8_t i = 0x80; i != 0x00; i >>= 1) {
        SCL = 0;
        SDA = (byte & i) != 0;
        SCL = 1;
    }
}

void oled_send_command(uint8_t command) {
    DC = 0;
    i2c_send_byte(command);
}

void oled_send_data(uint8_t dat) {
    DC = 1;
    i2c_send_byte(dat);
}

void oled_set_cursor(uint8_t row, uint8_t column) {
    oled_send_command(0xB0 | row);
    oled_send_command(0x10 | ((column & 0xF0) >> 4));
    oled_send_command(0x00 | ((column & 0x0F) >> 0));
}

void oled_clear() {
    for (uint8_t i = 0; i < 8; ++i) {
        oled_set_cursor(i, 0);
        for (uint8_t j = 0; j < 128; ++j) {
            oled_send_data(0x00);
        }
    }
}

void oled_init() {
    P0SEL &= ~0x01;
    P0DIR |= 0x01;

    P1SEL &= ~0x8C;
    P1DIR |= 0x8C;

    SCL = 1;
    RST = 0;
    delay_ms(100);
    RST = 1;

    oled_send_command(0XAE);
    oled_send_command(0X00);
    oled_send_command(0X10);
    oled_send_command(0X40);
    oled_send_command(0X81);
    oled_send_command(0XCF);
    oled_send_command(0XA1);
    oled_send_command(0XC8);
    oled_send_command(0XA6);
    oled_send_command(0XA8);
    oled_send_command(0X3F);
    oled_send_command(0XD3);
    oled_send_command(0X00);
    oled_send_command(0XD5);
    oled_send_command(0X80);
    oled_send_command(0XD9);
    oled_send_command(0XF1);
    oled_send_command(0XDA);
    oled_send_command(0X12);
    oled_send_command(0XDB);
    oled_send_command(0X40);
    oled_send_command(0X20);
    oled_send_command(0X02);
    oled_send_command(0X8D);
    oled_send_command(0X14);
    oled_send_command(0XA4);
    oled_send_command(0XA6);
    oled_send_command(0XAF);

    oled_clear();
}

/**
 * @brief  显示字符
 * @param  row     行 [0, 3]
 * @param  column  列 [0,15]
 * @param  c       字符 [ASCII]
 */
void oled_show_char(uint8_t row, uint8_t column, char c) {
    oled_set_cursor(row * 2, column * 8);
    if (' ' <= c && c <= '~') {
        c -= ' ';
    } else {
        c = 0;
    }
    for (uint8_t i = 0; i < 8; ++i) {
        oled_send_data(OLED_F8x16[c][i]);
    }
    oled_set_cursor(row * 2 + 1, column * 8);
    for (uint8_t i = 0; i < 8; ++i) {
        oled_send_data(OLED_F8x16[c][i + 8]);
    }
}

/**
 * @brief  显示字符串
 * @param  row     行 [0,3]
 * @param  column  列 [0,15]
 * @param  str     字符串 [ASCII]
 */
void oled_show_string(uint8_t row, uint8_t column, char *str, uint8_t length) {
    for (uint8_t i = 0; i < length; ++i) {
        oled_show_char(row, column + i, str[i]);
    }
}

/**
 * @brief  显示R进制数字
 * @param  row     行 [0,3]
 * @param  column  列 [0,15]
 * @param  radix   进制
 * @param  width   宽度
 * @param  number  数字
 */
void oled_show_number(uint8_t row, uint8_t column, uint32_t number, uint8_t width, uint8_t radix) {
    for (int8_t i = width - 1; i >= 0; --i) {
        oled_show_char(row, column + i, charset[number % radix]);
        number /= radix;
    }
}

/**
 * @brief  显示带符号数字
 * @param  row     行 [0,3]
 * @param  column  列 [0,15]
 * @param  width   宽度
 * @param  number  数字
 */
void oled_show_signed_number(uint8_t row, uint8_t column, int32_t number, uint8_t width) {
    oled_show_char(row, column, number > 0 ? '+' : number < 0 ? '-' : ' ');
    for (int8_t i = width - 1; i >= 1; --i) {
        int8_t mod = number % 10;
        oled_show_char(row, column + i, charset[mod >= 0 ? mod : -mod]);
        number /= 10;
    }
}

#endif //OLED_H
