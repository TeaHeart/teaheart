#include "button.h"
#include "reg51.h"
#include "stdint.h"

#define S_KEY_0 P3_1
#define S_KEY_1 P3_0
#define S_KEY_2 P3_2
#define S_KEY_3 P3_3

#define M_KEY P1

#define KEY_PRESS(P__, body) if (P__ == 0) body

Key s_key = KEY_NULL;

void single_button_scan() {
    static Key prev = KEY_NULL;
    Key curr = KEY_NULL;
    KEY_PRESS(S_KEY_0, curr |= KEY_0);
    KEY_PRESS(S_KEY_1, curr |= KEY_1);
    KEY_PRESS(S_KEY_2, curr |= KEY_2);
    KEY_PRESS(S_KEY_3, curr |= KEY_3);
    if (curr == KEY_NULL && prev != KEY_NULL) {
        s_key = prev;
    }
    prev = curr;
}

Key single_button_get_key() {
    return s_key;
}

Key single_button_clear_key() {
    Key key = s_key;
    s_key = KEY_NULL;
    return key;
}

Key m_key = KEY_NULL;

void matrix_button_scan() {
    static Key prev = KEY_NULL;
    Key curr = KEY_NULL;
    Key key = KEY_0;
    for (uint8_t i = 0x80; i >= 0x10; i >>= 1) {
        M_KEY = ~i;
        for (uint8_t j = 0x08; j >= 0x01; j >>= 1, key <<= 1) {
            KEY_PRESS((M_KEY & j), curr |= key);
        }
    }
    if (curr == KEY_NULL && prev != KEY_NULL) {
        m_key = prev;
    }
    prev = curr;
}

Key matrix_button_get_key() {
    return m_key;
}

Key matrix_button_clear_key() {
    Key key = m_key;
    m_key = KEY_NULL;
    return key;
}
