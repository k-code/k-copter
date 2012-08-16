#include "protocol.h"
#include "usbd_cdc_vcp.h"

static void PROTOCOL_parseFrame(uint8_t *buf, PROTOCOL_Frame *f);
static uint32_t PROTOCOL_parseInt(uint8_t *mess);
static void PROTOCOL_intToByteArray(uint32_t val, uint8_t *arr);

uint32_t PROTOCOL_toByteArray(PROTOCOL_Protocol *p, uint8_t *buf) {
    uint32_t bufLen = 12;
    PROTOCOL_intToByteArray(p->num, &buf[8]);

    for (uint8_t i = 0; i < 2; i++) {
        buf[bufLen] = p->frames[i].cmd;
        bufLen++;
        buf[bufLen] = p->frames[i].type;
        bufLen++;
        if (p->frames[i].type == PROTOCOL_TYPE_BYTE) {
            buf[bufLen] = p->frames[i].bData;
            bufLen++;
        } else if (p->frames[i].type == PROTOCOL_TYPE_INT) {
            PROTOCOL_intToByteArray(p->frames[i].iData, &buf[bufLen]);
            bufLen += 4;
        }
    }

    PROTOCOL_intToByteArray(bufLen, &buf[4]);
    PROTOCOL_intToByteArray(0x55555555, &buf[0]);
    return bufLen;
}

// TODO : fixed parse protocol. Added check control frame
void PROTOCOL_parseProtocol(uint8_t *buf, PROTOCOL_Protocol *p) {
    uint32_t bufLen = PROTOCOL_parseInt(&buf[0]);
    p->framesLen = 0;
    if (bufLen <= 0 || bufLen > PROTOCOL_MAX_LEN) {
        return;
    }
    p->num = PROTOCOL_parseInt(&buf[4]);
    PROTOCOL_Frame f;
    for (uint32_t i = 8; i < bufLen;) {
        PROTOCOL_parseFrame(&buf[i], &f);
        switch (f.type) {
        case PROTOCOL_TYPE_BYTE:
            i += 3;
            break;
        case PROTOCOL_TYPE_INT:
            i += 6;
            break;
        default:
            return;
        }
        p->frames[p->framesLen] = f;
        p->framesLen++;
    }

    return;
}

static void PROTOCOL_parseFrame(uint8_t *buf, PROTOCOL_Frame *f) {
    f->cmd = buf[0];
    f->type = buf[1];
    switch (f->type) {
    case PROTOCOL_TYPE_BYTE:
        f->bData = (uint32_t) buf[2];
        break;
    case PROTOCOL_TYPE_INT:
        f->iData = PROTOCOL_parseInt(&buf[2]);
        break;
    }
    return;
}

static uint32_t PROTOCOL_parseInt(uint8_t *mess) {
    uint32_t res = (((uint32_t) (mess[0])) << 24);
    res |= (((uint32_t) (mess[1])) << 16);
    res |= (((uint32_t) (mess[2])) << 8);
    res |= (((uint32_t) mess[3]));

    return res;
}

static inline void PROTOCOL_intToByteArray(uint32_t val, uint8_t *arr) {
    arr[0] = (uint8_t) (val >> 24);
    arr[1] = (uint8_t) (val >> 16);
    arr[2] = (uint8_t) (val >> 8);
    arr[3] = (uint8_t) val;
}

