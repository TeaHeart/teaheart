#include "nixie.h"

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
        0x76, 0, 0, 0, 0x38, 0, 0, 0x3F,    //  72 -  79 => H I J K L M N O
        0, 0, 0x31, 0, 0, 0, 0, 0,    //  80 -  87 => P Q R S T U V W
        0, 0, 0, 0, 0, 0, 0, 0,    //  88 -  95 => X Y Z [ \ ] ^ _
        0, 0, 0, 0, 0, 0, 0, 0,    //  96 - 103 => ` a b c d e f g
        0, 0, 0, 0, 0, 0, 0, 0,    // 104 - 111 => h i j k l m n o
        0, 0, 0, 0, 0, 0, 0, 0,    // 112 - 119 => p q r s t u v w
        0, 0, 0, 0, 0, 0, 0, 0,    // 120 - 127 => x y z { | } ~
};

uint8_t seg_data[8] = {0};

void nixie_fill(uint8_t seg) {
    for (int i = 0; i < 8; ++i) {
        seg_data[i] = seg;
    }
}

void nixie_show(uint8_t i, uint8_t seg) {
    _74HC138_select(NIXIE_SEG);
    P0 = 0xFF;
    _74HC138_select(NIXIE_BIT);
    P0 = 1 << i;
    _74HC138_select(NIXIE_SEG);
    P0 = ~seg;
}

void nixie_scan(uint16_t t) {
    static uint8_t i = 0;
    nixie_show(i, seg_data[i]);
    if (++i >= 8) {
        i = 0;
    }
    if (t != 0) {
        delay_ms(t);
    }
}

void nixie_write_at(uint8_t index, uint8_t seg) {
    seg_data[index] = seg;
}

void nixie_write_number_at(uint8_t end, uint16_t number, uint8_t width) {
    const char charset[] = "0123456789ABCDEF";
    uint8_t i = end;
    do {
        seg_data[--i] = NIXIE_SEG_DATA[charset[number % 10]];
        number /= 10;
    } while (--width != 0);
}

void nixie_write_string(uint8_t *str) {
    for (int i = 0; str[i] != '\0'; ++i) {
        seg_data[i] = NIXIE_SEG_DATA[str[i]];
    }
}
