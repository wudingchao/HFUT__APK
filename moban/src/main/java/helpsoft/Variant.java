package com.helpsoft;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Variant {
    public static final byte V_BOOL = 3;

    public static final byte V_BYTEARRAY = 20;

    public static final byte V_DATE = 15;

    public static final byte V_DOUBLE = 12;

    public static final byte V_INT16 = 5;

    public static final byte V_INT32 = 6;

    public static final byte V_INT64 = 7;

    public static final byte V_INT8 = 4;

    public static final byte V_MAP = 19;

    public static final byte V_NULL = 1;

    public static final byte V_STRING = 17;

    public static final byte V_TIME = 16;

    public static final byte V_TIMESTAMP = 14;

    public static final byte V_TYPED_MAP = 18;

    public static final byte V_UINT16 = 9;

    public static final byte V_UINT32 = 10;

    public static final byte V_UINT8 = 8;

    public static final byte V_UNDEFINED = 2;

    public static final byte _V_NUMERIC = 13;

    private boolean m_b = false;

    private double m_d = 0.0D;

    private int m_i = 0;

    private long m_l = 0L;

    private Map<String, Variant> m_map = null;

    private byte[] m_pbuf = null;

    private String m_s = null;

    private byte m_type = 1;

    public Variant() { this.m_type = 1; }

    public Variant(double paramDouble) {
        this.m_type = 12;
        this.m_d = paramDouble;
    }

    public Variant(int paramInt) {
        this.m_type = 6;
        this.m_i = paramInt;
    }

    public Variant(int paramInt, byte paramByte) {
        this.m_type = paramByte;
        this.m_i = paramInt;
    }

    public Variant(long paramLong) {
        this.m_type = 7;
        this.m_l = paramLong;
    }

    public Variant(Variant paramVariant) {
        this.m_type = 19;
        this.m_map = paramVariant.m_map;
    }

    public Variant(String paramString) {
        this.m_type = 17;
        this.m_s = paramString;
    }

    public Variant(boolean paramBoolean) {
        this.m_type = 3;
        this.m_b = paramBoolean;
    }

    public Variant(byte[] paramArrayOfbyte) {
        this.m_type = 20;
        this.m_pbuf = paramArrayOfbyte;
    }

    public static boolean BufferToVariant(ByteBuffer paramByteBuffer, Variant paramVariant) { // Byte code:
        //   0: aload_0
        //   1: invokevirtual remaining : ()I
        //   4: iconst_1
        //   5: if_icmpge -> 10
        //   8: iconst_1
        //   9: ireturn
        //   10: aload_0
        //   11: invokevirtual get : ()B
        //   14: istore_2
        //   15: aload_1
        //   16: iload_2
        //   17: putfield m_type : B
        //   20: iload_2
        //   21: tableswitch default -> 108, 3 -> 110, 4 -> 137, 5 -> 148, 6 -> 159, 7 -> 170, 8 -> 137, 9 -> 148, 10 -> 159, 11 -> 108, 12 -> 181, 13 -> 108, 14 -> 108, 15 -> 108, 16 -> 108, 17 -> 192, 18 -> 251, 19 -> 251, 20 -> 238
        //   108: iconst_1
        //   109: ireturn
        //   110: aload_0
        //   111: invokevirtual get : ()B
        //   114: bipush #49
        //   116: if_icmpne -> 131
        //   119: iconst_1
        //   120: istore #6
        //   122: aload_1
        //   123: iload #6
        //   125: invokevirtual setBoolValue : (Z)V
        //   128: goto -> 108
        //   131: iconst_0
        //   132: istore #6
        //   134: goto -> 122
        //   137: aload_1
        //   138: aload_0
        //   139: invokevirtual get : ()B
        //   142: invokevirtual setInt8Value : (I)V
        //   145: goto -> 108
        //   148: aload_1
        //   149: aload_0
        //   150: invokevirtual getShort : ()S
        //   153: invokevirtual setInt16Value : (I)V
        //   156: goto -> 108
        //   159: aload_1
        //   160: aload_0
        //   161: invokevirtual getInt : ()I
        //   164: invokevirtual setInt32Value : (I)V
        //   167: goto -> 108
        //   170: aload_1
        //   171: aload_0
        //   172: invokevirtual getLong : ()J
        //   175: invokevirtual setInt64Value : (J)V
        //   178: goto -> 108
        //   181: aload_1
        //   182: aload_0
        //   183: invokevirtual getDouble : ()D
        //   186: invokevirtual setDoubleValue : (D)V
        //   189: goto -> 108
        //   192: aload_0
        //   193: invokevirtual getShort : ()S
        //   196: istore_3
        //   197: iload_3
        //   198: newarray byte
        //   200: astore #7
        //   202: aload_0
        //   203: aload #7
        //   205: iconst_0
        //   206: iload_3
        //   207: invokevirtual get : ([BII)Ljava/nio/ByteBuffer;
        //   210: pop
        //   211: new java/lang/String
        //   214: dup
        //   215: aload #7
        //   217: ldc 'UTF-8'
        //   219: invokespecial <init> : ([BLjava/lang/String;)V
        //   222: astore_0
        //   223: aload_1
        //   224: aload_0
        //   225: invokevirtual setStringValue : (Ljava/lang/String;)V
        //   228: goto -> 108
        //   231: astore_0
        //   232: aload_0
        //   233: invokevirtual printStackTrace : ()V
        //   236: iconst_0
        //   237: ireturn
        //   238: aload_1
        //   239: aload_0
        //   240: invokevirtual getInt : ()I
        //   243: newarray byte
        //   245: invokevirtual setByteArrayValue : ([B)V
        //   248: goto -> 108
        //   251: aload_0
        //   252: invokevirtual getShort : ()S
        //   255: istore #4
        //   257: iconst_0
        //   258: istore_3
        //   259: iload_3
        //   260: iload #4
        //   262: if_icmpge -> 108
        //   265: aload_0
        //   266: invokevirtual getShort : ()S
        //   269: istore #5
        //   271: iload #5
        //   273: ifne -> 317
        //   276: new java/lang/StringBuilder
        //   279: dup
        //   280: invokespecial <init> : ()V
        //   283: ldc '__index__value__'
        //   285: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   288: iload_3
        //   289: invokevirtual append : (I)Ljava/lang/StringBuilder;
        //   292: invokevirtual toString : ()Ljava/lang/String;
        //   295: astore #7
        //   297: new com/helpsoft/Variant
        //   300: dup
        //   301: invokespecial <init> : ()V
        //   304: astore #8
        //   306: aload_0
        //   307: aload #8
        //   309: invokestatic BufferToVariant : (Ljava/nio/ByteBuffer;Lcom/helpsoft/Variant;)Z
        //   312: ifne -> 356
        //   315: iconst_0
        //   316: ireturn
        //   317: iload #5
        //   319: newarray byte
        //   321: astore #7
        //   323: aload_0
        //   324: aload #7
        //   326: iconst_0
        //   327: iload #5
        //   329: invokevirtual get : ([BII)Ljava/nio/ByteBuffer;
        //   332: pop
        //   333: new java/lang/String
        //   336: dup
        //   337: aload #7
        //   339: ldc 'UTF-8'
        //   341: invokespecial <init> : ([BLjava/lang/String;)V
        //   344: astore #7
        //   346: goto -> 297
        //   349: astore_0
        //   350: aload_0
        //   351: invokevirtual printStackTrace : ()V
        //   354: iconst_0
        //   355: ireturn
        //   356: aload_1
        //   357: aload #7
        //   359: aload #8
        //   361: invokevirtual addValue : (Ljava/lang/String;Lcom/helpsoft/Variant;)V
        //   364: iload_3
        //   365: iconst_1
        //   366: iadd
        //   367: istore_3
        //   368: goto -> 259
        // Exception table:
        //   from	to	target	type
        //   211	223	231	java/io/UnsupportedEncodingException
        //   333	346	349	java/io/UnsupportedEncodingException }

        public static boolean VariantToBuffer(Variant paramVariant, ByteBuffer paramByteBuffer) {
            byte b;
            byte[] arrayOfByte;
            if (paramVariant.getType() == 1 || paramVariant.getType() == 2)
                return true;
            paramByteBuffer.put(paramVariant.getType());
            switch (paramVariant.getType()) {
                default:
                    return true;
                case 3:
                    if (paramVariant.getBoolValue() == true) {
                        b = 49;
                    } else {
                        b = 48;
                    }
                    paramByteBuffer.put((byte)b);
                case 4:
                case 8:
                    paramByteBuffer.put((byte)paramVariant.getInt8Value());
                case 5:
                case 9:
                    paramByteBuffer.putShort((short)paramVariant.getInt16Value());
                case 6:
                case 10:
                    paramByteBuffer.putInt(paramVariant.getInt32Value());
                case 7:
                    paramByteBuffer.putLong(paramVariant.getInt64Value());
                case 12:
                    paramByteBuffer.putDouble(paramVariant.getDoubleValue());
                case 17:
                    try {
                        arrayOfObject = (Object[])paramVariant.getStringValue().getBytes("UTF-8");
                        paramByteBuffer.putShort((short)arrayOfObject.length);
                        paramByteBuffer.put((byte[])arrayOfObject);
                    } catch (UnsupportedEncodingException arrayOfObject) {
                        arrayOfObject.printStackTrace();
                        return false;
                    }
                case 20:
                    arrayOfByte = arrayOfObject.getByteArrayValue();
                    paramByteBuffer.putShort((short)arrayOfByte.length);
                    paramByteBuffer.put(arrayOfByte);
                case 18:
                case 19:
                    break;
            }
            paramByteBuffer.putShort((short)arrayOfByte.m_map.size());
            Iterator<Map.Entry> iterator = arrayOfByte.m_map.entrySet().iterator();
            while (true) {
                Variant variant;
                if (iterator.hasNext()) {
                    Map.Entry entry = iterator.next();
                    String str = (String)entry.getKey();
                    variant = (Variant)entry.getValue();
                    if (str.indexOf("__index__value__") < 0)
                        try {
                            byte[] arrayOfByte1 = str.getBytes("UTF-8");
                            paramByteBuffer.putShort((short)arrayOfByte1.length);
                            paramByteBuffer.put(arrayOfByte1);
                            if (!VariantToBuffer(variant, paramByteBuffer))
                                return false;
                            continue;
                        } catch (UnsupportedEncodingException unsupportedEncodingException) {
                            unsupportedEncodingException.printStackTrace();
                            return false;
                        }
                    paramByteBuffer.putShort((short)0);
                } else {

                }
                if (!VariantToBuffer(variant, paramByteBuffer))
                    return false;
            }
        }

        public void addValue(Variant paramVariant) {
            this.m_type = 19;
            if (this.m_map == null)
                this.m_map = new LinkedHashMap<String, Variant>();
            String str = String.format("__index__value__%d", new Object[] { Integer.valueOf(this.m_map.size()) });
            this.m_map.put(str, new Variant(paramVariant));
        }

        public void addValue(String paramString, double paramDouble) {
            this.m_type = 19;
            if (this.m_map == null)
                this.m_map = new LinkedHashMap<String, Variant>();
            this.m_map.put(paramString, new Variant(paramDouble));
        }

        public void addValue(String paramString, int paramInt) {
            this.m_type = 19;
            if (this.m_map == null)
                this.m_map = new LinkedHashMap<String, Variant>();
            this.m_map.put(paramString, new Variant(paramInt));
        }

        public void addValue(String paramString, int paramInt, byte paramByte) {
            this.m_type = 19;
            if (this.m_map == null)
                this.m_map = new LinkedHashMap<String, Variant>();
            this.m_map.put(paramString, new Variant(paramInt, paramByte));
        }

        public void addValue(String paramString, long paramLong) {
            this.m_type = 19;
            if (this.m_map == null)
                this.m_map = new LinkedHashMap<String, Variant>();
            this.m_map.put(paramString, new Variant(paramLong));
        }

        public void addValue(String paramString, Variant paramVariant) {
            this.m_type = 19;
            if (this.m_map == null)
                this.m_map = new LinkedHashMap<String, Variant>();
            this.m_map.put(paramString, paramVariant);
        }

        public void addValue(String paramString1, String paramString2) {
            this.m_type = 19;
            if (this.m_map == null)
                this.m_map = new LinkedHashMap<String, Variant>();
            this.m_map.put(paramString1, new Variant(paramString2));
        }

        public void addValue(String paramString, boolean paramBoolean) {
            this.m_type = 19;
            if (this.m_map == null)
                this.m_map = new LinkedHashMap<String, Variant>();
            this.m_map.put(paramString, new Variant(paramBoolean));
        }

        public void addValue(String paramString, byte[] paramArrayOfbyte) {
            this.m_type = 19;
            if (this.m_map == null)
                this.m_map = new LinkedHashMap<String, Variant>();
            this.m_map.put(paramString, new Variant(paramArrayOfbyte));
        }

        public Variant getArray(int paramInt) {
            Object object = null;
            if (this.m_map != null && paramInt < this.m_map.size()) {
                object = String.format("__index__value__%d", new Object[] { Integer.valueOf(paramInt) });
                Variant variant = this.m_map.get(object);
                object = variant;
                if (variant == null)
                    return new Variant();
            }
            return (Variant)object;
        }

        public boolean getBoolValue() { return this.m_b; }

        public boolean getBoolValue(String paramString) {
            if (this.m_map != null) {
                Variant variant = this.m_map.get(paramString);
                if (variant != null)
                    return variant.m_b;
            }
            return false;
        }

        public byte[] getByteArrayValue() { return this.m_pbuf; }

        public byte[] getByteArrayValue(String paramString) {
            if (this.m_map != null) {
                Variant variant = this.m_map.get(paramString);
                if (variant != null)
                    return variant.m_pbuf;
            }
            return null;
        }

        public double getDoubleValue() { return this.m_d; }

        public double getDoubleValue(String paramString) {
            if (this.m_map != null) {
                Variant variant = this.m_map.get(paramString);
                if (variant != null)
                    return variant.m_d;
            }
            return 0.0D;
        }

        public int getInt16Value() { return this.m_i; }

        public int getInt16Value(String paramString) {
            if (this.m_map != null) {
                Variant variant = this.m_map.get(paramString);
                if (variant != null)
                    return variant.m_i;
            }
            return 0;
        }

        public int getInt32Value() { return this.m_i; }

        public int getInt32Value(String paramString) {
            if (this.m_map != null) {
                Variant variant = this.m_map.get(paramString);
                if (variant != null)
                    return variant.m_i;
            }
            return 0;
        }

        public long getInt64Value() { return this.m_l; }

        public long getInt64Value(String paramString) {
            if (this.m_map != null) {
                Variant variant = this.m_map.get(paramString);
                if (variant != null)
                    return variant.m_l;
            }
            return 0L;
        }

        public int getInt8Value() { return this.m_i; }

        public int getInt8Value(String paramString) {
            if (this.m_map != null) {
                Variant variant = this.m_map.get(paramString);
                if (variant != null)
                    return variant.m_i;
            }
            return 0;
        }

        public int getMapSize() { return (this.m_map == null) ? 0 : this.m_map.size(); }

        public Variant getObjectValue(String paramString) {
            if (this.m_map == null)
                return null;
            Variant variant2 = this.m_map.get(paramString);
            Variant variant1 = variant2;
            return (variant2 == null) ? new Variant() : variant1;
        }

        public String getStringValue() { return this.m_s; }

        public String getStringValue(String paramString) {
            if (this.m_map == null)
                return "";
            Variant variant = this.m_map.get(paramString);
            return (variant == null) ? "" : variant.m_s;
        }

        public byte getType() { return this.m_type; }

        public void setBoolValue(boolean paramBoolean) {
            this.m_type = 3;
            this.m_b = paramBoolean;
        }

        public void setByteArrayValue(byte[] paramArrayOfbyte) {
            this.m_type = 20;
            this.m_pbuf = paramArrayOfbyte;
        }

        public void setDoubleValue(double paramDouble) {
            this.m_type = 12;
            this.m_d = paramDouble;
        }

        public void setInt16Value(int paramInt) {
            this.m_type = 5;
            this.m_i = paramInt;
        }

        public void setInt32Value(int paramInt) {
            this.m_type = 6;
            this.m_i = paramInt;
        }

        public void setInt64Value(long paramLong) {
            this.m_type = 7;
            this.m_l = paramLong;
        }

        public void setInt8Value(int paramInt) {
            this.m_type = 4;
            this.m_i = paramInt;
        }

        public void setObjectValue(Variant paramVariant) {
            this.m_type = 19;
            this.m_map = paramVariant.m_map;
        }

        public void setStringValue(String paramString) {
            this.m_type = 17;
            this.m_s = paramString;
        }
    }
