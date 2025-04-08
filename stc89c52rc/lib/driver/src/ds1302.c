#include "ds1302.h"
#include "reg51.h"
#include "util.h"

#define SCLK P3_6
#define IO P3_4
#define CE P3_5

void ds1302_write_byte(DS1302Command cmd, uint8_t value) {
    cmd &= ~DS1302_READ;
    CE = 0;
    SCLK = 0;
    CE = 1;
    for (uint8_t i = 0x01; i != 0x00; i <<= 1) {
        IO = (cmd & i) != 0 ? 1 : 0;
        SCLK = 1;
        SCLK = 0;
    }
    for (uint8_t i = 0x01; i != 0x00; i <<= 1) {
        IO = (value & i) != 0 ? 1 : 0;
        SCLK = 1;
        SCLK = 0;
    }
    CE = 0;
}

uint8_t ds1302_read_byte(DS1302Command cmd) {
    cmd |= DS1302_READ;
    CE = 0;
    SCLK = 0;
    CE = 1;
    for (uint8_t i = 0x01; i != 0x00; i <<= 1) {
        IO = (cmd & i) != 0 ? 1 : 0;
        SCLK = 0;
        SCLK = 1;
    }
    uint8_t value = 0x00;
    for (uint8_t i = 0x01; i != 0x00; i <<= 1) {
        SCLK = 1;
        SCLK = 0;
        if (IO == 1) {
            value |= i;
        }
    }
    CE = 0;
    IO = 0;
    return value;
}

DS1302Time ds1302_time = {
        .year = 70,
        .month = 1,
        .date = 1,
        .day = 4,
        .hour = 0,
        .minute = 0,
        .second = 0
};

DS1302Time *ds1302_time_instance() {
    return &ds1302_time;
}

void ds1302_write_time() {
    ds1302_write_byte(DS1302_WRITE_PROTECT, 0x00);

    ds1302_write_byte(DS1302_YEAR, dec_to_bcd(ds1302_time.year));
    ds1302_write_byte(DS1302_MONTH, dec_to_bcd(ds1302_time.month));
    ds1302_write_byte(DS1302_DATE, dec_to_bcd(ds1302_time.date));
    ds1302_write_byte(DS1302_DAT, dec_to_bcd(ds1302_time.day));

    ds1302_write_byte(DS1302_HOUR, dec_to_bcd(ds1302_time.hour));
    ds1302_write_byte(DS1302_MINUTE, dec_to_bcd(ds1302_time.minute));
    ds1302_write_byte(DS1302_SECOND, dec_to_bcd(ds1302_time.second));
}

void ds1302_read_time() {
    ds1302_time.year = bcd_to_dec(ds1302_read_byte(DS1302_YEAR));
    ds1302_time.month = bcd_to_dec(ds1302_read_byte(DS1302_MONTH));
    ds1302_time.date = bcd_to_dec(ds1302_read_byte(DS1302_DATE));
    ds1302_time.day = bcd_to_dec(ds1302_read_byte(DS1302_DAT));

    ds1302_time.hour = bcd_to_dec(ds1302_read_byte(DS1302_HOUR));
    ds1302_time.minute = bcd_to_dec(ds1302_read_byte(DS1302_MINUTE));
    ds1302_time.second = bcd_to_dec(ds1302_read_byte(DS1302_SECOND));
}
