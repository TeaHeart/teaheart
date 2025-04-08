#ifdef TASK3

#include "reg51.h"
#include "timer.h"
#include "button.h"
#include "nixie.h"
#include "util.h"

void timer0_isr() __interrupt(TF0_VECTOR) {
    TL0 = 0x66;
    TH0 = 0xFC;
    matrix_button_scan();
    nixie_scan();
}

typedef enum BoardKey {
    _0 = 0x00,
    _1 = 0x01,
    _2 = 0x02,
    _3 = 0x03,
    _4 = 0x04,
    _5 = 0x05,
    _6 = 0x06,
    _7 = 0x07,
    _8 = 0x08,
    _9 = 0x09,
    Add = 0x0A,
    Sub = 0x0B,
    Mul = 0x0C,
    Div = 0x0D,
    Eq = 0x0E,
    CE = 0x0F
} BoardKey;

typedef enum Status {
    X,
    Y,
    Operator,
    Error,
    Clear,
} Status;

// 映射到矩阵按键
BoardKey board[] = {
        _7, _8, _9, Div,
        _4, _5, _6, Mul,
        _1, _2, _3, Sub,
        CE, _0, Eq, Add
};

int16_t x = 0;
int16_t y = 0;
Status status = Clear;
BoardKey operator = CE;

void calculator(BoardKey key) {
    if (_0 <= key && key <= _9) {
        if (status == Clear || status == X) {
            x = (int16_t) ((int32_t) x * 10 + (int16_t) key);
            status = X;
        } else if (status == Operator || status == Y) {
            y = (int16_t) ((int32_t) y * 10 + (int16_t) key);
            status = Y;
        }
    } else if ((status == X || status == Operator) && Add <= key && key <= Div) {
        status = Operator;
        operator = key;
    } else if (status == Y && key == Eq) {
        if (operator == Add) {
            x += y;
        } else if (operator == Sub) {
            x -= y;
        } else if (operator == Mul) {
            x *= y;
        } else if (operator == Div) {
            if (y != 0) {
                x /= y;
            } else {
                status = Error;
            }
        }
        if (status != Error) {
            y = 0;
            status = X;
            operator = CE;
            nixie_clear();
        }
    } else if (key == CE) {
        x = y = 0;
        status = Clear;
        operator = CE;
        nixie_clear();
    }
    if (status == X) {
        nixie_write_int16(x);
    } else if (status == Y) {
        nixie_write_int16(y);
    } else if (status == Error) {
        nixie_write_error();
    }
}

int main() {
    timer0_init();
    EA = 1;
    while (true) {
        int8_t index = lowest_bit_at(matrix_button_clear_key());
        if (index >= 0) {
            calculator(board[index]);
        }
    }
}

#endif //TASK3
