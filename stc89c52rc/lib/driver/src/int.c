#include "int.h"
#include "reg51.h"

void int0_init() {
    IT0 = 1;
    EX0 = 1;
}
