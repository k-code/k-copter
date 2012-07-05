# THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED
# WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
# MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE. NEITHER RECIPIENT NOR
# ANY CONTRIBUTORS SHALL HAVE ANY LIABILITY FOR ANY DIRECT, INDIRECT,
# INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING WITHOUT
# LIMITATION LOST PROFITS), HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
# WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
# OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OR DISTRIBUTION OF THE PROGRAM
# OR THE EXERCISE OF ANY RIGHTS GRANTED HEREUNDER, EVEN IF ADVISED OF THE
# POSSIBILITY OF SUCH DAMAGES.
# 
# The name of the Copyright Holder may not be used to endorse or promote
# products derived from this software without specific prior written permission.
# 
# Copyright 2002-2007 George Rhoten, Daniel Kristjansson and others.
# 

DEPOPTS= -M
DEPLINE= 1> Makedepend.linux

# C++ source files
SWSRCS= com_centralnexus_input_Joystick.cpp \
	com_centralnexus_input_ExtendedJoystick.cpp \
	LinuxJoystick.cpp

SRCS=$(SWSRCS) $(ARCHSRCS)

# C source
SWCSRCS=

CSRCS=$(SWCSRCS) $(ARCHCSRCS)

# source files with no associated .o 
SRCSNOO=

# source files with no associated .h 
SRCSNOH=

# header files with no associated .cpp
HDRSNOS=

# sources that are #include'd, and have no associated .o
TABLESRCS=

ALLSRCS=$(SRCSNOH) $(CSRCS) $(SRCS) $(SRCSNOO) $(TABLESRCS) 

# All the local .h files (these are not automatically depended)
HDRS=$(HDRSNOS) ${SRCS:.cpp=.h} ${CSRCS:.c=.h} ${SRCSNOO:.cpp=.h}

# All .o files generated
COBJS=${CSRCS:.c=.o}
OBJS= $(COBJS) ${SRCS:.cpp=.o}
ALLOBJS=$(OBJS) ${SRCSNOH:.cpp=.o}
PROTOOBJS = ${PROTOS:.cpp=.o}

# Man pages, etc. (leave blank if none)
DOCS=

# Application name
TARGET=../lib/libjjstick.so

##############################################################################
# Application-specific definitions  -- EDIT THESE
##############################################################################

BASE_JVM = /usr/lib/jvm/java-6-sun
INCS =-I. -I$(BASE_JVM)/include -I$(BASE_JVM)/include/linux

# other C and C++ common options

# For making a shared library
SHARED_FLAGS = -fPIC -DPIC

# CC options

#DEBUGOPTS= -ggdb -g3
INOPTS=-O

OPTS=  $(INOPTS) $(SHARED_FLAGS) $(DEBUGOPTS)

#Architecture flags in case you want to build as 32-bit on a 64-bit machine.
#ARCHFLAGS = -m32
ARCHFLAGS = 

# C specific options
COPTS+= $(ARCHFLAGS)

# C++ specific options
CCOPTS+= $(ARCHFLAGS)

# C++ specific options
LDFLAGS+= $(ARCHFLAGS)

# linker options
#MLIBS=-L/opt/IBMJava2-13/bin/java/include

# ----------------------------------------------------------------------------
# -- You shouldn't need to edit anything below here, unless you have more
# -- than one target or are building a library, or you're doing something
# -- nonstandard.
# ----------------------------------------------------------------------------

##############################################################################
# Language definitions
##############################################################################

CC=g++
cc=gcc

CFLAGS= $(INCS) $(DEFS) $(DEBUG) $(OPTS) $(COPTS) 
CCFLAGS=$(INCS) $(DEFS) $(DEBUG) $(OPTS) $(CCOPTS)

TARGET_COMPILER_FLAGS=$(CCFLAGS) $(LIBOPTS) 
TARGET_COMPILER=$(CC)

##############################################################################
# extra default rules
##############################################################################
.SUFFIXES:
.SUFFIXES: .C .cpp .c .C.o .cpp.o .c.o .o

.cpp:
	$(CC)  $(CCFLAGS) -o $@ $<

.c:
	$(cc) $(CFLAGS) -o $@ $<

.cpp.o:
	$(CC)  $(CCFLAGS) -c $<

.c.o:
	$(cc) $(CFLAGS) -c $<


# This rule must be first.  It insures that the desired target is the default.


all: $(TARGET)


##############################################################################
# Application-specific rules and dependencies - may need editing
##############################################################################

VERSION_MAJOR = 0
VERSION_MINOR = 7

../lib/libjjstick.so: $(ALLOBJS)
	javah -jni -classpath ..:../Joystick.jar com.centralnexus.input.Joystick
	javah -jni -classpath ..:../Joystick.jar com.centralnexus.input.ExtendedJoystick
	rm -f ../lib/libjjstick.so.$(VERSION_MAJOR).$(VERSION_MINOR)
	$(TARGET_COMPILER) $(LDFLAGS) -shared -Wl,-soname,libjjstick.so.$(VERSION_MAJOR).$(VERSION_MINOR) -o ../lib/libjjstick.so.$(VERSION_MAJOR).$(VERSION_MINOR) $(ALLOBJS)
	ln -f -s libjjstick.so.$(VERSION_MAJOR).$(VERSION_MINOR) ../lib/libjjstick.so.$(VERSION_MAJOR)
	ln -f -s libjjstick.so.$(VERSION_MAJOR).$(VERSION_MINOR) ../lib/libjjstick.so


depend: 
	$(TARGET_COMPILER) $(ALLSRCS) $(TARGET_COMPILER_FLAGS) $(DEPOPTS) $(DEPLINE)

##############################################################################
# General-purpose targets - do not edit, in general:
##############################################################################

clean:	
	rm -f $(TARGET) $(OBJS) \
		*.o *.a Makedepend.linux \
		core a.out \#*
.IGNORE:    

# DO NOT DELETE THIS LINE -- make depend depends on it.

sinclude Makedepend.linux
