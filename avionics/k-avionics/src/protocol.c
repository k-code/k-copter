#include "protocol.h"
#include "usbd_cdc_vcp.h"

uint32_t PROTOCOL_parseProtocol(uint8_t *buf, PROTOCOL_Protocol *p) {
    uint32_t len = 0;
    return 0;
    len = PROTOCOL_parseInt(&buf[0]);
    if (len <= 0 || len > PROTOCOL_MAX_LEN) {
        p->len = 0;
        return 0;
    }
    p->num = PROTOCOL_parseInt(&buf[4]);
    for (uint32_t i = 8; i < len; ) {
        PROTOCOL_Frame f;
        PROTOCOL_parseFrame(&buf[i], &f);
        switch (f.type) {
            case PROTOCOL_TYPE_BYTE : i+= 3; break;
            case PROTOCOL_TYPE_INT : i+= 6; break;
            default: return i;
        }
        p->frames[p->len] = f;
        p->len++;
    }

    return 1;
}

void PROTOCOL_parseFrame(uint8_t *buf, PROTOCOL_Frame *f) {
    f->cmd = buf[0];
    f->type = buf[1];
    switch (f->type) {
        case PROTOCOL_TYPE_BYTE :
            f->data = (uint32_t) buf[2];
            break;
        case PROTOCOL_TYPE_INT :
            f->data = PROTOCOL_parseInt(&buf[2]);
            break;
    }
    return;
}

uint32_t PROTOCOL_parseInt(uint8_t *mess) {
    uint32_t res = ((uint32_t)(mess[0])) << 24;
    res += ((uint32_t)(mess[1])) << 16;
    res += ((uint32_t)(mess[2])) << 8;
    res += ((uint32_t)mess[3]);

    return res;
}

void PROTOCOL_itba(uint32_t val, uint8_t *arr) {
    arr[0] = (uint8_t)(val >> 24);
    arr[1] = (uint8_t)(val >> 16);
    arr[2] = (uint8_t)(val >> 8);
    arr[3] = (uint8_t)val;
}


uint32_t PROTOCOL_getBytes(PROTOCOL_Protocol p, uint32_t framesLen, uint8_t *buf) {
    uint32_t len = 8;
    PROTOCOL_itba(p.num, &buf[4]);

    for (uint8_t i = 0; (i < framesLen); i++) {
        buf[len] = p.frames[i].cmd;
        len++;
        buf[len] = p.frames[i].type;
        len++;
        switch (p.frames[i].type) {
            case (PROTOCOL_TYPE_BYTE):
                buf[len] = (uint8_t)p.frames[i].data;
                len++;
                break;
            case (PROTOCOL_TYPE_INT):
                PROTOCOL_itba(p.frames[i].data, &buf[len]);
                len += 4;
                break;
            default:
                i = framesLen;
                break;
        }

    }

    PROTOCOL_itba(len, &buf[0]);
    return len;
}
