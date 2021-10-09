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

group = "cn.hotdb"
version = "1.0"

dependencies {
	implementation(kotlin("stdlib-jdk8"))
	testImplementation(kotlin("test-junit"))
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
