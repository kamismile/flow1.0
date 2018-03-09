可以继承基础POM
```
<parent>
	<groupId>com.shziyuan.flow</groupId>
	<artifactId>maven-base</artifactId>
	<version>1.0.0</version>
	<relativePath/>
</parent>
```

基础pom加载内容:
1. spring-boot 1.5.9
2. spring-cloud Edgware.RELEASE
3. Sub_Common 0.0.1
	spring加载内容:
	1. eureka客户端
	2. config客户端
	3. web
	4. test
	5. package打包插件
扩展内容:
打包时,自动读取2个附加配置文件
dockerfile / Shanghai 用于docker build镜像的配置,默认复制两个配置文件及项目生成jar到 项目/docker/
继承基础pom时,在properties中添加一条
<docker.common.path>/Users/yaohu/Documents/WorkspaceZY/Flow-MavenBase/docker</docker.common.path>
修改为Flow-MavenBase项目的路径(项目下级docker目录)

例:info-relation-service的pom
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>info-relation-service</artifactId>
	<packaging>jar</packaging>

	<name>Flow-InfoRelationService</name>
	<description>FlowPlatform Info relation service</description>

	<parent>
		<groupId>com.shziyuan.flow</groupId>
		<artifactId>maven-base</artifactId>
		<version>1.0.0</version>
		<relativePath/>
	</parent>

	<properties>
		<server.port>8773</server.port>	<!-- 用于给docker配置文件替换程序端口号 -->
		<skipTests>true</skipTests>	<!-- package时跳过test -->
		<docker.common.path>Flow-MavenBase项目路径/docker</docker.common.path>
	</properties>

	<dependencies>
		<!-- 加载spring data redis -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>
		
		<!-- 加载spring jdbc -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>
		
		<!-- 加载mysql -->
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>

		<!-- spring devtools -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<!-- 启用自动复制docker配置文件 -->
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-antrun-plugin</artifactId>
			</plugin>
		</plugins>
	</build>


</project>

```