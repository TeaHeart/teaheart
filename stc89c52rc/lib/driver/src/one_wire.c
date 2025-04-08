#include "one_wire.h"
#include "reg51.h"
#include "intrins.h"

#define DQ P3_7

void Delay500us()        //@11.0592MHz
{
    unsigned char __data i;

    _nop_();
    i = 227;
    while (--i);
}

void Delay70us()        //@11.0592MHz
{
    unsigned char __data i;

    _nop_();
    i = 29;
    while (--i);
}

void Delay10us()        //@11.0592MHz
{
    unsigned char __data i;

    i = 2;
    while (--i);
}

void Delay50us()        //@11.0592MHz
{
    unsigned char __data i;

    _nop_();
    i = 20;
    while (--i);
}

void Delay5us()        //@11.0592MHz
{
}

bool one_wire_start() {
    bool tmp = EA;
    EA = 0;
    DQ = 1;
    DQ = 0;
    Delay500us();
    DQ = 1;
    Delay70us();
    bool ack = DQ == 0 ? true : false;
    Delay500us();
    EA = tmp;
    return ack;
}

void one_wire_send_bit(bool b) {
    bool tmp = EA;
    EA = 0;
    DQ = 1;
    DQ = 0;
    Delay10us();
    DQ = b;
    Delay50us();
    DQ = 1;
    EA = tmp;
}

bool one_wire_receive_bit() {
    bool tmp = EA;
    EA = 0;
    DQ = 1;
    DQ = 0;
    Delay5us();
    DQ = 1;
    Delay5us();
    bool b = DQ;
    Delay50us();
    EA = tmp;
    return b;
}

void one_wire_send_byte(uint8_t value) {
    bool tmp = EA;
    EA = 0;
    for (uint8_t i = 0x01; i != 0x00; i <<= 1) {
        one_wire_send_bit((value & i) != 0 ? 1 : 0);
    }
    EA = tmp;
}

uint8_t one_wire_receive_byte() {
    bool tmp = EA;
    EA = 0;
    uint8_t value = 0x00;
    for (uint8_t i = 0x01; i != 0x00; i <<= 1) {
        if (one_wire_receive_bit() == 1) {
            value |= i;
        }
    }
    EA = tmp;
    return value;
}
