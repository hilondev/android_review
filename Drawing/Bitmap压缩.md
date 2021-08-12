
### 前言

图片的存在形式：
1. **文件形式**（以二进制形式存在于硬盘上）
2. **流的形式**（以二进制形式存在于内存中，一般用于文件传输）
3. **Bitmap的形式**（位图，是一种存储像素的数据结构，通过这个对象可以得到一系列的图像属性。还可以对图像进行旋转，切割，放大，缩小等操作）

区别：文件形式和流的形式对图片体积大小没有影响，也就是说，如果图片在手机SD卡上的大小是100KB，那么通过流的形式读到内存中，也是占用100KB的大小，注意是流的形式，不是bitmap的形式；当图片以Bitmap的形式存在时，其占用的内存会瞬间变大，曾经试过把500KB左右的图片加载到内存中，以Bitmap形式存在时，内存占用近10MB，当然这个增大的倍数不是固定的。

### 图片占用内存计算方法

Android中一张图片（BitMap）占用的内存主要和以下几个参数有关：图片长度、图片宽度、单位像素占用的字节数。一张图片（BitMap）占用的内存=图片长度*图片宽度*单位像素占用的字节数（注：图片长度和图片宽度的单位是像素）。

图片（BitMap）占用的内存应该和屏幕密度(Density)无关，虽然我暂时还拿不出直接证据。创建一个BitMap时，其单位像素占用的字节数由其参数BitmapFactory.Options的inPreferredConfig变量决定。

简单点说，图片所占内存计算方法如下：

|图片格式（Bitmap.Config）|	占用内存的计算方法 | 一张100*100的图片占用内存的大小 |
|----|----|----|
|ALPHA_8|图片长度 * 图片宽度|100 * 100=10000字节|
|ARGB_4444|图片长度 * 图片宽度 * 2|100 * 100 * 2=20000字节|
|ARGB_8888|图片长度 * 图片宽度 * 4|100 * 100 * 4=40000字节|
|RGB_565|图片长度 * 图片宽度 * 2|100 * 100 * 2=20000字节|

按照以上计算方法，可以很容易计算出华为P9相机拍出的照片如果载入到内存中，所占用的内存为：
3968 * 2976 * 4 = 47235072字节 = 45M

试想这样的照片，如果未经压缩就在差一点的手机上打开的话，APP很容易就OOM了！

### 压缩概述

Android 系统分配给我们每个应用的内存是有限的，由于解析、加载一张图片，需要占用的内存大小，是远大于图片自身大小的。所以，这时程序就可能因为占用了过多的内存，从而出现OOM 现象。

在 Android 中进行图片压缩是非常常见的开发场景，主要的压缩方法有两种：其一是质量压缩，其二是下采样压缩。

前者是在不改变图片尺寸的情况下，改变图片的存储体积，而后者则是降低图像尺寸，达到相同目的。



### 压缩方式

#### 质量压缩

压缩图片的质量，但并不会减少图片的像素。

在Android中，对图片进行质量压缩，通常我们的实现方式如下所示：
```
ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//quality 为0～100，0表示最小体积，100表示最高质量，对应体积也是最大
bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

```

在上述代码中，我们选择的压缩格式是 CompressFormat.JPEG，除此之外还有两个选择：

- 1）其一，CompressFormat.PNG：PNG 格式是无损的，它无法再进行质量压缩，quality 这个参数就没有作用了，会被忽略，所以最后图片保存成的文件大小不会有变化；
- 2）其二，CompressFormat.WEBP：这个格式是 google 推出的图片格式，它会比 JPEG 更加省空间，经过实测大概可以优化 30% 左右。

将 PNG 图片转成 JPEG 格式之后不会降低这个图片的尺寸，但是会降低视觉质量，从而降低存储体积。同时，由于尺寸不变，所以将这个图片解码成相同色彩模式的 bitmap 之后，占用的内存大小和压缩前是一样的。


完整实例：

```
public static void compressBmpToFile(Bitmap bmp, File file) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    int options = 80;// 压缩质量从80开始 
    bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
    while (baos.toByteArray().length / 1024 > 100) { // 如果大于100KB时，压缩质量-10；  
        baos.reset();
        options -= 10;
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
    }
    try {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(baos.toByteArray());
        fos.flush();
        fos.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

```

#### 尺寸压缩

针对图片尺寸的修改其实就是一个图像重新采样的过程，放大图像称为上采样（upsamping），缩小图像称为下采样（downsampling），这里我们重点讨论下采样。

在 Android 中图片重采样提供了两种方法，一种叫做邻近采样（Nearest Neighbour Resampling），另一种叫做双线性采样（Bilinear Resampling）。


##### 邻近采样（Nearest Neighbour Resampling）

Nearest Neighbour Resampling（邻近采样），是 Android 中常用的压缩方法之一。
通过设置采样率，减少图片的像素。

我们先来看看在 Android 中使用邻近采样的示例代码：

```
BitmapFactory.Options options = new BitmapFactory.Options();
//或者 inDensity 搭配 inTargetDensity 使用，算法和 inSampleSize 一样
options.inSampleSize = 2;
Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/test.png");
Bitmap compress = BitmapFactory.decodeFile("/sdcard/test.png", options);

```

接着我们来看看 inSampleSzie 的官方描述：
> If set to a value > 1, requests the decoder to subsample the original image, returning a smaller image to save memory. The sample size is the number of pixels in either dimension that correspond to a single pixel in the decoded bitmap. For example, inSampleSize == 4 returns an image that is 1/4 the width/height of the original, and 1/16 the number of pixels. Any value <= 1 is treated the same as 1. Note: the decoder uses a final value based on powers of 2, any other value will be rounded down to the nearest power of 2.


从官方的解释中我们可以看到 x（x 为 2 的倍数）个像素最后对应一个像素，由于采样率设置为 1/2，所以是两个像素生成一个像素。邻近采样的方式比较粗暴，直接选择其中的一个像素作为生成像素，另一个像素直接抛弃，这样就造成了图片变成了纯绿色，也就是红色像素被抛弃。

邻近采样采用的算法叫做邻近点插值算法。

##### 双线性采样（Bilinear Resampling）

双线性采样（Bilinear Resampling）在 Android 中的使用方式一般有两种：

```
Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/test.png");
Bitmap compress = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/2, bitmap.getHeight()/2, true);

```
或者直接使用 matrix 进行缩放：

```
Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/test.png");
Matrix matrix = new Matrix();
matrix.setScale(0.5f, 0.5f);
bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
```

看源码可以知道 createScaledBitmap 函数最终也是使用第二种方式的 matrix 进行缩放。

双线性采样使用的是双线性內插值算法，这个算法不像邻近点插值算法一样，直接粗暴的选择一个像素，而是参考了源像素相应位置周围 2x2 个点的值，根据相对位置取对应的权重，经过计算之后得到目标图像。

双线性内插值算法在图像的缩放处理中具有抗锯齿功能, 是最简单和常见的图像缩放算法，当对相邻 2x2 个像素点采用双线性內插值算法时，所得表面在邻域处是吻合的，但斜率不吻合，并且双线性内插值算法的平滑作用可能使得图像的细节产生退化，这种现象在上采样时尤其明显。