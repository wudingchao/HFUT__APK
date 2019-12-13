.class public Lcom/stub/StubApp;
.super Landroid/app/Application;
.source "SourceFile"


# static fields
.field private static loadFromLib:Z

.field public static strEntryApplication:Ljava/lang/String;

.field private static ᵢˋ:Landroid/app/Application;

.field private static ᵢˎ:Landroid/app/Application;

.field private static ᵢˏ:Ljava/lang/String;

.field private static ᵢˑ:Landroid/content/Context;


# direct methods
.method static constructor <clinit>()V
    .locals 2

    .prologue
    const/4 v1, 0x0

    .line 24
    sput-object v1, Lcom/stub/StubApp;->ᵢˋ:Landroid/app/Application;

    .line 25
    const-string v0, "com.qihoo360.crypt.entryRunApplication"

    sput-object v0, Lcom/stub/StubApp;->strEntryApplication:Ljava/lang/String;

    .line 26
    sput-object v1, Lcom/stub/StubApp;->ᵢˎ:Landroid/app/Application;

    .line 27
    const-string v0, "libjiagu"

    sput-object v0, Lcom/stub/StubApp;->ᵢˏ:Ljava/lang/String;

    .line 28
    const/4 v0, 0x0

    sput-boolean v0, Lcom/stub/StubApp;->loadFromLib:Z

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .prologue
    .line 23
    invoke-direct {p0}, Landroid/app/Application;-><init>()V

    return-void
.end method

.method public static getAppContext()Landroid/content/Context;
    .locals 1

    .prologue
    .line 33
    sget-object v0, Lcom/stub/StubApp;->ᵢˑ:Landroid/content/Context;

    return-object v0
.end method

.method public static native interface11(I)V
.end method

.method public static native interface12(Ldalvik/system/DexFile;)Ljava/util/Enumeration;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ldalvik/system/DexFile;",
            ")",
            "Ljava/util/Enumeration",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end method

.method public static native interface5(Landroid/app/Application;)V
.end method

.method public static native interface6(Ljava/lang/String;)Ljava/lang/String;
.end method

.method public static native interface7(Landroid/app/Application;Landroid/content/Context;)Z
.end method

.method public static native interface8(Landroid/app/Application;Landroid/content/Context;)Z
.end method

.method public static native mark(Landroid/location/LocationManager;Ljava/lang/String;)Landroid/location/Location;
.end method

.method public static native mark()V
.end method

.method public static native mark(Landroid/location/Location;)V
.end method

