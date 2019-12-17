package com.helpsoft;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class Variant  {
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

    public Variant() {
        this.m_type = 1;
    }




    public void writeTo(BufferedSink sink) throws IOException {

    }

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

    public byte getType() { return this.m_type; }

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

    public String getStringValue() { return this.m_s; }

    public String getStringValue(String paramString) {
        if (this.m_map == null)
            return "";
        Variant variant = this.m_map.get(paramString);
        return (variant == null) ? "" : variant.m_s;
    }


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
                /*try {
                    arrayOfObject = (Object[])paramVariant.getStringValue().getBytes("UTF-8");
                    paramByteBuffer.putShort((short)arrayOfObject.length);
                    paramByteBuffer.put((byte[])arrayOfObject);
                } catch (UnsupportedEncodingException arrayOfObject) {
                    arrayOfObject.printStackTrace();
                    return false;
                }*/
            /*case 20:
                arrayOfByte = arrayOfObject.getByteArrayValue();
                paramByteBuffer.putShort((short)arrayOfByte.length);
                paramByteBuffer.put(arrayOfByte);*/
            case 18:
            case 19:
                break;
        }
        //paramByteBuffer.putShort((short)arrayOfByte.m_map.size());
        //Iterator<Map.Entry> iterator = arrayOfByte.m_map.entrySet().iterator();
        /*while (true) {
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

        }*/
        return false;
    }



}
