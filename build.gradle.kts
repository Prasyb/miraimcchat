plugins {
    val kotlinVersion = "1.4.30"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("net.mamoe.mirai-console") version "2.6.4"
}

group = "net.prasyb"
version = "0.0.1"

repositories {
    maven{ url =uri("https://maven.aliyun.com/nexus/content/groups/public/")}
    jcenter()
    mavenCentral()
    mavenLocal()
}
dependencies{
    implementation("com.alibaba:fastjson:1.2.73")
    implementation("io.netty:netty-all:4.1.65.Final")
    implementation("com.google.guava:guava:30.1.1-jre")
}