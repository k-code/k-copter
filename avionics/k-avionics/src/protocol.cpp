#include "protocol.h"

class Protocol {
    int32_t num = 0;

    int32_t len = 0;
    int8_t  mess[MAX_LENGTH];

    Protocol() {
        len++;
        num++;
        addInt(num);
        addLenToMess();
    }

    Protocol(int8_t* mess) {
        len++;
        num++;
        addInt(num);
        addLenToMess();
    }

    void addParam(int8_t id, int8_t val) {
        addByte(id);
        addByte(TYPE_BYTE);
        addInt(val);
    }

    void addParam(int8_t id, int32_t val) {
        addByte(id);
        addByte(TYPE_INT);
        addInt(val);
    }

    void addParam(int8_t id, int8_t *val) {
        addByte(id);
        addByte(TYPE_STR);
        addString(val);
    }

    int8_t* getMess() {
        addLenToMess();
        return mess;
    }

    int32_t getLen() {
        return len;
    }

    void addByte(int8_t val) {
        mess[len] = val;
        len++;
    }

    void addInt(int32_t val) {
        arrayCopy(mess, itba(val), len, 4);
        len+=4;
    }

    void addString(int8_t* val) {
        int strlen = strnlen((char*)val, MAX_LENGTH);
        arrayCopy(mess, val, len, strlen);
        len+= strlen;
    }

    /**
     * Integer to byte array
     * @param value
     * @return
     */
    int8_t *itba(int value) {
        return {
                (int8_t)(value >> 24),
                (int8_t)(value >> 16),
                (int8_t)(value >> 8),
                (int8_t)value};
    }

    void arrayCopy(int8_t *to, int8_t *from, int32_t pos, int32_t len) {
        for (int i=pos, j=0; i < pos+len; i++, j++) {
            to[i] = from[j];
        }
    }

    void addLenToMess() {
        arrayCopy(mess, itba(len), 0, 4);
    }

};
