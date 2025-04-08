#include "reg51.h"
#include "nixie.h"
#include "util.h"

#define SEG_DATA P0
#define SEG P2

uint8_t __code NIXIE_SEG_DATA[] = {
        0, 0, 0, 0, 0, 0, 0, 0,    //   0 -   7 =>
        0, 0, 0, 0, 0, 0, 0, 0,    //   8 -  15 =>
        0, 0, 0, 0, 0, 0, 0, 0,    //  16 -  23 =>
        0, 0, 0, 0, 0, 0, 0, 0,    //  24 -  31 =>
        0, 0, 0, 0, 0, 0, 0, 0,    //  32 -  39 => _ ! " # $ % & '
        0, 0, 0, 0, 0, 0x40, 0x80, 0,    //  40 -  47 => ( ) * + , - . /
        0x3F, 0x06, 0x5B, 0x4F, 0x66, 0x6D, 0x7D, 0x07, //  48 -  55 => 0 1 2 3 4 5 6 7
        0x7F, 0x6F, 0, 0, 0, 0, 0, 0, //  56 -  63 => 8 9 : ; < = > ?
        0, 0x77, 0x7C, 0x39, 0x5E, 0x79, 0x71, 0,    //  64 -  71 => @ A B C D E F G
        0, 0, 0, 0, 0, 0, 0, 0,    //  72 -  79 => H I J K L M N O
        0, 0, 0x31, 0, 0, 0, 0, 0,    //  80 -  87 => P Q R S T U V W
        0, 0, 0, 0, 0, 0, 0, 0,    //  88 -  95 => X Y Z [ \ ] ^ _
        0, 0, 0, 0, 0, 0, 0, 0,    //  96 - 103 => ` a b c d e f g
        0, 0, 0, 0, 0, 0, 0, 0,    // 104 - 111 => h i j k l m n o
        0, 0, 0, 0, 0, 0, 0, 0,    // 112 - 119 => p q r s t u v w
        0, 0, 0, 0, 0, 0, 0, 0,    // 120 - 127 => x y z { | } ~
};

uint8_t seg_data[8] = {0};

uint8_t nixie_seg(uint8_t ch) {
    return NIXIE_SEG_DATA[ch];
}

void nixie_write_at(int8_t index, uint8_t seg) {
    index = (index + 8) % 8;
#ifndef SEG_REVERSE
    index = 8 - 1 - index;
#endif
    seg_data[index] = seg;
}

void nixie_set_seg(uint8_t seg[8]) {
    copy(seg_data, seg, LENGTH_OF(seg_data));
}

void nixie_clear() {
    fill(seg_data, 0, LENGTH_OF(seg_data));
}

void nixie_scan() {
    static uint8_t seg = 0;
    SEG_DATA = 0x00;
    SEG &= ~(7 << 2);
    SEG |= seg << 2;
    SEG_DATA = seg_data[seg];
    if (++seg >= 8) {
        seg = 0;
    }
}

void nixie_write_int16(int16_t number) {
    nixie_clear();
    uint8_t buffer[8 + 1] = {0};
    uint8_t width = 1 + int16_string_size(10, number);
    int16_to_string(buffer, width, number);
    int8_t i = width - 1;
    int8_t j = 8 - 1;
    do {
        nixie_write_at(j--, nixie_seg(buffer[i--]));
    } while (i >= 0);
}

void nixie_write_uint16(uint16_t number) {
    nixie_clear();
    uint8_t buffer[8 + 1] = {0};
    uint8_t width = uint16_string_size(10, number);
    uint16_to_string(buffer, 10, width, number);
    int8_t i = width - 1;
    int8_t j = 8 - 1;
    do {
        nixie_write_at(j--, nixie_seg(buffer[i--]));
    } while (i >= 0);
}

void nixie_write_error() {
    nixie_clear();
    nixie_write_at(-3, nixie_seg('E'));
    nixie_write_at(-2, nixie_seg('R'));
    nixie_write_at(-1, nixie_seg('R'));
}
