#ifndef BUTTON_H
#define BUTTON_H

typedef enum Key {
    KEY_NULL = 0x0000,
    KEY_0 = 0x0001,
    KEY_1 = 0x0002,
    KEY_2 = 0x0004,
    KEY_3 = 0x0008,
    KEY_4 = 0x0010,
    KEY_5 = 0x0020,
    KEY_6 = 0x0040,
    KEY_7 = 0x0080,
    KEY_8 = 0x0100,
    KEY_9 = 0x0200,
    KEY_A = 0x0400,
    KEY_B = 0x0800,
    KEY_C = 0x1000,
    KEY_D = 0x2000,
    KEY_E = 0x4000,
    KEY_F = 0x8000,
} Key;

void single_button_scan();

Key single_button_get_key();

Key single_button_clear_key();

void matrix_button_scan();

Key matrix_button_get_key();

Key matrix_button_clear_key();

#endif //BUTTON_H
