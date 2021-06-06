echo "> Installing GraalVM"
brew install --cask graalvm/tap/graalvm-ce-java11
brew install --cask graalvm/tap/graalvm-ce-lts-java11

# TODO: Dynamic versioning
# Needed due to https://github.com/graalvm/homebrew-tap#macos-catalina-specifics
xattr -r -d com.apple.quarantine /Library/Java/JavaVirtualMachines/graalvm-ce-java11-21.1.0
xattr -r -d com.apple.quarantine /Library/Java/JavaVirtualMachines/graalvm-ce-lts-java11-20.3.1