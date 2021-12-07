# TODO
# Bundletool
# Apk analyzer

# Restart ADB if issues present
alias radb='adb kill-server && adb start-server'

# bundletool
alias bt='bundletool'

# mount the android file image
function mountAndroid { hdiutil attach ~/android.dmg.sparseimage -mountpoint /Volumes/android; }

# unmount the android file image
function umountAndroid() { hdiutil detach /Volumes/android; }