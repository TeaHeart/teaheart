#ifdef TASK16

#include "stdbool.h"
#include "delay.h"
#include "ds18b20.h"
#include "lcd1602.h"

int main() {
    lcd_init();
    lcd_write_string_at(0, 0, "Temp:");
    while (true) {
        ds18b20_convert_t();
        delay_ms(500);
        float temp = ds18b20_read_temp();
        if (temp < 0) {
            lcd_write_char_at(1, 0, '-');
            temp = -temp;
        } else {
            lcd_write_char_at(1, 0, '+');
        }
        lcd_write_uint16(10, 3, (uint16_t) temp);
        lcd_write_char('.');
        lcd_write_uint16(10, 4, (uint32_t) (temp * 10000) % 10000);
    }
}

#endif //TASK16
