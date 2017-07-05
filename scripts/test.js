// Script to pause a job
print("\$ARG=${$ARG}");
print("arguments=" + arguments);
CP = Java.type("java.lang.System").getProperty("java.class.path");
print("classpath=${CP}");
for each (x in CP.split(":")) {
    print(x);
}