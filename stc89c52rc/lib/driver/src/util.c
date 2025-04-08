#include "util.h"
#include "stdbool.h"

int8_t lowest_bit_at(uint16_t n) {
    for (int8_t i = 0; n != 0; i++, n >>= 1) {
        if ((n & 1) == 1) {
            return i;
        }
    }
    return -1;
}

uint8_t *copy(uint8_t *dest, uint8_t *src, uint8_t count) {
    for (uint8_t i = 0; i < count; ++i) {
        dest[i] = src[i];
    }
    return dest;
}

uint8_t *fill(uint8_t *dest, uint8_t value, uint8_t count) {
    while (count > 0) {
        dest[--count] = value;
    }
    return dest;
}

uint8_t *uint16_to_string(uint8_t *buffer, uint8_t radix, uint8_t width, uint16_t number) {
    static const uint8_t charset[] = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    buffer[width] = '\0';
    while (width > 0) {
        buffer[--width] = charset[number % radix];
        number /= radix;
    }
    return buffer;
}

uint8_t *int16_to_string(uint8_t *buffer, uint8_t width, int16_t number) {
    bool min_flag = false;
    if (number == INT16_MIN) {
        min_flag = true;
        ++number;
    }
    if (number > 0) {
        buffer[0] = '+';
    } else if (number < 0) {
        buffer[0] = '-';
        number = -number;
    } else {
        buffer[0] = ' ';
    }
    uint16_to_string(buffer + 1, 10, width - 1, number);
    if (min_flag) {
        buffer[width - 1] = '8';
    }
    return buffer;
}

uint8_t uint16_string_size(uint8_t radix, uint16_t number) {
    uint8_t i = 0;
    do {
        i++;
        number /= radix;
    } while (number != 0);
    return i;
}

uint8_t int16_string_size(uint8_t radix, int16_t number) {
    uint8_t i = 0;
    do {
        i++;
        number /= radix;
    } while (number != 0);
    return i;
}

uint8_t uint8_reverse_bit(uint8_t value) {
    uint8_t result = 0x00;
    for (uint8_t i = 0; i < BIT_OF(uint8_t); i++) {
        result <<= 1;
        result |= value & 1;
        value >>= 1;
    }
    return result;
}

void insert_sort(uint8_t *array, uint8_t length) {
    for (uint8_t i = 1, j; i < length; i++) {
        uint8_t tmp = array[i];
        for (j = i; j - 1 >= 0; j--) {
            if (array[j - 1] < tmp) {
                break;
            }
            array[j] = array[j - 1];
        }
        array[j] = tmp;
    }
}

uint8_t average(uint8_t *array, uint8_t length) {
    uint16_t sum = 0;
    for (uint8_t i = 0; i < length; i++) {
        sum += array[i];
    }
    return sum / length;
}

uint8_t bcd_to_dec(uint8_t value) {
    return value / 16 * 10 + value % 16;
}

uint8_t dec_to_bcd(uint8_t value) {
    return value / 10 * 16 + value % 10;
}
