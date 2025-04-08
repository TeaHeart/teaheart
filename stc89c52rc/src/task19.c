#ifdef TASK19

#include "reg51.h"
#include "int.h"
#include "ir.h"
#include "lcd1602.h"
#include "stdbool.h"

void int0_isr() __interrupt(IE0_VECTOR) {
    ir_scan();
}

int main() {
    int0_init();
    lcd_init();
    ir_init();
    EA = 1;
    lcd_write_string_at(0, 0, "Addr:");
    lcd_write_string_at(0, 8, "Cmd:");
    while (true) {
        lcd_write_uint16_at(1, 0, 16, 2, ir_get_address());
        lcd_write_uint16_at(1, 8, 16, 2, ir_get_command());
    }
}

#endif //TASK19
