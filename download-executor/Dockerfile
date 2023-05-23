FROM ubuntu:22.04

RUN apt-get update && apt-get install -y curl gnupg2

RUN rm /bin/sh && ln -s /bin/bash /bin/sh
RUN apt-get -qq -y install curl wget unzip zip

RUN curl -s "https://get.sdkman.io" | bash
RUN source "$HOME/.sdkman/bin/sdkman-init.sh"
RUN apt-get install -y openjdk-11-jdk


SHELL ["/bin/bash", "-c", "source $HOME/.bashrc"]

RUN sdk install kotlin

COPY build.gradle .
COPY settings.gradle .

COPY src ./src

RUN /opt/gradle-7.2/bin/gradle build

COPY build/libs/download-executor-11.jar app.jar

ENV PORT=8088

EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "app.jar"]