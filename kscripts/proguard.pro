-verbose

-dontwarn

# Preserve all public applications.

-keepclasseswithmembers public class * {
    public static void main(java.lang.String[]);
}
