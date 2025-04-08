#ifndef DS1302_H
#define DS1302_H

#include "stdint.h"

typedef struct DS1302Time {
    uint8_t year;
    uint8_t month;
    uint8_t date;
    uint8_t day;
    uint8_t hour;
    uint8_t minute;
    uint8_t second;
} DS1302Time;

typedef enum DS1302Command {
    DS1302_WRITE = 0x00,
    DS1302_READ = 0x01,
    DS1302_SECOND = 0x80,
    DS1302_MINUTE = 0x82,
    DS1302_HOUR = 0x84,
    DS1302_DATE = 0x86,
    DS1302_MONTH = 0x88,
    DS1302_DAT = 0x8A,
    DS1302_YEAR = 0x8C,
    DS1302_WRITE_PROTECT = 0x8E,
} DS1302Command;

void ds1302_write_byte(DS1302Command cmd, uint8_t value);

uint8_t ds1302_read_byte(DS1302Command cmd);

DS1302Time *ds1302_time_instance();

void ds1302_write_time();

void ds1302_read_time();

#endif //DS1302_H
