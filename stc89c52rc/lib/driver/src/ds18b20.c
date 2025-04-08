#include "ds18b20.h"
#include "one_wire.h"

#define SKIP_ROM 0xCC
#define CONVERT_T 0x44
#define READ_SCRATCHPAD 0xBE

void ds18b20_convert_t() {
    one_wire_start();
    one_wire_send_byte(SKIP_ROM);
    one_wire_send_byte(CONVERT_T);
}

float ds18b20_read_temp() {
    one_wire_start();
    one_wire_send_byte(SKIP_ROM);
    one_wire_send_byte(READ_SCRATCHPAD);
    uint8_t lsb = one_wire_receive_byte();
    uint8_t msb = one_wire_receive_byte();
    return ((uint16_t) msb << 8 | lsb) / 16.0f;
}
