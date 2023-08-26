#!/usr/bin/bash

echo "unpacking apk..."
unzip app-release.apk -d app 
echo "converting pngs to webps..."
cd app/res
cp /home/h0wdy/Desktop/personal/blogs/bggp4/pngs2webps .
./pngs2webps
rm *.png
rm pngs2webps
echo "recompressing..."
cd ../ 
zip -X app-unsigned.apk AndroidManifest.xml assets/dexopt/baseline.prof assets/dexopt/baseline.profm classes.dex DebugProbesKt.bin kotlin/annotation/annotation.kotlin_builtins kotlin/collections/collections.kotlin_builtins kotlin/coroutines/coroutines.kotlin_builtins kotlin/internal/internal.kotlin_builtins kotlin/kotlin.kotlin_builtins kotlin/ranges/ranges.kotlin_builtins kotlin/reflect/reflect.kotlin_builtins META-INF/*.version META-INF/com/android/build/gradle/app-metadata.properties META-INF/services/t0.d META-INF/services/v0.e res/*.png res/*.xml res/color/*.xml res/color-v23/abc_* resources.arsc
mv app-unsigned.apk ../.
cd ../
echo "realigning..."
zipalign -v -p 4 app-unsigned.apk app-aligned.apk
echo "signing apk..."
apksigner sign --ks /home/h0wdy/AndroidStudioProjects/KEYSTORE/bggp.jks --v1-signing-enabled false --v2-signing-enabled true --in app-aligned.apk --out 4.apk
echo "done"
