#ifdef TASK18

#include "reg51.h"
#include "stdbool.h"
#include "button.h"
#include "timer.h"
#include "lcd1602.h"
#include "at24c02.h"

uint8_t addr = 0;
uint8_t value = 0;

void timer0_isr() __interrupt(TF0_VECTOR) { //1ms
    TL0 = 0x66;
    TH0 = 0xFC;
    matrix_button_scan();
    if (matrix_button_get_key() != KEY_NULL) {
        switch (matrix_button_clear_key()) {
            case KEY_0: {
                --addr;
                value = at24c02_read_byte_at(addr);
                break;
            }
            case KEY_1: {
                ++addr;
                value = at24c02_read_byte_at(addr);
                break;
            }
            case KEY_2: {
                --value;
                break;
            }
            case KEY_3: {
                ++value;
                break;
            }
            case KEY_4: {
                value = at24c02_read_byte_at(addr);
                lcd_write_string_at(1, 0, "Read  ");
                lcd_write_uint16_at(1, 6, 16, 2, value);
                break;
            }
            case KEY_5: {
                at24c02_write_byte_at(addr, value);
                lcd_write_string_at(1, 0, "Write ");
                lcd_write_uint16_at(1, 6, 16, 2, value);
                break;
            }
        }
        lcd_write_uint16_at(0, 5, 16, 2, addr);
        lcd_write_uint16_at(0, 14, 16, 2, value);
    }
}

int main() {
    lcd_init();
    lcd_write_string_at(0, 0, "Addr:__ ");
    lcd_write_string_at(0, 8, "Value:__");
    value = at24c02_read_byte_at(addr);
    lcd_write_uint16_at(0, 5, 16, 2, addr);
    lcd_write_uint16_at(0, 14, 16, 2, value);
    timer0_init();
    EA = 1;
    while (true);
}

#endif //TASK18
