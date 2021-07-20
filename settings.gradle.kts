include("base")

include("json")

include("logger:jdk")
include("logger:slf4j")
include("logger:log4j")


include("net:java-net")
include("net:http-server")
include("net:okhttp")
include("net:retrofit")
include("net:netty")

include("database:jdbc")
include("database:hibernate:basic")
include("database:hibernate:jpa")

include("nosql:mongodb")

include("utils:lombok")
include("utils:apache-common")
include("utils:guava")
include("utils:swagger")

include("spring:property-config")

include("cloud:apollo-test")

include("io:bio")
include("io:nio")
include("io:aio")