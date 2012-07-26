#include "protocol.h"

struct Protocol parseProtocol(uint8_t *buf) {
    struct Protocol p;
    struct Frame f;
    uint32_t len = parseInt(&buf[0]);
    p.num = parseInt(&buf[4]);
    uint32_t j = 0;
    for (uint32_t i = 8; i < len; ) {
        f = parseFrame(&buf[i]);
        switch (f.type) {
            case TYPE_BYTE : i+= 3; break;
            case TYPE_INT : i+= 6; break;
            default: return p;
        }
        p.frames[j] = f;
        j++;
        p.len = j;
    }

    return p;
}

struct Frame parseFrame(uint8_t *buf) {
    struct Frame f;
    f.cmd = buf[0];
    f.type = buf[1];
    switch (f.type) {
        case TYPE_BYTE :
            f.data = (uint32_t) buf[2];
            break;
        case TYPE_INT :
            f.data = parseInt(&buf[2]);
            break;
    }
    return f;
}

uint32_t parseInt(uint8_t *mess) {
    uint32_t i = 0;

    uint32_t res = (uint32_t)(mess[i++] << 24);
    res += (uint32_t)(mess[i++] << 16);
    res += (uint32_t)(mess[i++] << 8);
    res += (uint32_t)mess[i];

    return res;
}

