#ifndef XPT2046_H
#define XPT2046_H

#include "stdint.h"

typedef enum XPT2046Command {
    XPT_MSB = 0x80,
    XPT_A2 = 0x40,
    XPT_A1 = 0x20,
    XPT_A0 = 0x10,
    XPT_MODE_8_BIT = 0x08, XPT_MODE_12_BIT = 0x00,
    XPT_SER = 0x04, XPT_DFR = 0x00,
    XPT_PD1 = 0x02,
    XPT_PD0 = 0x01,

    XPT_XP = XPT_A0, // A1 | A0
    XPT_YP = XPT_A2 | XPT_A0,
    XPT_VBAT = XPT_A1,
    XPT_AUX = XPT_A2 | XPT_A1,
} XPT2046Command;

uint16_t xpt2046_read_ad(XPT2046Command cmd);

#endif //XPT2046_H
