#ifndef AT24C02_H
#define AT24C02_H

#include "stdint.h"

void at24c02_write_byte_at(uint8_t address, uint8_t value);

uint8_t at24c02_read_byte_at(uint8_t address);

#endif //AT24C02_H
