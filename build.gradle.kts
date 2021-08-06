plugins {
	kotlin("jvm") version "1.5.0"
}

buildscript {
	repositories {
		maven("https://maven.aliyun.com/nexus/content/groups/public")
		mavenCentral()
	}
}

repositories {
	maven("https://maven.aliyun.com/nexus/content/groups/public")
	mavenCentral()
}

group = "com.windea"
version = "1.0"

dependencies {
	//标准库
	implementation(kotlin("stdlib-jdk8"))
	testImplementation(kotlin("test-junit"))
	//序列化
	//implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.2")
	//implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.11.2")
}

tasks {
	compileKotlin {
		kotlinOptions {
			jvmTarget = "1.8"
		}
	}
	compileTestKotlin {
		kotlinOptions {
			jvmTarget = "1.8"
		}
	}
	test {
		useJUnitPlatform()
	}
}