.method public static native declared-synchronized n010333(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
.end method

.method public static native n0110()V
.end method

.method public static native n0111()I
.end method

.method public static native n01110(I)V
.end method

.method public static native n01111(I)I
.end method

.method public static native n011110(II)V
.end method

.method public static native n011111(II)Z
.end method

.method public static native n0111110(III)V
.end method

.method public static native n01111110(IIIZ)V
.end method

.method public static native n011111110(IIIII)V
.end method

.method public static native n011111111110(IIIZIIFF)V
.end method

.method public static native n011111122220(IIIIDDDD)V
.end method

.method public static native n0111111313(IIZZLjava/lang/Object;I)Ljava/lang/Object;
.end method

.method public static native n0111113(III)Ljava/lang/Object;
.end method

.method public static native n011112(II)D
.end method

.method public static native n0111121111110(IIJIIIFFF)V
.end method

.method public static native n011113(II)Ljava/lang/Object;
.end method

.method public static native n0111130(IILjava/lang/Object;)V
.end method

.method public static native n01111310(IILjava/lang/Object;I)V
.end method

.method public static native n01112(I)D
.end method

.method public static native n0111222(FDD)D
.end method

.method public static native n01113(I)Ljava/lang/Object;
.end method

.method public static native n011130(ILjava/lang/Object;)V
.end method

.method public static native n011131(ILjava/lang/Object;)Z
.end method

.method public static native n01113110(ILjava/lang/Object;II)V
.end method

.method public static native n011131111(ILjava/lang/Object;III)Z
.end method

.method public static native n0111311110(ILjava/lang/Object;IIII)V
.end method

.method public static native n0111311111(ILjava/lang/Object;IIII)Z
.end method

.method public static native n01113311(ILjava/lang/Object;Ljava/lang/Object;I)Z
.end method

.method public static native n0112()J
.end method

.method public static native n01120(J)V
.end method

.method public static native n011210(JI)V
.end method

.method public static native n0112110(JII)V
.end method

.method public static native n011213(JI)Ljava/lang/Object;
.end method

.method public static native n0112130(JILjava/lang/Object;)V
.end method

.method public static native n01122113(DDII)Ljava/lang/Object;
.end method

.method public static native n0112213133(DDILjava/lang/Object;ILjava/lang/Object;)Ljava/lang/Object;
.end method

.method public static native n011222213133(DDDDILjava/lang/Object;ILjava/lang/Object;)Ljava/lang/Object;
.end method

.method public static native n01123(J)Ljava/lang/Object;
.end method

.method public static native n011231(JLjava/lang/Object;)I
.end method

.method public static native n011233(JLjava/lang/Object;)Ljava/lang/Object;
.end method

.method public static native n0112331(JLjava/lang/Object;Ljava/lang/Object;)Z
.end method

.method public static native n01123333(JLjava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
.end method

.method public static native n0113()Ljava/lang/Object;
.end method

.method public static native n01130(Ljava/lang/Object;)V
.end method

.method public static native n01131(Ljava/lang/Object;)I
.end method

.method public static native n011310(Ljava/lang/Object;I)V
.end method

.method public static native n011311(Ljava/lang/Object;I)Z
.end method

.method public static native n0113113(Ljava/lang/Object;II)Ljava/lang/Object;
.end method

.method public static native n011313(Ljava/lang/Object;I)Ljava/lang/Object;
.end method

.method public static native n0113131(Ljava/lang/Object;ILjava/lang/Object;)Z
.end method

.method public static native n01131312(Ljava/lang/Object;ILjava/lang/Object;I)D
.end method

.method public static native n011313133(Ljava/lang/Object;ILjava/lang/Object;ILjava/lang/Object;)Ljava/lang/Object;
.end method

.method public static native n01131323(Ljava/lang/Object;ILjava/lang/Object;D)Ljava/lang/Object;
.end method

.method public static native n0113133(Ljava/lang/Object;ILjava/lang/Object;)Ljava/lang/Object;
.end method

.method public static native n011313313(Ljava/lang/Object;ILjava/lang/Object;Ljava/lang/Object;Z)Ljava/lang/Object;
.end method

.method public static native n01132(Ljava/lang/Object;)J
.end method

.method public static native n011320(Ljava/lang/Object;J)V
.end method

.method public static native n01132213(Ljava/lang/Object;DDZ)Ljava/lang/Object;
.end method

.method public static native n011322213(Ljava/lang/Object;DDDI)Ljava/lang/Object;
.end method

.method public static native n01132222133(Ljava/lang/Object;DDDDILjava/lang/Object;)Ljava/lang/Object;
.end method

.method public static native n0113223(Ljava/lang/Object;DD)Ljava/lang/Object;
.end method

.method public static native n01132232(Ljava/lang/Object;JJLjava/lang/Object;)J
.end method

.method public static native n01133(Ljava/lang/Object;)Ljava/lang/Object;
.end method

.method public static native n011330(Ljava/lang/Object;Ljava/lang/Object;)V
.end method

.method public static native n011331(Ljava/lang/Object;Ljava/lang/Object;)I
.end method

.method public static native n0113310(Ljava/lang/Object;Ljava/lang/Object;I)V
.end method

.method public static native n01133110(Ljava/lang/Object;Ljava/lang/Object;ZI)V
.end method

.method public static native n011331113(Ljava/lang/Object;Ljava/lang/Object;III)Ljava/lang/Object;
.end method

.method public static native n01133131(Ljava/lang/Object;Ljava/lang/Object;ILjava/lang/Object;)I
.end method

.method public static native n0113313113(Ljava/lang/Object;Ljava/lang/Object;ILjava/lang/Object;II)Ljava/lang/Object;
.end method

.method public static native n01133132(Ljava/lang/Object;Ljava/lang/Object;ILjava/lang/Object;)D
.end method

.method public static native n01133133(Ljava/lang/Object;Ljava/lang/Object;ILjava/lang/Object;)Ljava/lang/Object;
.end method

.method public static native n011332(Ljava/lang/Object;Ljava/lang/Object;)J
.end method

.method public static native n011333(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
.end method

.method public static native n0113330(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
.end method

.method public static native n0113331(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Z
.end method

.method public static native n01133310(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Z)V
.end method

.method public static native n0113333(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
.end method

.method public static native n01133330(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
.end method

.method private static ᵢˋ(Landroid/content/Context;)Landroid/app/Application;
    .locals 2

    .prologue
    .line 45
    :try_start_0
    sget-object v0, Lcom/stub/StubApp;->ᵢˎ:Landroid/app/Application;

    if-nez v0, :cond_0

    .line 46
    invoke-virtual {p0}, Landroid/content/Context;->getClassLoader()Ljava/lang/ClassLoader;

    move-result-object v0

    .line 47
    if-eqz v0, :cond_0

    .line 48
    sget-object v1, Lcom/stub/StubApp;->strEntryApplication:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/ClassLoader;->loadClass(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    .line 49
    if-eqz v0, :cond_0

    .line 50
    invoke-virtual {v0}, Ljava/lang/Class;->newInstance()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/Application;

    sput-object v0, Lcom/stub/StubApp;->ᵢˎ:Landroid/app/Application;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 56
    :cond_0
    :goto_0
    sget-object v0, Lcom/stub/StubApp;->ᵢˎ:Landroid/app/Application;

    return-object v0

    .line 53
    :catch_0
    move-exception v0

    .line 54
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_0
.end method

.method private static ᵢˋ()Ljava/lang/Boolean;
    .locals 8

    .prologue
    const/4 v2, 0x0

    const/4 v3, 0x0

    const/4 v7, 0x1

    .line 132
    :try_start_0
    sget-object v1, Landroid/os/Build;->SUPPORTED_32_BIT_ABIS:[Ljava/lang/String;

    array-length v4, v1

    move v0, v3

    :goto_0
    if-ge v0, v4, :cond_1

    aget-object v5, v1, v0

    .line 133
    const-string v6, "x86"

    invoke-virtual {v5, v6}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v5

    if-eqz v5, :cond_0

    .line 134
    const/4 v0, 0x1

    invoke-static {v0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v0

    .line 192
    :goto_1
    return-object v0

    .line 132
    :cond_0
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 137
    :cond_1
    sget-object v0, Landroid/os/Build;->CPU_ABI:Ljava/lang/String;

    const-string v1, "x86"

    invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_2

    sget-object v0, Landroid/os/Build;->CPU_ABI2:Ljava/lang/String;

    const-string v1, "x86"

    invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_3

    .line 138
    :cond_2
    const/4 v0, 0x1

    invoke-static {v0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;
    :try_end_0
    .catch Ljava/lang/NoSuchFieldError; {:try_start_0 .. :try_end_0} :catch_5

    move-result-object v0

    goto :goto_1

    .line 143
    :cond_3
    :try_start_1
    new-instance v1, Ljava/io/RandomAccessFile;

    const-string v0, "/system/build.prop"

    const-string v4, "r"

    invoke-direct {v1, v0, v4}, Ljava/io/RandomAccessFile;-><init>(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_1
    .catch Ljava/io/FileNotFoundException; {:try_start_1 .. :try_end_1} :catch_1
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_3
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 144
    :try_start_2
    invoke-virtual {v1}, Ljava/io/RandomAccessFile;->readLine()Ljava/lang/String;

    move-result-object v0

    .line 145
    :goto_2
    if-eqz v0, :cond_5

    .line 146
    const-string v4, "ro.product.cpu.abi"

    invoke-virtual {v0, v4}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v4

    if-eqz v4, :cond_4

    const-string v4, "x86"

    invoke-virtual {v0, v4}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_4

    .line 147
    const/4 v0, 0x1

    invoke-static {v0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;
    :try_end_2
    .catch Ljava/io/FileNotFoundException; {:try_start_2 .. :try_end_2} :catch_12
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_2} :catch_11
    .catchall {:try_start_2 .. :try_end_2} :catchall_4

    move-result-object v0

    .line 159
    :try_start_3
    invoke-virtual {v1}, Ljava/io/RandomAccessFile;->close()V
    :try_end_3
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_3} :catch_0
    .catch Ljava/lang/NoSuchFieldError; {:try_start_3 .. :try_end_3} :catch_5

    goto :goto_1

    :catch_0
    move-exception v1

    goto :goto_1

    .line 148
    :cond_4
    :try_start_4
    invoke-virtual {v1}, Ljava/io/RandomAccessFile;->readLine()Ljava/lang/String;
    :try_end_4
    .catch Ljava/io/FileNotFoundException; {:try_start_4 .. :try_end_4} :catch_12
    .catch Ljava/io/IOException; {:try_start_4 .. :try_end_4} :catch_11
    .catchall {:try_start_4 .. :try_end_4} :catchall_4

    move-result-object v0

    goto :goto_2

    .line 159
    :cond_5
    :try_start_5
    invoke-virtual {v1}, Ljava/io/RandomAccessFile;->close()V
    :try_end_5
    .catch Ljava/lang/Exception; {:try_start_5 .. :try_end_5} :catch_c
    .catch Ljava/lang/NoSuchFieldError; {:try_start_5 .. :try_end_5} :catch_5

    .line 192
    :cond_6
    :goto_3
    invoke-static {v3}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v0

    goto :goto_1

    .line 150
    :catch_1
    move-exception v0

    move-object v1, v2

    .line 152
    :goto_4
    :try_start_6
    invoke-virtual {v0}, Ljava/io/FileNotFoundException;->printStackTrace()V
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_4

    .line 158
    if-eqz v1, :cond_6

    .line 159
    :try_start_7
    invoke-virtual {v1}, Ljava/io/RandomAccessFile;->close()V
    :try_end_7
    .catch Ljava/lang/Exception; {:try_start_7 .. :try_end_7} :catch_2
    .catch Ljava/lang/NoSuchFieldError; {:try_start_7 .. :try_end_7} :catch_5

    goto :goto_3

    .line 162
    :catch_2
    move-exception v0

    goto :goto_3

    .line 153
    :catch_3
    move-exception v0

    move-object v1, v2

    .line 155
    :goto_5
    :try_start_8
    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V
    :try_end_8
    .catchall {:try_start_8 .. :try_end_8} :catchall_4

    .line 158
    if-eqz v1, :cond_6

    .line 159
    :try_start_9
    invoke-virtual {v1}, Ljava/io/RandomAccessFile;->close()V
    :try_end_9
    .catch Ljava/lang/Exception; {:try_start_9 .. :try_end_9} :catch_4
    .catch Ljava/lang/NoSuchFieldError; {:try_start_9 .. :try_end_9} :catch_5

    goto :goto_3

    .line 162
    :catch_4
    move-exception v0

    goto :goto_3

    .line 157
    :catchall_0
    move-exception v0

    move-object v1, v2

    .line 158
    :goto_6
    if-eqz v1, :cond_7

    .line 159
    :try_start_a
    invoke-virtual {v1}, Ljava/io/RandomAccessFile;->close()V
    :try_end_a
    .catch Ljava/lang/Exception; {:try_start_a .. :try_end_a} :catch_d
    .catch Ljava/lang/NoSuchFieldError; {:try_start_a .. :try_end_a} :catch_5

    .line 161
    :cond_7
    :goto_7
    :try_start_b
    throw v0
    :try_end_b
    .catch Ljava/lang/NoSuchFieldError; {:try_start_b .. :try_end_b} :catch_5

    .line 166
    :catch_5
    move-exception v0

    sget-object v0, Landroid/os/Build;->CPU_ABI:Ljava/lang/String;

    const-string v1, "x86"

    invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_8

    sget-object v0, Landroid/os/Build;->CPU_ABI2:Ljava/lang/String;

    const-string v1, "x86"

    invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_9

    .line 167
    :cond_8
    invoke-static {v7}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v0

    goto/16 :goto_1

    .line 171
    :cond_9
    :try_start_c
    new-instance v1, Ljava/io/RandomAccessFile;

    const-string v0, "/system/build.prop"

    const-string v4, "r"

    invoke-direct {v1, v0, v4}, Ljava/io/RandomAccessFile;-><init>(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_c
    .catch Ljava/io/FileNotFoundException; {:try_start_c .. :try_end_c} :catch_8
    .catch Ljava/io/IOException; {:try_start_c .. :try_end_c} :catch_a
    .catchall {:try_start_c .. :try_end_c} :catchall_1

    .line 172
    :try_start_d
    invoke-virtual {v1}, Ljava/io/RandomAccessFile;->readLine()Ljava/lang/String;

    move-result-object v0

    .line 173
    :goto_8
    if-eqz v0, :cond_b

    .line 174
    const-string v2, "ro.product.cpu.abi"

    invoke-virtual {v0, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v2

    if-eqz v2, :cond_a

    const-string v2, "x86"

    invoke-virtual {v0, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_a

    .line 175
    const/4 v0, 0x1

    invoke-static {v0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;
    :try_end_d
    .catch Ljava/io/FileNotFoundException; {:try_start_d .. :try_end_d} :catch_10
    .catch Ljava/io/IOException; {:try_start_d .. :try_end_d} :catch_f
    .catchall {:try_start_d .. :try_end_d} :catchall_2

    move-result-object v0

    .line 187
    :try_start_e
    invoke-virtual {v1}, Ljava/io/RandomAccessFile;->close()V
    :try_end_e
    .catch Ljava/lang/Exception; {:try_start_e .. :try_end_e} :catch_6

    goto/16 :goto_1

    :catch_6
    move-exception v1

    goto/16 :goto_1

    .line 176
    :cond_a
    :try_start_f
    invoke-virtual {v1}, Ljava/io/RandomAccessFile;->readLine()Ljava/lang/String;
    :try_end_f
    .catch Ljava/io/FileNotFoundException; {:try_start_f .. :try_end_f} :catch_10
    .catch Ljava/io/IOException; {:try_start_f .. :try_end_f} :catch_f
    .catchall {:try_start_f .. :try_end_f} :catchall_2

    move-result-object v0

    goto :goto_8

    .line 187
    :cond_b
    :try_start_10
    invoke-virtual {v1}, Ljava/io/RandomAccessFile;->close()V
    :try_end_10
    .catch Ljava/lang/Exception; {:try_start_10 .. :try_end_10} :catch_7

    goto :goto_3

    .line 190
    :catch_7
    move-exception v0

    goto :goto_3

    .line 178
    :catch_8
    move-exception v0

    .line 180
    :goto_9
    :try_start_11
    invoke-virtual {v0}, Ljava/io/FileNotFoundException;->printStackTrace()V
    :try_end_11
    .catchall {:try_start_11 .. :try_end_11} :catchall_3

    .line 186
    if-eqz v2, :cond_6

    .line 187
    :try_start_12
    invoke-virtual {v2}, Ljava/io/RandomAccessFile;->close()V
    :try_end_12
    .catch Ljava/lang/Exception; {:try_start_12 .. :try_end_12} :catch_9

    goto/16 :goto_3

    .line 190
    :catch_9
    move-exception v0

    goto/16 :goto_3

    .line 181
    :catch_a
    move-exception v0

    move-object v1, v2

    .line 183
    :goto_a
    :try_start_13
    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V
    :try_end_13
    .catchall {:try_start_13 .. :try_end_13} :catchall_2

    .line 186
    if-eqz v1, :cond_6

    .line 187
    :try_start_14
    invoke-virtual {v1}, Ljava/io/RandomAccessFile;->close()V
    :try_end_14
    .catch Ljava/lang/Exception; {:try_start_14 .. :try_end_14} :catch_b

    goto/16 :goto_3

    .line 190
    :catch_b
    move-exception v0

    goto/16 :goto_3

    .line 185
    :catchall_1
    move-exception v0

    move-object v1, v2

    .line 186
    :goto_b
    if-eqz v1, :cond_c

    .line 187
    :try_start_15
    invoke-virtual {v1}, Ljava/io/RandomAccessFile;->close()V
    :try_end_15
    .catch Ljava/lang/Exception; {:try_start_15 .. :try_end_15} :catch_e

    .line 189
    :cond_c
    :goto_c
    throw v0

    .line 162
    :catch_c
    move-exception v0

    goto/16 :goto_3

    :catch_d
    move-exception v1

    goto :goto_7

    :catch_e
    move-exception v1

    goto :goto_c

    .line 185
    :catchall_2
    move-exception v0

    goto :goto_b

    :catchall_3
    move-exception v0

    move-object v1, v2

    goto :goto_b

    .line 181
    :catch_f
    move-exception v0

    goto :goto_a

    .line 178
    :catch_10
    move-exception v0

    move-object v2, v1

    goto :goto_9

    .line 157
    :catchall_4
    move-exception v0

    goto/16 :goto_6

    .line 153
    :catch_11
    move-exception v0

    goto/16 :goto_5

    .line 150
    :catch_12
    move-exception v0

    goto/16 :goto_4
.end method

.method private static ᵢˋ(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
    .locals 8

    .prologue
    const/4 v0, 0x1

    const/4 v1, 0x0

    .line 271
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "/"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    .line 273
    new-instance v2, Ljava/io/File;

    invoke-direct {v2, p2}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 281
    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v4

    if-nez v4, :cond_0

    .line 282
    invoke-virtual {v2}, Ljava/io/File;->mkdir()Z

    .line 284
    :cond_0
    :try_start_0
    new-instance v2, Ljava/io/File;

    invoke-direct {v2, v3}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 286
    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v4

    if-eqz v4, :cond_1

    .line 287
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v4

    invoke-virtual {v4}, Landroid/content/res/Resources;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v4

    invoke-virtual {v4, p1}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v4

    .line 288
    new-instance v5, Ljava/io/FileInputStream;

    invoke-direct {v5, v2}, Ljava/io/FileInputStream;-><init>(Ljava/io/File;)V

    .line 289
    new-instance v6, Ljava/io/BufferedInputStream;

    invoke-direct {v6, v4}, Ljava/io/BufferedInputStream;-><init>(Ljava/io/InputStream;)V

    .line 290
    new-instance v7, Ljava/io/BufferedInputStream;

    invoke-direct {v7, v5}, Ljava/io/BufferedInputStream;-><init>(Ljava/io/InputStream;)V

    .line 292
    invoke-static {v6, v7}, Lcom/stub/StubApp;->ᵢˋ(Ljava/io/BufferedInputStream;Ljava/io/BufferedInputStream;)Z

    move-result v2

    if-eqz v2, :cond_3

    move v2, v0

    .line 296
    :goto_0
    invoke-virtual {v4}, Ljava/io/InputStream;->close()V

    .line 297
    invoke-virtual {v5}, Ljava/io/InputStream;->close()V

    .line 298
    invoke-virtual {v6}, Ljava/io/BufferedInputStream;->close()V

    .line 299
    invoke-virtual {v7}, Ljava/io/BufferedInputStream;->close()V

    .line 300
    if-eqz v2, :cond_1

    .line 323
    :goto_1
    return v0

    .line 304
    :cond_1
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2}, Landroid/content/res/Resources;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v2

    invoke-virtual {v2, p1}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v2

    .line 305
    new-instance v4, Ljava/io/FileOutputStream;

    invoke-direct {v4, v3}, Ljava/io/FileOutputStream;-><init>(Ljava/lang/String;)V

    .line 307
    const/16 v5, 0x1c00

    new-array v5, v5, [B

    .line 309
    :goto_2
    invoke-virtual {v2, v5}, Ljava/io/InputStream;->read([B)I

    move-result v6

    if-lez v6, :cond_2

    .line 310
    const/4 v7, 0x0

    invoke-virtual {v4, v5, v7, v6}, Ljava/io/FileOutputStream;->write([BII)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_2

    .line 315
    :catch_0
    move-exception v0

    .line 316
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    move v0, v1

    .line 317
    goto :goto_1

    .line 312
    :cond_2
    :try_start_1
    invoke-virtual {v4}, Ljava/io/FileOutputStream;->close()V

    .line 313
    invoke-virtual {v2}, Ljava/io/InputStream;->close()V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    .line 320
    :try_start_2
    invoke-static {}, Ljava/lang/Runtime;->getRuntime()Ljava/lang/Runtime;

    move-result-object v1

    new-instance v2, Ljava/lang/StringBuilder;

    const-string v4, "chmod 755 "

    invoke-direct {v2, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/Runtime;->exec(Ljava/lang/String;)Ljava/lang/Process;
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_1

    goto :goto_1

    :catch_1
    move-exception v1

    goto :goto_1

    :cond_3
    move v2, v1

    goto :goto_0
.end method

.method private static ᵢˋ(Ljava/io/BufferedInputStream;Ljava/io/BufferedInputStream;)Z
    .locals 7

    .prologue
    const/4 v0, 0x0

    .line 328
    :try_start_0
    invoke-virtual {p0}, Ljava/io/BufferedInputStream;->available()I

    move-result v2

    .line 329
    invoke-virtual {p1}, Ljava/io/BufferedInputStream;->available()I

    move-result v1

    .line 331
    if-ne v2, v1, :cond_0

    .line 332
    new-array v3, v2, [B

    .line 333
    new-array v4, v1, [B

    .line 335
    invoke-virtual {p0, v3}, Ljava/io/BufferedInputStream;->read([B)I

    .line 336
    invoke-virtual {p1, v4}, Ljava/io/BufferedInputStream;->read([B)I

    move v1, v0

    .line 338
    :goto_0
    if-ge v1, v2, :cond_2

    .line 339
    aget-byte v5, v3, v1

    aget-byte v6, v4, v1
    :try_end_0
    .catch Ljava/io/FileNotFoundException; {:try_start_0 .. :try_end_0} :catch_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_1

    if-eq v5, v6, :cond_1

    .line 352
    :cond_0
    :goto_1
    return v0

    .line 338
    :cond_1
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 343
    :cond_2
    const/4 v0, 0x1

    goto :goto_1

    .line 347
    :catch_0
    move-exception v1

    .line 348
    invoke-virtual {v1}, Ljava/io/FileNotFoundException;->printStackTrace()V

    goto :goto_1

    .line 349
    :catch_1
    move-exception v1

    .line 350
    invoke-virtual {v1}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_1
.end method


# virtual methods
.method protected attachBaseContext(Landroid/content/Context;)V
    .locals 8

    .prologue
    const/4 v1, 0x0

    const/4 v7, 0x1

    .line 207
    invoke-super {p0, p1}, Landroid/app/Application;->attachBaseContext(Landroid/content/Context;)V

    .line 208
    sput-object p1, Lcom/stub/StubApp;->ᵢˑ:Landroid/content/Context;

    .line 209
    sget-object v0, Lcom/stub/StubApp;->ᵢˋ:Landroid/app/Application;

    if-nez v0, :cond_0

    .line 210
    sput-object p0, Lcom/stub/StubApp;->ᵢˋ:Landroid/app/Application;

    .line 212
    :cond_0
    sget-object v0, Lcom/stub/StubApp;->ᵢˎ:Landroid/app/Application;

    if-nez v0, :cond_5

    .line 214
    invoke-static {}, Lcom/stub/StubApp;->ᵢˋ()Ljava/lang/Boolean;

    move-result-object v2

    .line 215
    invoke-static {v1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v0

    .line 216
    invoke-static {v1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    .line 217
    sget-object v3, Landroid/os/Build;->CPU_ABI:Ljava/lang/String;

    const-string v4, "64"

    invoke-virtual {v3, v4}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v3

    if-nez v3, :cond_1

    sget-object v3, Landroid/os/Build;->CPU_ABI2:Ljava/lang/String;

    const-string v4, "64"

    invoke-virtual {v3, v4}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v3

    if-eqz v3, :cond_2

    .line 218
    :cond_1
    invoke-static {v7}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v0

    .line 220
    :cond_2
    sget-object v3, Landroid/os/Build;->CPU_ABI:Ljava/lang/String;

    const-string v4, "mips"

    invoke-virtual {v3, v4}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v3

    if-nez v3, :cond_3

    sget-object v3, Landroid/os/Build;->CPU_ABI2:Ljava/lang/String;

    const-string v4, "mips"

    invoke-virtual {v3, v4}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v3

    if-eqz v3, :cond_4

    .line 221
    :cond_3
    invoke-static {v7}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    .line 223
    :cond_4
    sget-boolean v3, Lcom/stub/StubApp;->loadFromLib:Z

    if-eqz v3, :cond_8

    .line 224
    invoke-virtual {v2}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v0

    if-eqz v0, :cond_7

    .line 225
    const-string v0, "jiagu_x86"

    invoke-static {v0}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V

    .line 251
    :cond_5
    :goto_0
    sget-object v0, Lcom/stub/StubApp;->ᵢˋ:Landroid/app/Application;

    invoke-static {v0}, Lcom/stub/StubApp;->interface5(Landroid/app/Application;)V

    .line 252
    invoke-static {p1}, Lcom/stub/StubApp;->ᵢˋ(Landroid/content/Context;)Landroid/app/Application;

    move-result-object v0

    sput-object v0, Lcom/stub/StubApp;->ᵢˎ:Landroid/app/Application;

    .line 253
    sget-object v0, Lcom/stub/StubApp;->ᵢˎ:Landroid/app/Application;

    if-eqz v0, :cond_d

    .line 255
    :try_start_0
    const-class v0, Landroid/app/Application;

    const-string v1, "attach"

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Class;

    const/4 v3, 0x0

    const-class v4, Landroid/content/Context;

    aput-object v4, v2, v3

    invoke-virtual {v0, v1, v2}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v0

    .line 256
    if-eqz v0, :cond_6

    .line 257
    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Ljava/lang/reflect/Method;->setAccessible(Z)V

    .line 258
    sget-object v1, Lcom/stub/StubApp;->ᵢˎ:Landroid/app/Application;

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    aput-object p1, v2, v3

    invoke-virtual {v0, v1, v2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 263
    :cond_6
    :goto_1
    sget-object v0, Lcom/stub/StubApp;->ᵢˎ:Landroid/app/Application;

    invoke-static {v0, p1}, Lcom/stub/StubApp;->interface8(Landroid/app/Application;Landroid/content/Context;)Z

    .line 2197
    :goto_2
    :try_start_1
    const-string v0, "com.qihoo.\u1d62\u02cb.\u1d62\u02cb"

    invoke-static {v0}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    .line 2198
    const-string v1, "initAssetForNative"

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Class;

    const/4 v3, 0x0

    const-class v4, Landroid/content/Context;

    aput-object v4, v2, v3

    invoke-virtual {v0, v1, v2}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v0

    .line 2199
    const/4 v1, 0x0

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    aput-object p0, v2, v3

    invoke-virtual {v0, v1, v2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_1

    .line 2202
    :goto_3
    return-void

    .line 227
    :cond_7
    const-string v0, "jiagu"

    invoke-static {v0}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V

    goto :goto_0

    .line 230
    :cond_8
    invoke-virtual {p1}, Landroid/content/Context;->getFilesDir()Ljava/io/File;

    move-result-object v3

    invoke-virtual {v3}, Ljava/io/File;->getParentFile()Ljava/io/File;

    move-result-object v3

    invoke-virtual {v3}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v3

    .line 231
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v4, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, "/.jiagu"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    .line 232
    invoke-virtual {v1}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v4

    if-eqz v4, :cond_9

    .line 233
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v5, Lcom/stub/StubApp;->ᵢˏ:Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, "_mips.so"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v6, Lcom/stub/StubApp;->ᵢˏ:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ".so"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {p1, v4, v3, v5}, Lcom/stub/StubApp;->ᵢˋ(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    .line 239
    :goto_4
    invoke-virtual {v0}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v0

    if-eqz v0, :cond_c

    invoke-virtual {v1}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v0

    if-nez v0, :cond_c

    .line 240
    invoke-virtual {v2}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v0

    if-eqz v0, :cond_b

    .line 241
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v1, Lcom/stub/StubApp;->ᵢˏ:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "_x64.so"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v2, Lcom/stub/StubApp;->ᵢˏ:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, "_64.so"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {p1, v0, v3, v1}, Lcom/stub/StubApp;->ᵢˋ(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    .line 245
    :goto_5
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "/"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lcom/stub/StubApp;->ᵢˏ:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "_64.so"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Ljava/lang/System;->load(Ljava/lang/String;)V

    goto/16 :goto_0

    .line 234
    :cond_9
    invoke-virtual {v2}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v4

    if-eqz v4, :cond_a

    .line 235
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v5, Lcom/stub/StubApp;->ᵢˏ:Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, "_x86.so"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v6, Lcom/stub/StubApp;->ᵢˏ:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ".so"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {p1, v4, v3, v5}, Lcom/stub/StubApp;->ᵢˋ(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    goto/16 :goto_4

    .line 237
    :cond_a
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v5, Lcom/stub/StubApp;->ᵢˏ:Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, ".so"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v6, Lcom/stub/StubApp;->ᵢˏ:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ".so"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {p1, v4, v3, v5}, Lcom/stub/StubApp;->ᵢˋ(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    goto/16 :goto_4

    .line 243
    :cond_b
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v1, Lcom/stub/StubApp;->ᵢˏ:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "_a64.so"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v2, Lcom/stub/StubApp;->ᵢˏ:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, "_64.so"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {p1, v0, v3, v1}, Lcom/stub/StubApp;->ᵢˋ(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    goto/16 :goto_5

    .line 247
    :cond_c
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "/"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lcom/stub/StubApp;->ᵢˏ:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ".so"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Ljava/lang/System;->load(Ljava/lang/String;)V

    goto/16 :goto_0

    .line 260
    :catch_0
    move-exception v0

    .line 261
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    goto/16 :goto_1

    .line 265
    :cond_d
    invoke-static {v7}, Ljava/lang/System;->exit(I)V

    goto/16 :goto_2

    .line 269
    :catch_1
    move-exception v0

    goto/16 :goto_3
.end method

.method public native declared-synchronized n11030(Ljava/lang/Object;)V
.end method

.method public native declared-synchronized n11033(Ljava/lang/Object;)Ljava/lang/Object;
.end method

.method public native declared-synchronized n110331(Ljava/lang/Object;Ljava/lang/Object;)Z
.end method

.method public native n1110()V
.end method

.method public native n1111()Z
.end method

.method public native n11110(Z)V
.end method

.method public native n11111112(IIII)J
.end method

.method public native n1111120(IIJ)V
.end method

.method public native n111120(IJ)V
.end method

.method public native n111131(ILjava/lang/Object;)Z
.end method

.method public native n11113330(ILjava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
.end method

.method public native n1112()J
.end method

.method public native n11120(J)V
.end method

.method public native n11121(J)I
.end method

.method public native n111210(JF)V
.end method

.method public native n111211(JI)I
.end method

.method public native n1112110(JIZ)V
.end method

.method public native n1112111310(JIIILjava/lang/Object;I)V
.end method

.method public native n1112113(JFF)Ljava/lang/Object;
.end method

.method public native n11121131(JFFLjava/lang/Object;)Z
.end method

.method public native n111212(JI)J
.end method

.method public native n11121220(JIDD)V
.end method

.method public native n111213(JI)Ljava/lang/Object;
.end method

.method public native n1112130(JILjava/lang/Object;)V
.end method

.method public native n1112131(JILjava/lang/Object;)Z
.end method

.method public native n11121322221(JILjava/lang/Object;DDDD)Z
.end method

.method public native n1112132222223311111(JILjava/lang/Object;DDDDDDLjava/lang/Object;Ljava/lang/Object;IIII)Z
.end method

.method public native n11121322223311(JILjava/lang/Object;DDDDLjava/lang/Object;Ljava/lang/Object;Z)Z
.end method

.method public native n111213311130(JILjava/lang/Object;Ljava/lang/Object;IIILjava/lang/Object;)V
.end method

.method public native n11121333310(JILjava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;I)V
.end method

.method public native n11122(J)D
.end method

.method public native n111220(JD)V
.end method

.method public native n111221(JJ)I
.end method

.method public native n1112210(JJI)V
.end method

.method public native n1112211(JJI)Z
.end method

.method public native n11122110(JDFF)V
.end method

.method public native n1112220(JDD)V
.end method

.method public native n111222113(JDDII)Ljava/lang/Object;
.end method

.method public native n111222220(JDDDD)V
.end method

.method public native n111222221(JDDDD)Z
.end method

.method public native n11122222310(JDDDDLjava/lang/Object;I)V
.end method

.method public native n1112223(JDD)Ljava/lang/Object;
.end method

.method public native n1112231(JJLjava/lang/Object;)Z
.end method

.method public native n11123(J)Ljava/lang/Object;
.end method

.method public native n111230(JLjava/lang/Object;)V
.end method

.method public native n111231(JLjava/lang/Object;)Z
.end method

.method public native n1112311(JLjava/lang/Object;Z)Z
.end method

.method public native n11123111111(JLjava/lang/Object;IIIII)I
.end method

.method public native n11123122220(JLjava/lang/Object;IDDDD)V
.end method

.method public native n111231330(JLjava/lang/Object;ILjava/lang/Object;Ljava/lang/Object;)V
.end method

.method public native n111232(JLjava/lang/Object;)J
.end method

.method public native n111233(JLjava/lang/Object;)Ljava/lang/Object;
.end method

.method public native n1112330(JLjava/lang/Object;Ljava/lang/Object;)V
.end method

.method public native n1112331(JLjava/lang/Object;Ljava/lang/Object;)I
.end method

.method public native n111233110(JLjava/lang/Object;Ljava/lang/Object;FF)V
.end method

.method public native n11123333(JLjava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
.end method

.method public native n1112333311(JLjava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;I)I
.end method

.method public native n1112333333(JLjava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
.end method

.method public native n1113()Ljava/lang/Object;
.end method

.method public native n11130(Ljava/lang/Object;)V
.end method

.method public native n11131(Ljava/lang/Object;)Z
.end method

.method public native n111310(Ljava/lang/Object;I)V
.end method

.method public native n1113110(Ljava/lang/Object;II)V
.end method

.method public native n1113111(Ljava/lang/Object;II)I
.end method

.method public native n111311112(Ljava/lang/Object;IIFZ)J
.end method

.method public native n1113130(Ljava/lang/Object;ZLjava/lang/Object;)V
.end method

.method public native n11133(Ljava/lang/Object;)Ljava/lang/Object;
.end method

.method public native n111330(Ljava/lang/Object;Ljava/lang/Object;)V
.end method

.method public native n111331(Ljava/lang/Object;Ljava/lang/Object;)Z
.end method

.method public native n1113310(Ljava/lang/Object;Ljava/lang/Object;Z)V
.end method

.method public native n11133110(Ljava/lang/Object;Ljava/lang/Object;ZI)V
.end method

.method public native n1113320(Ljava/lang/Object;Ljava/lang/Object;J)V
.end method

.method public native n1113321(Ljava/lang/Object;Ljava/lang/Object;J)Z
.end method

.method public native n1113323(Ljava/lang/Object;Ljava/lang/Object;J)Ljava/lang/Object;
.end method

.method public native n111333(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
.end method

.method public native n1113331(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)I
.end method

.method public native n11133310(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Z)V
.end method

.method public native n1113333(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
.end method

.method public native n11133331(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)I
.end method

.method public native n111333333(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
.end method

.method public onCreate()V
    .locals 5

    .prologue
    .line 77
    invoke-super {p0}, Landroid/app/Application;->onCreate()V

    .line 79
    sget-boolean v0, Lcom/qihoo/util/Configuration;->ENABLE_CRASH_REPORT:Z

    if-eqz v0, :cond_0

    .line 1098
    :try_start_0
    const-string v0, "com.qihoo.bugreport.CrashReport"

    invoke-static {v0}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    .line 1099
    const-string v1, "prepareInit"

    const/4 v2, 0x0

    new-array v2, v2, [Ljava/lang/Class;

    invoke-virtual {v0, v1, v2}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v0

    .line 1100
    const/4 v1, 0x0

    const/4 v2, 0x0

    new-array v2, v2, [Ljava/lang/Object;

    invoke-virtual {v0, v1, v2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_0
    .catch Ljava/lang/Throwable; {:try_start_0 .. :try_end_0} :catch_3

    .line 2067
    :cond_0
    :goto_0
    sget-object v0, Lcom/stub/StubApp;->ᵢˋ:Landroid/app/Application;

    invoke-virtual {v0}, Landroid/app/Application;->getBaseContext()Landroid/content/Context;

    move-result-object v0

    .line 2069
    :try_start_1
    sget-object v1, Lcom/stub/StubApp;->ᵢˎ:Landroid/app/Application;

    invoke-static {v1, v0}, Lcom/stub/StubApp;->interface7(Landroid/app/Application;Landroid/content/Context;)Z
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    .line 85
    :goto_1
    sget-object v0, Lcom/stub/StubApp;->ᵢˎ:Landroid/app/Application;

    if-eqz v0, :cond_1

    .line 86
    sget-object v0, Lcom/stub/StubApp;->ᵢˎ:Landroid/app/Application;

    invoke-virtual {v0}, Landroid/app/Application;->onCreate()V

    .line 89
    :cond_1
    sget-boolean v0, Lcom/qihoo/util/Configuration;->ENABLE_CRASH_REPORT:Z

    if-eqz v0, :cond_2

    .line 2109
    :try_start_2
    const-string v0, "com.qihoo.bugreport.CrashReport"

    invoke-static {v0}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    .line 2110
    const-string v1, "init"

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Class;

    const/4 v3, 0x0

    const-class v4, Landroid/content/Context;

    aput-object v4, v2, v3

    invoke-virtual {v0, v1, v2}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v0

    .line 2111
    const/4 v1, 0x0

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    invoke-virtual {p0}, Lcom/stub/StubApp;->getApplicationContext()Landroid/content/Context;

    move-result-object v4

    aput-object v4, v2, v3

    invoke-virtual {v0, v1, v2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_2
    .catch Ljava/lang/Throwable; {:try_start_2 .. :try_end_2} :catch_2

    .line 2119
    :cond_2
    :goto_2
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0xe

    if-lt v0, v1, :cond_3

    .line 2121
    :try_start_3
    const-string v0, "com.stub.adsdk.rpa.util.RpaActivityTracker"

    invoke-static {v0}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    .line 2122
    const-string v1, "register"

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Class;

    const/4 v3, 0x0

    const-class v4, Landroid/app/Application;

    aput-object v4, v2, v3

    invoke-virtual {v0, v1, v2}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v0

    .line 2123
    const/4 v1, 0x0

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    sget-object v4, Lcom/stub/StubApp;->ᵢˎ:Landroid/app/Application;

    aput-object v4, v2, v3

    invoke-virtual {v0, v1, v2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_3
    .catch Ljava/lang/Throwable; {:try_start_3 .. :try_end_3} :catch_1

    .line 2126
    :cond_3
    :goto_3
    return-void

    .line 2070
    :catch_0
    move-exception v0

    .line 2071
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_1

    :catch_1
    move-exception v0

    goto :goto_3

    :catch_2
    move-exception v0

    goto :goto_2

    :catch_3
    move-exception v0

    goto :goto_0
.end method
