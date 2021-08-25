echo "* Generating JAR"
lein do clean, uberjar
echo "* Generating native image"
native-image --report-unsupported-elements-at-runtime \
             --initialize-at-build-time \
             -H:ReflectionConfigurationFiles="$PWD/reflect-config.json" \
             -H:+AllowIncompleteClasspath \
             -jar ./target/uberjar/see*-standalone.jar \
             -H:Name=./target/see
echo "[!] Native image generated at target/see"
