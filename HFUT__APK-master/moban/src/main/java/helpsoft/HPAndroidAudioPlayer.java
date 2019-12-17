package com.helpsoft;

import android.media.AudioTrack;
import android.util.Log;
import java.nio.ByteBuffer;

public class HPAndroidAudioPlayer implements Runnable {
    private AudioTrack m_audioTrack = null;

    private final int m_bufferCount = 15;

    private int m_bufferSize = 0;

    private int m_cacheTime = 1;

    private boolean m_exitThread = false;

    private boolean m_exitThreaded = true;

    private int m_playTime = 0;

    private ByteBuffer m_playerBuffer = null;

    private ByteBuffer[] m_playerBuffers = new ByteBuffer[15];

    private int[] m_playerTimes = new int[15];

    private int m_rindex = 0;

    private long m_startMs = 0L;

    private int m_startMs2 = 0;

    private Thread m_thread = null;

    private int m_windex = 0;

    public void closePlayer() { // Byte code:
        //   0: aload_0
        //   1: getfield m_audioTrack : Landroid/media/AudioTrack;
        //   4: ifnull -> 41
        //   7: aload_0
        //   8: iconst_1
        //   9: putfield m_exitThread : Z
        //   12: aload_0
        //   13: getfield m_exitThreaded : Z
        //   16: istore_1
        //   17: iload_1
        //   18: ifne -> 57
        //   21: lconst_1
        //   22: invokestatic sleep : (J)V
        //   25: goto -> 12
        //   28: astore_2
        //   29: aload_2
        //   30: invokevirtual printStackTrace : ()V
        //   33: goto -> 12
        //   36: astore_2
        //   37: aload_2
        //   38: invokevirtual printStackTrace : ()V
        //   41: aload_0
        //   42: aconst_null
        //   43: putfield m_playerBuffers : [Ljava/nio/ByteBuffer;
        //   46: aload_0
        //   47: aconst_null
        //   48: putfield m_playerTimes : [I
        //   51: aload_0
        //   52: aconst_null
        //   53: putfield m_playerBuffer : Ljava/nio/ByteBuffer;
        //   56: return
        //   57: aload_0
        //   58: aconst_null
        //   59: putfield m_thread : Ljava/lang/Thread;
        //   62: aload_0
        //   63: getfield m_audioTrack : Landroid/media/AudioTrack;
        //   66: invokevirtual stop : ()V
        //   69: aload_0
        //   70: getfield m_audioTrack : Landroid/media/AudioTrack;
        //   73: invokevirtual release : ()V
        //   76: aload_0
        //   77: aconst_null
        //   78: putfield m_audioTrack : Landroid/media/AudioTrack;
        //   81: goto -> 41
        // Exception table:
        //   from	to	target	type
        //   0	12	36	java/lang/Exception
        //   12	17	36	java/lang/Exception
        //   21	25	28	java/lang/InterruptedException
        //   21	25	36	java/lang/Exception
        //   29	33	36	java/lang/Exception
        //   57	81	36	java/lang/Exception }

        public boolean openPlayer(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
            if (paramInt3 == 16) {
                paramInt3 = 2;
            } else {
                paramInt3 = 3;
            }
            if (paramInt2 == 1) {
                paramInt2 = 2;
            } else {
                paramInt2 = 3;
            }
            try {
                this.m_bufferSize = AudioTrack.getMinBufferSize(paramInt1, paramInt2, paramInt3);
                Log.e("m_bufferSize", "" + this.m_bufferSize + "  audioSessionId = " + paramInt4);
                this.m_audioTrack = new AudioTrack(0, paramInt1, paramInt2, paramInt3, this.m_bufferSize, 1, paramInt4);
                if (this.m_bufferSize < 12800)
                    this.m_bufferSize = 12800;
                if (this.m_audioTrack == null)
                    return false;
                this.m_thread = new Thread(this);
                if (this.m_thread == null) {
                    this.m_audioTrack.release();
                    this.m_audioTrack = null;
                    return false;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                return false;
            }
            for (paramInt1 = 0; paramInt1 < 15; paramInt1++)
                this.m_playerBuffers[paramInt1] = ByteBuffer.allocate(this.m_bufferSize);
            this.m_playerBuffer = ByteBuffer.allocate(this.m_bufferSize);
            this.m_thread.start();
            this.m_audioTrack.play();
            this.m_playTime = 0;
            this.m_startMs = 0L;
            this.m_startMs2 = 0;
            return true;
        }

        public boolean playFrame(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
            if (paramInt2 == 0) {
                this.m_playTime += 20;
            } else {
                this.m_playTime += paramInt2;
            }
            try {
                synchronized (this.m_playerBuffers) {
                    ByteBuffer byteBuffer2 = this.m_playerBuffers[this.m_windex];
                    ByteBuffer byteBuffer1 = byteBuffer2;
                    if (byteBuffer2 == null)
                        byteBuffer1 = ByteBuffer.allocate(this.m_bufferSize);
                    if (byteBuffer1.position() > 0)
                        return false;
                    byteBuffer1.clear();
                    byteBuffer1.put(paramArrayOfbyte);
                    this.m_playerTimes[this.m_windex] = this.m_playTime;
                    this.m_windex++;
                    if (this.m_windex >= 15)
                        this.m_windex = 0;
                    return true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                return false;
            }
        }

        public void run() { // Byte code:
            //   0: aload_0
            //   1: getfield m_exitThread : Z
            //   4: istore_2
            //   5: iload_2
            //   6: ifne -> 309
            //   9: lconst_1
            //   10: invokestatic sleep : (J)V
            //   13: aload_0
            //   14: getfield m_playerBuffer : Ljava/nio/ByteBuffer;
            //   17: iconst_0
            //   18: invokevirtual position : (I)Ljava/nio/Buffer;
            //   21: pop
            //   22: aload_0
            //   23: getfield m_playerBuffers : [Ljava/nio/ByteBuffer;
            //   26: astore #5
            //   28: aload #5
            //   30: monitorenter
            //   31: aload_0
            //   32: getfield m_playerBuffers : [Ljava/nio/ByteBuffer;
            //   35: aload_0
            //   36: getfield m_rindex : I
            //   39: aaload
            //   40: astore #4
            //   42: aload #4
            //   44: astore_3
            //   45: aload #4
            //   47: ifnonnull -> 58
            //   50: aload_0
            //   51: getfield m_bufferSize : I
            //   54: invokestatic allocate : (I)Ljava/nio/ByteBuffer;
            //   57: astore_3
            //   58: aload_3
            //   59: invokevirtual position : ()I
            //   62: ifle -> 358
            //   65: aload_0
            //   66: getfield m_playerTimes : [I
            //   69: aload_0
            //   70: getfield m_rindex : I
            //   73: iaload
            //   74: istore_1
            //   75: aload_0
            //   76: getfield m_startMs : J
            //   79: lconst_0
            //   80: lcmp
            //   81: ifne -> 102
            //   84: aload_0
            //   85: invokestatic currentTimeMillis : ()J
            //   88: aload_0
            //   89: getfield m_cacheTime : I
            //   92: i2l
            //   93: ladd
            //   94: putfield m_startMs : J
            //   97: aload_0
            //   98: iload_1
            //   99: putfield m_startMs2 : I
            //   102: iload_1
            //   103: aload_0
            //   104: getfield m_startMs2 : I
            //   107: isub
            //   108: i2l
            //   109: invokestatic currentTimeMillis : ()J
            //   112: aload_0
            //   113: getfield m_startMs : J
            //   116: lsub
            //   117: lsub
            //   118: invokestatic abs : (J)J
            //   121: ldc2_w 500
            //   124: lcmp
            //   125: iflt -> 146
            //   128: aload_0
            //   129: invokestatic currentTimeMillis : ()J
            //   132: aload_0
            //   133: getfield m_cacheTime : I
            //   136: i2l
            //   137: ladd
            //   138: putfield m_startMs : J
            //   141: aload_0
            //   142: iload_1
            //   143: putfield m_startMs2 : I
            //   146: invokestatic currentTimeMillis : ()J
            //   149: aload_0
            //   150: getfield m_startMs : J
            //   153: lsub
            //   154: iload_1
            //   155: aload_0
            //   156: getfield m_startMs2 : I
            //   159: isub
            //   160: i2l
            //   161: lcmp
            //   162: ifge -> 323
            //   165: aload_0
            //   166: aload_0
            //   167: getfield m_rindex : I
            //   170: iconst_1
            //   171: iadd
            //   172: putfield m_rindex : I
            //   175: aload_0
            //   176: getfield m_rindex : I
            //   179: bipush #15
            //   181: if_icmplt -> 189
            //   184: aload_0
            //   185: iconst_0
            //   186: putfield m_rindex : I
            //   189: aload_0
            //   190: getfield m_playerBuffer : Ljava/nio/ByteBuffer;
            //   193: invokevirtual clear : ()Ljava/nio/Buffer;
            //   196: pop
            //   197: aload_0
            //   198: getfield m_playerBuffer : Ljava/nio/ByteBuffer;
            //   201: aload_3
            //   202: invokevirtual array : ()[B
            //   205: iconst_0
            //   206: aload_3
            //   207: invokevirtual position : ()I
            //   210: invokevirtual put : ([BII)Ljava/nio/ByteBuffer;
            //   213: pop
            //   214: aload_3
            //   215: iconst_0
            //   216: invokevirtual position : (I)Ljava/nio/Buffer;
            //   219: pop
            //   220: aload_0
            //   221: aload_0
            //   222: getfield m_cacheTime : I
            //   225: bipush #10
            //   227: isub
            //   228: putfield m_cacheTime : I
            //   231: aload_0
            //   232: getfield m_cacheTime : I
            //   235: bipush #100
            //   237: if_icmpgt -> 246
            //   240: aload_0
            //   241: bipush #100
            //   243: putfield m_cacheTime : I
            //   246: aload #5
            //   248: monitorexit
            //   249: aload_0
            //   250: getfield m_playerBuffer : Ljava/nio/ByteBuffer;
            //   253: invokevirtual position : ()I
            //   256: ifle -> 389
            //   259: aload_0
            //   260: getfield m_audioTrack : Landroid/media/AudioTrack;
            //   263: aload_0
            //   264: getfield m_playerBuffer : Ljava/nio/ByteBuffer;
            //   267: invokevirtual array : ()[B
            //   270: iconst_0
            //   271: aload_0
            //   272: getfield m_playerBuffer : Ljava/nio/ByteBuffer;
            //   275: invokevirtual position : ()I
            //   278: invokevirtual write : ([BII)I
            //   281: pop
            //   282: aload_0
            //   283: aload_0
            //   284: getfield m_playerBuffer : Ljava/nio/ByteBuffer;
            //   287: invokevirtual position : ()I
            //   290: putfield m_bufferSize : I
            //   293: goto -> 0
            //   296: astore_3
            //   297: aload_3
            //   298: invokevirtual printStackTrace : ()V
            //   301: goto -> 0
            //   304: astore_3
            //   305: aload_3
            //   306: invokevirtual printStackTrace : ()V
            //   309: aload_0
            //   310: iconst_1
            //   311: putfield m_exitThreaded : Z
            //   314: return
            //   315: astore_3
            //   316: aload_3
            //   317: invokevirtual printStackTrace : ()V
            //   320: goto -> 13
            //   323: aload_0
            //   324: aload_0
            //   325: getfield m_cacheTime : I
            //   328: bipush #10
            //   330: isub
            //   331: putfield m_cacheTime : I
            //   334: aload_0
            //   335: getfield m_cacheTime : I
            //   338: bipush #100
            //   340: if_icmpgt -> 246
            //   343: aload_0
            //   344: bipush #100
            //   346: putfield m_cacheTime : I
            //   349: goto -> 246
            //   352: astore_3
            //   353: aload #5
            //   355: monitorexit
            //   356: aload_3
            //   357: athrow
            //   358: aload_0
            //   359: aload_0
            //   360: getfield m_cacheTime : I
            //   363: bipush #10
            //   365: iadd
            //   366: putfield m_cacheTime : I
            //   369: aload_0
            //   370: getfield m_cacheTime : I
            //   373: sipush #2000
            //   376: if_icmplt -> 246
            //   379: aload_0
            //   380: sipush #2000
            //   383: putfield m_cacheTime : I
            //   386: goto -> 246
            //   389: aload_0
            //   390: getfield m_playerBuffer : Ljava/nio/ByteBuffer;
            //   393: invokevirtual clear : ()Ljava/nio/Buffer;
            //   396: pop
            //   397: iconst_0
            //   398: istore_1
            //   399: iload_1
            //   400: aload_0
            //   401: getfield m_bufferSize : I
            //   404: if_icmpge -> 424
            //   407: aload_0
            //   408: getfield m_playerBuffer : Ljava/nio/ByteBuffer;
            //   411: invokevirtual array : ()[B
            //   414: iload_1
            //   415: iconst_0
            //   416: bastore
            //   417: iload_1
            //   418: iconst_1
            //   419: iadd
            //   420: istore_1
            //   421: goto -> 399
            //   424: aload_0
            //   425: getfield m_playerBuffer : Ljava/nio/ByteBuffer;
            //   428: aload_0
            //   429: getfield m_bufferSize : I
            //   432: invokevirtual position : (I)Ljava/nio/Buffer;
            //   435: pop
            //   436: aload_0
            //   437: getfield m_audioTrack : Landroid/media/AudioTrack;
            //   440: aload_0
            //   441: getfield m_playerBuffer : Ljava/nio/ByteBuffer;
            //   444: invokevirtual array : ()[B
            //   447: iconst_0
            //   448: aload_0
            //   449: getfield m_playerBuffer : Ljava/nio/ByteBuffer;
            //   452: invokevirtual position : ()I
            //   455: invokevirtual write : ([BII)I
            //   458: pop
            //   459: goto -> 0
            // Exception table:
            //   from	to	target	type
            //   0	5	304	java/lang/Exception
            //   9	13	315	java/lang/InterruptedException
            //   9	13	304	java/lang/Exception
            //   13	31	296	java/lang/Exception
            //   31	42	352	finally
            //   50	58	352	finally
            //   58	102	352	finally
            //   102	146	352	finally
            //   146	189	352	finally
            //   189	246	352	finally
            //   246	249	352	finally
            //   249	293	296	java/lang/Exception
            //   297	301	304	java/lang/Exception
            //   316	320	304	java/lang/Exception
            //   323	349	352	finally
            //   353	356	352	finally
            //   356	358	296	java/lang/Exception
            //   358	386	352	finally
            //   389	397	296	java/lang/Exception
            //   399	417	296	java/lang/Exception
            //   424	459	296	java/lang/Exception }
        }
