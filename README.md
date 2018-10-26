# android-clipping-demo

Обзор различных подходов отсечения (clipping) в Android.

Рассмотрены 4 варианта реализации:
* View.clipToOutline
* Canvas.clipPath()
* PorterDuff.Mode + View.LAYER_TYPE_SOFTWARE
* PorterDuff.Mode + View.LAYER_TYPE_HARDWARE
