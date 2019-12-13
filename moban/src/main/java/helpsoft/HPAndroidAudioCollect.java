package com.helpsoft;

import android.media.AudioRecord;
import android.media.audiofx.AcousticEchoCanceler;
import android.util.Log;
import java.nio.ByteBuffer;

public class HPAndroidAudioCollect implements Runnable {
    private AcousticEchoCanceler m_aec = null;

    private AudioRecord m_audio = null;

    private HPAndroidAudioCollectCallback m_audioCollectCallback = null;

    private int m_audioSize = 0;

    private ByteBuffer m_buffer = null;

    private boolean m_exitThread = false;

    private boolean m_exitThreaded = true;

    private Thread m_thread = null;

    public void closeCollect() { // Byte code:
        //   0: aload_0
        //   1: getfield m_aec : Landroid/media/audiofx/AcousticEchoCanceler;
        //   4: ifnull -> 28
        //   7: aload_0
        //   8: getfield m_aec : Landroid/media/audiofx/AcousticEchoCanceler;
        //   11: iconst_0
        //   12: invokevirtual setEnabled : (Z)I
        //   15: pop
        //   16: aload_0
        //   17: getfield m_aec : Landroid/media/audiofx/AcousticEchoCanceler;
        //   20: invokevirtual release : ()V
        //   23: aload_0
        //   24: aconst_null
        //   25: putfield m_aec : Landroid/media/audiofx/AcousticEchoCanceler;
        //   28: aload_0
        //   29: getfield m_audio : Landroid/media/AudioRecord;
        //   32: ifnull -> 88
        //   35: aload_0
        //   36: getfield m_audio : Landroid/media/AudioRecord;
        //   39: invokevirtual stop : ()V
        //   42: aload_0
        //   43: getfield m_audio : Landroid/media/AudioRecord;
        //   46: invokevirtual release : ()V
        //   49: aload_0
        //   50: aconst_null
        //   51: putfield m_audio : Landroid/media/AudioRecord;
        //   54: aload_0
        //   55: iconst_1
        //   56: putfield m_exitThread : Z
        //   59: aload_0
        //   60: getfield m_exitThreaded : Z
        //   63: istore_1
        //   64: iload_1
        //   65: ifne -> 102
        //   68: lconst_1
        //   69: invokestatic sleep : (J)V
        //   72: goto -> 59
        //   75: astore_2
        //   76: aload_2
        //   77: invokevirtual printStackTrace : ()V
        //   80: goto -> 59
        //   83: astore_2
        //   84: aload_2
        //   85: invokevirtual printStackTrace : ()V
        //   88: aload_0
        //   89: aconst_null
        //   90: putfield m_buffer : Ljava/nio/ByteBuffer;
        //   93: return
        //   94: astore_2
        //   95: aload_2
        //   96: invokevirtual printStackTrace : ()V
        //   99: goto -> 28
        //   102: aload_0
        //   103: aconst_null
        //   104: putfield m_thread : Ljava/lang/Thread;
        //   107: goto -> 88
        // Exception table:
        //   from	to	target	type
        //   0	28	94	java/lang/Exception
        //   28	59	83	java/lang/Exception
        //   59	64	83	java/lang/Exception
        //   68	72	75	java/lang/InterruptedException
        //   68	72	83	java/lang/Exception
        //   76	80	83	java/lang/Exception
        //   102	107	83	java/lang/Exception }

        public int openCollect(int paramInt1, int paramInt2, int paramInt3) {
            boolean bool = false;
            if (paramInt3 == 16) {
                paramInt3 = 2;
            } else {
                paramInt3 = 3;
            }
            try {
                this.m_audioSize = AudioRecord.getMinBufferSize(paramInt1, paramInt2, paramInt3);
                Log.d("m_audioSize", "" + this.m_audioSize);
                if (AcousticEchoCanceler.isAvailable() == true) {
                    this.m_audio = new AudioRecord(7, paramInt1, paramInt2, paramInt3, this.m_audioSize);
                    if (this.m_audio == null)
                        return -1;
                    paramInt3 = this.m_audio.getAudioSessionId();
                    paramInt2 = paramInt3;
                    if (paramInt3 > 0) {
                        this.m_aec = AcousticEchoCanceler.create(paramInt3);
                        this.m_aec.setEnabled(true);
                        paramInt2 = paramInt3;
                    }
                } else {
                    this.m_audio = new AudioRecord(7, paramInt1, paramInt2, paramInt3, this.m_audioSize);
                    paramInt2 = bool;
                    if (this.m_audio == null)
                        return -1;
                }
                this.m_thread = new Thread(this);
                if (this.m_thread == null) {
                    this.m_audio.release();
                    this.m_audio = null;
                    return -1;
                }
                if (paramInt1 == 8000)
                    this.m_audioSize = 320;
                this.m_buffer = ByteBuffer.allocate(this.m_audioSize);
                if (this.m_buffer == null) {
                    this.m_thread = null;
                    this.m_audio.release();
                    this.m_audio = null;
                    return -1;
                }
                this.m_exitThread = false;
                this.m_thread.start();
                this.m_audio.startRecording();
                this.m_exitThreaded = false;
                return paramInt2;
            } catch (Exception exception) {
                exception.printStackTrace();
                return -1;
            }
        }

        public void run() {
            int i = 0;
            while (true) {
                while (true)
                    break;
                if (this.m_audioCollectCallback != null) {
                    i += 20;
                    this.m_audioCollectCallback.onCollectAudio(this.m_buffer, this.m_audioSize, i);
                }
            }
        }

        public void setAudioCollectCallback(HPAndroidAudioCollectCallback paramHPAndroidAudioCollectCallback) { this.m_audioCollectCallback = paramHPAndroidAudioCollectCallback; }
    }
