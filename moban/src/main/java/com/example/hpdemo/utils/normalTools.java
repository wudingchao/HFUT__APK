package com.example.hpdemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;
import com.ytxt.logger.Logg;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class normalTools {
    public static int NETWORK_TYPE_MOBILE = 0;

    public static int NETWORK_TYPE_WIFI = 1;

    private static final String TAG = "normalTools";

    public static Toast toast;

    private normalTools instance = new normalTools();

    static  {
        NETWORK_TYPE_MOBILE = 2;
    }

    public static byte[] Bitmap2Bytes(Bitmap paramBitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap Bytes2Bimap(byte[] paramArrayOfbyte) { return (paramArrayOfbyte.length != 0) ? BitmapFactory.decodeByteArray(paramArrayOfbyte, 0, paramArrayOfbyte.length) : null; }

    public static String ListToString(List<Double[]> paramList) {
        StringBuffer stringBuffer = new StringBuffer();
        if (paramList.isEmpty())
            return "";
        for (int i = 0; i < paramList.size(); i++) {
            Double[] arrayOfDouble = paramList.get(i);
            stringBuffer.append(arrayOfDouble[0]);
            stringBuffer.append(",");
            stringBuffer.append(arrayOfDouble[1]);
            if (i != paramList.size() - 1)
                stringBuffer.append(",");
        }
        return stringBuffer.toString();
    }

    public static List<double[]> StringToList(String paramString) {
        ArrayList<double[]> arrayList = new ArrayList();
        if (!paramString.isEmpty()) {
            String[] arrayOfString = paramString.split(",");
            int i = 0;
            while (true) {
                if (i < arrayOfString.length) {
                    double[] arrayOfDouble = new double[2];
                    arrayOfDouble[0] = 1.0D;
                    arrayOfDouble[1] = 2.0D;
                    arrayOfDouble[0] = Double.valueOf(arrayOfString[i]).doubleValue();
                    arrayOfDouble[1] = Double.valueOf(arrayOfString[i + 1]).doubleValue();
                    arrayList.add(arrayOfDouble);
                    i += 2;
                    continue;
                }
                return (List<double[]>)arrayList;
            }
        }
        return (List<double[]>)arrayList;
    }

    public static void copyFileUsingFileChannels(File paramFile1, File paramFile2) {
        FileChannel fileChannel4 = null;
        FileChannel fileChannel2 = null;
        FileChannel fileChannel6 = null;
        FileChannel fileChannel5 = null;
        FileChannel fileChannel1 = fileChannel5;
        FileChannel fileChannel3 = fileChannel6;
        try {
            FileChannel fileChannel7 = (new FileInputStream(paramFile1)).getChannel();
            fileChannel1 = fileChannel5;
            fileChannel2 = fileChannel7;
            fileChannel3 = fileChannel6;
            fileChannel4 = fileChannel7;
            FileChannel fileChannel8 = (new FileOutputStream(paramFile2)).getChannel();
            fileChannel1 = fileChannel8;
            fileChannel2 = fileChannel7;
            fileChannel3 = fileChannel8;
            fileChannel4 = fileChannel7;
            fileChannel8.transferFrom(fileChannel7, 0L, fileChannel7.size());
        } catch (IOException iOException) {
            fileChannel3 = fileChannel1;
            fileChannel4 = fileChannel2;
            iOException.printStackTrace();
            FileChannel fileChannel8 = fileChannel1;
            FileChannel fileChannel7 = fileChannel2;
        } finally {
            try {
                fileChannel4.close();
                fileChannel3.close();
            } catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
    }

    public static Uri createImageFile() {
        Object object = (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(new Date());
        String str = "JPEG_" + object + "_";
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!file.exists())
            file.mkdir();
        object = null;
        try {
            File file1 = File.createTempFile(str, ".jpg", file);
            object = file1;
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        return Uri.fromFile((File)object);
    }

    public static byte[] f2b(String paramString) {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(paramString));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (int i = bufferedInputStream.read(); i != -1; i = bufferedInputStream.read())
                byteArrayOutputStream.write(i);
            bufferedInputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static byte[] filePath2Bytes(String paramString) {
        Bitmap bitmap = BitmapFactory.decodeFile(paramString);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static String getCurrentData() { return (new SimpleDateFormat("yyyy/MM/dd")).format(new Date()); }

    public static String getCurrentTime() { return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()); }

    public static String getCurrentTime(long paramLong) { return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date(paramLong)); }

    public static String getErrorInfoById(int paramInt) {
        String str = "";
        return (paramInt == -3) ? "������" : ((paramInt == -4) ? "������" : ((paramInt == -5) ? "����������" : str));
    }

    public static int getNetWorkType(Context paramContext) {
        byte b = 0;
        Object object = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
        int i = b;
        if (object != null) {
            i = b;
            if (object.isConnected()) {
                object = object.getTypeName();
                if (object.equals("WIFI"))
                    return NETWORK_TYPE_WIFI;
            } else {
                return i;
            }
        } else {
            return i;
        }
        i = b;
        return object.equals("MOBILE") ? NETWORK_TYPE_MOBILE : i;
    }

    public static boolean isCameraCanUse() {
        boolean bool = true;
        Camera camera = null;
        try {
            Camera camera1 = Camera.open(0);
            camera = camera1;
            camera1.setDisplayOrientation(90);
            camera = camera1;
        } catch (Exception exception) {
            bool = false;
            exception.printStackTrace();
            Logg.e("camera", "no_use_camera() false: " + exception.getMessage());
        }
        if (bool)
            camera.release();
        return bool;
    }

    public static boolean isIP(String paramString) {
        boolean bool2 = false;
        boolean bool1 = bool2;
        if (paramString.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            String[] arrayOfString = paramString.split("\\.");
            bool1 = bool2;
            if (Integer.parseInt(arrayOfString[0]) < 255) {
                bool1 = bool2;
                if (Integer.parseInt(arrayOfString[1]) < 255) {
                    bool1 = bool2;
                    if (Integer.parseInt(arrayOfString[2]) < 255) {
                        bool1 = bool2;
                        if (Integer.parseInt(arrayOfString[3]) < 255)
                            bool1 = true;
                    }
                }
            }
        }
        return bool1;
    }

    public static int[] randomArray(int paramInt1, int paramInt2, int paramInt3) {
        int i = paramInt2 - paramInt1 + 1;
        if (paramInt2 < paramInt1 || paramInt3 > i)
            return null;
        int[] arrayOfInt2 = new int[i];
        for (paramInt2 = paramInt1; paramInt2 < paramInt1 + i; paramInt2++)
            arrayOfInt2[paramInt2 - paramInt1] = paramInt2;
        int[] arrayOfInt1 = new int[paramInt3];
        Random random = new Random();
        paramInt1 = 0;
        paramInt2 = i;
        while (true) {
            int[] arrayOfInt = arrayOfInt1;
            if (paramInt1 < arrayOfInt1.length) {
                i = random.nextInt();
                paramInt3 = paramInt2 - 1;
                paramInt2 = Math.abs(i % paramInt2);
                arrayOfInt1[paramInt1] = arrayOfInt2[paramInt2];
                arrayOfInt2[paramInt2] = arrayOfInt2[paramInt3];
                paramInt1++;
                paramInt2 = paramInt3;
                continue;
            }
            return arrayOfInt;
        }
    }

    public static File scal(String paramString) { // Byte code:
        //   0: new java/io/File
        //   3: dup
        //   4: aload_0
        //   5: invokespecial <init> : (Ljava/lang/String;)V
        //   8: astore #7
        //   10: aload #7
        //   12: invokevirtual length : ()J
        //   15: lstore #5
        //   17: lload #5
        //   19: ldc2_w 512000
        //   22: lcmp
        //   23: iflt -> 203
        //   26: new android/graphics/BitmapFactory$Options
        //   29: dup
        //   30: invokespecial <init> : ()V
        //   33: astore #7
        //   35: aload #7
        //   37: iconst_1
        //   38: putfield inJustDecodeBounds : Z
        //   41: aload_0
        //   42: aload #7
        //   44: invokestatic decodeFile : (Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
        //   47: pop
        //   48: aload #7
        //   50: getfield outHeight : I
        //   53: istore_3
        //   54: aload #7
        //   56: getfield outWidth : I
        //   59: istore #4
        //   61: lload #5
        //   63: l2f
        //   64: ldc_w 512000.0
        //   67: fdiv
        //   68: f2d
        //   69: invokestatic sqrt : (D)D
        //   72: dstore_1
        //   73: aload #7
        //   75: iload_3
        //   76: i2d
        //   77: dload_1
        //   78: ddiv
        //   79: d2i
        //   80: putfield outHeight : I
        //   83: aload #7
        //   85: iload #4
        //   87: i2d
        //   88: dload_1
        //   89: ddiv
        //   90: d2i
        //   91: putfield outWidth : I
        //   94: aload #7
        //   96: ldc2_w 0.5
        //   99: dload_1
        //   100: dadd
        //   101: d2i
        //   102: putfield inSampleSize : I
        //   105: aload #7
        //   107: iconst_0
        //   108: putfield inJustDecodeBounds : Z
        //   111: aload_0
        //   112: aload #7
        //   114: invokestatic decodeFile : (Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
        //   117: astore #8
        //   119: new java/io/File
        //   122: dup
        //   123: invokestatic createImageFile : ()Landroid/net/Uri;
        //   126: invokevirtual getPath : ()Ljava/lang/String;
        //   129: invokespecial <init> : (Ljava/lang/String;)V
        //   132: astore #7
        //   134: new java/io/FileOutputStream
        //   137: dup
        //   138: aload #7
        //   140: invokespecial <init> : (Ljava/io/File;)V
        //   143: astore_0
        //   144: aload #8
        //   146: getstatic android/graphics/Bitmap$CompressFormat.JPEG : Landroid/graphics/Bitmap$CompressFormat;
        //   149: bipush #50
        //   151: aload_0
        //   152: invokevirtual compress : (Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
        //   155: pop
        //   156: aload_0
        //   157: invokevirtual close : ()V
        //   160: ldc 'normalTools'
        //   162: new java/lang/StringBuilder
        //   165: dup
        //   166: invokespecial <init> : ()V
        //   169: ldc_w 'outputFile.length():'
        //   172: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   175: aload #7
        //   177: invokevirtual length : ()J
        //   180: invokevirtual append : (J)Ljava/lang/StringBuilder;
        //   183: invokevirtual toString : ()Ljava/lang/String;
        //   186: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)I
        //   189: pop
        //   190: aload #8
        //   192: invokevirtual isRecycled : ()Z
        //   195: ifne -> 214
        //   198: aload #8
        //   200: invokevirtual recycle : ()V
        //   203: aload #7
        //   205: areturn
        //   206: astore_0
        //   207: aload_0
        //   208: invokevirtual printStackTrace : ()V
        //   211: goto -> 160
        //   214: new java/io/File
        //   217: dup
        //   218: invokestatic createImageFile : ()Landroid/net/Uri;
        //   221: invokevirtual getPath : ()Ljava/lang/String;
        //   224: invokespecial <init> : (Ljava/lang/String;)V
        //   227: astore_0
        //   228: aload #7
        //   230: aload_0
        //   231: invokestatic copyFileUsingFileChannels : (Ljava/io/File;Ljava/io/File;)V
        //   234: aload_0
        //   235: areturn
        //   236: astore_0
        //   237: goto -> 207
        // Exception table:
        //   from	to	target	type
        //   134	144	206	java/io/IOException
        //   144	160	236	java/io/IOException }

        public static boolean sendStreamToServer(int paramInt1, int paramInt2, int paramInt3, String paramString, int paramInt4, boolean paramBoolean, FileUploadOrDownloadProgressListener paramFileUploadOrDownloadProgressListener) { // Byte code:
            //   0: aconst_null
            //   1: astore #12
            //   3: iload #5
            //   5: iconst_1
            //   6: if_icmpne -> 645
            //   9: aload_3
            //   10: invokestatic scal : (Ljava/lang/String;)Ljava/io/File;
            //   13: invokevirtual getAbsolutePath : ()Ljava/lang/String;
            //   16: astore_3
            //   17: new java/io/BufferedInputStream
            //   20: dup
            //   21: new java/io/FileInputStream
            //   24: dup
            //   25: aload_3
            //   26: invokespecial <init> : (Ljava/lang/String;)V
            //   29: invokespecial <init> : (Ljava/io/InputStream;)V
            //   32: astore_3
            //   33: new java/io/ByteArrayOutputStream
            //   36: dup
            //   37: invokespecial <init> : ()V
            //   40: astore #13
            //   42: aload_3
            //   43: invokevirtual read : ()I
            //   46: istore #8
            //   48: iload #8
            //   50: iconst_m1
            //   51: if_icmpeq -> 70
            //   54: aload #13
            //   56: iload #8
            //   58: invokevirtual write : (I)V
            //   61: aload_3
            //   62: invokevirtual read : ()I
            //   65: istore #8
            //   67: goto -> 48
            //   70: aload_3
            //   71: invokevirtual close : ()V
            //   74: aload #13
            //   76: invokevirtual toByteArray : ()[B
            //   79: astore_3
            //   80: aload_3
            //   81: arraylength
            //   82: ldc_w 65536
            //   85: idiv
            //   86: istore #9
            //   88: iconst_0
            //   89: istore #11
            //   91: iconst_0
            //   92: istore #5
            //   94: aload_3
            //   95: arraylength
            //   96: ldc_w 65536
            //   99: irem
            //   100: ifeq -> 384
            //   103: iload #9
            //   105: iconst_1
            //   106: iadd
            //   107: istore #9
            //   109: ldc 'normalTools'
            //   111: new java/lang/StringBuilder
            //   114: dup
            //   115: invokespecial <init> : ()V
            //   118: ldc_w 'count:'
            //   121: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   124: iload #9
            //   126: invokevirtual append : (I)Ljava/lang/StringBuilder;
            //   129: invokevirtual toString : ()Ljava/lang/String;
            //   132: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)I
            //   135: pop
            //   136: iconst_0
            //   137: istore #8
            //   139: iload #8
            //   141: iload #9
            //   143: if_icmpge -> 642
            //   146: ldc2_w 200
            //   149: invokestatic sleep : (J)V
            //   152: iload #8
            //   154: iload #9
            //   156: iconst_1
            //   157: isub
            //   158: if_icmpne -> 276
            //   161: aload_3
            //   162: ldc_w 65536
            //   165: iload #8
            //   167: imul
            //   168: aload_3
            //   169: arraylength
            //   170: invokestatic copyOfRange : ([BII)[B
            //   173: astore #12
            //   175: aload #12
            //   177: arraylength
            //   178: istore #10
            //   180: iload_1
            //   181: aload #12
            //   183: iload #10
            //   185: iload #4
            //   187: iload_2
            //   188: iconst_0
            //   189: invokestatic sendStreamData : (I[BIIII)Z
            //   192: istore #5
            //   194: iload_1
            //   195: aload #12
            //   197: iload #10
            //   199: iload #4
            //   201: iload_2
            //   202: iconst_2
            //   203: invokestatic sendStreamData : (I[BIIII)Z
            //   206: pop
            //   207: ldc 'normalTools'
            //   209: new java/lang/StringBuilder
            //   212: dup
            //   213: invokespecial <init> : ()V
            //   216: ldc_w 'isSendCorrect:'
            //   219: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   222: iload #5
            //   224: invokevirtual append : (Z)Ljava/lang/StringBuilder;
            //   227: invokevirtual toString : ()Ljava/lang/String;
            //   230: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)I
            //   233: pop
            //   234: iload #5
            //   236: ifeq -> 276
            //   239: aload #6
            //   241: ifnull -> 253
            //   244: aload #6
            //   246: iload_0
            //   247: fconst_1
            //   248: invokeinterface transferred : (IF)V
            //   253: iconst_1
            //   254: ireturn
            //   255: astore_3
            //   256: aload_3
            //   257: invokevirtual printStackTrace : ()V
            //   260: aload #12
            //   262: astore_3
            //   263: goto -> 80
            //   266: astore #12
            //   268: aload #12
            //   270: invokevirtual printStackTrace : ()V
            //   273: goto -> 152
            //   276: aload_3
            //   277: ldc_w 65536
            //   280: iload #8
            //   282: imul
            //   283: ldc_w 65536
            //   286: iload #8
            //   288: iconst_1
            //   289: iadd
            //   290: imul
            //   291: invokestatic copyOfRange : ([BII)[B
            //   294: astore #12
            //   296: iload_1
            //   297: aload #12
            //   299: aload #12
            //   301: arraylength
            //   302: iload #4
            //   304: iload_2
            //   305: iconst_0
            //   306: invokestatic sendStreamData : (I[BIIII)Z
            //   309: istore #5
            //   311: ldc 'normalTools'
            //   313: new java/lang/StringBuilder
            //   316: dup
            //   317: invokespecial <init> : ()V
            //   320: ldc_w 'isSendCorrect:'
            //   323: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   326: iload #5
            //   328: invokevirtual append : (Z)Ljava/lang/StringBuilder;
            //   331: invokevirtual toString : ()Ljava/lang/String;
            //   334: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)I
            //   337: pop
            //   338: iload #5
            //   340: ifne -> 345
            //   343: iconst_0
            //   344: ireturn
            //   345: ldc_w 65536
            //   348: iload #8
            //   350: iconst_1
            //   351: iadd
            //   352: imul
            //   353: i2f
            //   354: aload_3
            //   355: arraylength
            //   356: i2f
            //   357: fdiv
            //   358: fstore #7
            //   360: aload #6
            //   362: ifnull -> 375
            //   365: aload #6
            //   367: iload_0
            //   368: fload #7
            //   370: invokeinterface transferred : (IF)V
            //   375: iload #8
            //   377: iconst_1
            //   378: iadd
            //   379: istore #8
            //   381: goto -> 139
            //   384: ldc 'normalTools'
            //   386: new java/lang/StringBuilder
            //   389: dup
            //   390: invokespecial <init> : ()V
            //   393: ldc_w 'count:'
            //   396: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   399: iload #9
            //   401: invokevirtual append : (I)Ljava/lang/StringBuilder;
            //   404: invokevirtual toString : ()Ljava/lang/String;
            //   407: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)I
            //   410: pop
            //   411: iconst_0
            //   412: istore #8
            //   414: iload #11
            //   416: istore #5
            //   418: iload #8
            //   420: iload #9
            //   422: if_icmpge -> 642
            //   425: ldc2_w 200
            //   428: invokestatic sleep : (J)V
            //   431: aload_3
            //   432: ldc_w 65536
            //   435: iload #8
            //   437: imul
            //   438: ldc_w 65536
            //   441: iload #8
            //   443: iconst_1
            //   444: iadd
            //   445: imul
            //   446: invokestatic copyOfRange : ([BII)[B
            //   449: astore #12
            //   451: iload #8
            //   453: iload #9
            //   455: iconst_1
            //   456: isub
            //   457: if_icmpne -> 554
            //   460: aload #12
            //   462: arraylength
            //   463: istore #8
            //   465: iload_1
            //   466: aload #12
            //   468: iload #8
            //   470: iload #4
            //   472: iload_2
            //   473: iconst_0
            //   474: invokestatic sendStreamData : (I[BIIII)Z
            //   477: istore #11
            //   479: iload_1
            //   480: aload #12
            //   482: iload #8
            //   484: iload #4
            //   486: iload_2
            //   487: iconst_2
            //   488: invokestatic sendStreamData : (I[BIIII)Z
            //   491: pop
            //   492: ldc 'normalTools'
            //   494: new java/lang/StringBuilder
            //   497: dup
            //   498: invokespecial <init> : ()V
            //   501: ldc_w 'isSendCorrect:'
            //   504: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   507: iload #11
            //   509: invokevirtual append : (Z)Ljava/lang/StringBuilder;
            //   512: invokevirtual toString : ()Ljava/lang/String;
            //   515: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)I
            //   518: pop
            //   519: iload #11
            //   521: istore #5
            //   523: iload #11
            //   525: ifeq -> 642
            //   528: aload #6
            //   530: ifnull -> 542
            //   533: aload #6
            //   535: iload_0
            //   536: fconst_1
            //   537: invokeinterface transferred : (IF)V
            //   542: iconst_1
            //   543: ireturn
            //   544: astore #12
            //   546: aload #12
            //   548: invokevirtual printStackTrace : ()V
            //   551: goto -> 431
            //   554: iload_1
            //   555: aload #12
            //   557: aload #12
            //   559: arraylength
            //   560: iload #4
            //   562: iload_2
            //   563: iconst_0
            //   564: invokestatic sendStreamData : (I[BIIII)Z
            //   567: istore #5
            //   569: ldc 'normalTools'
            //   571: new java/lang/StringBuilder
            //   574: dup
            //   575: invokespecial <init> : ()V
            //   578: ldc_w 'isSendCorrect:'
            //   581: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   584: iload #5
            //   586: invokevirtual append : (Z)Ljava/lang/StringBuilder;
            //   589: invokevirtual toString : ()Ljava/lang/String;
            //   592: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)I
            //   595: pop
            //   596: iload #5
            //   598: ifne -> 603
            //   601: iconst_0
            //   602: ireturn
            //   603: ldc_w 65536
            //   606: iload #8
            //   608: iconst_1
            //   609: iadd
            //   610: imul
            //   611: i2f
            //   612: aload_3
            //   613: arraylength
            //   614: i2f
            //   615: fdiv
            //   616: fstore #7
            //   618: aload #6
            //   620: ifnull -> 633
            //   623: aload #6
            //   625: iload_0
            //   626: fload #7
            //   628: invokeinterface transferred : (IF)V
            //   633: iload #8
            //   635: iconst_1
            //   636: iadd
            //   637: istore #8
            //   639: goto -> 418
            //   642: iload #5
            //   644: ireturn
            //   645: goto -> 17
            // Exception table:
            //   from	to	target	type
            //   9	17	255	java/lang/Exception
            //   17	48	255	java/lang/Exception
            //   54	67	255	java/lang/Exception
            //   70	80	255	java/lang/Exception
            //   146	152	266	java/lang/InterruptedException
            //   425	431	544	java/lang/InterruptedException }

            public static boolean sendStreamToServer(int paramInt1, int paramInt2, String paramString, int paramInt3, boolean paramBoolean) { // Byte code:
                //   0: iconst_0
                //   1: newarray byte
                //   3: astore #11
                //   5: ldc ''
                //   7: astore #9
                //   9: ldc ''
                //   11: astore #10
                //   13: iload #4
                //   15: iconst_1
                //   16: if_icmpne -> 291
                //   19: aload_2
                //   20: invokestatic scal : (Ljava/lang/String;)Ljava/io/File;
                //   23: invokevirtual getAbsolutePath : ()Ljava/lang/String;
                //   26: astore_2
                //   27: aload_2
                //   28: astore #10
                //   30: aload #10
                //   32: astore_2
                //   33: aload #10
                //   35: astore #9
                //   37: aload #10
                //   39: ldc ''
                //   41: invokevirtual equals : (Ljava/lang/Object;)Z
                //   44: ifeq -> 294
                //   47: iconst_0
                //   48: ireturn
                //   49: astore #12
                //   51: aload #9
                //   53: astore_2
                //   54: aload #12
                //   56: invokevirtual printStackTrace : ()V
                //   59: goto -> 30
                //   62: astore #9
                //   64: aload #9
                //   66: invokevirtual printStackTrace : ()V
                //   69: aload_2
                //   70: astore #9
                //   72: aload #11
                //   74: astore_2
                //   75: aload_2
                //   76: arraylength
                //   77: ldc_w 65536
                //   80: idiv
                //   81: istore #6
                //   83: iconst_0
                //   84: istore #8
                //   86: iconst_0
                //   87: istore #4
                //   89: ldc 'normalTools'
                //   91: new java/lang/StringBuilder
                //   94: dup
                //   95: invokespecial <init> : ()V
                //   98: ldc_w 'first count:'
                //   101: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   104: iload #6
                //   106: invokevirtual append : (I)Ljava/lang/StringBuilder;
                //   109: ldc_w ';path='
                //   112: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   115: aload #9
                //   117: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   120: invokevirtual toString : ()Ljava/lang/String;
                //   123: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)V
                //   126: aload_2
                //   127: arraylength
                //   128: ldc_w 65536
                //   131: irem
                //   132: ifeq -> 502
                //   135: iload #6
                //   137: iconst_1
                //   138: iadd
                //   139: istore #6
                //   141: ldc 'normalTools'
                //   143: new java/lang/StringBuilder
                //   146: dup
                //   147: invokespecial <init> : ()V
                //   150: ldc_w '!=0 count:'
                //   153: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   156: iload #6
                //   158: invokevirtual append : (I)Ljava/lang/StringBuilder;
                //   161: invokevirtual toString : ()Ljava/lang/String;
                //   164: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)V
                //   167: iconst_0
                //   168: istore #5
                //   170: iload #5
                //   172: iload #6
                //   174: if_icmpge -> 758
                //   177: ldc2_w 200
                //   180: invokestatic sleep : (J)V
                //   183: iload #5
                //   185: iload #6
                //   187: iconst_1
                //   188: isub
                //   189: if_icmpne -> 400
                //   192: aload_2
                //   193: ldc_w 65536
                //   196: iload #5
                //   198: imul
                //   199: aload_2
                //   200: arraylength
                //   201: invokestatic copyOfRange : ([BII)[B
                //   204: astore_2
                //   205: aload_2
                //   206: arraylength
                //   207: istore #6
                //   209: iload_0
                //   210: aload_2
                //   211: iload #6
                //   213: iload_3
                //   214: iload_1
                //   215: iconst_0
                //   216: invokestatic sendStreamData : (I[BIIII)Z
                //   219: istore #8
                //   221: iload_0
                //   222: aload_2
                //   223: iload #6
                //   225: iload_3
                //   226: iload_1
                //   227: iconst_2
                //   228: invokestatic sendStreamData : (I[BIIII)Z
                //   231: pop
                //   232: ldc 'normalTools'
                //   234: new java/lang/StringBuilder
                //   237: dup
                //   238: invokespecial <init> : ()V
                //   241: ldc_w 'isSendCorrect:'
                //   244: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   247: iload #8
                //   249: invokevirtual append : (Z)Ljava/lang/StringBuilder;
                //   252: ldc_w ', count = '
                //   255: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   258: iload #5
                //   260: invokevirtual append : (I)Ljava/lang/StringBuilder;
                //   263: ldc_w ', length = '
                //   266: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   269: iload #6
                //   271: invokevirtual append : (I)Ljava/lang/StringBuilder;
                //   274: invokevirtual toString : ()Ljava/lang/String;
                //   277: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)V
                //   280: iload #8
                //   282: istore #4
                //   284: iload #8
                //   286: ifeq -> 758
                //   289: iconst_1
                //   290: ireturn
                //   291: aload_2
                //   292: astore #9
                //   294: aload #9
                //   296: astore_2
                //   297: new java/io/BufferedInputStream
                //   300: dup
                //   301: new java/io/FileInputStream
                //   304: dup
                //   305: aload #9
                //   307: invokespecial <init> : (Ljava/lang/String;)V
                //   310: invokespecial <init> : (Ljava/io/InputStream;)V
                //   313: astore #10
                //   315: aload #9
                //   317: astore_2
                //   318: new java/io/ByteArrayOutputStream
                //   321: dup
                //   322: invokespecial <init> : ()V
                //   325: astore #12
                //   327: aload #9
                //   329: astore_2
                //   330: aload #10
                //   332: invokevirtual read : ()I
                //   335: istore #5
                //   337: iload #5
                //   339: iconst_m1
                //   340: if_icmpeq -> 366
                //   343: aload #9
                //   345: astore_2
                //   346: aload #12
                //   348: iload #5
                //   350: invokevirtual write : (I)V
                //   353: aload #9
                //   355: astore_2
                //   356: aload #10
                //   358: invokevirtual read : ()I
                //   361: istore #5
                //   363: goto -> 337
                //   366: aload #9
                //   368: astore_2
                //   369: aload #10
                //   371: invokevirtual close : ()V
                //   374: aload #9
                //   376: astore_2
                //   377: aload #12
                //   379: invokevirtual toByteArray : ()[B
                //   382: astore #10
                //   384: aload #10
                //   386: astore_2
                //   387: goto -> 75
                //   390: astore #9
                //   392: aload #9
                //   394: invokevirtual printStackTrace : ()V
                //   397: goto -> 183
                //   400: aload_2
                //   401: ldc_w 65536
                //   404: iload #5
                //   406: imul
                //   407: ldc_w 65536
                //   410: iload #5
                //   412: iconst_1
                //   413: iadd
                //   414: imul
                //   415: invokestatic copyOfRange : ([BII)[B
                //   418: astore #9
                //   420: aload #9
                //   422: arraylength
                //   423: istore #7
                //   425: iload_0
                //   426: aload #9
                //   428: iload #7
                //   430: iload_3
                //   431: iload_1
                //   432: iconst_0
                //   433: invokestatic sendStreamData : (I[BIIII)Z
                //   436: istore #4
                //   438: ldc 'normalTools'
                //   440: new java/lang/StringBuilder
                //   443: dup
                //   444: invokespecial <init> : ()V
                //   447: ldc_w 'isSendCorrect:'
                //   450: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   453: iload #4
                //   455: invokevirtual append : (Z)Ljava/lang/StringBuilder;
                //   458: ldc_w ', count = '
                //   461: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   464: iload #5
                //   466: invokevirtual append : (I)Ljava/lang/StringBuilder;
                //   469: ldc_w ', length = '
                //   472: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   475: iload #7
                //   477: invokevirtual append : (I)Ljava/lang/StringBuilder;
                //   480: invokevirtual toString : ()Ljava/lang/String;
                //   483: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)V
                //   486: iload #4
                //   488: ifne -> 493
                //   491: iconst_0
                //   492: ireturn
                //   493: iload #5
                //   495: iconst_1
                //   496: iadd
                //   497: istore #5
                //   499: goto -> 170
                //   502: ldc 'normalTools'
                //   504: new java/lang/StringBuilder
                //   507: dup
                //   508: invokespecial <init> : ()V
                //   511: ldc_w '==0 count:'
                //   514: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   517: iload #6
                //   519: invokevirtual append : (I)Ljava/lang/StringBuilder;
                //   522: invokevirtual toString : ()Ljava/lang/String;
                //   525: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)V
                //   528: iconst_0
                //   529: istore #5
                //   531: iload #8
                //   533: istore #4
                //   535: iload #5
                //   537: iload #6
                //   539: if_icmpge -> 758
                //   542: ldc2_w 200
                //   545: invokestatic sleep : (J)V
                //   548: aload_2
                //   549: ldc_w 65536
                //   552: iload #5
                //   554: imul
                //   555: ldc_w 65536
                //   558: iload #5
                //   560: iconst_1
                //   561: iadd
                //   562: imul
                //   563: invokestatic copyOfRange : ([BII)[B
                //   566: astore #9
                //   568: iload #5
                //   570: iload #6
                //   572: iconst_1
                //   573: isub
                //   574: if_icmpne -> 676
                //   577: aload #9
                //   579: arraylength
                //   580: istore #6
                //   582: iload_0
                //   583: aload #9
                //   585: iload #6
                //   587: iload_3
                //   588: iload_1
                //   589: iconst_0
                //   590: invokestatic sendStreamData : (I[BIIII)Z
                //   593: istore #8
                //   595: iload_0
                //   596: aload #9
                //   598: iload #6
                //   600: iload_3
                //   601: iload_1
                //   602: iconst_2
                //   603: invokestatic sendStreamData : (I[BIIII)Z
                //   606: pop
                //   607: ldc 'normalTools'
                //   609: new java/lang/StringBuilder
                //   612: dup
                //   613: invokespecial <init> : ()V
                //   616: ldc_w 'isSendCorrect:'
                //   619: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   622: iload #8
                //   624: invokevirtual append : (Z)Ljava/lang/StringBuilder;
                //   627: ldc_w ', count = '
                //   630: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   633: iload #5
                //   635: invokevirtual append : (I)Ljava/lang/StringBuilder;
                //   638: ldc_w ', length = '
                //   641: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   644: iload #6
                //   646: invokevirtual append : (I)Ljava/lang/StringBuilder;
                //   649: invokevirtual toString : ()Ljava/lang/String;
                //   652: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)V
                //   655: iload #8
                //   657: istore #4
                //   659: iload #8
                //   661: ifeq -> 758
                //   664: iconst_1
                //   665: ireturn
                //   666: astore #9
                //   668: aload #9
                //   670: invokevirtual printStackTrace : ()V
                //   673: goto -> 548
                //   676: aload #9
                //   678: arraylength
                //   679: istore #7
                //   681: iload_0
                //   682: aload #9
                //   684: iload #7
                //   686: iload_3
                //   687: iload_1
                //   688: iconst_0
                //   689: invokestatic sendStreamData : (I[BIIII)Z
                //   692: istore #4
                //   694: ldc 'normalTools'
                //   696: new java/lang/StringBuilder
                //   699: dup
                //   700: invokespecial <init> : ()V
                //   703: ldc_w 'isSendCorrect:'
                //   706: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   709: iload #4
                //   711: invokevirtual append : (Z)Ljava/lang/StringBuilder;
                //   714: ldc_w ', count = '
                //   717: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   720: iload #5
                //   722: invokevirtual append : (I)Ljava/lang/StringBuilder;
                //   725: ldc_w ', length = '
                //   728: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   731: iload #7
                //   733: invokevirtual append : (I)Ljava/lang/StringBuilder;
                //   736: invokevirtual toString : ()Ljava/lang/String;
                //   739: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)V
                //   742: iload #4
                //   744: ifne -> 749
                //   747: iconst_0
                //   748: ireturn
                //   749: iload #5
                //   751: iconst_1
                //   752: iadd
                //   753: istore #5
                //   755: goto -> 535
                //   758: iload #4
                //   760: ireturn
                // Exception table:
                //   from	to	target	type
                //   19	27	49	java/lang/Exception
                //   37	47	62	java/lang/Exception
                //   54	59	62	java/lang/Exception
                //   177	183	390	java/lang/InterruptedException
                //   297	315	62	java/lang/Exception
                //   318	327	62	java/lang/Exception
                //   330	337	62	java/lang/Exception
                //   346	353	62	java/lang/Exception
                //   356	363	62	java/lang/Exception
                //   369	374	62	java/lang/Exception
                //   377	384	62	java/lang/Exception
                //   542	548	666	java/lang/InterruptedException }

                public static void showToast(Context paramContext, String paramString) {
                    if (toast == null && Thread.currentThread() != Looper.getMainLooper().getThread()) {
                        Toast.makeText(paramContext, paramString, 0).show();
                        return;
                    }
                    if (toast == null) {
                        toast = Toast.makeText(paramContext, paramString, 0);
                    } else {
                        toast.setText(paramString);
                    }
                    toast.show();
                }

                public static double str2Double(String paramString) {
                    try {
                        return Double.valueOf(paramString).doubleValue();
                    } catch (NumberFormatException numberFormatException) {
                        numberFormatException.printStackTrace();
                        return -1.0D;
                    }
                }

                public static int str2Int(String paramString) {
                    try {
                        return Integer.valueOf(paramString).intValue();
                    } catch (NumberFormatException numberFormatException) {
                        numberFormatException.printStackTrace();
                        return 0;
                    }
                }

                public static int str2IntNot(String paramString) {
                    try {
                        return Integer.valueOf(paramString).intValue();
                    } catch (NumberFormatException numberFormatException) {
                        numberFormatException.printStackTrace();
                        return -1;
                    }
                }

                public normalTools getInstance() { return (this.instance != null) ? this.instance : new normalTools(); }

                public static interface FileUploadOrDownloadProgressListener {
                    void transferred(int param1Int, float param1Float);
                }
            }
