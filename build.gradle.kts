plugins {
	id("org.jetbrains.kotlin.jvm") version "1.6.0"
}

repositories {
	maven("https://maven.aliyun.com/nexus/content/groups/public")
	mavenCentral()
}

group = "cn.hotdb"
version = "1.0"

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-stdlib")
	testImplementation("org.jetbrains.kotlin:test-junit")
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(11))
	}
}

kotlin {
	jvmToolchain {
		this as JavaToolchainSpec
		languageVersion.set(JavaLanguageVersion.of(11))
	}
}

val projectCompiler = javaToolchains.compilerFor {
	languageVersion.set(JavaLanguageVersion.of(11))
}

tasks {
	compileJava {
		javaCompiler.set(projectCompiler)
	}
	compileTestJava {
		javaCompiler.set(projectCompiler)
	}
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
